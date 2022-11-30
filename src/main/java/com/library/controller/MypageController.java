package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MypageController {
	
	@RequestMapping("login/mypage")
	public String main(Model model) {
		model.addAttribute("data", "hello rozy~!");
		return "mypage/updateForm";
	}
	
	
	@GetMapping("login/mypage")
	public String updateForm() {
		
		return "mypage/updateForm";
	}
	
	/** 회원 수정하기 전 비밀번호 확인 **/
    @GetMapping("/checkPwd")
    public String checkPwdView(){
        return "mypage/check-pwd";
    }
}
