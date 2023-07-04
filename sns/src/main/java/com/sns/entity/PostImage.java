package com.sns.entity;

import com.sns.converter.BooleanToYNConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "post_image")
public class PostImage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long image_no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_no")
	private Post post;
	
	@Column(nullable = false)
	private String imageName;
	
	@Column(nullable = false)
	private String oriImageName;
	
	@Column(nullable = false)
	private String imageUrl;
	
	@Column(name = "rep_img_yn", nullable = false)
	@Convert(converter = BooleanToYNConverter.class)
	private boolean repImgYN;
}
