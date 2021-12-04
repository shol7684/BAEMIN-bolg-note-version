package com.baemin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.remoting.soap.SoapFaultException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.baemin.dto.Join;

@Controller
public class UserController {
	
	@GetMapping("/myPage")
	public String myPage() {
		return "user/myPage";
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}
	
	
	@PostMapping("/join")
	public String joinProc(@Valid Join join, BindingResult bindingResult, Model model) {
		System.out.println(join);
		
		if(bindingResult.hasErrors()) {
			System.out.println("에러");
			List<FieldError> list = bindingResult.getFieldErrors();
			System.out.println(list.get(0).getField());;
			System.out.println(list.get(0).getDefaultMessage());
			
			model.addAttribute("msg", list.get(0).getDefaultMessage());

			
			System.out.println(list);
			return "user/join";
			
		} else {
			System.out.println("에러없음");
		}
		
		
		return "redirect:/join";
	}
}
