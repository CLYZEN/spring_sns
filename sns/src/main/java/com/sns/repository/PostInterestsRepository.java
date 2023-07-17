package com.sns.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sns.entity.PostInterests;

public interface PostInterestsRepository extends JpaRepository<PostInterests, Long>{
	
}
