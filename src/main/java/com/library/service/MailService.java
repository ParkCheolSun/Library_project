package com.library.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.library.dto.MemberDto;
import com.library.entity.Member;

@Service("mailService")
public class MailService {
	@Autowired
	 private JavaMailSender mailSender;
	
	@Autowired
	MemberService memberService;
 
	// 메일 전송
    @Async
	public void sendMail(String to, String subject, String body) {
      MimeMessage message = mailSender.createMimeMessage();
      try {
		MimeMessageHelper messageHelper = 
		new MimeMessageHelper(message, true, "UTF-8");
		messageHelper.setFrom("qkr5759@naver.com", "박철순");
		messageHelper.setSubject(subject);
		messageHelper.setTo(to); 
		messageHelper.setText(body );
		mailSender.send(message);  
      }catch(Exception e){
		e.printStackTrace();
	  }
	}
    
    // 테이블에 동일한 메일이 있는지 검사
    public boolean checkEmail(String email) {
    	MemberDto memDto = memberService.findByEmail(email);
    	if(memDto == null)
    		return false;
    	return true;
    }
    
    // 해당 이메일을 가지고있는 멤버 반환
    public MemberDto findEmail(String email) {
    	MemberDto memDto = memberService.findByEmail(email);
    	return memDto;
    }
 
}

