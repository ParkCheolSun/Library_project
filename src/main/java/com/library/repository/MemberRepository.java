package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Member findById(String id);
	Member findByEmail(String email);
}
