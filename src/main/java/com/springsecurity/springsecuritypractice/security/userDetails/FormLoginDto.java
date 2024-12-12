package com.springsecurity.springsecuritypractice.security.userDetails;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class FormLoginDto {
    private String email;

    private String password;

    @Builder.Default
    private boolean isSocial = false; 

    @Builder.Default
    private List<String> roleNames = new ArrayList<>();
}
