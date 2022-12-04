package com.library.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.library.constant.Role;
import com.library.entity.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberResponseDto {
	private Long number;
	private String id;
	private String name;
	private String email;
	private String address;
	private String address_detail;
	private String gender;
	private LocalDateTime registerTime;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static List<MemberResponseDto> createMemDto(List<Member> memList) {
		List<MemberResponseDto> memResDtoList = new ArrayList<MemberResponseDto>();
		MemberResponseDto memResDto; 
		for(Member mem : memList) {
			memResDto = new MemberResponseDto();
			memResDto.number = mem.getMNumber();
			memResDto.id = mem.getId();
			memResDto.name = mem.getName();
			memResDto.email = mem.getEmail();
			memResDto.address = mem.getAddress();
			memResDto.address_detail = mem.getAddress_detail();
			memResDto.gender = mem.getGender();
			memResDto.role = mem.getRole();
			memResDto.registerTime = mem.getRegisterTime();
			memResDtoList.add(memResDto);
		}
		return memResDtoList;
	}
}
