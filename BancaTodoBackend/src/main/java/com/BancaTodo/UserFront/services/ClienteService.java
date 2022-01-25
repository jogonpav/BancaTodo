package com.BancaTodo.UserFront.services;

import java.util.List;

import com.BancaTodo.UserFront.entity.ClienteEntity;

public interface ClienteService {
	public List<ClienteEntity> findAll();
	public ClienteEntity getById(long id);
	public void add(ClienteEntity cliente);	
	public void update(ClienteEntity cliente);
	public void delete(long id);
}
