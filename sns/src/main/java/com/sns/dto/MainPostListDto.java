package com.sns.dto;

import com.sns.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
public class MainPostListDto {

    private Long postNo;

    private String nickname;

    private String subject;

    private String content;

    private String createdBy;

    private LocalDateTime regTime;

    ModelMapper modelMapper = new ModelMapper();


}
