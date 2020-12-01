package com.web.blog.dto.board;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Valid
@Getter
@ToString
public class BoardCreateRequest {
	@ApiModelProperty(required = true)
    @NotEmpty(message = "제목은 반드시 입력해야합니다(공백 제외)")
    private String subject;
	
	@ApiModelProperty(required = true)
    @NotEmpty(message = "잘못된 요청입니다.")
    private int userId;
	
	@ApiModelProperty(required = true)
	@NotEmpty(message = "잘못된 요청입니다.")
    private String nickname;
	
	@ApiModelProperty(required = true)
	@NotEmpty(message = "내용은 반드시 입력해야합니다(공백 제외)")
    private String content;
	
	@ApiModelProperty(required = true)
    @NotNull
    private int status;
	
	private String url;
    
    private List<String> tags;
}
