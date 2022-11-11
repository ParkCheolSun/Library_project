package com.library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.library.dto.MemberDto;
import com.library.entity.Member;
import com.library.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/login")
@Controller
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	// 회원가입
	@GetMapping(value = "/signUp")
	public String memberForm(HttpServletRequest request, Model model) {
		Map<String, ?> flashMap =RequestContextUtils.getInputFlashMap(request);
	    if(flashMap!=null) {
	    	MemberDto memberDto =(MemberDto)flashMap.get("memberDto");
	        model.addAttribute("memberDto", memberDto);
	        System.out.println("redirect 성공!");
	    } else {
	    	model.addAttribute("memberDto", new MemberDto());
	    }
		return "member/SignUpForm";
	}
	
	// 로그인
	@PostMapping(value = "/signIn")
	public String memberSignIn(Model model) {
		return "member/SignUpForm";
	}
	
	// ajax를 통한 ID체크
	@ResponseBody
	@PostMapping(value = "/idCheck")
	public HashMap<String, String> idCheck(@RequestParam("id") String id) {
		boolean check = memberService.findById(id);
		 HashMap<String, String> map = new HashMap<String, String>();
		if(check) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
	}
	

	@PostMapping(value = "/save")
	public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		System.out.println(memberDto.toString());
		if (bindingResult.hasErrors()) {
			System.out.println("bindingResult : " + bindingResult.toString());
			List<ObjectError> list =  bindingResult.getAllErrors();
            for(ObjectError e : list) {
                 System.out.println(e.getDefaultMessage());
            }
			redirectAttributes.addFlashAttribute("memberDto", memberDto);
			return "member/SignUpForm";
			//return "redirect:/login/signUp";
		}
		try {
			System.out.println("정상구문!!!!");
			Member member = Member.createMember(memberDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/SignUpForm";
		}
		return "redirect:/";
	}
	
	@ResponseBody
	@PostMapping(value = "/findEmail")
	public HashMap<String, String> findId(@RequestParam("email") String email) {
		Member member = memberService.findByEmail(email);
		System.out.println(member);
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(member.getId() == null) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
	}
	
	@GetMapping(value = "/find")
	public String findId(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "member/FindMemberForm";
	}
}
