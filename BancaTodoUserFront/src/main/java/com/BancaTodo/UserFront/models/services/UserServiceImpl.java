package com.BancaTodo.UserFront.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.models.dao.IUserDao;
import com.BancaTodo.UserFront.models.entity.User;



@Service
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List<User>) userDao.findAll();		
	
	}
	
}
