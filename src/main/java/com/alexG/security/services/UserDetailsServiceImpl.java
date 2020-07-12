package com.alexG.security.services;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexG.domain.UserDomainService;
import com.alexG.security.model.ModelMapperUserToModel;
import com.alexG.security.model.RoleModel;
import com.alexG.security.model.UserModel;

@Service
public class UserDetailsServiceImpl implements UserDetailsServices {

	@Autowired
	private UserDomainService userDomainService;

	private ModelMapperUserToModel mapper = Mappers.getMapper(ModelMapperUserToModel.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = mapper.userToUserModel(userDomainService.getUserByUsername(username));

		return UserDetailsImpl.build(user);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userDomainService.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userDomainService.existsByEmail(email);
	}

	@Override
	public RoleModel findRoleByName(String roleName) {
		return mapper.roleToRoleModel(userDomainService.findRoleByName(roleName));
	}

	@Override
	public void save(UserModel user) {
		userDomainService.save(mapper.userModelToUser(user));
	}
}
