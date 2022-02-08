package com.BancaTodo.UserFront.dto;

import com.BancaTodo.UserFront.entity.UserEntity;

public class UserDto {
	
	private UserEntity user; 
	
	private String newPassword;

		
	public UserDto() {
	}

	public UserDto(UserEntity user, String newPassword) {
		this.user = user;
		this.newPassword = newPassword;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	} 
	
	
	
	
	

}
