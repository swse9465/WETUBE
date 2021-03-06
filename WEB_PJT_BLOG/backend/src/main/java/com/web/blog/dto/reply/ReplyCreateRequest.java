package com.web.blog.dto.reply;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Valid
@Getter
@ToString
public class ReplyCreateRequest {
	@ApiModelProperty(required = true)
	@NotEmpty(message = "잘못된 요청 입니다.")
	private int boardId;
	
	@ApiModelProperty(required = true)
    @NotEmpty(message = "잘못된 요청 입니다.")
    private String nickname;
	private int parent;
	private int seq;
	
	@ApiModelProperty(required = true)
	@NotEmpty(message = "내용은 반드시 작성해야합니다 (공백제외)")
	private String content;
}
