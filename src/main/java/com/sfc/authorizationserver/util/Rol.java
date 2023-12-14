package com.sfc.authorizationserver.util;

public class Rol {
	private Long idRol;
	private String nombre;
	private Long idAplicacion;
	private String ipCliente;
	private Long idUsuarioAudit;
	private Integer interno;
	private Aplicacion aplicacion;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}

	public Long getIdUsuarioAudit() {
		return idUsuarioAudit;
	}

	public void setIdUsuarioAudit(Long idUsuarioAudit) {
		this.idUsuarioAudit = idUsuarioAudit;
	}

	public Integer getInterno() {
		return interno;
	}

	public void setInterno(Integer interno) {
		this.interno = interno;
	}

	public Aplicacion getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(Aplicacion aplicacion) {
		this.aplicacion = aplicacion;
	}
}