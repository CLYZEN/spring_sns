package com.sns.repository;

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
	
	
	@Test
	@DisplayName("취미집어넣기")
	public void inputData() {
		Member member = new Member();
		member.setEmail("123123123@123.123");
		member.setMemberId(1L);
		member.setPassword("12341234");
		member.setNickname("12314");
		member.setRole(Role.ADMIN);
		MemberInterests memberInterests = new MemberInterests();
		
		memberInterests.setMember(member);
		memberInterests.setAnimal(false);
		memberInterests.setDevelop(true);
		memberInterests.setFood(false);
		memberInterests.setLife(true);
		memberInterests.setTravel(false);

	}
}
