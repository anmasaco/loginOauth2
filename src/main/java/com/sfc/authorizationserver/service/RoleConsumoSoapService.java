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
public class RoleConsumoSoapService {

	private final String urlSoapRole;
	private final String portSoapRole;
	private final RestTemplate restTemplate;

	@Autowired
	public RoleConsumoSoapService(RestTemplate restTemplate, @Value("${URL_SOAP_ROLE}") String urlSoapAuth,
			@Value("${PORT_SOAP_ROLE}") String portSoapAuth) {
		this.restTemplate = restTemplate;
		this.urlSoapRole = urlSoapAuth;
		this.portSoapRole = portSoapAuth;
	}

	public String ConsumoSoapRole(String user, String codeApp) {
		String soapXml = buildSoapRequest(user, codeApp);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/xml");

		HttpEntity<String> request = new HttpEntity<>(soapXml, headers);
		String url = urlSoapRole + ":" + portSoapRole + "/ServicioPerfilamiento/autorizacion";

		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
			return response.getBody();
		} catch (ResourceAccessException e) {
			throw new RuntimeException("Error de conectividad al conectar al servicio Perfilamiento", e);
		} catch (HttpClientErrorException e) {
			throw new RuntimeException("Error al procesar la respuesta del servicio Perfilamiento", e);
		}
	}

	private String buildSoapRequest(String user, String codeApp) {
		return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ns=\"http://www.superfinanciera.gov.co/autorizacion/ns\">"
				+ "   <soapenv:Header/>" + "   <soapenv:Body>" + "      <ns:getOpcionesAplicacion>" + "         <arg0>"
				+ codeApp + "</arg0>" + "         <arg1>" + user + "</arg1>" + "      </ns:getOpcionesAplicacion>"
				+ "   </soapenv:Body>" + "</soapenv:Envelope>";
	}
}
