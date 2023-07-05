package com.sns.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.sns.constant.Role;
import com.sns.entity.Member;
import com.sns.entity.MemberInterests;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class MemberInterestsTest {

	@Autowired
	MemberInterestsRepository memberInterestsRepository;

	@Autowired
	MemberRepository memberRepository;
	
	@Disabled
	@Test
	@DisplayName("취미집어넣기")
	public void inputData() {
		Member member = new Member();
		
		member.setEmail("jimuny03@gmail.com");
		member.setNickname("승철");
		member.setPassword("12341234");
		member.setRole(Role.ADMIN);
		
		MemberInterests memberInterests = new MemberInterests();
		
		memberInterests.setMember(member);
		memberInterests.setAnimal(false);
		memberInterests.setDevelop(false);
		memberInterests.setFood(true);
		memberInterests.setLife(true);
		memberInterests.setTravel(true);
		
		memberRepository.save(member);
		
		memberInterestsRepository.save(memberInterests);
	}
		
}
