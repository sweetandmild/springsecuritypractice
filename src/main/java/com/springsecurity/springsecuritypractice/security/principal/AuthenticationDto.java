package com.springsecurity.springsecuritypractice.security.principal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class 


AuthenticationDto {
    private Long id;

    private String email;

    private String password;

    @Builder.Default
    private String provider = "my web site";

    @Builder.Default
    private boolean isSocial = false; 

    @Builder.Default
    private List<String> roleNames = new ArrayList<>();

    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();
}
