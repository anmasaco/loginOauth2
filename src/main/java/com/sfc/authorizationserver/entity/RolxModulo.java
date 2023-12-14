package com.sfc.authorizationserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "ROLXMODULO")
@Entity
public class RolxModulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long IDROLXMODULO;
	private Long IDROL;
	private Long IDMODULO;


}