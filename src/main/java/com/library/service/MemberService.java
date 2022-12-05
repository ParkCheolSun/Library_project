package com.library.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.constant.WorkNumber;
import com.library.dto.MemberResponseDto;
import com.library.entity.Member;
import com.library.entity.MemberLog;
import com.library.repository.MemberLogRepository;
import com.library.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;
	private final MemberLogRepository memberLogRepository;

	// 계정 생성
	public Member saveMember(Member member) {
		validateDuplicateMember(member);
		Member mem = memberRepository.save(member);
		if(mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 계정 생성 완료";
			MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.CREATE_MEMBER, contents);
			memberLogRepository.save(memLog);
		}
		return mem;
	}
	
	// 계정 생성(관리자)
		public Member saveMember(MemberResponseDto member) {
			Member temp = Member.createMember(member);
			validateDuplicateMember(temp);
			Member mem = memberRepository.save(temp);
			if(mem.getId() != null) {
				String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 계정 생성 완료";
				MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.CREATE_MEMBER, contents);
				memberLogRepository.save(memLog);
			}
			return mem;
		}
	
	
	// 계정 수정(비밀번호 변경)
	public Member updateMember(Member member) {
		Member mem = memberRepository.save(member);
		if(mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 비밀번호 변경 완료";
			MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.UPDATE_MEMBER, contents);
			memberLogRepository.save(memLog);
		}
		return mem;
	}
	
	// 계정 수정(관리자)
	public Member updateMember_admin(MemberResponseDto memberResDto) {
		Member temp = change(memberRepository.findById(memberResDto.getId()),memberResDto);
		Member mem = memberRepository.save(temp);
		if(mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 수정 완료";
			MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.UPDATE_MEMBER, contents);
			memberLogRepository.save(memLog);
		}
		return mem;
	}
	
	// 계정 삭제(관리자)
	public void deleteMember(MemberResponseDto memberResDto) {
		memberRepository.deleteById(memberResDto.getNumber());
		String contents = "ID : " + memberResDto.getId() + "/ Name : " + memberResDto.getName() + " 삭제 완료";
		MemberLog memLog = MemberLog.createMemberLog(Member.createMember(memberResDto), WorkNumber.UPDATE_MEMBER, contents);
		memberLogRepository.save(memLog);
	}
	
	private Member change(Member ori, MemberResponseDto res) {
		ori.setId(res.getId());
		ori.setName(res.getName());
		ori.setAddress(res.getAddress());
		ori.setAddress_detail(res.getAddress_detail());
		ori.setEmail(res.getEmail());
		ori.setRole(res.getRole());
		return ori;
	}
	
	// 모든 유저 가져오기
	public List<MemberResponseDto> findAll(){
		return MemberResponseDto.createMemDto(memberRepository.findAll());
	}
	
	// ajax를 통한 ID체크
	public boolean findById(String id) {
		Member mem = memberRepository.findById(id);
		if(mem != null) {
			return true;
		}
		return false;
	}
	
	// Email 체크
	public Member findByEmail(String email) {
		System.out.println(email);
		Member mem = memberRepository.findByEmail(email);
		return mem;
	}
	
	// ID중복 확인
	private void validateDuplicateMember(Member member) {
		Member findMember = memberRepository.findById(member.getId());
		if (findMember != null) {
			throw new IllegalStateException("이미 가입된 회원입니다."); // 이미 가입된 회원의 경우 예외를 발생시킨다.
		}
	}

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		Member member = memberRepository.findById(id);
		if (member == null) {
			throw new UsernameNotFoundException(id);
		}
		return User.builder().username(member.getEmail()).password(member.getPassword())
				.roles(member.getRole().toString()).build();
	}
}
