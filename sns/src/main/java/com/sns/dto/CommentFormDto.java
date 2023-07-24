package com.sns.dto;

import com.sns.entity.Comment;
import com.sns.entity.Member;
import com.sns.entity.Post;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class CommentFormDto {
    private Member member;
    private Post post;
    private String content;

    private static ModelMapper modelMapper = new ModelMapper();

    public static  CommentFormDto of(Comment comment) {
        return modelMapper.map(comment, CommentFormDto.class);
    }

    public Comment createComment(CommentFormDto commentFormDto) {
        Comment comment = new Comment();
        comment.setPost(commentFormDto.getPost());
        comment.setMember(commentFormDto.getMember());
        comment.setContent(commentFormDto.getContent());

        return comment;
    }
}
