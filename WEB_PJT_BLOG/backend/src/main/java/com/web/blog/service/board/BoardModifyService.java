package com.web.blog.service.board;

import com.web.blog.dto.board.BoardDetail;

public interface BoardModifyService {
	// 포스트 추가
	BoardDetail createPost(BoardDetail request) throws Exception;
	
	// boardId를 이용하여 포스트 내용 조회
	BoardDetail getPost(int boardId) throws Exception;
	
	// 포스트 삭제 
	void deletePost(BoardDetail request) throws Exception;
}
