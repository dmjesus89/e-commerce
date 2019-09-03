package com.dm.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dm.config.model.Authority;

public interface AuthRepository extends JpaRepository<Authority, String>{
	
	Authority findByName(String name);
	
}