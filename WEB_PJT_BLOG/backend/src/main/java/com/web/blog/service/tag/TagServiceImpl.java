package com.web.blog.service.tag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.dao.tag.TagCountDao;
import com.web.blog.dao.tag.TagDao;
import com.web.blog.dto.tag.Tag;
import com.web.blog.dto.tag.TagCount;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	TagCountDao tagCountDao; 

	@Autowired
	TagDao tagDao;
	
	@Override
	public List<TagCount> countBytagName(){
		
		return tagCountDao.countBytagName();
	}

	@Override
	public List<String> getTags(int boardId) throws Exception {
		List<Tag> originTags = tagDao.findByBoardId(boardId);
		List<String> tags = new ArrayList<String>();
		
		for (Tag tag : originTags) 
			tags.add(tag.getTagName());
		
		return tags;
	}

	@Override
	public List<String> saveTags(int boardId, List<String> tag) throws Exception {
		List<Tag> tags = new ArrayList<Tag>();
		for (String tagStr : tag) 
			tags.add(new Tag(tagStr, boardId));
		
		tags = tagDao.saveAll(tags);
		
		return tag;
	}

	@Override
	public List<String> updateTags(int boardId, List<String> tag) throws Exception {
		List<Tag> newtags = new ArrayList<Tag>();
		
		for (String tagStr : tag) 
			newtags.add(new Tag(tagStr, boardId));
		
		tagDao.deleteByBoardId(boardId);
		tagDao.saveAll(newtags);
		
		return tag;
	}

	@Override
	public void deletetags(int boardId) throws Exception {
		tagDao.deleteByBoardId(boardId);
	}

	@Override
	public List<TagCount> countByUserId(int userid) {
		
		List<TagCount> countList = tagCountDao.countByUserId(userid);
		
		// 사옹횟수가 많은 순서대로 내림차순 정렬
		countList.sort(new Comparator<TagCount>() {
			@Override
			public int compare(TagCount o1, TagCount o2) {
				if(o1.getCount() < o2.getCount())
					return 1;
				else
					return -1;
			}
		});
		
		return countList;
	}
	
	
}
