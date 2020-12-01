package com.web.blog.dao.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.blog.dto.board.BoardDetail;

public interface BoardModifyDao extends JpaRepository<BoardDetail, Integer>{

	BoardDetail findByBoardId(int boardId);

}
