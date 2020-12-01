package com.web.blog.service.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.reply.ReplyDao;
import com.web.blog.dao.user.UserDao;
import com.web.blog.dto.reply.Reply;

@Service
public class ReplyServiceImpl implements ReplyService{
	@Autowired
	ReplyDao replydao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public Reply createReply(Reply request) {
		return replydao.save(request);
	}

	@Override
	public List<Reply> getReplies(int boardId) {
		List<Reply> replyList =replydao.findByBoardId(boardId);
		for (Reply reply : replyList) {
			reply.setImgSrc(userDao.getImgSrcByNickname(reply.getNickname()));
		}
		return replyList;
	}

	@Override
	public Reply getreply(int replyId) {
		return replydao.findByReplyId(replyId);
	}

	
	@Override
	public List<Reply> getRereplies(int replyId) {
		 
		List<Reply> replyList = replydao.findByParent(replyId);
		for (Reply reply : replyList) {
			reply.setImgSrc(userDao.getImgSrcByNickname(reply.getNickname()));
		}
		
		return replyList;
		
	}
	
	@Override
	public void deleteReply(Reply reply) {
		replydao.delete(reply);
	}

	@Override
	public int getReplyCount(int boardId) {
		return replydao.countByBoardId(boardId);
	}

}
