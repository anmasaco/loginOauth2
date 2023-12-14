package com.sfc.authorizationserver.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "AUTHORIZATIONCODES")
@Data
public class AuthorizationCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "AUTH_CODE", nullable = false, unique = true)
	private String authCode;

	@Column(name = "CLIENT_ID", nullable = false)
	private String clientId;

	@Column(name = "USER_ID", nullable = false)
	private Integer userId;

	@Column(name = "EXPIRATION_DATE", nullable = false)
	private LocalDateTime expirationDate;

	@Column(name = "SCOPE", nullable = false)
	private String scope;

	@Column(name = "REDIRECT_URI", nullable = false)
	private String redirectUri;

	@Column(name = "USED", nullable = false)
	private boolean used;

	@Column(name = "STATE")
	private String state;

	@Column(name = "USER_DATA", length = 2000)
	private String userData;

}
