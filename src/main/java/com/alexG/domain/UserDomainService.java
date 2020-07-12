package com.alexG.domain;

import com.alexG.domain.entities.Role;
import com.alexG.domain.entities.User;

public interface UserDomainService {
	User getUserByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	Role findRoleByName(String roleName);

	void save(User user);
}
