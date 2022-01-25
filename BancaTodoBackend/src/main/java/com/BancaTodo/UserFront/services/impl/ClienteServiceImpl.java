package com.BancaTodo.UserFront.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.dao.IClienteDao;
import com.BancaTodo.UserFront.entity.ClienteEntity;
import com.BancaTodo.UserFront.services.ClienteService;



@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private IClienteDao userDao;

	@Override
	public List<ClienteEntity> findAll() {
		// TODO Auto-generated method stub
		return (List<ClienteEntity>) userDao.findAll();
	}
	
	@Override
	public ClienteEntity getById(long id) {		
		return userDao.findById(id);
	}
	
	@Override
	public void add(ClienteEntity cliente) {
		userDao.save(cliente);		
	}
	@Override
	public void update(ClienteEntity cliente) {
		userDao.save(cliente);
		
	}	

	@Override
	public void delete(long id) {
		userDao.deleteById(id);		
	}



}
