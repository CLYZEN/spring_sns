package com.sns.dto;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.sns.entity.Post;

import com.sns.entity.PostInterests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFormDto {
	
	
	private Long id;
	
	@NotBlank(message = "제목은 비어있을 수 없습니다.")
	private String postSubject;
	
	@NotBlank(message = "내용은 비어있을 수 없습니다.")
	private String postContent;
	
	private PostInterests postInterests;
	
	// 이미지 정보
	private List<PostImgDto> postImgDtos = new ArrayList<>();
	
	// 이미지 아이디들
	private List<Long> postImgIds = new ArrayList<>();
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	// dto -> entity
	public Post ceratePost() {
		return modelMapper.map(this, Post.class);
	}
	
	// entity -> dto
	public static PostFormDto of(Post post) {
		return modelMapper.map(post, PostFormDto.class);
	}
}
