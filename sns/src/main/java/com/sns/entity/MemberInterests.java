package com.sns.entity;

import com.sns.converter.BooleanToYNConverter;
import com.sns.id.MemberId;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@Table(name = "member_interests")
public class MemberInterests extends BaseTimeEntity {

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member; // 회원식별자
	
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

/*
 * @Converter class BooleanToYNConverter implements AttributeConverter<Boolean,
 * String> {
 * 
 * @Override public String convertToDatabaseColumn(Boolean attribute) {
 * 
 * return (attribute != null && attribute) ? "Y" : "N"; }
 * 
 * @Override public Boolean convertToEntityAttribute(String dbData) {
 * 
 * return "Y".equals(dbData); }
 * 
 * }
 */