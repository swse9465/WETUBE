package com.web.blog.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.web.blog.dto.user.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtService {
	
	@Value("${jwt.salt}")
	private String salt;
	
	
	/*
	 * 로그인 성공시 사용자 정보를 기반으로 JWTToken을 생성해서 반환한다.
	 * 
	 * @param user
	 * 
	 * @return
	 */
	public String create(final User user) {
		log.trace("time:{} , expireMin");
		final JwtBuilder builder = Jwts.builder();
		//JWT Token = Header+Payload + Signature
		//Header 설정
		builder.setHeaderParam("typ", "JWT");
		
		//payload 설정 -claim 정보 포함
		builder.setSubject("accesstoken")
			.claim("User",user);
		
		//signature- secret key를 이용한 암호화
		builder.signWith(SignatureAlgorithm.HS256,salt.getBytes());
		
		//마지막 직렬화 처리
		final String jwt = builder.compact();
		log.debug("토큰 발행:{}",jwt);
		return jwt;
	}
	
	
	/*
	 * 전달 받은 토큰이 제대로 생성된 것인지 확인하고 문제가 있다면 Runtime 예외를 발생시킨다.
	 * 
	 * @param jwt
	 */
	public void checkValid(final String jwt)  throws Exception{
		//별 문제가 없었는지 즉 예외가 발생하지 않으면 OK
		log.trace("토큰 점검:{} ,jwt");
		Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
	}
	
	
	/*
	 * jwt 토큰을 분석해서 필요한 정보를 반환한다.
	 * 
	 * @param jwt
	 * 
	 * @return
	 */
	public Map<String,Object> get(final String jwt){
		Jws<Claims>claims = null;
		try {
			claims=Jwts.parser().setSigningKey(salt.getBytes()).parseClaimsJws(jwt);
		}catch(final Exception e) {
			throw new RuntimeException();
		}
		
		log.trace("claims:{}",claims);
		//claims는 Map 의 구현체다.
		return claims.getBody();
	}
}
