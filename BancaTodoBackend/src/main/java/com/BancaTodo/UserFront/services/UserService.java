package com.BancaTodo.UserFront.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.BancaTodo.UserFront.entity.UserEntity;

public interface UserService {
	
	public UserEntity findUserByUserName(String userName) throws Exception;
	public UserEntity add(UserEntity user) throws Exception;
//	public UserEntity findByUserName(String UserName) throws Exception;
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException;	
	public void updateUserById(UserEntity user) throws Exception;
	
	
	
 
}
