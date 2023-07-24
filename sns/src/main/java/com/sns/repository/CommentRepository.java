package com.sns.repository;

import com.sns.entity.Comment;
import com.sns.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostOrderByCommentNoDesc(Post post, Pageable pageable);
}
