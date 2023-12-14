package com.sfc.authorizationserver.config;

import com.sfc.authorizationserver.entity.OAuthClient;
import com.sfc.authorizationserver.service.OAuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppInitializer {
/*
	//Descomentar Esta clase para agregar Clientes desde la aplicacion
	 * 
	 * 
    @Autowired
    private OAuthClientService oAuthClientService;
    
    @Bean
    public CommandLineRunner initDatabase() {
    	
        return args -> {
        	
            OAuthClient oAuthClient = new OAuthClient();
            oAuthClient.setClient("S1r1c4s1ll3r0");
            oAuthClient.setClientSecret("0r3ll1s4c1r1s");
            oAuthClient.setClientAuthenticationMethod("BASIC_SECRET"); // Ejemplo: "basic", dependiendo de cómo lo manejes.
            oAuthClient.setAuthorizationGranType("AUTHORIZATION_CODE");
            oAuthClient.setRedirectUri("http://127.0.0.1:4200/login");
            oAuthClient.setScope("openid");
            
            oAuthClientService.saveClient(oAuthClient);
            
        	
            OAuthClient loginClient = new OAuthClient();
            loginClient.setClient("L0g1n4ppCl13nt1D");  // Elige un ID único para este cliente
            loginClient.setClientSecret("D1tn31lCpp4n1g0L"); // Elige un secreto único para este cliente
            loginClient.setClientAuthenticationMethod("BASIC_SECRET"); // Ejemplo: "basic".
            loginClient.setAuthorizationGranType("AUTHORIZATION_CODE");
            loginClient.setRedirectUri("http://127.0.0.1:4300/home");  // La URL de redirección de tu aplicación de login
            loginClient.setScope("openid");

            oAuthClientService.saveClient(loginClient);
        };
    }
    
    */
}
