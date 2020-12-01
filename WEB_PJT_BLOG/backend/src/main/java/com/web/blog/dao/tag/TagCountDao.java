package com.web.blog.dao.tag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.dto.tag.TagCount;

//<TagCount,String> 주의 TagCount 객체 사용위해 ...
public interface TagCountDao extends JpaRepository<TagCount ,String> {

	//태그이름별  갯수 리턴 (3개까지), status가 1인 글에 사용된 횟수만 세기
	 @Query(value="select  tag_name ,count(*) as count from tags "
	 		+ "where board_id = any (select board_id from board where status=1) "
	 		+ "group by tag_name order by count desc limit 3",
			 nativeQuery=true)
	List<TagCount> countBytagName();
	 
	@Query(value="select count(tag_name) as count, tag_name from tags "
				+ "where board_id= ANY(select board_id from board where user_id=?1) "
				+ "group by tag_name", nativeQuery = true)
	List<TagCount> countByUserId(int userid);
}
