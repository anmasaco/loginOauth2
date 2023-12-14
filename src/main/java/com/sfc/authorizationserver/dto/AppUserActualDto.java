package com.sfc.authorizationserver.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AppUserActualDto {

	private Long id;
	private String login;
	private Date fechaCreacion;
	private Boolean isActive;
	private Long numeroIntentos;

}