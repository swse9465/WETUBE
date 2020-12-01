package com.web.blog.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Valid
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateRequest {
	
	@ApiModelProperty(required = true)
    int userId;
	
    @ApiModelProperty(required = true)
    String nickname;
    
    @ApiModelProperty(required = true)
    String imgSrc;
    
    @ApiModelProperty(required = true)
    String introduce;
    
    
    
}
