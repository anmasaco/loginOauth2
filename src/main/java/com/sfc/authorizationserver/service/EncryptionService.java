package com.sfc.authorizationserver.service;

import com.sfc.authorizationserver.dto.UsuarioDto;
import com.sfc.authorizationserver.util.UtilEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	@Autowired
	UtilEncrypt utilEncrypt;

	public UsuarioDto encryptUser(UsuarioDto usuarioDto) {
		UsuarioDto usuarioDtoEncrypt = new UsuarioDto();
		String nameEncrypt = encryptData(usuarioDto.getName());
		usuarioDtoEncrypt.setName(nameEncrypt);
		String passwordEncrypt = encryptData(usuarioDto.getPassword());
		usuarioDtoEncrypt.setPassword(passwordEncrypt);
		return usuarioDtoEncrypt;
	}

	public String encryptData(String dataToEncrypt) {
		try {
			String encryptedData = utilEncrypt.cifrado(dataToEncrypt);
			return encryptedData;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
