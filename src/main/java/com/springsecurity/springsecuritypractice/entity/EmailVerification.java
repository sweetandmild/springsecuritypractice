package com.springsecurity.springsecuritypractice.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Entity
@Table(name = "email_verification")
@SequenceGenerator(									//	시퀸스 작성 방식
		name			= "EMAIL_VERIFICATION_SEQ_GENERATOR",	//	{테이블 이름}_SEQ_GENERATOR
		sequenceName	= "EMAIL_VERIFICATION_ID_SEQ",			//	{테이블 PK}_SEQ
		initialValue	= 1,						//	초기값
		allocationSize	= 1							//	증가값
		)
public class EmailVerification {
	@Id
	@GeneratedValue(
			strategy= GenerationType.SEQUENCE,
			generator= "EMAIL_VERIFICATION_SEQ_GENERATOR"
			)
	@Column(name = "email_verification_id")
	private Long emailVerificationId;
	
	@Column(name = "email_verification_email", length = 255, nullable = false)
	private String emailVerificationEmail;
	
	@Column(name = "email_verification_code", length = 255, nullable = false)
	private String emailVerificationCode;
	
	@Builder.Default
	@Column(name = "email_verification_is_verified", precision = 1, scale = 0)
	private Integer emailVerificationIsVerified = 0;
	
}
