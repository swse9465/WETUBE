package com.web.blog.service.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.board.BoardModifyDao;
import com.web.blog.dto.board.BoardDetail;


@Service
public class BoardModifyServiceImpl implements BoardModifyService{
	@Autowired
	BoardModifyDao modifyDao;
	
	@Override
	public BoardDetail createPost(BoardDetail request) throws Exception {
		return modifyDao.save(request);
	}

	@Override
	public BoardDetail getPost(int boardId) throws Exception {
		return modifyDao.findByBoardId(boardId);
	}

	@Override
	public void deletePost(BoardDetail request) throws Exception {
		modifyDao.delete(request);
	}

}
