package com.sns.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "report_posts")
public class ReportPost extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "report_post_no")
	private Long reportPostNo; // 신고게시물식별자
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_no")
	private Post post; // 게시물번호
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; // 신고자
	
	@Column(nullable = false)
	private String reportReason; // 신고내용

	public ReportPost createReportPost(Post post, String reportReason, Member member) {
		this.post = post;
		this.reportReason = reportReason;
		this.member = member;

		return this;
	}
}
