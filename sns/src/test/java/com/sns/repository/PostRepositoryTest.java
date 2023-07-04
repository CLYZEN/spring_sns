package com.sns.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.sns.entity.Member;
import com.sns.entity.Post;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class PostRepositoryTest {
	
	@Autowired
	PostRepository postRepository;
	
	@Disabled
	@Test
	@DisplayName("데이터집어넣기")
	public void insertData() {
		String data = "";
		
		Member member = new Member();
		
		member.setMemberId(1L);
		
		for (int i=0; i<20000; i++) {
			data += "+";
		}
		
		Post post = new Post();
		post.setMember(member);
		post.setContent(data);
		post.setPostNo(1L);
		post.setSubject("123123");
		
		postRepository.save(post);
	}
}
