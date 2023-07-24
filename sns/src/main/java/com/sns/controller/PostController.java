package com.sns.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

import com.sns.dto.CommentFormDto;
import com.sns.dto.MainPostDto;
import com.sns.dto.PostImgDto;
import com.sns.entity.*;
import com.sns.service.CommentService;
import com.sns.service.MemberService;
import com.sns.service.PostImageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.sns.dto.PostFormDto;
import com.sns.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PostController {
	
	private final PostService postService;
	private final MemberService memberService;
	private final PostImageService postImageService;
	private final CommentService commentService;
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
		Member member = memberService.findByEmail(principal.getName());
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10);

		Page<MainPostDto> posts =  postService.findPostsByMemberInterests(member,pageable);
		//Page<PostFormDto> posts =  postService.findPostsByMemberInterests(member,pageable);

		List<MainPostDto> dtos = posts.getContent();
		//List<PostFormDto> dtos = posts.getContent();



		model.addAttribute("posts",posts);
		model.addAttribute("maxPage",5);

		return "html/main";
	}
	@GetMapping(value = {"/post/article", "/post/article/{postNo}"})
	public String articlePost(@PathVariable Long postNo, Model model,Principal principal,
							  @RequestParam(value = "page", defaultValue = "0") int page) {

		Post post = postService.articlePost(postNo);
		List<PostImgDto> postImgDtoList = postService.articelPostImage(postNo);

		/* 댓글 페이징 관련 */
		Page<Comment> comments = commentService.getPostComments(page,post);
		model.addAttribute("comments",comments);

		model.addAttribute("post",post);
		model.addAttribute("postImgDtos", postImgDtoList);
		model.addAttribute("commentFormDto", new CommentFormDto());

		return "post/article";
	}

	@PostMapping(value = "addComment/{postNo}")
	public String addComment(Principal principal, @PathVariable Long postNo, @Valid CommentFormDto commentFormDto){
		commentFormDto.setPost(postService.findId(postNo));
		commentFormDto.setMember(memberService.findByEmail(principal.getName()));
		commentService.saveComment(commentFormDto);

		return "redirect:/post/article/" + postNo;
	}

}
