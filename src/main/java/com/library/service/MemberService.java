package com.library.service;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.library.entity.Member;
import com.library.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final MemberRepository memberRepository;

	public Member saveMember(Member member) {
		System.out.println("member 저장전....");
		System.out.println(member.toString());
		validateDuplicateMember(member);
		System.out.println("member 저장중....");
		return memberRepository.save(member);
	}
	
	// ajax를 통한 ID체크
	public boolean findById(String id) {
		Member mem = memberRepository.findById(id);
		if(mem != null) {
			return true;
		}
		return false;
	}

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