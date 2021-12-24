package com.baemin.util;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baemin.login.LoginService;

public class UserInfoSessionUpdate {

	public static void sessionUpdate(String value, String valueType, LoginService user) {
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		
		LoginService loginService = (LoginService) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(valueType.equals("nickname")) {
			loginService.getUser().setNickname(value);
		} 
		else  if(valueType.equals("password")) {
			loginService.getUser().setPassword(value);
		}
		else if(valueType.equals("point")) {
			int point = loginService.getUser().getPoint() + Integer.parseInt(value);
			loginService.getUser().setPoint(point);
		}

		SecurityContext sc = SecurityContextHolder.getContext();

		sc.setAuthentication(new UsernamePasswordAuthenticationToken(loginService, null, user.getAuthorities()));
		
		session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
	}
}
