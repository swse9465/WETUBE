package com.web.blog.controller.account;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.blog.dao.user.UserDao;
import com.web.blog.dto.BasicResponse;
import com.web.blog.dto.user.SignupRequest;
import com.web.blog.dto.user.UpdateRequest;
import com.web.blog.dto.user.User;
import com.web.blog.util.EmailService;
import com.web.blog.util.JwtService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
		@ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
		@ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
		@ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

//@CrossOrigin(origins = { "http://localhost:3000" ,"http://i3a302.p.ssafy.io" })
@RequestMapping("/api")	
@RestController
@Slf4j
public class AccountController {
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	UserDao userDao;

	@Autowired
	EmailService emailService;
	
	@GetMapping("/account/login")
	@ApiOperation(value = "로그인")
	public Object login(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String password ,
			HttpServletResponse res ) {

		Map<String, Object> resultMap = new HashMap<>();
		Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);

		ResponseEntity response  = null;
		if (userOpt.isPresent()) {
			User user = userDao.findUserByEmail(email);
			try {
				String token = jwtService.create(user);
				res.setHeader("accesstoken",token);
				resultMap.put("user", userOpt);
				resultMap.put("accesstoken", token);
				
			}catch(Exception e ) {
				log.error("로그인 실패",e);
				response = new ResponseEntity<>("jwt토큰생성실패",HttpStatus.UNAUTHORIZED);
			}
			response = new ResponseEntity<>(resultMap, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>("로그인에 실패하였습니다", HttpStatus.NOT_FOUND);
		}

		return response;
	}
	
	@GetMapping("/account/findpw")
	@ApiOperation(value = "비밀번호 찾기")
	public Object findPw(@RequestParam(required = true) final String email) {
		
		User userOpt = userDao.findUserByEmail(email);
		ResponseEntity response = null;

		if(userOpt != null){
			String number = userOpt.getPassword();
			response = new ResponseEntity<>(number, HttpStatus.OK);
		}else {
			
			response = new ResponseEntity<>("비밀번호 찾기에 실패하였습니다.", HttpStatus.NOT_FOUND);
		}
		
		return response;
		
	}
	
	@GetMapping("/account/pwdverify")
	@ApiOperation(value = "비밀번호 찾기 이메일 인증")
	public Object pwdVerify(@RequestParam(required = true) final String email) {

		User user = userDao.getUserByEmail(email);
		//email 중복 체크 
		if (user == null) {
			return new ResponseEntity<>("email", HttpStatus.NOT_FOUND);
		}

		// 인증번호 저장 변수
		String number;
		try {
			// email 전송
			 number = emailService.joinMemeber(email);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("이메일 전송에 실패했습니다.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	
	@GetMapping("/account/memberverify")
	@ApiOperation(value = "회원가입 이메일 인증")
	public Object memberVerify(@RequestParam(required = true) final String email,
			@RequestParam(required = true) final String nickname) {


		User user = userDao.getUserByEmail(email);
		//email 중복 체크 
		if (user != null) {
			return new ResponseEntity<>("email", HttpStatus.CONFLICT);
		}
		//닉네임 중복 체크 
		user= userDao.findUserByNickname(nickname);
		if(user!=null) {
			return new ResponseEntity<>("nickname", HttpStatus.CONFLICT);
		}

		// 인증번호 저장 변수
		String number;
		try {
			// email 전송
			 number = emailService.joinMemeber(email);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("이메일 전송에 실패했습니다.", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(number, HttpStatus.OK);
	}

	@GetMapping("/account/{userId}")
	@ApiOperation(value = "회원정보 가져오기")
	public Object getUserInfo(@PathVariable(required = true) final int userId) {
		User user  = userDao.getUserByUserId(userId);

		ResponseEntity response = null;

		if (user!=null) {
			response = new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>("회원정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@PutMapping("/account")
	@ApiOperation(value = "회원정보 수정하기 (닉네임,프로필이미지,자기소개글 만 수정 가능 ")
	public Object updateUserInfo( @RequestBody UpdateRequest request) {
		User user = userDao.getUserByUserId(request.getUserId());
		
		//닉네임을 바꾸려고 할때 
		if(!user.getNickname().equals(request.getNickname())) {
			//이미 존재하는 닉네임이면 
			if(userDao.findUserByNickname(request.getNickname())!=null) {
				//닉네임 중복 에러 
				return new ResponseEntity<>("nickname", HttpStatus.CONFLICT);
			}
			user.setNickname(request.getNickname());
		}
		user.setImgSrc(request.getImgSrc());
		user.setIntroduce(request.getIntroduce());
		
		userDao.save(user);
		
		return new ResponseEntity<>("회원정보 수정에 성공하였습니다." , HttpStatus.OK);
	}
	
	@PostMapping("/account/signup")
	@ApiOperation(value = "회원가입")
	public Object signUp(@Valid @RequestBody SignupRequest request) {
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setNickname(request.getNickname());
		user.setPassword(request.getPassword());
		user.setLevel(1);
		
		userDao.save(user);
		
		return new ResponseEntity<>("회원가입 완료" , HttpStatus.OK);
	}

	@DeleteMapping("/account/{userId}")
	@ApiOperation(value = "회원정보 삭제하기")
	public Object deleteUserInfo(@PathVariable(required = true) final int userId) {
		userDao.deleteByUserId(userId);
		return new ResponseEntity<>("회원정보 삭제에 성공하였습니다." , HttpStatus.OK);
	}
}