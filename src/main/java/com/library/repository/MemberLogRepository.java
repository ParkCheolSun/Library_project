package com.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.MemberLog;

public interface MemberLogRepository extends JpaRepository<MemberLog, Long> {

}
