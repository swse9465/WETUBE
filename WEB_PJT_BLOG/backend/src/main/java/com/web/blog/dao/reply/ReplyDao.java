package com.web.blog.dao.reply;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.dto.reply.Reply;

public interface ReplyDao extends JpaRepository<Reply, Integer>{
    
	 @Query(value="select *from reply  where board_id =?1 and parent =0 ;", nativeQuery=true)
	List<Reply> findByBoardId(int boardId);
    
	Reply findByReplyId(int replyId);
    
	 @Query(value="select *from reply  where parent=?1 ;", nativeQuery=true)
	List<Reply> findByParent(int replyId);
	 
	 
	int countByBoardId(int boardId);
	
}
