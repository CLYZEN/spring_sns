package com.sns.service;

import com.sns.entity.Member;
import com.sns.entity.PostLike;
import com.sns.repository.MemberRepository;
import com.sns.repository.PostLikeRepository;
import com.sns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService{
    private final PostLikeRepository postLikeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;



    @Override
    public void addLike(Long postNo, Long memberId) {
        PostLike postLike = new PostLike();
        postLike.setPost(postRepository.findById(postNo).orElseThrow());
        postLike.setMember(memberRepository.findById(memberId).orElseThrow());
        postLikeRepository.save(postLike);
    }

    @Override
    public void deleteLike(Long postNo, Long memberId) {
       PostLike postLike = postLikeRepository.findByPostPostNoAndMemberMemberId(postNo, memberId);
       postLikeRepository.delete(postLike);
    }

    @Override
    public boolean checkLike(Long postNo, Long memberId) {
        return postLikeRepository.existsByPostPostNoAndMemberMemberId(postNo, memberId);
    }

    @Override
    @Async
    public CompletableFuture<Long> countLike(Long postNo) {
        return postLikeRepository.countByPostPostNo(postNo);
    }


}
