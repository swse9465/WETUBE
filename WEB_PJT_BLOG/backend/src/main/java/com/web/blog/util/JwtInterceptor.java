package com.web.blog.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/* System.out.println(request.getMethod()+":"+request.getServletPath()); */
		
		if(request.getMethod().equals("OPTIONS")||request.getMethod().equals("GET")) {
			return true;
		}else {
			//request의 Header에서 accesstoken 으로 넘어온 녀석을 찾아온다.
			String token = request.getHeader("accesstoken");
			if(token!=null && token.length()>0) {
				//유요한 토큰이면 진행 , 그렇지 않으면 예외를 발생시킨다.
				try {
					
				//유효한 토큰인지 확인 	
				jwtService.checkValid(token);
				
				}catch(Exception e){
					response.setStatus(401);
					throw new RuntimeException();
				}
				log.trace("토큰 사용 가능 :{} ",token);
				return true;
			}else {
				response.setStatus(401);
				throw new RuntimeException();
			}
		}
	}
	
}
