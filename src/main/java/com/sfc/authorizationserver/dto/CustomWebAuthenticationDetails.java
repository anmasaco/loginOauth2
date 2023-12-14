package com.sfc.authorizationserver.dto;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

	private final HttpServletRequest request;

	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}
}
