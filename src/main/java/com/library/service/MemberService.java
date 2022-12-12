package com.library.service;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.constant.Role;
import com.library.constant.WorkNumber;
import com.library.dto.MemberDto;
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
		if (mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 계정 생성 완료";
			MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.CREATE_MEMBER, contents);
			memberLogRepository.save(memLog);
		}
		return mem;
	}

	// 계정 생성(관리자)
	public Member saveMember(MemberResponseDto member, String myid, Role myRole, String ip) {
		Member temp = Member.createMember(member);
		validateDuplicateMember(temp);
		Member mem = memberRepository.save(temp);
		if (mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 계정 생성 완료";
			MemberLog memLog = MemberLog.createMemberLog(WorkNumber.CREATE_MEMBER, contents, myid, myRole, ip);
			memberLogRepository.save(memLog);
		}
		return mem;
	}

	// 계정 수정(비밀번호 변경)
	public MemberDto updateMember(MemberDto memDto) {
		Member temp = change(memberRepository.findByEmail(memDto.getEmail()), memDto);
		Member mem = memberRepository.save(temp);
		if (mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 비밀번호 변경 완료";
			MemberLog memLog = MemberLog.createMemberLog(mem, WorkNumber.UPDATE_MEMBER, contents);
			memberLogRepository.save(memLog);
		}
		return MemberDto.createMemberDto(mem);
	}

	// 계정 수정(마이페이지)
	public MemberDto updateMyPage(@Valid MemberDto memDto, String myid, Role myRole, String ip) {
		Member temp = change(memberRepository.findById(memDto.getId()), memDto);
		Member mem = memberRepository.save(temp);
		if (mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 마이페이지 정보 수정 완료";
			MemberLog memLog = MemberLog.createMemberLog(WorkNumber.UPDATE_MEMBER, contents, myid, myRole, ip);
			memberLogRepository.save(memLog);
		}
		return MemberDto.createMemberDto(mem);
	}

	// 계정 수정(관리자)
	public Member updateMember_admin(MemberResponseDto memberResDto, String myid, Role myRole, String ip) {
		Member temp = change(memberRepository.findBymNumber(memberResDto.getNumber()), memberResDto);
		Member mem = memberRepository.save(temp);
		if (mem.getId() != null) {
			String contents = "ID : " + mem.getId() + "/ Name : " + mem.getName() + " 수정 완료";
			MemberLog memLog = MemberLog.createMemberLog(WorkNumber.UPDATE_MEMBER, contents, myid, myRole, ip);
			memberLogRepository.save(memLog);
		}
		return mem;
	}

	// 계정 삭제(관리자)
	public void deleteMember(MemberResponseDto memberResDto, String myid, Role myRole, String ip) {
		String id = memberResDto.getId();
		String name = memberResDto.getName();
		memberRepository.deleteById(memberResDto.getNumber());
		String contents = "ID : " + id + "/ Name : " + name + " 삭제 완료";
		MemberLog memLog = MemberLog.createMemberLog(WorkNumber.DELETE_MEMBER, contents, myid, myRole, ip);
		memberLogRepository.save(memLog);
	}

	// 계정 삭제(마이페이지)
	public void deleteMember(MemberDto memberDto, String myid, Role myRole, String ip) {
		String id = memberDto.getId();
		String name = memberDto.getName();
		Member temp = memberRepository.findById(id);
		memberRepository.deleteById(temp.getMNumber());
		String contents = "ID : " + id + "/ Name : " + name + " 회원탈퇴 완료";
		MemberLog memLog = MemberLog.createMemberLog(WorkNumber.DELETE_MEMBER, contents, myid, myRole, ip);
		memberLogRepository.save(memLog);
		contents = "ID : " + id + "/ Name : " + name + " 회원탈퇴 인원 로그아웃 완료";
		memLog = MemberLog.createMemberLog(WorkNumber.DELETE_MEMBER, contents, myid, myRole, ip);
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

	private Member change(Member ori, MemberDto dto) {
		System.out.println(dto);
		ori.setName(dto.getName());
		ori.setAddress(dto.getAddress());
		ori.setAddress_detail(dto.getAddress_detail());
		ori.setEmail(dto.getEmail());
		ori.setGender(dto.getGender());
		if (dto.getRole() != null)
			ori.setRole(dto.getRole());
		if (!dto.getPassword().isEmpty())
			ori.setPassword(dto.getPassword());
		return ori;
	}

	// 모든 유저 가져오기
	public List<MemberResponseDto> findAll() {
		return MemberResponseDto.createMemDto(memberRepository.findAll());
	}

	// ajax를 통한 ID체크
	public boolean findById(String id) {
		Member mem = memberRepository.findById(id);
		if (mem != null) {
			return true;
		}
		return false;
	}

	// Email 체크
	public MemberDto findByEmail(String email) {
		Member mem = memberRepository.findByEmail(email);
		if (mem != null)
			return MemberDto.createMemberDto(mem);
		return null;
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
