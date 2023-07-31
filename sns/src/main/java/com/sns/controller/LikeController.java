package com.sns.controller;

import com.sns.dto.LikeRequestDto;
import com.sns.service.LikeService;
import com.sns.service.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeServiceImpl likeService;


    // 게시글에 좋아요 추가
    @PostMapping("/addLike")
    public void addLike(@RequestBody LikeRequestDto likeRequest) {
        likeService.addLike(likeRequest.getPostNo(), likeRequest.getMemberId());
    }

    // 게시글에 좋아요 취소
    @DeleteMapping("/deleteLike")
    public void deleteLike(@RequestBody LikeRequestDto likeRequest) {
        likeService.deleteLike(likeRequest.getPostNo(), likeRequest.getMemberId());
    }

}
