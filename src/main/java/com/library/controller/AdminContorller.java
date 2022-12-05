package com.library.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.dto.MemberResponseDto;
import com.library.entity.Member;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminContorller {
	private final MemberService memberService;

	@GetMapping(value = "/accounts")
	public String adminAccountsPage(Model model) {
		model.addAttribute("memberResponseDto", memberService.findAll());
		return "admin/accounts";
	}

	@PutMapping(value = "/memberUpdate")
	public String adminUpdate(MemberResponseDto memberResDto, Model model) {
		memberService.updateMember_admin(memberResDto);
		return "redirect:/admin/accounts";
	}

	@PostMapping(value = "/memberSave")
	public String adminSave(MemberResponseDto memberResDto, Model model) {
		memberService.saveMember(memberResDto);
		return "redirect:/admin/accounts";
	}

	@DeleteMapping(value = "/memberDelete")
	public String adminDelete(MemberResponseDto memberResDto, Model model) {
		memberService.deleteMember(memberResDto);
		return "redirect:/admin/accounts";
	}

	// ajax를 통한 ID,Email체크
	@ResponseBody
	@PostMapping(value = "/check")
	public HashMap<String, String> check(@RequestParam("id") String id, @RequestParam("email") String email) {
		boolean checkID = memberService.findById(id);
		boolean checkEmail = true;
		Member mem = memberService.findByEmail(email);
		if(mem == null)
			checkEmail = false;
		System.out.println("id : " + checkID);
		System.out.println("email : " + checkEmail);
		HashMap<String, String> map = new HashMap<String, String>();
		if (checkID || checkEmail) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
	}
}
