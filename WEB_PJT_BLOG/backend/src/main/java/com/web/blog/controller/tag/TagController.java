package com.web.blog.controller.tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dto.tag.TagCount;
import com.web.blog.service.tag.TagService;

import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = { "http://localhost:3000" ,"http://i3a302.p.ssafy.io" })
@RequestMapping("/api")
@RestController
public class TagController {
 
	@Autowired
	TagService tagService;

	@GetMapping("/tags/popular")
	@ApiOperation(value = "인기태그(태그이름,태그된갯수) 3개 가져오기")
	public Object getTagsPopular() {
		List<TagCount> tagList = null;
		
		
		try {
			tagList = tagService.countBytagName();
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return tagList; 
	}
	
	@GetMapping("/tags/{userid}")
	@ApiOperation(value="해당 유저가 사용한 태그와 사용횟수 가져오기")
	public Object getTagCount(@PathVariable("userid") int userid) {
		List<TagCount> tagList = null;
		
		try {
			tagList = tagService.countByUserId(userid);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		
		return tagList;
	}
	
}
