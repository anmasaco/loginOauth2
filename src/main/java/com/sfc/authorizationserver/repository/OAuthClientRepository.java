package com.sfc.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sfc.authorizationserver.entity.OAuthClient;

public interface OAuthClientRepository extends JpaRepository<OAuthClient, Long> {
	OAuthClient findByClient(String client);
}