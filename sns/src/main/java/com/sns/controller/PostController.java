package com.sns.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.sns.dto.*;
import com.sns.entity.*;
import com.sns.repository.MemberInterestsRepository;
import com.sns.repository.MemberRepository;
import com.sns.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final MemberService memberService;
	private final PostImageService postImageService;
	private final CommentService commentService;
	private final LikeService likeService;
	private final MemberInterestsRepository memberInterestsRepository;
	private final MemberRepository memberRepository;

	@GetMapping(value = "/post/new")
	public String newPost(Model model) {
		
		model.addAttribute("postFormDto", new PostFormDto());
		
		return "html/writePost";
	}
	
	@PostMapping(value = "/post/new")
	public String newPost(@Valid PostFormDto postFormDto, BindingResult bindingResult, Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFiles, PostInterests postInterest, Principal principal) {
		if(bindingResult.hasErrors()) {
			return "html/writePost";
		}
		
		//상품등록전에 첫번째 이미지가 있는지 없는지 검사(첫번째 이미지는 필수 입력값)
		if(itemImgFiles.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "게시글 첫번째 이미지는 필수입니다.");
			return "html/writePost";
		}
		
		try {
			postService.savePost(postFormDto, itemImgFiles, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "게시글 등록 중 에러가 발생했습니다.");
			return "html/writePost";
		}
		
		return "redirect:/main";
	}
	@GetMapping(value = {"/main", "/main/{page}"})
	public String mainPost(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {

		if (memberInterestsRepository.findByMember(memberRepository.findByEmail(principal.getName())) == null) {
			model.addAttribute("memberInterestsDto",new MemberInterestsDto());
			return "member/selectMemberInterests";
		}

		Member member = memberService.findByEmail(principal.getName());
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10);

		Page<Post> posts =  postService.findPostsByMemberInterests(member,pageable);
		//Page<PostFormDto> posts =  postService.findPostsByMemberInterests(member,pageable);


		List<Boolean> likeStatusList = new ArrayList<>();
		List<CompletableFuture<Long>> likeCountList = new ArrayList<>();
		for (Post post : posts) {
			boolean liked = likeService.checkLike(post.getPostNo(),member.getMemberId());
			CompletableFuture<Long> likeCount = likeService.countLike(post.getPostNo());
			likeStatusList.add(liked);
			likeCountList.add(likeCount);
		}

		List<Long> resolvedLikeCountList = likeCountList.stream()
				.map(CompletableFuture::join)
				.collect(Collectors.toList());

		model.addAttribute("likeCountList", resolvedLikeCountList);
		model.addAttribute("likeStatusList", likeStatusList);
		model.addAttribute("member",member);
		model.addAttribute("posts",posts);
		model.addAttribute("maxPage",5);

		return "html/main";
	}
	@GetMapping(value = {"/post/article", "/post/article/{postNo}"})
	public String articlePost(@PathVariable Long postNo, Model model,Principal principal,
							  @RequestParam(value = "page", defaultValue = "0") int page) {

		Post post = postService.articlePost(postNo);
		List<PostImgDto> postImgDtoList = postService.articelPostImage(postNo);
		Member member = memberService.findByEmail(principal.getName());

		boolean liked = likeService.checkLike(post.getPostNo(),member.getMemberId());

		/* 댓글 페이징 관련 */
		Page<Comment> comments = commentService.getPostComments(page,post);

		model.addAttribute("liked",liked);
		model.addAttribute("comments",comments);
		model.addAttribute("member",member);
		model.addAttribute("post",post);
		model.addAttribute("postImgDtos", postImgDtoList);
		model.addAttribute("commentFormDto", new CommentFormDto());
		model.addAttribute("reportPostDto", new ReportPostDto());

		return "post/article";
	}


	@PostMapping(value = "/addComment/{postNo}")
	public String addComment(Principal principal, @PathVariable Long postNo, @Valid CommentFormDto commentFormDto){
		commentFormDto.setPost(postService.findId(postNo));
		commentFormDto.setMember(memberService.findByEmail(principal.getName()));
		commentService.saveComment(commentFormDto);

		return "redirect:/post/article/" + postNo;
	}

	@PostMapping(value = "reportPost/{postNo}")
	public String reportPost(@Valid ReportPostDto reportPostDto, BindingResult bindingResult,Model model,@PathVariable Long postNo, Principal principal) {
		postService.reportPost(principal.getName(),postNo,reportPostDto);

		return "redirect:/post/article/" + postNo;
	}

	@DeleteMapping("/delete/{postNo}")
	public @ResponseBody ResponseEntity deletePost(@PathVariable("postNo") Long postNo, Principal principal) {

		if(!postService.validatePost(postNo,principal.getName())) {
			return new ResponseEntity<String>("게시글 삭제 권한이 없습니다,", HttpStatus.FORBIDDEN);
		}

		postService.deletePost(postNo);

		return new ResponseEntity<Long>(postNo, HttpStatus.OK);
	}

	@GetMapping(value = "/postModify/{postNo}")
	public String modifyPost(@PathVariable("postNo") Long postNo, Principal principal,Model model) {

		if(!postService.validatePost(postNo,principal.getName())) {
			model.addAttribute("errorMessage", "게시글 수정 권한이 없습니다!");
			return "redirect:/main";
		}
		PostFormDto postFormDto = postService.getPostDtl(postNo);

		model.addAttribute("postFormDto", postFormDto);
		return "post/modifyPost";
	}

}
