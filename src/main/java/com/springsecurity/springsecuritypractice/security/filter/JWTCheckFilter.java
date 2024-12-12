package com.springsecurity.springsecuritypractice.security.filter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nimbusds.jose.shaded.gson.Gson;
import com.springsecurity.springsecuritypractice.security.userDetails.FormLoginDto;
import com.springsecurity.springsecuritypractice.security.userDetails.PrincipalDetails;
import com.springsecurity.springsecuritypractice.util.JWTUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

    // Preflight요청은 체크하지 않음
    if (request.getMethod().equals("OPTIONS")) {
      return true;
    }

    /****************************************************
      추후에 JWTCheckFilter를 무시할 url 처리를 위한 코드
     ****************************************************/
    String path = request.getRequestURI();

    // log.info("check uri.............." + path);
    // // swagger 경로의 호출은 체크하지 않음
    // if (path.startsWith("/swagger") ||path.startsWith("/v3/api-docs")) {
    //   return true;
    // }
 
 

    // // /context 경로의 호출은 체크하지 않음
    // if (path.startsWith("/api/member/context")) {
    //   return true;
    // }
    // // api/member/ 경로의 호출은 체크하지 않음
    // if (path.startsWith("/api/member/")) {
    //   return true;
    // }

    // // 이미지 조회 경로는 체크하지 않는다면
    // if (path.startsWith("/api/products/view/") ) {
    //   return true;
    // }

    return false;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    log.info("------------------------JWTCheckFilter.......................");

    // 1. Authorization 헤더에서 JWT 토큰 추출
    String authHeaderStr = request.getHeader("Authorization");

    try {
        // 1.1 Bearer 토큰의 "Bearer " 접두사를 제거하여 실제 JWT 토큰만 추출
        String accessToken = authHeaderStr.substring(7);

        // 2. JWT 검증 및 클레임 추출
        // - JWT의 서명 검증과 클레임(Claims) 추출을 수행
        Map<String, Object> claims = JWTUtil.validateToken(accessToken);

        log.info("JWT claims: " + claims);

        // 3. 클레임 데이터를 기반으로 FormLoginDto 객체 생성
        // - JWT 클레임에서 사용자 정보를 추출하여 DTO로 변환
        FormLoginDto formLoginDto = FormLoginDto.builder()
                .email((String) claims.get("email"))           // 이메일 정보
                .password((String) claims.get("password"))     // 비밀번호 정보 (보안상 부적절, 제거 권장)
                .roleNames((List<String>) claims.get("roleNames")) // 권한 정보
                .isSocial((boolean) claims.get("social"))      // 소셜 로그인 여부
                .build();

        // 4. 사용자 세부 정보 객체 생성
        // - FormLoginDto를 사용하여 PrincipalDetails 객체를 생성
        PrincipalDetails principalDetails = PrincipalDetails.builder()
                .formLoginDto(formLoginDto)
                .build();

        log.info("-----------------------------------");
        log.info(principalDetails);  // 디버깅용 PrincipalDetails 출력
        log.info(principalDetails.getAuthorities()); // 사용자의 권한 출력

        // 5. 인증 객체 생성
        // - Spring Security에서 사용하는 UsernamePasswordAuthenticationToken을 생성
        UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(
                        principalDetails,                             // Principal (사용자 정보)
                        principalDetails.getFormLoginDto().getPassword(), // Credentials (비밀번호)
                        principalDetails.getAuthorities()              // Authorities (권한 정보)
                );

        // 6. SecurityContext에 인증 객체 저장
        // - Spring Security가 요청의 인증 상태를 확인할 수 있도록 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 7. 다음 필터로 요청 전달
        // - 필터 체인의 다음 필터로 요청을 전달하여 추가 처리를 수행
        filterChain.doFilter(request, response);

    } catch (Exception e) {
        // 8. 예외 처리
        // - JWT 검증 실패, 토큰 만료 등 오류 발생 시 처리
        e.printStackTrace();
        log.error("JWT Check Error..............");
        log.error(e.getMessage());

        // 8.1 클라이언트에 에러 메시지 반환
        Gson gson = new Gson();
        String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN")); // JSON 형식의 에러 메시지
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(msg);
        printWriter.close();
    }
}
    
    

}

