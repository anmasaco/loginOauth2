package com.sfc.authorizationserver.service;

import com.sfc.authorizationserver.dto.ClientDetails;
import com.sfc.authorizationserver.dto.Menu;
import com.sfc.authorizationserver.dto.Modulo;
import com.sfc.authorizationserver.dto.Opcion;
import com.sfc.authorizationserver.dto.OpcionesAplicacion;
import com.sfc.authorizationserver.dto.Roles;
import com.sfc.authorizationserver.dto.OpcionesAplicacionResponse;
import com.sfc.authorizationserver.dto.PerfilamientoResponse;
import com.sfc.authorizationserver.dto.Rol;
import com.sfc.authorizationserver.dto.UsuarioDto;
import com.sfc.authorizationserver.util.GetAuthenticatorResponse;
import com.sfc.authorizationserver.util.XmlToPojoConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiClientService {

	private final String urlAutPojo;
	private final String tagAutBody;
	private final String urlRolePojo;
	private final String tagRoleBody;
	private final EncryptionService encryptionService;
	private final UserConsumoSoapService userConsumoSoapService;
	private final RoleConsumoSoapService roleConsumoSoapService;

	public ApiClientService(EncryptionService encryptionService, UserConsumoSoapService userConsumoSoapService,
			RoleConsumoSoapService roleConsumoSoapService, @Value("${URL_AUT_POJO}") String urlAutPojo,
			@Value("${TAG_AUT_POJO}") String tagAutBody, @Value("${URL_ROLE_POJO}") String urlRolePojo,
			@Value("${TAG_ROLE_POJO}") String tagRoleBody) {
		this.encryptionService = encryptionService;
		this.userConsumoSoapService = userConsumoSoapService;
		this.roleConsumoSoapService = roleConsumoSoapService;
		this.urlAutPojo = urlAutPojo;
		this.tagAutBody = tagAutBody;
		this.urlRolePojo = urlRolePojo;
		this.tagRoleBody = tagRoleBody;

	}

	public ClientDetails getClientFromApi(UsuarioDto usuarioDto) throws Exception {
		ClientDetails clientDetails = new ClientDetails();
		UsuarioDto encryptedUser = encryptionService.encryptUser(usuarioDto);
		String soapResponse;
		try {
			soapResponse = userConsumoSoapService.ConsumoSoap(encryptedUser.getName(), encryptedUser.getPassword());
		} catch (Exception e) {
			throw new Exception("Error al consumir el servicio SOAP", e);
		}

		GetAuthenticatorResponse authenticatorResponse;
		try {
			authenticatorResponse = XmlToPojoConverter.convert(soapResponse);
		} catch (Exception e) {
			throw new Exception("Error al convertir la respuesta SOAP a POJO", e);
		}

		clientDetails.setAuthenticated(authenticatorResponse.getReturnResponse().getAuthenticated());
		clientDetails.setUser(obtenerSegmento(authenticatorResponse.getReturnResponse().getUser()));
		clientDetails.setPassword(authenticatorResponse.getReturnResponse().getPassword());
		clientDetails.setCdCodent(authenticatorResponse.getReturnResponse().getCdCodent());
		clientDetails.setCdCotien(authenticatorResponse.getReturnResponse().getCdCotien());
		return clientDetails;
	}

	public PerfilamientoResponse getRoleFromApi(String name, String idApplication) throws Exception {
		OpcionesAplicacionResponse soapRoleResponse = new OpcionesAplicacionResponse();
		String soapResponse;
		try {
			soapResponse = roleConsumoSoapService.ConsumoSoapRole(name, idApplication);
		} catch (Exception e) {
			throw new Exception("Error al consumir el servicio SOAP", e);
		}
		try {
			soapRoleResponse = XmlToPojoConverter.convertRole(soapResponse, urlRolePojo, tagRoleBody);
			PerfilamientoResponse perfilamientoResponse = mapToPerfilamientoResponse(
					soapRoleResponse.getOpcionesAplicacion());
			return perfilamientoResponse;

		} catch (Exception e) {
			throw new Exception("Error al convertir la respuesta SOAP a POJO", e);
		}
	}

	public PerfilamientoResponse mapToPerfilamientoResponse(OpcionesAplicacion opcionesAplicacion) {
		PerfilamientoResponse perfilamientoResponse = new PerfilamientoResponse();
		List<Menu> menus = new ArrayList<>();
		for (Modulo modulo : opcionesAplicacion.getModulos()) {
			for (Opcion opcion : modulo.getOpciones()) {
				Menu menu = new Menu();

				menu.setTitulo(opcion.getNombre());
				menu.setUrl(opcion.getURL());
				menus.add(menu);
			}
		}
		perfilamientoResponse.setMenu(menus);
		List<Roles> rolesList = new ArrayList<>();
		for (Rol rol : opcionesAplicacion.getRoles()) {
			Roles roles = new Roles();

			roles.setIdRol(rol.getIdRol());
			roles.setNombre(rol.getNombre());
			roles.setFechaSolicitud(new Date().toString());

			rolesList.add(roles);
		}
		perfilamientoResponse.setRoles(rolesList);
		perfilamientoResponse.setUsuario(opcionesAplicacion.getUsuario());

		return perfilamientoResponse;
	}

	public String obtenerSegmento(String cadena) {
		if (cadena == null || cadena.isEmpty()) {
			return "";
		}
		String[] partes = cadena.split("\\|");
		if (partes.length > 0) {
			return partes[0];
		}
		return "";
	}

}
