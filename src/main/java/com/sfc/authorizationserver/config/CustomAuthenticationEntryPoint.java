package com.sfc.authorizationserver.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final String loginUrl;
	private final List<String> openEndpoints;

	public CustomAuthenticationEntryPoint(String loginUrl, List<String> openEndpoints) {
		this.loginUrl = loginUrl;
		this.openEndpoints = openEndpoints;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.info("inicia Commence para redirect");
		String uri = request.getRequestURI();
		String clientId = request.getParameter("client_id");
		if (openEndpoints.stream().anyMatch(uri::startsWith)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		} else {
			String redirectUrl = loginUrl;
			if (clientId != null && !clientId.isEmpty()) {
				redirectUrl = loginUrl + (loginUrl.contains("?") ? "&" : "?") + "client_id=" + clientId;
			}
			new LoginUrlAuthenticationEntryPoint(redirectUrl).commence(request, response, authException);
		}
	}
}
