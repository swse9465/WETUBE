package com.web.blog.service.reply;

import java.util.List;

import com.web.blog.dto.reply.Reply;

public interface ReplyService {

	Reply createReply(Reply request); // 새로운 댓글 추가

	List<Reply> getReplies(int boardId); // 특정 글의 최상위댓글들 가져오기
	
	List<Reply>getRereplies(int replyId); //특정 댓글의 대댓글 가져오기 

	Reply getreply(int replyId); // 특정 댓글 1개만 가져오기 

	void deleteReply(Reply reply); // 댓글 삭제

	int getReplyCount(int boardId);
}
