package com.sns.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.sns.constant.Role;
import com.sns.entity.Member;
import com.sns.entity.Post;
import com.sns.entity.PostInterests;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class PostInterestsTest {
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	PostInterestsRepository interestsRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@DisplayName("게시물관심사 집어넣기")
	public void insertPostInt() {
		Member member = new Member();
		
		member.setEmail("jimuny03@gmail.com");
		member.setNickname("승철");
		member.setPassword("12341234");
		member.setRole(Role.ADMIN);
		
		Post post = new Post();
		
		post.setContent("컨텐츠");
		post.setSubject("제목");
		post.setMember(member);
		
		PostInterests postInterests = new PostInterests();
		
		postInterests.setPost(post);
		postInterests.setAnimal(false);
		postInterests.setDevelop(true);
		postInterests.setFood(true);
		postInterests.setLife(false);
		postInterests.setTravel(true);
		
		memberRepository.save(member);
		postRepository.save(post);
		interestsRepository.save(postInterests);
		
		
	}
	
}
