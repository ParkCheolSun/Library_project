package com.library.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = { AuditingEntityListener.class })
@MappedSuperclass // 공통 매핑 정보가 필요할 때 사용하는 어노테이션으로, 부모 클래스를 상속 받는 자식 클래스에 매핑정보만 제공한다.
@Getter
@Setter
public class BaseTimeEntity {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime registerTime;

	@LastModifiedDate
	private LocalDateTime updateTime;

}
