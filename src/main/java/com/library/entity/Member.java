package com.library.entity;

import java.net.InetAddress;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.library.constant.Role;

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
	@Column(name = "id")
	@NotEmpty(message = "아이디 입력은 필수 입니다.")
	private String id;					// 아이디
	
	@NotEmpty(message = "이름 입력은 필수 입니다.")
	@NotNull
	private String name;				// 이름
	
	@Column(unique = true)
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
	@NotNull
	private String email;				// 이메일
	
	@NotEmpty(message = "비밀번호 입력은 필수 입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
	@NotNull
	private String password;			// 비밀번호
	private String address;				// 주소
	private String gender;				// 성별
	
	@Enumerated(EnumType.STRING)
	private Role role;					// 권한
	
	private LocalDateTime lastLogin;	// 마지막 접속 날짜
	
	private InetAddress ipAddress;		// 접속한 ip주소
}
