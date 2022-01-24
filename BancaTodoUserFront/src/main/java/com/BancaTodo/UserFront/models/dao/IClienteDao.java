package com.BancaTodo.UserFront.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.models.entity.Cliente;


public interface IClienteDao extends CrudRepository<Cliente, Long>{
	Cliente findById(long id);
}
