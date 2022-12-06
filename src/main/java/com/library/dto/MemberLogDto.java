package com.library.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.library.constant.Role;
import com.library.constant.WorkNumber;
import com.library.entity.Member;
import com.library.entity.MemberLog;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberLogDto {
	private Long logNumber;
	
	private String userID;
	
	private LocalDateTime logDate;
	
	private String ipAddress;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Enumerated(EnumType.STRING)
	private WorkNumber workNumber;
	
	private String contents;
	
	public static List<MemberLogDto> createMemLogDto(List<MemberLog> memList) {
		List<MemberLogDto> memLogDtoList = new ArrayList<MemberLogDto>();
		MemberLogDto memLogDto; 
		for(MemberLog mem : memList) {
			memLogDto = new MemberLogDto();
			memLogDto.logNumber = mem.getLogNumber();
			memLogDto.userID = mem.getUserID();
			memLogDto.logDate = mem.getLogDate();
			memLogDto.ipAddress = mem.getIpAddress();
			memLogDto.role = mem.getRole();
			memLogDto.workNumber = mem.getWorkNumber();
			memLogDto.contents = mem.getContents();
			memLogDtoList.add(memLogDto);
		}
		return memLogDtoList;
	}
}
