package com.library.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.library.constant.Role;
import com.library.entity.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {
	@NotBlank(message = "아이디은 필수 입력 값입니다.")
	private String id;
	
	@NotBlank(message = "이름은 필수 입력 값입니다.")
	private String name;
	
	@Email(message = "이메일 형식으로 입력해주세요.")
	@NotBlank(message = "이메일은 필수 입력 값입니다.")
	private String email;
	
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
	//@Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
	@NotBlank(message = "비밀번호는 필수 입력 값입니다.")
	private String password;
	
	private String address;
	
	private String address_detail;
	
	private String gender;
	
	private Role role;
	
	public static MemberDto createMemberDto(Member mem) {
		MemberDto memDto = new MemberDto();
		memDto.id = mem.getId();
		memDto.name = mem.getName();
		memDto.email = mem.getEmail();
		memDto.password = mem.getPassword();
		memDto.address = mem.getAddress();
		memDto.address_detail = mem.getAddress_detail();
		memDto.gender = mem.getGender();
		memDto.role = mem.getRole();
		return memDto;
	}
}
