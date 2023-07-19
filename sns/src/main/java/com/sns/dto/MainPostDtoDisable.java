package com.sns.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MainPostDtoDisable {
    // m.nickname, p.subject, p.content, p.reg_time, p.post_no, p.created_by
    private Long postNo;

    private String nickname;

    private String subject;

    private String content;

    private String createdBy;

    private LocalDateTime regTime;

    private List<PostImgDto> postImgDtos = new ArrayList<>();

    private List<Long> postImgIds = new ArrayList<>();


}
