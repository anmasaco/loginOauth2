package com.sfc.authorizationserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserConsumoSoapService {

	private final String urlSoapAuth;
	private final String portSoapAuth;
	private final RestTemplate restTemplate;

	@Autowired
	public UserConsumoSoapService(RestTemplate restTemplate, @Value("${URL_SOAP_AUTH}") String urlSoapAuth,
			@Value("${PORT_SOAP_AUTH}") String portSoapAuth) {
		this.restTemplate = restTemplate;
		this.urlSoapAuth = urlSoapAuth;
		this.portSoapAuth = portSoapAuth;
	}

	public String ConsumoSoap(String UserEncrypt, String PasswordEncrypt) {
		String soapXml = buildSoapRequest(UserEncrypt, PasswordEncrypt);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/xml");

		HttpEntity<String> request = new HttpEntity<>(soapXml, headers);
		String url = urlSoapAuth + ":" + portSoapAuth + "/federalizacionusuarios/AutenticacionWebService/Autenticacion";

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			return response.getBody();
		} catch (ResourceAccessException e) {
			throw new RuntimeException("Error de conectividad al conectar al servicio Federalizacion", e);
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error al procesar la respuesta del servicio Federalizacion", e);
		}
	}

	private String buildSoapRequest(String userEncrypt, String passwordEncrypt) {
		return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webServiceAutenticacion.action.superfinanciera.nexura.sc.com.co/\">"
				+ "   <soapenv:Header/>" + "   <soapenv:Body>" + "      <web:getAuthenticator>" + "         <user>"
				+ userEncrypt + "</user>" + "         <password>" + passwordEncrypt + "</password>"
				+ "      </web:getAuthenticator>" + "   </soapenv:Body>" + "</soapenv:Envelope>";
	}
}
