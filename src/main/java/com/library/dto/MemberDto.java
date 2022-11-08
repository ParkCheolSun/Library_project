package com.library.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String id;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@Email(message = "이메일 형식으로 입력해주세요.")
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
	
	@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String password;
	
	private String address;
	
	private String gender;
}
