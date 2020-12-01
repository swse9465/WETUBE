package com.web.blog.controller.board;

import java.util.Optional;

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

import com.web.blog.dao.user.UserDao;
import com.web.blog.dto.board.BoardCreateRequest;
import com.web.blog.dto.board.BoardDetail;
import com.web.blog.dto.board.BoardModifyRequest;
import com.web.blog.dto.user.User;
import com.web.blog.service.board.BoardLikeService;
import com.web.blog.service.board.BoardModifyService;
import com.web.blog.service.reply.ReplyService;
import com.web.blog.service.tag.TagService;

import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = { "http://localhost:3000" ,"http://i3a302.p.ssafy.io" })
@RequestMapping("/api")
@RestController 
public class BoardModifyController { 
	// 포스트 작성, 조회, 수정, 삭제 담당 컨트롤러
	
	@Autowired
	BoardModifyService boardModifyService;
	
	@Autowired
	ReplyService replyService;
	
	@Autowired
	BoardLikeService boardLikeService;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	TagService tagService;
	
	// 포스트 작성
	@PostMapping("/board")
	@ApiOperation(value="글 작성")
	public Object createPost(@RequestBody BoardCreateRequest request) {
		ResponseEntity response = null;
		Optional<User> user = userDao.findUserByUserId(request.getUserId());
		
		if(!user.isPresent()) { // 정보가 없는경우 잘못된 요청으로 처리 
			return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
		} 
		
		try {
			// board 테이블에 들어갈 정보들
			BoardDetail board = new BoardDetail();
			board.setSubject(request.getSubject());
			board.setUserId(request.getUserId());
			board.setNickname(request.getNickname());
			board.setContent(request.getContent());
			board.setStatus(request.getStatus());
			board.setUrl(request.getUrl());
			
			board = boardModifyService.createPost(board);
			
			// 태그가 있을때만 받은 태그들 저장
			if(request.getTags().size() != 0) 
				board.setTags(tagService.saveTags(board.getBoardId() , request.getTags()));
			
			response = new ResponseEntity<>(board, HttpStatus.OK);
		} catch (Exception e) {// 저장 중 문제 발생
			response = new ResponseEntity<>("작업 수행 중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		
		return response;
	}
	
	// 포스트 내용 조회
	@GetMapping("/board/{boardId}")
	@ApiOperation(value="글 내용 조회")
	public Object getPost(@PathVariable("boardId") int boardId) {
		ResponseEntity response = null;
		BoardDetail board = null;

		try {
			board = boardModifyService.getPost(boardId); // 객체 가져와서
			board.setCountLike(boardLikeService.countLike(boardId)); // like 개수 가져와서 저장
			board.setViews(board.getViews()+1); // 조회수 증가
			boardModifyService.createPost(board); // 조회수 증가 된것 반영
			User user = userDao.getUserByUserId(board.getUserId());
			board.setImgSrc(user.getImgSrc());
			board.setReplys(replyService.getReplies(boardId)); // 덧글 받아옴 
			board.setReplyCount(board.getReplys().size()); // 덧글 개수 
			board.setTags(tagService.getTags(boardId)); // 태그 조회해서 board에 저장
			
			response = new ResponseEntity<>(board, HttpStatus.OK);
		} catch (Exception e) {// 동작이 안됬음 
			response = new ResponseEntity<>("작업 수행 중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	// 수정한 내용 저장
	@PutMapping("/board")
	@ApiOperation(value="글 내용 수정")
	public Object updatePost(@RequestBody BoardModifyRequest request) {
		ResponseEntity response = null;
		
		// 검색해서 받아와서 고치고 save 
		try {
			BoardDetail board= boardModifyService.getPost(request.getBoardId());
			if(board==null) {
				response = new ResponseEntity<>("작업 수행 중에 문제가 발생하였습니다.", HttpStatus.NOT_FOUND);
			}else { // 수정한 뒤 저장
				board.setSubject(request.getSubject());
				board.setContent(request.getContent());
				board.setStatus(request.getStatus());
				board.setUrl(request.getUrl());

				board = boardModifyService.createPost(board);
				
				if(request.getTags().size()==0) {//tag 없을때 혹은 있던거 다 지운경우
					tagService.deletetags(request.getBoardId());
				}else { // 태그 수정 및 생성  
					board.setTags(tagService.updateTags(request.getBoardId(), request.getTags()));
				}
				
				response = new ResponseEntity<>(board, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			response = new ResponseEntity<>("작업 수행 중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	@DeleteMapping("/board/{boardId}")
	@ApiOperation(value="글 삭제")
	public Object deletePost(@PathVariable("boardId") int boardId) {
		ResponseEntity response = null;
		
		try {
			BoardDetail post = boardModifyService.getPost(boardId);
			
			if(post==null) {
				response = new ResponseEntity<>("작업 수행 중에 문제가 발생하였습니다.", HttpStatus.NOT_FOUND);
			}else { 
				boardModifyService.deletePost(post);
				response = new ResponseEntity<>("success", HttpStatus.OK);
			}
		} catch (Exception e) {
			response = new ResponseEntity<>("작업 수행 중에 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
