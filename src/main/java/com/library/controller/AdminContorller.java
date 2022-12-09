package com.library.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.constant.Role;
import com.library.dto.MemberDto;
import com.library.dto.MemberResponseDto;
import com.library.service.MemberLogService;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminContorller {
	private final MemberService memberService;
	private final MemberLogService memberLogService;

	@GetMapping(value = "/accounts")
	public String adminAccountsPage(Model model) {
		model.addAttribute("memberResponseDto", memberService.findAll());
		return "admin/accounts";
	}

	@PutMapping(value = "/memberUpdate")
	public String adminUpdate(MemberResponseDto memberResDto, Model model, HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		memberService.updateMember_admin(memberResDto, myid, myRole, ip);
		return "redirect:/admin/accounts";
	}

	@PostMapping(value = "/memberSave")
	public String adminSave(MemberResponseDto memberResDto, Model model, HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		memberService.saveMember(memberResDto, myid, myRole, ip);
		return "redirect:/admin/accounts";
	}

	@DeleteMapping(value = "/memberDelete")
	public String adminDelete(MemberResponseDto memberResDto, Model model, HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		memberService.deleteMember(memberResDto, myid, myRole, ip);
		return "redirect:/admin/accounts";
	}

	// ajax를 통한 ID,Email체크
	@ResponseBody
	@PostMapping(value = "/check")
	public HashMap<String, String> check(@RequestParam("email") String email) {
		boolean checkEmail = true;
		MemberDto memDto = memberService.findByEmail(email);
		if(memDto == null)
			checkEmail = false;
		HashMap<String, String> map = new HashMap<String, String>();
		if (checkEmail) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
	}
	
	// 관리자 로그
	@GetMapping(value = "/log")
	public String adminLogPage(Model model) {
		model.addAttribute("memberLogList", memberLogService.findAll());
		return "admin/accountLog";
	}
}
