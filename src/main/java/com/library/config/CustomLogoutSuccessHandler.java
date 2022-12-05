package com.library.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.library.constant.WorkNumber;
import com.library.entity.Member;
import com.library.entity.MemberLog;
import com.library.repository.MemberLogRepository;
import com.library.service.MemberService;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	@Autowired
	private MemberService MemberService;
	@Autowired
	private MemberLogRepository memberLogRepository;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Member mem = MemberService.findByEmail(userDetails.getUsername());
		String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 로그아웃 완료";
		MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.LOGOUT_MEMBER, contents);
		memberLogRepository.save(memLog);
		
		HttpSession session = request.getSession();
		session.setAttribute("mes", "USERLogout");
		super.onLogoutSuccess(request, response, authentication);
	}
}
