package com.BankFor.UserBack.services.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BankFor.UserBack.entity.UserEntity;
import com.BankFor.UserBack.repository.UserRepository;
import com.BankFor.UserBack.services.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	public UserRepository userRepository;
	
	@Override
	public UserEntity findUserByUserName(String userName) throws Exception {		
		return userRepository.findByUserName(userName);
	}

	@Override
	public UserEntity add(UserEntity user) throws Exception {		
		return userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUserName(userName);
		UserDetails userDetails = new User(user.getUserName(), user.getPassword(), new ArrayList<>());		
		return userDetails;
	}

	@Override
	public void updateUserById(UserEntity user) throws Exception {	
		userRepository.updateUserById(user.getFirstName(),user.getLastName(), user.getId());			
	}

	
	

}
