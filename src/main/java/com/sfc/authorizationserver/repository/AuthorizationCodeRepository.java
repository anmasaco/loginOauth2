package com.sfc.authorizationserver.repository;

import com.sfc.authorizationserver.entity.AuthorizationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizationCodeRepository extends JpaRepository<AuthorizationCode, Long> {
	AuthorizationCode findByAuthCode(String authCode);
}
