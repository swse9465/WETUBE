package com.web.blog.service.board;

import com.web.blog.dto.board.BoardLike;

//@Service
public interface BoardLikeService {

	BoardLike createlike(BoardLike like);

	void deleteLike(int userId, int boardId);

	long countLike(int boardId);

	boolean isExist(int boardId, int userId);

}
