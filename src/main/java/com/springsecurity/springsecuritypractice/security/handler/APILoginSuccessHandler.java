package com.springsecurity.springsecuritypractice.security.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.nimbusds.jose.shaded.gson.Gson;
import com.springsecurity.springsecuritypractice.security.principal.OAuth2UserDetails;
import com.springsecurity.springsecuritypractice.util.JWTUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        
        System.out.println(">>>>>>>>>>>>> authentication is :" + authentication);
        System.out.println("success login!!!!!!!!!!!!");

        OAuth2UserDetails oAuth2UserDetails = (OAuth2UserDetails)authentication.getPrincipal();
        


        String accessToken = JWTUtil.generateToken(oAuth2UserDetails.getClaims(), 10);
        String refreshToken = JWTUtil.generateToken(oAuth2UserDetails.getClaims(), 60*24);

        // JSON 응답 생성
        Gson gson = new Gson();

        String jsonStr = gson.toJson(Map.of(
            "accessToken", accessToken,
            "refreshToken", refreshToken
        ));

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }

}
