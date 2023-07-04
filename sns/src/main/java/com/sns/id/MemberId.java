package com.sns.id;

import java.io.Serializable;

import groovy.transform.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class MemberId implements Serializable {
	
	 private Long memberId;
	 private Long interestId;
}
