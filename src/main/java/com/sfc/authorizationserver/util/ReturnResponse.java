package com.sfc.authorizationserver.util;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.List;

public class ReturnResponse {
	private Boolean authenticated;
	private String cdCodent;
	private String cdCotien;
	private String password;
	private Date passwordExpiracion;
	@XmlElement(name = "roles")
	private List<RolUsuario> rolesList;
	private String user;

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(Boolean authenticated) {
		this.authenticated = authenticated;
	}

	public String getCdCodent() {
		return cdCodent;
	}

	public void setCdCodent(String cdCodent) {
		this.cdCodent = cdCodent;
	}

	public String getCdCotien() {
		return cdCotien;
	}

	public void setCdCotien(String cdCotien) {
		this.cdCotien = cdCotien;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getPasswordExpiracion() {
		return passwordExpiracion;
	}

	public void setPasswordExpiracion(Date passwordExpiracion) {
		this.passwordExpiracion = passwordExpiracion;
	}

	/*
	 * public List<RolUsuario> getRolesList() { return rolesList; }
	 */

	/*
	 * public void setRolesList(List<RolUsuario> rolesList) { this.rolesList =
	 * rolesList; }
	 */

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}