package com.alexG.security.model;

import org.mapstruct.Mapper;

import com.alexG.domain.entities.Role;
import com.alexG.domain.entities.User;

@Mapper
public interface ModelMapperUserToModel {
	UserModel userToUserModel(User user);

	RoleModel roleToRoleModel(Role role);

	User userModelToUser(UserModel user);

	Role roleModelToRole(RoleModel role);
}
