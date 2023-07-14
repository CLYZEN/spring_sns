package com.sns.dto;

import org.modelmapper.ModelMapper;

import com.sns.entity.PostImage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostImgDto {
	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl; 
	
	private String repimgYn; 
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static PostImgDto of(PostImage postImage) {
		return modelMapper.map(postImage, PostImgDto.class);
	}
	
}
