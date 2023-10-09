package com.sns.service;

import com.sns.dto.CommentFormDto;
import com.sns.entity.Comment;
import com.sns.entity.Post;
import com.sns.repository.CommentRepository;
import com.sns.repository.MemberRepository;
import com.sns.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void saveComment(CommentFormDto commentFormDto) {
        commentRepository.save(commentFormDto.createComment(commentFormDto));
    }

    public Page<Comment> getPostComments(int page,Post post) {


        Pageable pageable = PageRequest.of(page,5);

       return commentRepository.findByPostOrderByCommentNoDesc(post,pageable);
    }

}
