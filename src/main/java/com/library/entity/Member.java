package com.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.library.constant.Role;

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
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String email;
	private String password;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private Role role;
}
