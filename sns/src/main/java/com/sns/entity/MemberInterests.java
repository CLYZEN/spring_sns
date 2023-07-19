package com.sns.entity;

import com.sns.converter.BooleanToYNConverter;

import com.sns.dto.MemberInterestsDto;
import jakarta.persistence.*;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long memberInterestsId;
	
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;
	
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

	public static MemberInterests createMemberInterests(MemberInterestsDto memberInterestsDto) {
		MemberInterests memberInterests = new MemberInterests();
		memberInterests.setLife(memberInterestsDto.isLife());
		memberInterests.setFood(memberInterestsDto.isFood());
		memberInterests.setAnimal(memberInterestsDto.isAnimal());
		memberInterests.setTravel(memberInterests.isTravel());
		memberInterests.setDevelop(memberInterests.isDevelop());

		return memberInterests;
	}
}