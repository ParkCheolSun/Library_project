package com.library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.library.constant.Role;
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

	@PostConstruct
	private void createAdmin() {
		// admin 계정 생성
		boolean check = memberService.findById("1");
		if (check)
			return;
		Member mem;
		MemberDto memDto = new MemberDto();
		memDto.setId("1");
		memDto.setPassword("1");
		memDto.setAddress("관리자 주소");
		memDto.setGender("M");
		memDto.setName("총관리자");
		memDto.setEmail("admin@admin.admin");
		mem = Member.createMember(memDto, passwordEncoder);
		String password = passwordEncoder.encode(memDto.getPassword());
		mem.setPassword(password);
		mem.setRole(Role.ADMIN);
		memberService.saveMember(mem);

		// Manager 계정 생성
		check = memberService.findById("2");
		System.out.println(check);
		if (check)
			return;
		memDto = new MemberDto();
		memDto.setId("2");
		memDto.setPassword("2");
		memDto.setAddress("매니저 주소");
		memDto.setGender("M");
		memDto.setName("매니저");
		memDto.setEmail("manager@manager.manager");
		mem = Member.createMember(memDto, passwordEncoder);
		password = passwordEncoder.encode(memDto.getPassword());
		mem.setPassword(password);
		mem.setRole(Role.MANAGER);
		memberService.saveMember(mem);

		// admin 계정 생성
		check = memberService.findById("3");
		if (check)
			return;
		memDto = new MemberDto();
		memDto.setId("3");
		memDto.setPassword("3");
		memDto.setAddress("사용자 주소");
		memDto.setGender("F");
		memDto.setName("사용자");
		memDto.setEmail("user@user.user");
		mem = Member.createMember(memDto, passwordEncoder);
		password = passwordEncoder.encode(memDto.getPassword());
		mem.setPassword(password);
		mem.setRole(Role.USER);
		memberService.saveMember(mem);
	}

	// 회원가입
	@GetMapping(value = "/signUp")
	public String memberForm(HttpServletRequest request, Model model) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			MemberDto memberDto = (MemberDto) flashMap.get("memberDto");
			model.addAttribute("memberDto", memberDto);
		} else {
			model.addAttribute("memberDto", new MemberDto());
		}
		return "member/SignUpForm";
	}

	// ajax를 통한 ID체크
	@ResponseBody
	@PostMapping(value = "/idCheck")
	public HashMap<String, String> idCheck(@RequestParam("id") String id) {
		boolean check = memberService.findById(id);
		HashMap<String, String> map = new HashMap<String, String>();
		if (check) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
	}

	@PostMapping(value = "/save")
	public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		System.out.println(memberDto.toString());
		if (bindingResult.hasErrors()) {
			System.out.println("bindingResult : " + bindingResult.toString());
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError e : list) {
				System.out.println(e.getDefaultMessage());
			}
			redirectAttributes.addFlashAttribute("memberDto", memberDto);
			return "member/SignUpForm";
			// return "redirect:/login/signUp";
		}
		try {
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

		if (member.getId() == null) {
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

	@PostMapping(value = "/mod")
	public String newMember(MemberDto memberDto, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) {
		Member member = memberService.findByEmail(memberDto.getEmail());
		String password = passwordEncoder.encode(memberDto.getPassword());
		member.setPassword(password);
		memberService.updateMember(member);
		redirectAttributes.addFlashAttribute("memberDto", new MemberDto());
		redirectAttributes.addFlashAttribute("mes", "modPassword");
		return "redirect:/login/signUp";
	}

	@GetMapping(value = "/error")
	public String loginError(MemberDto memberDto, Model model) {
		model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
		return "member/SignUpForm";
	}
}
