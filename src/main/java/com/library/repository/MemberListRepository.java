package com.library.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.constant.Role;
import com.library.entity.Member;

@Repository
public interface MemberListRepository extends JpaRepository<Member, Long> {

	public List<Member> findBymNumber(Long mNumber);

	public List<Member> findByid(String id);

	public List<Member> findByname(String name);

	public List<Member> findBygender(String gender);

	public List<Member> findByemail(String email);

	public List<Member> findBylastLogin(LocalDateTime lastLogin);

	public List<Member> findByaddress(String address);

	public List<Member> findByaddress(Role role) {
		Member user = memberRe
	}
	
	
	
	
	// ajax를 통한 ID체크
		public boolean findById(String id) {
			Member mem = memberRepository.findById(id);
			if(mem != null) {
				return true;
			}
			return false;

}
