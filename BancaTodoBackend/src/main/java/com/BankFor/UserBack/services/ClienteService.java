package com.BankFor.UserBack.services;

import java.util.List;

import com.BankFor.UserBack.entity.ClienteEntity;

public interface ClienteService {
	public List<ClienteEntity> findAll()  throws Exception ;
	public ClienteEntity getById(long id)  throws Exception;
	
	public ClienteEntity add(ClienteEntity cliente)  throws Exception;	
	public ClienteEntity update(ClienteEntity cliente)  throws Exception;
	public void delete(long id) throws Exception;
}
