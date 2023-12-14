package com.sfc.authorizationserver.dto;

import lombok.Data;

@Data
public class ClientDetails {

	public Boolean authenticated;
	public String user;
	public String password;
	public String cdCodent;
	public String cdCotien;
	

}
