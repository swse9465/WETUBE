package com.web.blog.service.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.board.BoardLikeDao;
import com.web.blog.dao.board.BoardListDao;
import com.web.blog.dao.reply.ReplyDao;
import com.web.blog.dto.board.BoardList;

@Service
public class BoardListServiceImpl implements BoardListService{

	//boardListDao
	@Autowired
	private BoardListDao boardListDao;
	
	//boardLikeDao
	@Autowired
	private BoardLikeDao boardLikeDao;
	
	
	@Autowired
	private ReplyDao replyDao;
	
	@Override
	public List<BoardList> findByStatus(int status, int limit ,int offset) throws Exception {
		//status(저장상태) 1인 게시글 받아온다! 
		List<BoardList> boardList =  boardListDao.findByStatus(status,limit, offset);
		for (BoardList board : boardList) {
				//좋아요 수 넣기 
				board.setLikes(boardLikeDao.countByBoardId(board.getBoardId()));
			}
		return boardList;
	}

	@Override
	public List<BoardList> findBySearch(int status, String keyword, String filter , int limit, int offset) throws Exception {
		List<BoardList> boardList = new ArrayList<BoardList>();
		if(filter.equals("nickname")) {
			
			boardList = boardListDao.findByNicknameLike(status, keyword,limit,offset);
		}else if (filter.equals("subject")) {
			
			boardList = boardListDao.findBySubjectLike(status, keyword,limit,offset);
		}else if (filter.equals("tag")) {
			List<Integer> boardIdList = boardListDao.findBoardIdByTag(keyword, status, limit, offset);
			
			for (Integer boardId : boardIdList) 
				boardList.add(boardListDao.findByStatusAndBoardId(status,boardId));
		}
		else {
			//맞지 않는 검색조건 (filter)
			throw new NoSuchFieldException();
		}
		
		//좋아요 수 넣기
		for (BoardList board : boardList) 
			board.setLikes(boardLikeDao.countByBoardId(board.getBoardId()));
		
		return boardList;
	}

	//해당 user의 임시저장 or 저장 리스트 가져오기 
	@Override
	public List<BoardList> findByUserIdAndStatus(int userId, int status) {
		List<BoardList> boardList =  boardListDao.findByUserIdAndStatus(userId, status);
		for (BoardList board : boardList) {
				//좋아요 수 넣기 
				board.setLikes(boardLikeDao.countByBoardId(board.getBoardId()));
				//댓글 개수 넣기
				board.setReplies(replyDao.countByBoardId(board.getBoardId()));
		}
		
		return boardList;
	}

	@Override
	public void deleteByBoardId(int boardId) {
		 boardListDao.deleteById(boardId);
	}

	@Override
	public List<BoardList> getLikeBoards(int userid) {
		List<BoardList> boardList = boardListDao.findByLike(userid); 
		
		for (BoardList board : boardList) {
			//좋아요 수 넣기 
			board.setLikes(boardLikeDao.countByBoardId(board.getBoardId()));
			//댓글 개수 넣기
			board.setReplies(replyDao.countByBoardId(board.getBoardId()));
		}
				
		return boardList;
	}

	@Override
	public List<BoardList> getRecommendation() {
		List<BoardList> boardList = null;
		boardList = boardListDao.findByDate();
		
		for (BoardList board : boardList) 
			board.setLikes(boardLikeDao.countByBoardId(board.getBoardId()));
		
		return boardList;
	}
}
