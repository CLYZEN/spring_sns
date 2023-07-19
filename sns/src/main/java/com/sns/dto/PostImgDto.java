	package com.sns.dto;

	import com.sns.entity.Post;
	import org.modelmapper.ModelMapper;

	import com.sns.entity.PostImage;

	import lombok.Getter;
	import lombok.Setter;

	@Getter
	@Setter
	public class PostImgDto {
		private Long imageNo;

		private Post post;

		private String imageName;

		private String oriImageName;

		private String imageUrl;

		private boolean repImgYn;

		private static ModelMapper modelMapper = new ModelMapper();

		public static PostImgDto of(PostImage postImage) {
			return modelMapper.map(postImage, PostImgDto.class);
		}


	}
