package com.BancaTodo.UserFront.models.services;

import java.util.List;

import com.BancaTodo.UserFront.models.entity.Cliente;

public interface IClienteService {
	public List<Cliente> findAll();
	public Cliente getById(long id);
	public void add(Cliente cliente);	
	public void update(Cliente cliente);
	public void delete(long id);
}
