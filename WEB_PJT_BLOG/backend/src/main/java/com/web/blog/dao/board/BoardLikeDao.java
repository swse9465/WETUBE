package com.web.blog.dao.board;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.dto.board.BoardLike;

public interface BoardLikeDao  extends JpaRepository<BoardLike, Integer> {
	
	@Transactional
	void deleteByUserIdAndBoardId(int userId, int boardId);

	long countByBoardId(int boardId);

	@Query(value = "select * from board_like where board_id=?1 and user_id=?2", nativeQuery = true)
	BoardLike isExist(int boardId, int userId);

}
