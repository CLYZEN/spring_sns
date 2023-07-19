package com.sns.service;

import java.util.ArrayList;
import java.util.List;

import com.sns.dto.MainPostDto;
import com.sns.dto.PostImgDto;
import com.sns.dto.ProfilePostDto;
import com.sns.entity.*;
import com.sns.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.dto.PostFormDto;

import lombok.RequiredArgsConstructor;

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
	public Page<MainPostDto> findPostsByMemberInterests(Member member,Pageable pageable) {
		MemberInterests memberInterests = memberInterestsRepository.findByMember(member);
		boolean develop = memberInterests.isDevelop();
		boolean travel = memberInterests.isTravel();
		boolean animal = memberInterests.isAnimal();
		boolean life = memberInterests.isLife();
		boolean food = memberInterests.isFood();
		Page<MainPostDto> postDtos = postRepository.findPostsByInterests(member.getMemberId(), develop, travel, animal, life, food, pageable);


		return postDtos;
	}

	public List<ProfilePostDto> findMyPost(String email) {
		Member member = memberRepository.findByEmail(email);

		List<Post> postList = postRepository.findPostByMember(member);

		List<ProfilePostDto> profilePostDtoList = new ArrayList<>();

		for(Post post : postList) {
			ProfilePostDto profilePostDto = post.switchProfilePostDto(post);
			List<PostImgDto> postImageList = postImageRepository.findByPostPostNo(post.getPostNo());

			for(PostImgDto postImgDto : postImageList) {
				profilePostDto.addPostImgDto(postImgDto);
				profilePostDtoList.add(profilePostDto);
			}
		}

		return profilePostDtoList;
	}
}
