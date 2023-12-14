package com.sfc.authorizationserver.dto;

import java.util.List;

import lombok.Data;

@Data
public class PerfilamientoResponse {

	public List<Menu> menu;
	public List<Roles> roles;
	public Usuario usuario;
}
