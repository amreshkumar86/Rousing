package com.oen.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.oen.core.domain.JwtUser;
import com.oen.core.domain.model.Customer;
import com.oen.core.domain.model.User;
import com.oen.core.domain.repository.UserRepository;
import com.oen.core.security.JwtTokenUtil;
import com.oen.core.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public User getUserByToken(String token) {
		String username = null;
		User loggedInUser = null;
		username = jwtTokenUtil.getUsernameFromToken(token);
		loggedInUser = userRepository.findByUsername(username);
		return loggedInUser;
	}
	
	@Override
	public long getUserIdByToken(String token) {
		String username = null;
		User loggedInUser = null;
		username = jwtTokenUtil.getUsernameFromToken(token);
		loggedInUser = userRepository.findByUsername(username);
		return loggedInUser.getId();
	}
	
	@Override
	public JwtUser getUserDetails(String token) {
		String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
		return user;
	}

	@Override
	public Customer getCustomerByToken(String token) {
		return getUserByToken(token).getCustomer();
	}

	@Override
	public Long getCustomerIdByToken(String token) {
		return getUserByToken(token).getCustomer().getId();
	}
}
