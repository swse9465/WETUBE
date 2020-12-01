package com.web.blog.dto.board;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.web.blog.dto.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardList {

	//수정 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardId;
	
	private String url;
    private String subject;
	@Column(insertable = false, updatable = false)
    private int userId;
    private String nickname;
    private String content;
    private int views;
    private int status;
	
	  @ManyToOne
	 
	  @JoinColumn(name ="userId") private User user;
	
    @Column(insertable = false, updatable = false)
    private LocalDateTime createDate;
    
    @Column(insertable = false, updatable = false)
    private LocalDateTime updateDate;
    
    //boardTable에 없음 
    @Transient
    private long likes;
    
    @Transient
    private int replies;
    
}
