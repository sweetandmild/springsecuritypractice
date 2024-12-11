package com.springsecurity.springsecuritypractice.security.userDetails;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class FormLoginDto {
    private String email;

    private String password;

    @Builder.Default
    private Set<String> roleNames = new HashSet<>();
}
