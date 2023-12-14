package com.sfc.authorizationserver.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sfc.authorizationserver.repository.DatabaseRegisteredClientRepository;

@Configuration
public class OAuth2ServerConfig {

	@Autowired
	private DatabaseRegisteredClientRepository databaseRegisteredClientRepository;

	/*
	 * @Bean public RegisteredClientRepository registeredClientRepository() { return
	 * databaseRegisteredClientRepository; }
	 */

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
		return context -> {
			Authentication principal = context.getPrincipal();
			if (context.getTokenType().getValue().equals("id_token")) {
				context.getClaims().claim("token_type", "id token");
			}
			if (context.getTokenType().getValue().equals("access_token")) {
				context.getClaims().claim("token_type", "access token");
				Set<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				context.getClaims().claim("roles", roles).claim("username", principal.getName());
			}
		};
	}

	@Bean
	public ClientSettings clientSettings() {
		return ClientSettings.builder().requireProofKey(true).build();
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().issuer("http://localhost:9000").build();
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = generateRSAKey();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
	}

	private RSAKey generateRSAKey() {
		KeyPair keyPair = generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
	}

	private KeyPair generateKeyPair() {
		KeyPair keyPair;
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			keyPair = generator.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		}
		return keyPair;
	}
}
