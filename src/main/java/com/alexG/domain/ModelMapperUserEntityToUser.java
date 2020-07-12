package com.alexG.domain;

import org.mapstruct.Mapper;

import com.alexG.domain.entities.Role;
import com.alexG.domain.entities.User;
import com.alexG.entities.RoleEntity;
import com.alexG.entities.UserEntity;

@Mapper
public interface ModelMapperUserEntityToUser {
	User userEntityToUser(UserEntity user);

	Role roleEntityToRole(RoleEntity role);

	UserEntity userToUserEntity(User user);

	RoleEntity roleToRoleEntity(User user);
}
