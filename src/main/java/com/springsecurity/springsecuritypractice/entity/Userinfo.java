package com.springsecurity.springsecuritypractice.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Entity
@SequenceGenerator(name = "userinfo_SEQ", allocationSize = 1)
public class Userinfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userinfo_SEQ")
    private Long userinfoId;

    @Column(unique = true, length = 255, nullable = false)
    private String userinfoEmail;

    private String userinfoPassword; 

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userinfo")
    private List<UserRole> userRoles = new ArrayList<>();
}
