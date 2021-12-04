package com.baemin.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Join {
	
	@NotBlank(message = "아이디를 입력해주세요")
	@Pattern(regexp = "[A-Za-z0-9]{4,15}$", message = "형식에 맞지않습니다")
	private String username;
	
	
	private String password;
	
	private String email;
	
	private String nickname;
	
	private String phone;
}
