package com.sfc.authorizationserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sfc.authorizationserver.dto.AppUserActualDto;
import com.sfc.authorizationserver.entity.RolxModulo;
import com.sfc.authorizationserver.repository.RolxModuloRepository;

public class RolxModuloService {

	@Autowired
	private RolxModuloRepository rolxModuloRepository;

	public ResponseEntity<AppUserActualDto> validarIdRol(Long idRolPermiso) {
		if (idRolPermiso == null || idRolPermiso <= 0) {
			return ResponseEntity.badRequest().body(null);
		}

		Optional<RolxModulo> optionalRolXModulo = rolxModuloRepository.findById(idRolPermiso);

		if (!optionalRolXModulo.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		RolxModulo rolXModulo = optionalRolXModulo.get();

		AppUserActualDto appUserActualDto = new AppUserActualDto();
		appUserActualDto.setId(rolXModulo.getIDROLXMODULO());

		return ResponseEntity.ok(appUserActualDto);
	}
}
