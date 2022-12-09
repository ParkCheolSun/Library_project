package com.library.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.library.constant.WorkNumber;
import com.library.dto.MemberDto;
import com.library.entity.Member;
import com.library.entity.MemberLog;
import com.library.repository.MemberLogRepository;
import com.library.service.MemberService;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired
	private MemberService MemberService;
	@Autowired
	private MemberLogRepository memberLogRepository;
	
	public CustomLoginSuccessHandler(String defaultTargetUrl) {
		setDefaultTargetUrl(defaultTargetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		MemberDto memDto = MemberService.findByEmail(userDetails.getUsername());
		HttpSession session = request.getSession();
		
		if (session != null) {
			String redirectUrl = (String) session.getAttribute("prevPage");
			if (redirectUrl != null) {
				session.removeAttribute("prevPage");
				getRedirectStrategy().sendRedirect(request, response, redirectUrl);
			} else {
				super.onAuthenticationSuccess(request, response, authentication);
			}
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
		session.setAttribute("ipaddress", getClientIp(request));
		session.setAttribute("name", memDto.getName() + "님 환영합니다!");
		session.setAttribute("id", memDto.getId());
		session.setAttribute("Role", memDto.getRole());
		String contents = "ID : " + memDto.getId() + "/ Name : " + memDto.getName() + " 로그인 완료";
		Member mem = Member.createMember(memDto);
		mem.setRole(memDto.getRole());
		MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.LOGIN_MEMBER, contents, getClientIp(request));
		memberLogRepository.save(memLog);
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
