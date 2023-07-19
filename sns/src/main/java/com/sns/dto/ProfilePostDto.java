package com.sns.dto;

import com.sns.entity.Member;
import com.sns.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProfilePostDto {

    private Long postNo;
    private Member member;
    private String subject;
    private String content;
    private LocalDateTime regDate;
    private List<PostImgDto> postImgDtoList = new ArrayList<>();

    public void addPostImgDto(PostImgDto postImgDto) {
        postImgDtoList.add(postImgDto);
    }

    public ProfilePostDto createProfilePostDto(Post post) {
        this.postNo = post.getPostNo();
        this.subject = post.getSubject();
        this.content = post.getContent();
        this.regDate = post.getRegTime();

        return this;
    }
}
