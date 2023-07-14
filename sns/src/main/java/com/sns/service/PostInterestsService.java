package com.sns.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sns.entity.PostInterests;
import com.sns.repository.PostInterestsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostInterestsService {
	private final PostInterestsRepository postInterestsRepository;
	
	public void savePostInterests(PostInterests postInterests) {
		postInterestsRepository.save(postInterests);
	}
}
