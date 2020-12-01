
package com.web.blog.dao.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.dto.board.BoardList;



public interface BoardListDao extends JpaRepository<BoardList, Integer> {
	
	//전체조회 
	 @Query(value="select * from board where status = ?1 order by create_date desc limit ?2 offset ?3 ",
			 nativeQuery=true)
	List<BoardList> findByStatus (int status ,int limit , int offset);
	 
	 //nickname 검색어 검색
	 @Query(value="select * from board  where status = ?1 and nickname like concat('%', ?2 ,'%') order by create_date desc limit ?3 offset ?4",
			 nativeQuery=true)
	List<BoardList> findByNicknameLike(int status ,String keyword,int limit, int offset  );
	 
	 //subject 검색어 검색
	 @Query(value="select * from board  where status = ?1 and subject like concat('%', ?2 ,'%') order by create_date desc limit ?3 offset ?4" ,nativeQuery=true)
	List<BoardList> findBySubjectLike(int status ,String keyword ,int limit, int offset);
	 
	//태그 필터 에 맞는 검색어 검색 
	BoardList findByStatusAndBoardId(int status ,int boardId);
	
	//해당 user의 저장 or 임시저장 리스트 조회 
	List<BoardList>findByUserIdAndStatus(int userId , int status);
	
	//boardId로 게시글  삭제 
	void deleteByBoardId(int boardId);

	// 좋아요 누른 글 가져오기
	@Query(value = "select * from board where board_id = ANY (select board_id from board_like where user_id=?1);", 
			nativeQuery = true)
	List<BoardList> findByLike(int userid);

	// 태그명으로 status가 1이고 태그가 사용된 글 번호들을 가져오기 
	@Query(value="select board_id from board " + 
			"where board_id= ANY (select board_id from tags where tag_name=?1) and status = ?2 limit ?3 offset ?4", 
			nativeQuery = true)
	List<Integer> findBoardIdByTag(String keyword, int status, int limit, int offset);

	// 1주일 동안 작성된 글중 조회수가 높은 글 6개를 반환
	@Query(value = "select * from board "
			+ "where create_date between date(now())-7 and date(now())+1 and status=1 "
			+ "order by views desc limit 6",
			nativeQuery = true)
	List<BoardList> findByDate();
	 
}
