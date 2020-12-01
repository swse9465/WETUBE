package com.web.blog.service.board;

import java.util.List;

import com.web.blog.dto.board.BoardList;

public interface BoardListService {
	//조회
	List<BoardList> findByStatus(int status ,int limit, int offset) throws Exception;
	//검색
	List<BoardList> findBySearch(int status ,String keyword , String filter ,int limit, int offset)throws Exception;
	
	//user의 저장, 임시저장 리스트 조회 
	List<BoardList>findByUserIdAndStatus(int userId , int status);
	//boardId로 삭제
	void deleteByBoardId(int boardId);
	List<BoardList> getLikeBoards(int userid);
	List<BoardList> getRecommendation();
}
