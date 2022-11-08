package com.library.entity;

import java.net.InetAddress;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.library.constant.Role;
import com.library.dto.MemberDto;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member {
	
	@Id
	@Column(name = "mNumber")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long mNumber;
	
	@Column(name = "id", unique = true)
	private String id;					// 아이디
	
	@NotNull
	private String name;				// 이름
	
	@Column(unique = true)
	@NotNull
	private String email;				// 이메일
	
	@NotNull
	private String password;			// 비밀번호
	private String address;				// 주소
	private String gender;				// 성별
	
	@Enumerated(EnumType.STRING)
	private Role role;					// 권한
	
	private LocalDateTime lastLogin;	// 마지막 접속 날짜
	
	private InetAddress ipAddress;		// 접속한 ip주소
	
	public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
		Member member = new Member();
		member.setId(memberDto.getId());
		member.setName(memberDto.getName());
		member.setEmail(memberDto.getEmail());
		member.setAddress(memberDto.getAddress());
		String password = passwordEncoder.encode(memberDto.getPassword());
		member.setPassword(password);
		member.setAddress(memberDto.getAddress());
		member.setGender(memberDto.getGender());
		member.setRole(Role.USER);
		return member;
	}
	/*
	// 시큐리티 없을때 테스트용으로 사용
	public static Member createMember(MemberDto memberDto) {
		Member member = new Member();
		member.setId(memberDto.getId());
		System.out.println(memberDto.getId());
		member.setName(memberDto.getName());
		member.setEmail(memberDto.getEmail());
		member.setAddress(memberDto.getAddress());
		member.setPassword(memberDto.getPassword());
		member.setAddress(memberDto.getAddress());
		member.setGender(memberDto.getGender());
		member.setRole(Role.USER);
		return member;
	}*/
}
