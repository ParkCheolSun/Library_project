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
import com.library.dto.MemberDto;
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
		MemberDto memDto = MemberService.findByEmail(userDetails.getUsername());
		HttpSession session = request.getSession();
		
		if(memDto != null) {
			String contents = "ID : " + memDto.getId() + "/ Name : " + memDto.getName() + " 로그아웃 완료";
			MemberLog memLog = MemberLog.createMemberLog(Member.createMember(memDto), WorkNumber.LOGOUT_MEMBER, contents, getClientIp(request));
			memberLogRepository.save(memLog);
			session.setAttribute("mes", "USERLogout");
		} else {
			session.setAttribute("mes", "USERDelete");
		}
		
		super.onLogoutSuccess(request, response, authentication);
	}
	
	public static String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
