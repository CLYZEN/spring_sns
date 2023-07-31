package com.sns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ReportPostDto {

    private Long reportPostNo;

    @NotBlank(message = "신고 사유를 입력해주세요!")
    private String reportReason;

    ModelMapper modelMapper = new ModelMapper();


}
