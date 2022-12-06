package com.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.library.dto.MemberLogDto;
import com.library.repository.MemberLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberLogService {
	private final MemberLogRepository memberLogRepository;
	
	//전체 로그 출력
	public List<MemberLogDto> findAll(){
		return MemberLogDto.createMemLogDto(memberLogRepository.findAll());
	}
}
