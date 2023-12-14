package com.sfc.authorizationserver.service;

import com.sfc.authorizationserver.dto.ClientDetails;
import com.sfc.authorizationserver.dto.UsuarioDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Lazy
	private ApiClientService apiClientService;

	private ThreadLocal<ClientDetails> currentClientDetails = ThreadLocal.withInitial(() -> null);

	@SneakyThrows
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("inicia proceso de Autenticacion en metodo");
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setName(username);
		usuarioDto.setPassword(password);
		ClientDetails clientDetails = apiClientService.getClientFromApi(usuarioDto);
		currentClientDetails.set(clientDetails);
		if (clientDetails.authenticated == false) {
			return new AnonymousAuthenticationToken("notAuthenticated", "anonymousUser",
					List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS")));
		}
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

		return new UsernamePasswordAuthenticationToken(clientDetails, null, authorities);
	}

	public ClientDetails getClientDetails() {
		ClientDetails details = currentClientDetails.get();
		currentClientDetails.remove();
		return details;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
