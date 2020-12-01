package com.web.blog.dao.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.web.blog.dto.tag.Tag;

public interface TagDao extends JpaRepository<Tag ,Integer>{
	
	@Query(value="select", nativeQuery = true)
	List<Tag> test(String tagNmae, int limit, int offset);

	List<Tag> findByBoardId(int boardId);

	@Transactional
	void deleteByBoardId(int boardId);
}
