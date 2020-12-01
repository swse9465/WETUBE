package com.web.blog.controller.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dto.board.BoardLike;
import com.web.blog.dto.board.BoardList;
import com.web.blog.service.board.BoardListService;
import com.web.blog.service.board.BoardModifyService;

import io.swagger.annotations.ApiOperation;


//@CrossOrigin(origins = { "http://localhost:3000" ,"http://i3a302.p.ssafy.io" })
@RequestMapping("/api")
@RestController
public class BoardListController {
	
	@Autowired BoardListService boardListService;
	
	@Autowired BoardModifyService boardModifyService;
	
	@GetMapping("/boards")
	@ApiOperation(value = "전체 게시글리스트 가져오기/검색조건에 따른 게시글 리스트 가져오기 ")
	public Object getBoards(@RequestParam final int limit,	@RequestParam final int offset ,
			@RequestParam final String keyword,@RequestParam final String filter) {
		// limit offset keyword filter 
		List<BoardList> boardList = null;
		
		try {
			if(filter=="") { // 전체조회(메인페이지)
				//status(저장상태) 1인 게시글 받아온다. +likes 이다
				 boardList =  boardListService.findByStatus(1,limit,offset);
			}
			else {
				//검색기능 (닉네임, 태그, 제목)
				boardList = boardListService.findBySearch(1, keyword, filter,limit,offset);
			}
			 
		} catch (Exception e) {
			return new ResponseEntity<>(boardList, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		
		return new ResponseEntity<>(boardList, HttpStatus.OK); 
	}
	
	
	@GetMapping("/boards/{userId}")
	@ApiOperation(value = "status(0,1)에 따른 user의 임시저장, 저장 리스트 가져오기 ")
	public Object getBoardsByStatusAndUser(@PathVariable final int userId, @RequestParam(required = true) final int status) {
		
		List<BoardList> boardList = null;
		
		try {
			//status(0,1)에 따른 user의 임시저장, 저장 리스트 가져오기
			boardList = boardListService.findByUserIdAndStatus(userId, status);
			 
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		 
		return new ResponseEntity<>(boardList, HttpStatus.OK); 
	}
	
	@DeleteMapping("/tempboards/{boardId}")
	@ApiOperation(value = "user의 임시저장 리스트 삭제 ")
	public Object getBoardsByStatusAndUser(@PathVariable("boardId") int boardId) {

		
		try {	
					boardListService.deleteByBoardId(boardId);
			 
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		 
		
		return new ResponseEntity<>("success", HttpStatus.OK); 
	}

	@GetMapping("/boards/like/{userid}")
	@ApiOperation(value = "특정 유저가 좋아요 표시한 글 목록 가져오기")
	public Object getFavoriteBoards(@PathVariable int userid) {
		List<BoardList> boardList = null;
		
		try {
			boardList = boardListService.getLikeBoards(userid);
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		return new ResponseEntity<>(boardList, HttpStatus.OK);
	}
	
	@GetMapping("/boards/recommendation")
	@ApiOperation(value = "조회수를 기준으로 1주일 동안 작성된 글 6개 추천")
	public Object getRecommation() {
		List<BoardList> boardList = null;
		
		try {
			boardList = boardListService.getRecommendation();
					
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return new ResponseEntity<>(boardList, HttpStatus.OK);
	}
	
}