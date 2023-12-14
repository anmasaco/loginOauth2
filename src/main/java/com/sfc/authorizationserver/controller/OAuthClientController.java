package com.sfc.authorizationserver.controller;

import com.sfc.authorizationserver.entity.OAuthClient;
import com.sfc.authorizationserver.service.OAuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oauth-clients")
public class OAuthClientController {

	@Autowired
	private OAuthClientService oAuthClientService;

	@PostMapping
	public OAuthClient createClient(@RequestBody OAuthClient oAuthClient) {
		return oAuthClientService.saveClient(oAuthClient);
	}

	@GetMapping("/{id}")
	public OAuthClient getClient(@PathVariable Long id) {
		return oAuthClientService.getClientById(id).orElse(null);
	}

	@GetMapping
	public List<OAuthClient> getClients() {
		return oAuthClientService.getAllClients();
	}

	@DeleteMapping("/{id}")
	public void deleteClient(@PathVariable Long id) {
		oAuthClientService.deleteClient(id);
	}

}
