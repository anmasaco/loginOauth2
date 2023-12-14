package com.sfc.authorizationserver.config;

import com.sfc.authorizationserver.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

@Configuration
public class AuthenticationConfig {

	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(customAuthenticationProvider);
	}
}
