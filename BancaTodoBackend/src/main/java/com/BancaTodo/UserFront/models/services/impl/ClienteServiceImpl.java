package com.BancaTodo.UserFront.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.models.dao.IClienteDao;
import com.BancaTodo.UserFront.models.entity.Cliente;
import com.BancaTodo.UserFront.models.services.IClienteService;



@Service
public class ClienteServiceImpl implements IClienteService{
	
	@Autowired
	private IClienteDao userDao;

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) userDao.findAll();
	}
	
	@Override
	public Cliente getById(long id) {		
		return userDao.findById(id);
	}
	
	@Override
	public void add(Cliente cliente) {
		userDao.save(cliente);		
	}
	@Override
	public void update(Cliente cliente) {
		userDao.save(cliente);
		
	}	

	@Override
	public void delete(long id) {
		userDao.deleteById(id);		
	}



}
