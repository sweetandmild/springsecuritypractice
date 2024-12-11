package com.springsecurity.springsecuritypractice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@SequenceGenerator(name = "userinfo_role_seq", allocationSize = 1)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinfo_role_seq")
    private Long userRoleId;

    private String userRoleName;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userinfo_id", nullable = false)
	private Userinfo userinfo;
}
