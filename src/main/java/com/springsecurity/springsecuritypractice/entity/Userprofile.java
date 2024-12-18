package com.springsecurity.springsecuritypractice.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@DynamicUpdate
@Entity
@Table(name = "userprofile")
@SequenceGenerator(									//	시퀸스 작성 방식
		name			= "USERPROFILE_SEQ_GENERATOR",	//	{테이블 이름}_SEQ_GENERATOR
		sequenceName	= "USERPROFILE_ID_SEQ",			//	{테이블 PK}_SEQ
		initialValue	= 1,						//	초기값
		allocationSize	= 1							//	증가값
		)

public class Userprofile {
	@Id
	@GeneratedValue(
			strategy= GenerationType.SEQUENCE,
			generator= "USERPROFILE_SEQ_GENERATOR"
			)
	@Column(name = "userprofile_id", nullable = false)
	private Long userprofileId;

	@Column(name = "userprofile_nickname", length = 255)
	private String userprofileNickname;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userinfo_id", nullable = false, unique = true)
	private Userinfo userinfo;
}
