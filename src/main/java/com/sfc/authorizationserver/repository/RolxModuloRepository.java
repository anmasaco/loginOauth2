package com.sfc.authorizationserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sfc.authorizationserver.entity.RolxModulo;

@Repository
public interface RolxModuloRepository extends JpaRepository<RolxModulo, Long> {

}