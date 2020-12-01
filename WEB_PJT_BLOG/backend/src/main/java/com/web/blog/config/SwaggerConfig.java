package com.web.blog.config;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		//Authentication header 처리를 위해 사용
				List global = new ArrayList();
				global.add(new ParameterBuilder().name("Authorization").
										  description("accesstoken").parameterType("header").
										  required(false).modelRef(new ModelRef("string")).build());
				
				
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("web")
				.apiInfo(apiInfo())
				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.web.blog.controller.account"))
				.apis(RequestHandlerSelectors.basePackage("com.web.blog.controller"))
//				.paths(PathSelectors.ant("/account/**"))	// /api/qna형식으로
				.build()
				.securitySchemes(Arrays.asList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey("accesstoken" ,"accesstoken", "header");
	}
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("SSAFY API")
				.description("SSAFY API Reference for Developers")
				.termsOfServiceUrl("https://edu.ssafy.com")
				.license("SSAFY License")
				.licenseUrl("ssafy@ssafy.com").version("1.0").build();
	}

}


