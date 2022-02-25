package com.BankFor.UserBack.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankFor.UserBack.entity.ClienteEntity;
import com.BankFor.UserBack.repository.ClienteRepository;
import com.BankFor.UserBack.services.ClienteService;



@Service
public class ClienteServiceImpl implements ClienteService{
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public ClienteServiceImpl(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}


	@Override
	public List<ClienteEntity> findAll()  throws Exception{		
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
	public ClienteEntity update(ClienteEntity cliente)  throws Exception{
		return clienteRepository.save(cliente);		
	}	

	@Override
	public void delete(long id) throws Exception{
		clienteRepository.deleteById(id);		
	}



}
