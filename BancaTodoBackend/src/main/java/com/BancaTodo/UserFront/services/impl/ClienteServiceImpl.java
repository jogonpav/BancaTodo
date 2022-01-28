package com.BancaTodo.UserFront.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.dao.ClienteRepository;
import com.BancaTodo.UserFront.entity.ClienteEntity;
import com.BancaTodo.UserFront.services.ClienteService;



@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository userDao;

	@Override
	public List<ClienteEntity> findAll()  throws Exception{
		// TODO Auto-generated method stub
		return (List<ClienteEntity>) userDao.findAll();
	}
	
	@Override
	public ClienteEntity getById(long id)  throws Exception{		
		return userDao.findById(id);
	}
	
	@Override
	public ClienteEntity add(ClienteEntity cliente)  throws Exception{
		return userDao.save(cliente);		
	}
	@Override
	public void update(ClienteEntity cliente)  throws Exception{
		userDao.save(cliente);
		
	}	

	@Override
	public void delete(long id) throws Exception{
		userDao.deleteById(id);		
	}

}
