package com.sns.entity;

import com.sns.dto.ProfilePostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "posts")
public class Post extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long postNo; // 게시글번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; // 작성자
	
	@Column(nullable = false)
	private String subject; // 제목
	
	@Column(nullable = false, columnDefinition = "longtext")
	@Lob
	private String content; // 내용

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PostImage> postImageList = new ArrayList<>();

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch =  FetchType.LAZY, orphanRemoval = true)
	private List<ReportPost> reportPostList = new ArrayList<>();

	@OneToOne(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private PostInterests postInterests;

	public void addPostImage(PostImage postImage) {
		this.postImageList.add(postImage);
		postImage.setPost(this);
	}

	public static Post createPost(Member member, List<PostImage> postImageList) {
		Post post = new Post();
		post.setMember(member);

		for(PostImage postImage : postImageList) {
			post.addPostImage(postImage);
		}

		return post;
	}

	public ProfilePostDto switchProfilePostDto(Post post) {
		ProfilePostDto profilePostDto = new ProfilePostDto();
		profilePostDto.setPostNo(post.getPostNo());
		profilePostDto.setSubject(post.getSubject());
		profilePostDto.setContent(post.getContent());
		profilePostDto.setRegDate(post.getRegTime());

		return profilePostDto;
	}
}
