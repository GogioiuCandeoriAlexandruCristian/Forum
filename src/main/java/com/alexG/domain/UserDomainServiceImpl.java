package com.alexG.domain;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alexG.domain.entities.Role;
import com.alexG.domain.entities.User;
import com.alexG.entities.ERole;
import com.alexG.repository.RoleRepository;
import com.alexG.repository.UserRepository;

@Component
public class UserDomainServiceImpl implements UserDomainService {

	private ModelMapperUserEntityToUser mapper = Mappers.getMapper(ModelMapperUserEntityToUser.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Override
	public User getUserByUsername(String username) {
		return mapper.userEntityToUser(userRepo.findByUsername(username));
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public Role findRoleByName(String roleName) {
		return mapper.roleEntityToRole(roleRepo.findByName(ERole.valueOf(roleName)));
	}

	@Override
	public void save(User user) {
		userRepo.save(mapper.userToUserEntity(user));
	}

}
