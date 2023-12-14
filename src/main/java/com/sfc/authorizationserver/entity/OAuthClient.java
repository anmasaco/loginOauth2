package com.sfc.authorizationserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "OAUTH_CLIENT")
@Data
public class OAuthClient {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OAUTH_CLIENT_SEQ")
	@SequenceGenerator(name = "OAUTH_CLIENT_SEQ", sequenceName = "OAUTH_CLIENT_SEQ", allocationSize = 1)
	private Long id;

	private Long idAplicacion;

	@Column(nullable = false)
	private String client;

	private String clientSecret;
	private String clientAuthenticationMethod;
	private String authorizationGranType;
	private String redirectUri;
	private String scope;
}
