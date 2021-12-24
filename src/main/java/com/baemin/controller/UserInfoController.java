package com.baemin.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.baemin.login.LoginService;
import com.baemin.service.UserService;
import com.baemin.util.UserInfoSessionUpdate;

@Controller
public class UserInfoController {

	@Autowired
	private BCryptPasswordEncoder encodePwd;
	
	@Autowired
	private UserService userService;
	
	@PatchMapping("/user/info")
	public ResponseEntity<String> modifyInfo(String value, String valueType, String prevPassword, 
			@AuthenticationPrincipal LoginService user, HttpSession session) {
		// value = 변경할 값
		// valueType = password, nickname, phone
		long userId = user.getUser().getId();
		String msg = "";
		
		switch (valueType) {
		case "password":
			if(!encodePwd.matches(prevPassword, user.getPassword())) {
				return new ResponseEntity<String>("현재 비밀번호가 일치하지 않습니다", HttpStatus.OK);
			} 
			value = encodePwd.encode(value);
			msg = "비밀번호가 변경되었습니다";
			break;
			
		case "nickname":
			msg = "닉네임이 변경되었습니다";
			break;
		case "phone":
			msg = "전화번호가 변경되었습니다";
			session.setMaxInactiveInterval(0);
			session.setAttribute("authNum", null);
			break;
		}
		
			
		
		userService.modifyInfo(userId, valueType, value);
		UserInfoSessionUpdate.sessionUpdate(value, valueType, user);
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
	
	
	
	@PostMapping("/send/authNum")
	private ResponseEntity<String> authNum(String phone, HttpSession session){
		System.out.println("인증번호");
		System.out.println("전화번호 : " + phone);
		String authNum = "";
		
		for(int i=0;i<4;i++) {
			authNum += (int)(Math.random() * 10);
		}
		
		System.out.println(authNum);
		Map<String, Object> authNumMap = new HashMap<>();
		
		
		long createTime = System.currentTimeMillis(); // 인증번호 생성시간
		long endTime = createTime + (300 *1);	// 인증번호 만료시간
		
		authNumMap.put("createTime", createTime);
		authNumMap.put("endTime", endTime);
		authNumMap.put("authNum", authNum);
		
		session.setMaxInactiveInterval(300);
		session.setAttribute("authNum", authNumMap);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@PostMapping("/send/authNumCheck")
	private ResponseEntity<String> authNumCheck(String authNum, HttpSession session){
		Map<String, Object> sessionAuthNumMap = (Map<String, Object>) session.getAttribute("authNum");
		
		System.out.println(authNum);
		System.out.println(sessionAuthNumMap);
		String msg = "";
		
		if(sessionAuthNumMap == null) {
			msg = "인증번호를 전송해주세요";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
		String sessionAuthNum = (String) sessionAuthNumMap.get("authNum");
		
		
		if(!authNum.equals(sessionAuthNum)) {
			msg = "인증번호가 일치하지 않습니다";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		} 
		
		// 인증번호 만료시간
		long endTime = (long) sessionAuthNumMap.get("endTime");
		
		// 현재시간이 만료시간이 지났다면
		if(System.currentTimeMillis() > endTime) {
			msg = "인증시간이 만료되었습니다";
			return new ResponseEntity<String>(msg, HttpStatus.BAD_REQUEST);
		}
		
		msg = "전화번호를 변경하시겠습니까?";
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);
	}
	
}
