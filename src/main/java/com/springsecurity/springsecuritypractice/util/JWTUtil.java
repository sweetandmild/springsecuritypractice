package com.springsecurity.springsecuritypractice.util;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.oauth2.jwt.JwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

public class JWTUtil {
    private static String key = "1234567890123456789012345678901234567890";

    public static String generateToken(Map<String, Object> claims, int min) {

      SecretKey key = null;
  
      try {
          // 1. SecretKey 생성
          // - HMAC 알고리즘을 사용하기 위한 비밀키를 생성합니다.
          // - "JWTUtil.key"는 서버에 저장된 비밀키 문자열입니다.
          // - UTF-8로 인코딩하여 키로 변환합니다.
          key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
      } catch (Exception e) {
          // 2. 키 생성 중 예외 처리
          // - 키 생성 중 문제가 발생하면 RuntimeException으로 예외를 던집니다.
          throw new RuntimeException(e.getMessage());
      }
  
      // 3. JWT 생성
      // - Jwts.builder()를 사용하여 JWT를 생성합니다.
      String jwtStr = Jwts.builder()
              // 3.1 Header 설정
              // - 헤더에는 JWT의 타입을 명시합니다 ("typ": "JWT").
              .setHeader(Map.of("typ", "JWT"))
              
              // 3.2 Payload 설정 (Claims)
              // - 사용자 정보를 담은 클레임을 설정합니다.
              .setClaims(claims)
              
              // 3.3 발행 시간 (iat)
              // - 현재 시간을 기준으로 발행 시간을 설정합니다.
              .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
              
              // 3.4 만료 시간 (exp)
              // - 발행 시간으로부터 주어진 분(min) 후의 시간을 만료 시간으로 설정합니다.
              .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
              
              // 3.5 서명(Signature)
              // - HMAC 알고리즘과 생성된 키를 사용해 JWT의 서명을 추가합니다.
              .signWith(key)
              
              // 3.6 JWT 생성
              // - 위에서 설정한 정보를 기반으로 JWT를 생성합니다.
              .compact();
  
      // 4. 생성된 JWT 문자열 반환
      // - 최종적으로 생성된 JWT를 반환합니다.
      return jwtStr;
  }

  public static Map<String, Object> validateToken(String token) {

    Map<String, Object> claim = null;

    try {
        // 1. SecretKey 객체 생성
        // - HMAC 알고리즘을 사용하기 위해 키를 생성합니다.
        // - "JWTUtil.key"는 서명 검증에 사용할 비밀키입니다.
        SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));

        // 2. JWT 파싱 및 검증
        // - 토큰을 파싱하고 유효성을 검증합니다.
        // - 파싱 과정에서 서명 검증(Signature Validation)도 자동으로 수행됩니다.
        claim = Jwts.parserBuilder()
                .setSigningKey(key)   // 서명 검증에 사용할 키를 설정
                .build()
                .parseClaimsJws(token) // 토큰을 파싱하여 클레임 추출 (유효하지 않은 경우 예외 발생)
                .getBody();           // 검증된 JWT의 클레임(Claims)을 반환

    } catch (MalformedJwtException malformedJwtException) {
        // 3. MalformedJwtException 처리
        // - 토큰 형식이 잘못되었을 때 발생합니다.
        throw new CustomJWTException("MalFormed");
    } catch (ExpiredJwtException expiredJwtException) {
        // 4. ExpiredJwtException 처리
        // - 토큰이 만료되었을 때 발생합니다.
        throw new CustomJWTException("Expired");
    } catch (InvalidClaimException invalidClaimException) {
        // 5. InvalidClaimException 처리
        // - JWT의 클레임(Claims)이 예상과 다를 때 발생합니다.
        throw new CustomJWTException("Invalid");
    } catch (JwtException jwtException) {
        // 6. JwtException 처리
        // - 서명 검증 실패, 토큰 손상 등 기타 JWT 관련 오류가 발생했을 때 처리합니다.
        throw new CustomJWTException("JWTError");
    } catch (Exception e) {
        // 7. 기타 예외 처리
        // - 예상치 못한 다른 예외가 발생한 경우 처리합니다.
        throw new CustomJWTException("Error");
    }

    // 8. 검증된 클레임 반환
    // - 파싱 및 검증이 성공한 경우, JWT의 클레임(Claims)을 Map으로 반환합니다.
    return claim;
  }
}
