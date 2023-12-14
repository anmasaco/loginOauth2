package com.sfc.authorizationserver.util;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

/* Clase encargada de realizar c*/

public class CipherStrings {
	// ----------------------------------------------//
	// Class Constants
	// ----------------------------------------------//

	// ----------------------------------------------//
	// Supported algorithms
	// ----------------------------------------------//

	/**
	 * Advanced Encryption Standard as specified by NIST in a draft FIPS
	 */
	public static final String _ALGORITHM_AES = "AES";

	// ----------------------------------------------//
	// Class attributes
	// ----------------------------------------------//

	/**
	 * Current cipher object
	 */
	private Cipher currentCipher;

	/**
	 * Secret key used to encrypt or decrypt information
	 */
	private SecretKey secretKey;

	/**
	 * MAquina de ciframiento utilizar
	 */
	private String encryptionEngine;

	/**
	 * Llave(s) de ciframiento o desciframiento
	 */
	protected List<String> keys;

	// ----------------------------------------------//
	// Class constructors
	// ----------------------------------------------//

	/**
	 * Default class constructor
	 *
	 * @param encryptionEngine encryption engine to use. This parameter should be
	 *                         used like this: DES/CBC/PKCS5Padding
	 *                         (_ALGORITHM/_MODE/_PADDING)
	 */
	public CipherStrings(String encryptionEngine) {
		this.encryptionEngine = encryptionEngine;

		try {
			// Provider sunjce = new com.sun.crypto.provider.SunJCE();
			Provider sunjce = Security.getProvider("SunJCE");
			Security.addProvider(sunjce);
			this.currentCipher = Cipher.getInstance(this.encryptionEngine);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("No se ha encontrado el algoritmo de cifrado: [" + this.encryptionEngine
					+ "]. Descripci\u00F3n interna: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(
					"Error no controlado por el componente de manejo de cifrado de informaci\u00F3n. Descripci\u00F3n interna: "
							+ e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Cifrado de la clave
	 * 
	 * @param source cadena a cifrar
	 * @return bytes con datos cifrados
	 */
	public byte[] encrypt(byte[] source) {
		try {
			// Create and initialize the encryption engine
			this.currentCipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
			return this.currentCipher.doFinal(source);
		} catch (InvalidKeyException e) {
			System.out.println("Llave de ciframiento inv\u00E1lida. Descripci\u00F3n interna: " + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IllegalBlockSizeException e) {
			System.out.println(
					"Longitud de bloque ilegal en el proceso de ciframiento de informaci\u00F3n. Descripci\u00F3n interna: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(
					"Error no controlado por el componente de manejo de cifrado de informaci\u00F3n. Descripci\u00F3n interna: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see co.com.sc.pki.cipher.core.GenericAbstractCipherComponent#decrypt(byte[])
	 */
	public byte[] decrypt(byte[] source) {
		try {
			//
			// Create and initialize the decryption engine
			this.currentCipher.init(Cipher.DECRYPT_MODE, this.secretKey);
			return this.currentCipher.doFinal(source);
		} catch (InvalidKeyException e) {
			System.out.println("Llave de desciframiento inv\u00E1lida. Descripci\u00F3n interna: " + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (IllegalBlockSizeException e) {
			System.out.println(
					"Longitud de bloque ilegal en el proceso de desciframiento de informaci\u00F3n. Descripci\u00F3n interna: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println(
					"Error no controlado por el componente de manejo de cifrado de informaci\u00F3n. Descripci\u00F3n interna: "
							+ e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return the currentCipher
	 */
	public Cipher getCurrentCipher() {
		return currentCipher;
	}

	/**
	 * @param currentCipher the currentCipher to set
	 */
	public void setCurrentCipher(Cipher currentCipher) {
		this.currentCipher = currentCipher;
	}

	/**
	 * @return the secretKey
	 */
	public SecretKey getSecretKey() {
		return secretKey;
	}

	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Setear la llave simetrica desde un repositorio de llaves
	 * 
	 * @param keys lista de llaves
	 */
	public void setKeys(List<String> keys) {
		try {
			this.keys = keys;

			//
			// Create the key
			byte[] keyBytes = Base64.decodeBase64(keys.get(0));
			this.secretKey = new SecretKeySpec(keyBytes, this.encryptionEngine);
		} catch (IllegalArgumentException e) {
			System.out.println("Llave inv\u00E1lida. Descripci\u00F3n interna: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(
					"Error no controlado por el componente de manejo de cifrado de informaci\u00F3n. Descripci\u00F3n interna: "
							+ e.getMessage());
			e.printStackTrace();
		}
	}
}
