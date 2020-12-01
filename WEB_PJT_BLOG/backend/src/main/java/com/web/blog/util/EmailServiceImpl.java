package com.web.blog.util;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.blog.dao.user.UserDao;

@Service
public class EmailServiceImpl implements EmailService {
  
    @Autowired
    public JavaMailSender emailSender;
    
    @Override
    public String sendPwd(String to) {
    	
    	StringBuffer temp = new StringBuffer();
    	Random rnd = new Random();
    	
    	for (int i = 0; i < 10; i++) {
			int rIndex =rnd.nextInt(3);
			switch (rIndex) {
			case 0:
                // a-z
				temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
                temp.append((rnd.nextInt(10)));
				break;
			}
		}
    	
    	String AuthenticationKey = temp.toString();
    	
    	try {
        	SimpleMailMessage message = new SimpleMailMessage();
        	message.setFrom("ss-blog@ssafy.com");
        	message.setTo(to);
        	message.setSubject("비밀번호 찾기 인증 메일");
        	message.setText("인증 번호는 : "+temp);
        	emailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

    	return AuthenticationKey;
    }

	@Override
	public String joinMemeber(String to) {

    	StringBuffer temp = new StringBuffer();
    	Random rnd = new Random();
    	
    	for (int i = 0; i < 10; i++) {
			int rIndex =rnd.nextInt(3);
			switch (rIndex) {
			case 0:
                // a-z
				temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
                temp.append((rnd.nextInt(10)));
				break;
			}
		}
    	
    	String AuthenticationKey = temp.toString();
    	
    	try {
        	SimpleMailMessage message = new SimpleMailMessage();
        	message.setFrom("ss-blog@ssafy.com");
        	message.setTo(to);
        	message.setSubject("페이지 회원가입 인증 메일");
        	message.setText("인증 번호는 : "+temp);
//        	message.setText(new StringBuffer().append("회원가입을 축하합니다.").toString());
        	emailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return AuthenticationKey;
	}
 
}