package com.web.blog.dto.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Board {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int boardId;

	    private String subject;
	    private String nickname;
	    private String content;
	    private int views;
	    private int status;
	    
	    @Column(insertable = false, updatable = false)
	    private LocalDateTime createDate;
	    
	    @Column(insertable = false, updatable = false)
	    private LocalDateTime updateDate;
	    
	    
	    
}
