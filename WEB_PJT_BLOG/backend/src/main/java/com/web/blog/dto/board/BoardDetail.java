package com.web.blog.dto.board;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.blog.dto.reply.Reply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "board")
@Data
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDetail {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int boardId;

	 	// request에서 값을 받아오기 때문에 검증필요 X
	    private String subject;
	    private int userId;
	    private String nickname;
	    private String content;
	    private int views;
	    private int status;
	    
	    private String url;
	    
	    @Transient
	    private List<String> tags;
	    
	    @Transient // 테이블에 없으면 무시
	    private long countLike; // 좋아요 개수
	    
	    @Transient
	    private List<Reply> replys;
	    
	    @Transient
	    private int replyCount; // 현재 글의 댓글 개수
	    
	    @Transient
	    private String imgSrc; // 현재 글의 댓글 개수
	    
	    @Column(insertable = false, updatable = false)
	    private LocalDateTime createDate;
	    
	    @Column(insertable = false, updatable = false)
	    private LocalDateTime updateDate;
	    
}
