package com.library.entity;

import java.net.InetAddress;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.library.constant.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "category")
@Getter
@Setter
@ToString
public class Category {
	@Id
	@Column(name = "category_id")
	private Long category_id;
	
	private String category_name;
}
