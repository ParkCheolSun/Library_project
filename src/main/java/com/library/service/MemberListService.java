package com.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Member;
import com.library.repository.MemberRepository;

@Service
public class MemberListService {
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        memberRepository.findAll().forEach(e -> members.add(e));
        return members;
    }

    public Optional<Member> findById(Long mNumber) {
        Optional<Member> member = memberRepository.findById(mNumber);
        return member;
    }

    public void deleteById(Long mNumber) {
        memberRepository.deleteById(mNumber);
    }

    public Member save(Member member) {
        memberRepository.save(member);
        return member;
    }

    public void updateById(Long mNumber, Member member) {
        Optional<Member> e = memberRepository.findById(mNumber);

        if (e.isPresent()) {
            e.get().setMNumber(member.getMNumber());
            e.get().setId(member.getId());
            e.get().setGender(member.getGender());
            e.get().setName(member.getName());
            e.get().setEmail(member.getEmail());
            e.get().setLastLogin(member.getLastLogin());
            e.get().setAddress(member.getAddress());
            e.get().setRole(member.getRole());
            memberRepository.save(member);
        }
    }
}

