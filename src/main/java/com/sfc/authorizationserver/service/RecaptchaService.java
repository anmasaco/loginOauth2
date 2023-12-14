package com.sfc.authorizationserver.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RecaptchaService {

	@Value("${recaptcha.secret-key}")
	private String recaptchaSecretKey;

	private final RestTemplate restTemplate;

	@Autowired
	public RecaptchaService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public boolean isResponseValid(String clientRecaptchaResponse) {
		final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(RECAPTCHA_URL)
				.queryParam("secret", recaptchaSecretKey).queryParam("response", clientRecaptchaResponse);

		ResponseEntity<Map> recaptchaResponseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST,
				null, Map.class);

		Map<String, Object> responseBody = recaptchaResponseEntity.getBody();

		boolean recaptchaSuccess = (Boolean) responseBody.get("success");

		return recaptchaSuccess;
	}
}
