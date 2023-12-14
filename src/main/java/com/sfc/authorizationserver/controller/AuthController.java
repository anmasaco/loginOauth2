package com.sfc.authorizationserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfc.authorizationserver.dto.PerfilamientoResponse;
import com.sfc.authorizationserver.dto.RedirectResponse;
import com.sfc.authorizationserver.dto.UserDTO;
import com.sfc.authorizationserver.entity.AuthorizationCode;
import com.sfc.authorizationserver.entity.OAuthClient;
import com.sfc.authorizationserver.service.ApiClientService;
import com.sfc.authorizationserver.service.AuthorizationCodeService;
import com.sfc.authorizationserver.service.CustomAuthenticationProvider;
import com.sfc.authorizationserver.service.OAuthClientService;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

	private final OAuthClientService oAuthClientService;
	private final CustomAuthenticationProvider customAuthenticationProvider;
	private final ApiClientService apiClientService;

	@Autowired
	private AuthorizationCodeService authorizationCodeService;

	@Autowired
	public AuthController(OAuthClientService oAuthClientService,
			CustomAuthenticationProvider customAuthenticationProvider, HttpServletRequest request,
			@Lazy ApiClientService apiClientService) {
		this.oAuthClientService = oAuthClientService;
		this.customAuthenticationProvider = customAuthenticationProvider;
		this.apiClientService = apiClientService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String name, @RequestParam String password, @RequestParam String client_id) throws AuthenticationException, UnsupportedEncodingException, JsonProcessingException {

		log.info("Inicio del proceso de autenticación...");

		if (invalidParameters(name, password, client_id)) {
			log.error("Parámetros inválidos.");
			return ResponseEntity.badRequest().body("Parámetros inválidos.");
		}

		Authentication result = customAuthenticationProvider
				.authenticate(new UsernamePasswordAuthenticationToken(name, password));

		OAuthClient clientRegistered = oAuthClientService.getClientByClientName(client_id);
		if (clientRegistered == null) {
			log.error("Cliente no registrado.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cliente no registrado.");
		}

		String authorizationCode = generateAndStoreAuthorizationCode(result, clientRegistered);

		String redirectUri = buildRedirectUri(clientRegistered);
		redirectUri += "?code=" + authorizationCode;

		return ResponseEntity.ok().body(Collections.singletonMap("redirectUrl", redirectUri));
	}


	@PostMapping("/perfilamiento")
	public ResponseEntity<PerfilamientoResponse> getPerfilamiento(@RequestParam String name,
			@RequestParam String idAplication) {
		log.info("Inicio del proceso de Perfilamiento...");
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idAplication)) {
			log.error("Parámetros inválidos.");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		try {
			PerfilamientoResponse mapResponsePerfilamiento = apiClientService.getRoleFromApi(name, idAplication);
			return new ResponseEntity<>(mapResponsePerfilamiento, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error durante el proceso de perfilamiento.", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/token")
	public ResponseEntity<?> exchangeCodeForToken(@RequestBody Map<String, String> body)
			throws JsonMappingException, JsonProcessingException {

		String code = body.get("code");
		log.info("Intercambiando código de autorización por token...");
		ObjectMapper objectMapper = new ObjectMapper();
		if (StringUtils.isEmpty(code)) {
			log.error("Parámetros inválidos.");
			return ResponseEntity.badRequest().body("Parámetros inválidos.");
		}
		AuthorizationCode authCodeEntity = authorizationCodeService.findByAuthCode(code);
		if (authCodeEntity == null || authCodeEntity.isUsed()
				|| authCodeEntity.getExpirationDate().isBefore(LocalDateTime.now())) {
			log.error("Código de autorización inválido o expirado.");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de autorización inválido o expirado.");
		}
		UserDTO auth = objectMapper.readValue(authCodeEntity.getUserData(), UserDTO.class);
		authCodeEntity.setUsed(true);
		authorizationCodeService.storeAuthorizationCode(authCodeEntity);
		String accessToken = createTokenForUser(auth, authCodeEntity);
		return ResponseEntity.ok().body(Collections.singletonMap("access_token", accessToken));
	}

	private String createTokenForUser(UserDTO authentication, AuthorizationCode clientRegistered) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("principal", authentication.getPrincipal());
		claims.put("authorities",
				authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()));
		claims.put("client_id", clientRegistered.getClientId());
		claims.put("user_id", clientRegistered.getUserId());
		String token = Jwts.builder().setClaims(claims).signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, "S3cr3tk3y")
				.compact();

		return token;
	}

	private String generateAndStoreAuthorizationCode(Authentication result, OAuthClient clientRegistered)
			throws JsonProcessingException {
		String authorizationCode = UUID.randomUUID().toString();
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonResult = objectMapper.writeValueAsString(result);
		AuthorizationCode authCodeEntity = new AuthorizationCode();
		authCodeEntity.setAuthCode(authorizationCode);
		authCodeEntity.setClientId(clientRegistered.getClient());
		authCodeEntity.setUserId(clientRegistered.getIdAplicacion().intValue());
		authCodeEntity.setExpirationDate(LocalDateTime.now().plusMinutes(10));
		authCodeEntity.setScope("openid");
		authCodeEntity.setRedirectUri(clientRegistered.getRedirectUri());
		authCodeEntity.setUserData(jsonResult);
		authCodeEntity.setUsed(false);
		authCodeEntity.setState("");
		authorizationCodeService.storeAuthorizationCode(authCodeEntity);

		return authorizationCode;
	}

	private String buildRedirectUri(OAuthClient clientRegistered) {
		return clientRegistered.getRedirectUri();
	}

	private boolean invalidParameters(String name, String password, String clientId) {
		return StringUtils.isEmpty(name) || StringUtils.isEmpty(password) || StringUtils.isEmpty(clientId);
	}

}