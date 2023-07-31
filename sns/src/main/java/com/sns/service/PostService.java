package com.sns.service;

import java.util.ArrayList;
import java.util.List;

import com.sns.dto.*;
import com.sns.entity.*;
import com.sns.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
	private final PostRepository postRepository;
	private final PostImageRepository postImageRepository;
	private final PostInterestsRepository postInterestsRepository;
	private final PostImageService postImageService;
	private final PostInterestsService postInterestsService;
	private final MemberRepository memberRepository;
	private final MemberInterestsRepository memberInterestsRepository;
	private final ReportPostsRepository reportPostsRepository;

	public Long savePost(PostFormDto postFormDto, List<MultipartFile> postImgFiles,String email) throws Exception{
		// 게시글등록
		Post post = postFormDto.ceratePost();
		post.setMember(memberRepository.findByEmail(email));
		postRepository.save(post);
		
		PostInterests postInterests = postFormDto.getPostInterests();
		postInterests.setPost(post);
		for(int i=0; i<postImgFiles.size(); i++) {
			
			PostImage postImage = new PostImage();
			postImage.setPost(post);
			
			
			if(i == 0) {
				postImage.setRepImgYN(true);
			} else {
				postImage.setRepImgYN(false);
			}
			
			postImageService.savePostImg(postImage, postImgFiles.get(i));
		}
		
		postInterestsService.savePostInterests(postFormDto.getPostInterests());
		
		return post.getPostNo();
	}

	@Transactional(readOnly = true)
	public Page<Post> findPostsByMemberInterests(Member member,Pageable pageable) {
		MemberInterests memberInterests = memberInterestsRepository.findByMember(member);
		boolean develop = memberInterests.isDevelop();
		boolean travel = memberInterests.isTravel();
		boolean animal = memberInterests.isAnimal();
		boolean life = memberInterests.isLife();
		boolean food = memberInterests.isFood();
		//Page<MainPostDto> postDtos = postRepository.findPostsByInterests(member.getMemberId(), develop, travel, animal, life, food, pageable);
		Page<Post> postDtos = postRepository.findAll(pageable);



		return postDtos;
	}

	public List<ProfilePostDto> findMyPost(String email) {
		Member member = memberRepository.findByEmail(email);

		List<Post> postList = postRepository.findByMember(member);

		List<ProfilePostDto> profilePostDtoList = new ArrayList<>();

		for(Post post : postList) {
			ProfilePostDto profilePostDto = post.switchProfilePostDto(post);
			List<PostImgDto> postImageList = postImageRepository.findByPostPostNo(post.getPostNo());
			profilePostDto.setMember(member);
			for(PostImgDto postImgDto : postImageList) {

				profilePostDto.addPostImgDto(postImgDto);

			}
			profilePostDtoList.add(profilePostDto);
		}

		return profilePostDtoList;
	}

	public Post articlePost(Long postNo) {
		Post post = postRepository.findByPostNo(postNo);

		return post;
	}

	public List<PostImgDto> articelPostImage(Long postNo) {
		List<PostImgDto> postImgDtoList = postImageRepository.findByPostPostNo(postNo);
		return postImgDtoList;
	}

	public Post findId(Long postId) {

		Post post = postRepository.findById(postId).orElseThrow();

		return post;
	}

	public void reportPost(String email, Long postNo, ReportPostDto reportPostDto) {
		Member member = memberRepository.findByEmail(email); // 신고한 사용자
		Post post = postRepository.findById(postNo).orElseThrow(); // 신고할 게시물
		String reportReason = reportPostDto.getReportReason(); // 신고내용

		ReportPost reportPost = new ReportPost();

		reportPostsRepository.save(reportPost.createReportPost(post,reportReason,member));
	}

	@Transactional(readOnly = true)
	public boolean validatePost(Long postNo, String email) {
		Member member = memberRepository.findByEmail(email);
		Post post = postRepository.findById(postNo).orElseThrow(EntityNotFoundException::new);

		Member savedMember = post.getMember();

		if(!StringUtils.equals(member.getEmail(),savedMember.getEmail())) {
			return false;
		}
		return true;
	}

	public void deletePost(Long postNo) {
		Post post = postRepository.findById(postNo).orElseThrow(EntityNotFoundException::new);

		postRepository.delete(post);
	}

	@Transactional(readOnly = true)
	public PostFormDto getPostDtl(Long postNo) {
		List<PostImgDto> postImgDtoList = postImageRepository.findByPostPostNo(postNo);

		Post post = postRepository.findById(postNo).orElseThrow(EntityNotFoundException::new);

		PostFormDto postFormDto = PostFormDto.of(post);

		postFormDto.setPostImgDtos(postImgDtoList);

		return postFormDto;
	}
}
