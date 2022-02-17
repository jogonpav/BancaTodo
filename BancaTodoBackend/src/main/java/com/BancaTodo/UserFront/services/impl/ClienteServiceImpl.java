package com.BancaTodo.UserFront.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.entity.ClienteEntity;
import com.BancaTodo.UserFront.repository.ClienteRepository;
import com.BancaTodo.UserFront.services.ClienteService;



@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}


	@Override
	public List<ClienteEntity> findAll()  throws Exception{
		System.out.println((List<ClienteEntity>) clienteRepository.findAll());
		return (List<ClienteEntity>) clienteRepository.findAll();
	}
	
	@Override
	public ClienteEntity getById(long id)  throws Exception{		
		return clienteRepository.findById(id);
	}
	
	@Override
	public ClienteEntity add(ClienteEntity cliente)  throws Exception{
		return clienteRepository.save(cliente);		
	}
	@Override
	public void update(ClienteEntity cliente)  throws Exception{
		clienteRepository.save(cliente);
		
	}	

	@Override
	public void delete(long id) throws Exception{
		clienteRepository.deleteById(id);		
	}

}
