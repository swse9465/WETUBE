package com.web.blog.service.tag;

import java.util.List;

import com.web.blog.dto.tag.TagCount;

public interface TagService {

	List<TagCount> countBytagName();
	
	List<String> getTags(int boardId)  throws Exception;
	
	List<String> saveTags(int boardId, List<String> tag) throws Exception;
	
	List<String> updateTags(int boardId, List<String> tag) throws Exception;
	
	void deletetags(int boardId) throws Exception;

	List<TagCount> countByUserId(int userid);
}
