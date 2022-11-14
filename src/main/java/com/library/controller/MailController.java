package com.library.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.entity.Member;
import com.library.service.MailService;

@Controller
@RequestMapping(value = "/email")
@EnableAsync
public class MailController {
	@Autowired
	private MailService mailService;
	private Map<String, String> codeMap = new HashMap<String, String>();

	@PostMapping
	@ResponseBody
	@RequestMapping(value = "/sendEmail")
	public ResponseEntity sendSimpleMail(@RequestParam("email") String email, @RequestParam("action") String action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseEntity resEntity = null;
		HashMap<String, String> map = new HashMap<String, String>();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		boolean emailCheck = isValidEmail(email);
		boolean findEmail = mailService.checkEmail(email);
		if(action.equals("find")) {
			findEmail = false;
		}
		if (emailCheck == false || findEmail == true) {
			String result = "False";
			map.put("result", result);
			resEntity = new ResponseEntity(map, HttpStatus.OK);
			return resEntity;
		}
		String checkCode = String.format("%05d", (int) (Math.random() * 100000));
		codeMap.put(email, checkCode);
		mailService.sendMail(email, "Library 인증메일", "인증번호 : " + checkCode);
		String result = "Success";
		map.put("result", result);
		resEntity = new ResponseEntity(map, HttpStatus.OK);
		return resEntity;
	}

	@PostMapping
	@ResponseBody
	@RequestMapping(value = "/checkEmail")
	public ResponseEntity codeCheck(@RequestParam("email") String email, @RequestParam("code") String code, @RequestParam("action") String action, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseEntity resEntity = null;
		HashMap<String, String> map = new HashMap<String, String>();
		String result = "False";
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		if(codeMap.get(email).equals(code)) {
			result = "Success";
		}
		
		System.out.println("action : " + action);
		if(action.equals("find")) {
			Member mem = mailService.findEmail(email);
			System.out.println(mem.getId());
			result = mem.getId();
		} else if(action.equals("find2")) {
			Member mem = mailService.findEmail(email);
			if(mem.getId() == null)
				result = "NotID";
			result = mem.getId();
		}
		map.put("result", result);
		resEntity = new ResponseEntity(map, HttpStatus.OK);
		return resEntity;
	}

	private static boolean isValidEmail(String email) {
		boolean err = false;
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		if (m.matches() ) {
			err = true;
		}
		return err;
	}
}
