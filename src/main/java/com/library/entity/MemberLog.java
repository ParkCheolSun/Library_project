package com.library.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.library.constant.Role;
import com.library.constant.WorkNumber;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "memberLog")
@Getter
@Setter
@ToString
@EntityListeners(value = { AuditingEntityListener.class })
public class MemberLog {
	@Id
	@Column(name = "logNumber")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long logNumber;
	
	@NotNull
	private String userID;
	
	@CreatedDate
	private LocalDateTime logDate;
	
	private String ipAddress;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private WorkNumber workNumber;
	
	private String contents;
	
	public static MemberLog createMemberLog(Member mem, WorkNumber workNumber, String contents) {
		MemberLog memLog = new MemberLog();
		memLog.setUserID(mem.getId());
		memLog.setIpAddress(mem.getIpAddress());
		memLog.setRole(mem.getRole());
		memLog.setWorkNumber(workNumber);
		memLog.setContents(contents);
		return memLog;
	}
	
	public static MemberLog createMemberLog(Member mem, WorkNumber workNumber, String contents, String myid, Role role) {
		MemberLog memLog = new MemberLog();
		memLog.setUserID(myid);
		memLog.setIpAddress(mem.getIpAddress());
		memLog.setRole(role);
		memLog.setWorkNumber(workNumber);
		memLog.setContents(contents);
		return memLog;
	}
}
