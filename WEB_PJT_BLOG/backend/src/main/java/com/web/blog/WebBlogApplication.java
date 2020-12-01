package com.web.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.web.blog.util.JwtInterceptor;

@SpringBootApplication
public class WebBlogApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(WebBlogApplication.class, args);
	}

	@Autowired
	private JwtInterceptor jwtInterceptor;
	 
	
	  //여기서 CrossOrigin 전역 설정
	  
	  @Override public void addCorsMappings(CorsRegistry registry) {
	  
	  registry.addMapping("/**").allowedOrigins("http://localhost:3000"
	  ,"https://i3a302.p.ssafy.io","http://i3a302.p.ssafy.io").allowedMethods("GET","PUT","POST","DELETE","OPTIONS").allowedHeaders("*"); }

			
			//일단 주석처리
			 //jwt 토큰 검사
			  
	
	 @Override public void addInterceptors(InterceptorRegistry registry) {
	 
	 registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
	 .excludePathPatterns("/api/account/signup"); }
	
		 
			 
			 

	


}
