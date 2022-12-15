package com.library.controller;


import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		boolean check = memberService.findById("admin");
		if (check)
			return;
		Member mem;
		MemberDto memDto = new MemberDto();
		memDto.setId("admin");
		memDto.setPassword("admin");
		memDto.setAddress("대전 광역시 대덕구 송촌북로 23번길 7");
		memDto.setAddress_detail("111동 201호");
		memDto.setGender("M");
		memDto.setName("총관리자");
		memDto.setEmail("admin@adminEmail.com");
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
		memDto.setId("manager01");
		memDto.setPassword("manager01");
		memDto.setAddress("대전 동구 판교1길 3");
		memDto.setAddress_detail("5층 연구실");
		memDto.setGender("M");
		memDto.setName("매니저");
		memDto.setEmail("manager@manager.manager");
		mem = Member.createMember(memDto, passwordEncoder);
		password = passwordEncoder.encode(memDto.getPassword());
		mem.setPassword(password);
		mem.setRole(Role.MANAGER);
		memberService.saveMember(mem);
		
		// 사용자 생성(발표용)
		for(int i = 4; i < 14 ; i++) {
			check = memberService.findById(String.valueOf(i));
			if (check)
				return;
			memDto = new MemberDto();
			memDto.setId("User" + String.valueOf(i));
			memDto.setPassword(String.valueOf(i));
			memDto.setAddress("사용자 주소 "+i);
			memDto.setAddress_detail("사용자 상세주소 "+i);
			memDto.setGender("M");
			memDto.setName("사용자"+i);
			memDto.setEmail("User"+i+"@userEmail.com");
			mem = Member.createMember(memDto, passwordEncoder);
			password = passwordEncoder.encode(memDto.getPassword());
			mem.setPassword(password);
			mem.setRole(Role.USER);
			memberService.saveMember(mem);
		}
	}

	// 회원가입 뷰
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

	// 회원가입(DB저장)
	@PostMapping(value = "/save")
	public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			List<ObjectError> list = bindingResult.getAllErrors();
			for (ObjectError e : list) {
				System.out.println(e.getDefaultMessage());
			}
			redirectAttributes.addFlashAttribute("memberDto", memberDto);
			return "member/SignUpForm";
		}
		try {
			Member member = Member.createMember(memberDto, passwordEncoder);
			memberService.saveMember(member);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "member/SignUpForm";
		}
		redirectAttributes.addFlashAttribute("mes", "createUSER");
		return "redirect:/";
	}

	// 아이디 찾기
	@ResponseBody
	@PostMapping(value = "/findEmail")
	public HashMap<String, String> findId(@RequestParam("email") String email) {
		MemberDto memDto = memberService.findByEmail(email);
		HashMap<String, String> map = new HashMap<String, String>();

		if (memDto.getId() == null) {
			map.put("answer", "Fail");
		} else {
			map.put("answer", "Success");
		}
		return map;
	}

	// 아이디/비밀번호 찾기 뷰
	@GetMapping(value = "/find")
	public String findId(Model model) {
		model.addAttribute("memberDto", new MemberDto());
		return "member/FindMemberForm";
	}

	// 비밀번호 찾기(비밀번호 수정)
	@PostMapping(value = "/mod")
	public String newMember(MemberDto memberDto, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) {
		MemberDto memDto = memberService.findByEmail(memberDto.getEmail());
		String password = passwordEncoder.encode(memberDto.getPassword());
		memDto.setPassword(password);
		memberService.updateMember(memDto);
		redirectAttributes.addFlashAttribute("memberDto", new MemberDto());
		redirectAttributes.addFlashAttribute("mes", "modPassword");
		return "redirect:/login/signUp";
	}

	// 로그인 실패
	@GetMapping(value = "/error")
	public String loginError(MemberDto memberDto, Model model) {
		model.addAttribute("mes", "USERLoginFail");
		return "member/SignUpForm";
	}
	
	@ResponseBody
	@GetMapping(value="/userId")
	public String getUserId(Principal principal) {
		return principal.getName();
	}
	
	// 마이페이지
	@GetMapping(value = "/mypage")
	public String mypageView(Model model, HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		MemberDto memDto = memberService.myPagefindById(myid);
		if(memDto == null) {
			memDto = new MemberDto();
			memDto.setId(myid);
			memDto.setAddress("해당 아이디는 정보가 존재하지 않습니다.");
		}
		model.addAttribute("memberDto", memDto);
		return "member/MyPage";
	}
	
	// 마이페이지 수정
	@PutMapping(value = "/mypage/mod")
	public String mypageMod(MemberDto memDto, Model model, HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		if(!memDto.getPassword().isEmpty()) {
			String password = passwordEncoder.encode(memDto.getPassword());
			memDto.setPassword(password);
		}
		MemberDto resultDto = memberService.updateMyPage(memDto, myid, myRole, ip);
		mySession.setAttribute("name", resultDto.getName() + "님 환영합니다!");
		model.addAttribute("memberDto", resultDto);
		return "redirect:/login/mypage";
	}
	
	// 마이페이지 회원탈퇴
	@DeleteMapping(value = "/mypage/delete")
	public String mypageDelete(MemberDto memDto, HttpServletRequest request) {
		HttpSession mySession = request.getSession();
		String myid = (String)mySession.getAttribute("id");
		Role myRole = (Role)mySession.getAttribute("Role");
		String ip = (String)mySession.getAttribute("ipaddress");
		memberService.deleteMember(memDto, myid, myRole, ip);
		return "redirect:/login/logout";
	}
}
