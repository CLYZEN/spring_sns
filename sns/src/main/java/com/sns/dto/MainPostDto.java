package com.sns.dto;

import java.time.LocalDateTime;
import java.util.List;

public interface MainPostDto {

    Long getPostNo();
    String getNickname();
    String getSubject();
    String getContent();
    String getCreatedBy();
    LocalDateTime getRegTime();
    Long getImageNo();
    String getImageName();
    String getImageUrl();
    String getOriImageName();
    String getRepImgYn();

}
