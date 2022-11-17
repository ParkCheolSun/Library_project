package com.library.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


// 엔티티가 저장 또는 수정될 때 자동으로 등록일, 수정일, 등록자, 수정자를 입력해 준다. 일종의 감시자 역할
public class AuditorAwareImpl implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = "";
		if (authentication != null) {
			userId = authentication.getName(); // 현재 로그인한 사용자의 정보를 조회하여 사용자의 이름을 등록자와 수정자로 등록한다.
		}
		return Optional.of(userId);
	}

}
