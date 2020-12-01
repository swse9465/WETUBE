package com.web.blog.dto.reply;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int replyId;
	
	private int boardId;
	private String nickname;
	private int parent;
	private int seq;
	private String content;
	
	@Transient 
	private String imgSrc;
	
	@Column(insertable = false, updatable = false)
    private LocalDateTime createDate;
    
    @Column(insertable = false, updatable = false)
    private LocalDateTime updateDate;
}
