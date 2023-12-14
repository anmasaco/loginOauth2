package com.sfc.authorizationserver.repository;

import com.sfc.authorizationserver.entity.OAuthClient;
import com.sfc.authorizationserver.service.OAuthClientService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseRegisteredClientRepository implements RegisteredClientRepository {

	@Autowired
	private OAuthClientService oAuthClientService;

	@Override
	public RegisteredClient findById(String id) {
		Optional<OAuthClient> optionalClient = oAuthClientService.getClientById(Long.parseLong(id));
		return optionalClient.map(this::toRegisteredClient).orElse(null);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		OAuthClient oAuthClient = oAuthClientService.getClientByClientName(clientId);
		return oAuthClient != null ? toRegisteredClient(oAuthClient) : null;
	}

	private RegisteredClient toRegisteredClient(OAuthClient oAuthClient) {
		return RegisteredClient.withId(oAuthClient.getId().toString()).clientId(oAuthClient.getClient())
				.clientSecret(oAuthClient.getClientSecret()).clientName(oAuthClient.getIdAplicacion().toString())
				.clientAuthenticationMethod(
						convertToClientAuthenticationMethod(oAuthClient.getClientAuthenticationMethod()))
				.authorizationGrantType(convertToAuthorizationGrantType(oAuthClient.getAuthorizationGranType()))
				.redirectUri(oAuthClient.getRedirectUri()).scope(oAuthClient.getScope()).build();
	}

	private ClientAuthenticationMethod convertToClientAuthenticationMethod(String method) {
		switch (method) {
		case "BASIC_SECRET":
			return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
		default:
			throw new IllegalArgumentException("Invalid client authentication method: " + method);
		}
	}

	private AuthorizationGrantType convertToAuthorizationGrantType(String type) {
		switch (type) {
		case "AUTHORIZATION_CODE":
			return AuthorizationGrantType.AUTHORIZATION_CODE;
		case "PASSWORD":
			return AuthorizationGrantType.PASSWORD;
		case "CLIENT_CREDENTIALS":
			return AuthorizationGrantType.CLIENT_CREDENTIALS;
		// ... puedes añadir más casos según tus necesidades.
		default:
			throw new IllegalArgumentException("Invalid authorization grant type: " + type);
		}
	}

	@Override
	public void save(RegisteredClient registeredClient) {
		OAuthClient oAuthClient = toOAuthClient(registeredClient);
		oAuthClientService.saveClient(oAuthClient);
	}

	private OAuthClient toOAuthClient(RegisteredClient registeredClient) {
		OAuthClient oAuthClient = new OAuthClient();
		oAuthClient.setId(Long.parseLong(registeredClient.getId()));
		oAuthClient.setClient(registeredClient.getClientId());
		oAuthClient.setClientSecret(registeredClient.getClientSecret());

		if (!registeredClient.getClientAuthenticationMethods().isEmpty()) {
			oAuthClient.setClientAuthenticationMethod(
					registeredClient.getClientAuthenticationMethods().iterator().next().getValue());
		}

		if (!registeredClient.getAuthorizationGrantTypes().isEmpty()) {
			oAuthClient.setAuthorizationGranType(
					registeredClient.getAuthorizationGrantTypes().iterator().next().getValue());
		}

		if (!registeredClient.getRedirectUris().isEmpty()) {
			oAuthClient.setRedirectUri(registeredClient.getRedirectUris().iterator().next());
		}

		if (!registeredClient.getScopes().isEmpty()) {
			oAuthClient.setScope(registeredClient.getScopes().iterator().next());
		}

		return oAuthClient;
	}

}
