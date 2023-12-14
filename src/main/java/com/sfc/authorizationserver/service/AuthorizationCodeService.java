package com.sfc.authorizationserver.service;

import com.sfc.authorizationserver.entity.AuthorizationCode;
import com.sfc.authorizationserver.repository.AuthorizationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationCodeService {

	private final AuthorizationCodeRepository authorizationCodeRepository;

	@Autowired
	public AuthorizationCodeService(AuthorizationCodeRepository authorizationCodeRepository) {
		this.authorizationCodeRepository = authorizationCodeRepository;
	}

	public void storeAuthorizationCode(AuthorizationCode authCode) {
		authorizationCodeRepository.save(authCode);
	}

	public AuthorizationCode findByAuthCode(String code) {
		return authorizationCodeRepository.findByAuthCode(code);
	}

	public void updateAuthorizationCode(AuthorizationCode authCode) {
		authorizationCodeRepository.save(authCode);
	}
}
