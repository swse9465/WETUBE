package com.web.blog.controller.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dto.reply.Reply;
import com.web.blog.dto.reply.ReplyCreateRequest;
import com.web.blog.dto.reply.ReplyModifyRequest;
import com.web.blog.service.reply.ReplyService;

import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = { "http://localhost:3000" ,"http://i3a302.p.ssafy.io" })
@RequestMapping("/api")	
@RestController
public class ReplyController { // 덧글 추가, 댓글 목록 가져오기, 수정, 삭제 담당 
	@Autowired
	ReplyService replyService;

	@PostMapping("/reply")
	@ApiOperation(value = "댓글 작성")
	public Object createReply(@RequestBody ReplyCreateRequest request) {
		ResponseEntity response = null;
		
		try {
			Reply reply = new Reply();
			reply.setBoardId(request.getBoardId());
			reply.setNickname(request.getNickname());
			reply.setParent(request.getParent());
			reply.setSeq(request.getSeq());
			reply.setContent(request.getContent());
			
			reply = replyService.createReply(reply);
			
			response = new ResponseEntity<>(reply, HttpStatus.OK);
			
		} catch (Exception e) {
			response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@GetMapping("/reply/{boardId}")
	@ApiOperation(value = "특정 글의 상위 댓글 가져오기")
	public Object getReplies(@PathVariable("boardId") int boardId) {
		ResponseEntity response = null;
		
		try {
			List<Reply> reply = replyService.getReplies(boardId);
			
			response = new ResponseEntity<>(reply, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@GetMapping("/reply/re/{replyId}")
	@ApiOperation(value = "특정 댓글의 대 댓글 가져오기")
	public Object getRereplies(@PathVariable("replyId") int replyId) {
		ResponseEntity response = null;
		
		try {
			List<Reply> replyList = replyService.getRereplies(replyId);
			 response = new ResponseEntity<>(replyList, HttpStatus.OK);
		} catch (Exception e) {
			
			response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@PutMapping("/reply")
	@ApiOperation(value = "댓글 수정")
	public Object modifyReply(@RequestBody ReplyModifyRequest request) {
		ResponseEntity response = null;
		
		try {
			Reply reply = replyService.getreply(request.getReplyId());
			
			if(reply == null) {
				response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.NOT_FOUND);
			}else {
				reply.setContent(request.getContent());
				reply = replyService.createReply(reply);
				
				response = new ResponseEntity<>(reply, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@DeleteMapping("/reply/{replyId}")
	@ApiOperation(value = "댓글 삭제")
	public Object deleteReply(@PathVariable("replyId") int replyId) {
		ResponseEntity response = null;
		
		try {
			Reply reply = replyService.getreply(replyId);
			
			if(reply == null) {
				response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.NOT_FOUND);
			}else {
				replyService.deleteReply(reply);
				
				response = new ResponseEntity<>("삭제 성공", HttpStatus.OK);
			}
		} catch (Exception e) {
			response = new ResponseEntity<>("작업 수행중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
