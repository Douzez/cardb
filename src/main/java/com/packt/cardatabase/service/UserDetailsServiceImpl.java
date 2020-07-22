package com.packt.cardatabase.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packt.cardatabase.domain.User;
import com.packt.cardatabase.repositorry.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User currentUser = userRepository.findByUsername(username);
		
		UserDetails user = new org.springframework.security.core.userdetails
				.User(username, currentUser.getPassword(),
						true, true, true, true,
						AuthorityUtils.createAuthorityList(currentUser.getRole()));
		
		return user;
	}

}
