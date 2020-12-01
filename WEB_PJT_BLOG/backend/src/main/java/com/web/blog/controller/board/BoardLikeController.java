package com.web.blog.controller.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.board.BoardLike;
import com.web.blog.service.board.BoardLikeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" ,"http://i3a302.p.ssafy.io" })
@RequestMapping("/api")
@RestController
public class BoardLikeController {
	
	@Autowired
	BoardLikeService boardLikeService;
	
	@PostMapping("/like")
	@ApiOperation(value = "좋아요 생성")
	public Object createLike (@RequestParam final int userId,
			@RequestParam final int boardId) {

		BoardLike like = new BoardLike();
		like.setBoardId(boardId);
		like.setUserId(userId);
		
		ResponseEntity response = null;
		try {
			BoardLike newLike = boardLikeService.createlike(like);
			response = new ResponseEntity<>(newLike, HttpStatus.OK);

		} catch (Exception e) {
			response =  new ResponseEntity<>("좋아요 생성에 실패했습니다." , HttpStatus.CONFLICT);
		}
		
		return response;

	}
	
	@GetMapping("/like")
	@ApiOperation(value = "좋아요 개수 확인")
	public Long countLike(@RequestParam final int boardId) {
		
		long cnt = boardLikeService.countLike(boardId);
		
		return cnt;
	}
	
	
	@DeleteMapping("/like")
	@ApiOperation(value = "좋아요 삭제")
	public Object deleteLike(@RequestParam final int userId,
			@RequestParam(required = true) final int boardId) {
		
		boardLikeService.deleteLike(userId,boardId);

		return new ResponseEntity<>("좋아요 삭제에 성공하였습니다." , HttpStatus.OK);
	}

	@GetMapping("/likeExist")
	@ApiOperation(value = "특정글에 좋아요 존재여부 판별")
	public Object isExist(@RequestParam int boardId, @RequestParam int userId) {
		
		ResponseEntity response = null;
		
		try {
			boolean isExist = boardLikeService.isExist(boardId, userId);
			
			response = new ResponseEntity<>(isExist, HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<>("서버와 통신도중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
