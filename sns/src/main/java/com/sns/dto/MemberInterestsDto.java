package com.sns.dto;

import com.sns.entity.Member;
import com.sns.entity.MemberInterests;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MemberInterestsDto {

    private Member member;

    private boolean develop;

    private boolean animal;

    private boolean life;

    private boolean food;

    private boolean travel;

}
