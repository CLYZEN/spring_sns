package com.sns.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.sns.entity.PostImage;
import com.sns.repository.PostImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostImageService {
	private String itemImgLocation = "C:/shop/item";
	private final PostImageRepository postImageRepository;
	private final FileService fileService;
	
	public void savePostImg(PostImage postImage, MultipartFile postImgFile) throws Exception {
		String oriImgName = postImgFile.getOriginalFilename(); //파일이름 -> 이미지1.jpg
		String imgName = "";
		String imgUrl = "";

		if(!StringUtils.isEmpty(oriImgName)) {
			//oriImgName이 빈문자열이 아니라면 이미지 파일 업로드
			imgName = fileService.uploadFile(itemImgLocation, 
					oriImgName, postImgFile.getBytes());
			imgUrl = "/images/item/" + imgName;
			
		}
		
		postImage.updatePotsImg(oriImgName, imgName, imgUrl);
		postImageRepository.save(postImage);
	}
}
