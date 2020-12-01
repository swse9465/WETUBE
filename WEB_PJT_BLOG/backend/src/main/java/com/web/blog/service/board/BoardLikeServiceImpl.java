package com.web.blog.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.board.BoardLikeDao;
import com.web.blog.dto.board.BoardLike;

@Service
public class BoardLikeServiceImpl implements BoardLikeService{
	
	@Autowired
	BoardLikeDao boardLikeDao;

	@Override
	public BoardLike createlike(BoardLike like) {
		
		return boardLikeDao.save(like);
	}

	@Override
	public void deleteLike(int userId, int boardId) {
		  boardLikeDao.deleteByUserIdAndBoardId(userId, boardId);
	}

	@Override
	public long countLike(int boardId) {
		return boardLikeDao.countByBoardId(boardId);
	}

	@Override
	public boolean isExist(int boardId, int userId) {
		BoardLike check = boardLikeDao.isExist(boardId, userId);
		System.out.println(check);
		if(check == null)
			return false;
		else
			return true;
	}


}
