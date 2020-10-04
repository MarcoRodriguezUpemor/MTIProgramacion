package com.upemor.sesioncuatro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upemor.sesioncuatro.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	boolean findBolByName(String string);
	
	Role findByName(String string);
	
	boolean existsRoleByName(String name);
}
