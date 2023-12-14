package com.sfc.authorizationserver.util;

public class Aplicacion {
	private Long idAplicacion;
	private String nombre;
	private String descripcion;
	private String propietarioProducto;
	private String propietarioServicio;
	private Integer indActivo;
	private Long idUsuarioAudit;
	private String ipCliente;
	private Integer integrada;

	public Long getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(Long idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPropietarioProducto() {
		return propietarioProducto;
	}

	public void setPropietarioProducto(String propietarioProducto) {
		this.propietarioProducto = propietarioProducto;
	}

	public String getPropietarioServicio() {
		return propietarioServicio;
	}

	public void setPropietarioServicio(String propietarioServicio) {
		this.propietarioServicio = propietarioServicio;
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

	public Integer getIntegrada() {
		return integrada;
	}

	public void setIntegrada(Integer integrada) {
		this.integrada = integrada;
	}
}