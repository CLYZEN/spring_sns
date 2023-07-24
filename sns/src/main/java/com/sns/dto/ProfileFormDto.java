package com.sns.dto;

import com.sns.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileFormDto {

    @NotBlank(message = "닉네임은 비어있을 수 없습니다.")
    private String nickname;

    private String content;
    private String profileImgUrl;


}
