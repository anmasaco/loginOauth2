package com.sfc.authorizationserver.service;

import com.sfc.authorizationserver.entity.OAuthClient;
import com.sfc.authorizationserver.repository.OAuthClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OAuthClientService {

	@Autowired
	private OAuthClientRepository oAuthClientRepository;

	public OAuthClient saveClient(OAuthClient oAuthClient) {
		return oAuthClientRepository.save(oAuthClient);
	}

	public Optional<OAuthClient> getClientById(Long id) {
		return oAuthClientRepository.findById(id);
	}

	public OAuthClient getClientByClientName(String clientName) {
		return oAuthClientRepository.findByClient(clientName);
	}

	public List<OAuthClient> getAllClients() {
		return oAuthClientRepository.findAll();
	}

	public void deleteClient(Long id) {
		oAuthClientRepository.deleteById(id);
	}

}
