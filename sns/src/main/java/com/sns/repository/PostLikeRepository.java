package com.sns.repository;

import com.sns.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.CompletableFuture;

public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    PostLike findByPostPostNoAndMemberMemberId(Long postNo, Long memberId);
    boolean existsByPostPostNoAndMemberMemberId(Long postNo, Long memberId);
    CompletableFuture<Long> countByPostPostNo(Long postNo);
}
