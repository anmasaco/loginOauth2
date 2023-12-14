package com.sfc.authorizationserver.util;

import org.apache.axis.encoding.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public final class UtilEncrypt {

	@Value("${AES_ENCRYPT_KEY}")
	private String aesEncryptKey;

	public String cifrado(String informacionACifrar) {
		String informacionCifrada = null;
		String algoritmo = CipherStrings._ALGORITHM_AES;
		String symetricKey = aesEncryptKey;
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(symetricKey);
		CipherStrings cipherStrings = new CipherStrings(algoritmo);
		cipherStrings.setKeys(keys);

		byte[] array = cipherStrings.encrypt(informacionACifrar.getBytes());
		informacionCifrada = new String(Base64.encode(array));

		return informacionCifrada;

	}
}
