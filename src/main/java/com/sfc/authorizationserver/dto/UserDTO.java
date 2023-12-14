package com.sfc.authorizationserver.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDTO {

	private List<Authority> authorities;
	private Object details;
	private boolean authenticated;
	private Principal principal;
	private Object credentials;
	private String name;

	@Data
	public static class Authority {
		private String authority;

	}

	@Data
	public static class Principal {
		private boolean authenticated;
		private String user;
		private String password;
		private String cdCodent;
		private String cdCotien;

	}
}
