
package com.web.blog.dao.user;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.blog.dto.user.User;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);

    Optional<User> findUserByEmailAndPassword(String email, String password);

	Optional<User> findUserByUserId(int userId);
	@Transactional
	void deleteByUserId(int userId);

	User findUserByEmail(String email);
	
	User findUserByNickname(String nickname);
	
	User getUserByUserId(int userId);
	
	@Query(value="select img_src from user where nickname=?1 ;", nativeQuery=true)
	String getImgSrcByNickname(String nickname);

}
