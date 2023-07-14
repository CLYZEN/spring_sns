package com.sns.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sns.dto.PostFormDto;
import com.sns.entity.Post;
import com.sns.entity.PostImage;
import com.sns.entity.PostInterests;
import com.sns.repository.PostImageRepository;
import com.sns.repository.PostInterestsRepository;
import com.sns.repository.PostRepository;

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
	
	public Long savePost(PostFormDto postFormDto, List<MultipartFile> postImgFiles) throws Exception{
		// 게시글등록
		Post post = postFormDto.ceratePost();
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
	
}
