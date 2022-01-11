package com.BancaTodo.UserFront.models.services;

import java.util.List;

import com.BancaTodo.UserFront.models.entity.User;

public interface IUserService {
	public List<User> findAll();
}
