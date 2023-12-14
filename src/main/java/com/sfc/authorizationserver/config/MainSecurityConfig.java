package com.sfc.authorizationserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ AuthenticationConfig.class, AuthorizationConfig.class, OAuth2ServerConfig.class, UtilityConfig.class })
public class MainSecurityConfig {
}
