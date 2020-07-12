package com.alexG.security.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.alexG.security.model.RoleModel;
import com.alexG.security.model.UserModel;

public interface UserDetailsServices extends UserDetailsService {
	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	RoleModel findRoleByName(String roleName);

	void save(UserModel user);
}
