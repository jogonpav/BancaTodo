package com.BancaTodo.UserFront.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.entity.UserEntity;
import com.BancaTodo.UserFront.repository.UserRepository;
import com.BancaTodo.UserFront.services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	public UserRepository userRepository;

	@Override
	public UserEntity add(UserEntity user) throws Exception {
		
		return userRepository.save(user);
	}

//	@Override
//	public UserEntity findByUserName(String UserName) throws Exception {		
//		return userRepository.findByUserName(UserName);
//	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(userName);
		UserDetails userDetails = new User(user.getUserName(), user.getPassword(), new ArrayList<>());		
		return userDetails;
	}
	

}
