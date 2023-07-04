package com.sns.entity;

import com.sns.converter.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "post_interests")
public class PostInterests {
	
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_no")
	private Post post;
	
	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private boolean develop; // 개발
	
	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private boolean travel; // 여행
	
	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private boolean animal; // 동물
	
	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private boolean life; // 일상
	
	@Convert(converter = BooleanToYNConverter.class)
	@Column(nullable = false)
	private boolean food; // 음식
}

