package com.oen.core.service.impl;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.oen.core.domain.model.Role;
import com.oen.core.domain.model.User;
import com.oen.core.domain.model.UserRole;
import com.oen.core.domain.repository.RoleRepository;
import com.oen.core.domain.repository.UserRepository;
import com.oen.core.domain.repository.UserRoleRepository;
import com.oen.core.service.SignUpService;

@Service("userSignUpService")
public class SignUpServiceImpl implements SignUpService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public void createNewAccount(User newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setRegisteredOn(LocalDateTime.now());
		newUser.setUsername(newUser.getEmail());
		newUser.setEnabled(true);
		newUser = userRepository.saveAndFlush(newUser);
		saveUserRole(newUser);
	}

	@Override
	public void saveUserRole(User createdUser) {
		UserRole userRoleMapping = new UserRole();
		Role role = roleRepository.getAuthorityByName("ROLE_USER");
		userRoleMapping.setAuthority(role);
		userRoleMapping.setUser(createdUser);
		userRoleRepository.saveAndFlush(userRoleMapping);
	}

}
