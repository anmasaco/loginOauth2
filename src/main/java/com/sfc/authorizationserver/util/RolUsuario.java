package com.sfc.authorizationserver.util;

import java.util.Date;

public class RolUsuario {
	private Long idRolUsuario;
	private Long idUsuario;
	private Long idRol;
	private Date fechaSolicitud;
	private Integer indActivo;
	private Long idUsuarioAudit;
	private String ipCliente;
	private Rol rol;

	public Long getIdRolUsuario() {
		return idRolUsuario;
	}

	public void setIdRolUsuario(Long idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public Integer getIndActivo() {
		return indActivo;
	}

	public void setIndActivo(Integer indActivo) {
		this.indActivo = indActivo;
	}

	public Long getIdUsuarioAudit() {
		return idUsuarioAudit;
	}

	public void setIdUsuarioAudit(Long idUsuarioAudit) {
		this.idUsuarioAudit = idUsuarioAudit;
	}

	public String getIpCliente() {
		return ipCliente;
	}

	public void setIpCliente(String ipCliente) {
		this.ipCliente = ipCliente;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}