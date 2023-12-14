package com.sfc.authorizationserver.dto;

import lombok.Data;

@Data
public class Usuario {

	private int idUsuario;
	private String nombre;
	private String correoElectronico;
	private int idEntidad;
	private String identificacion;

}
