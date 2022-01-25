package com.BancaTodo.UserFront.dao;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.entity.ClienteEntity;


public interface IClienteDao extends CrudRepository<ClienteEntity, Long>{
	ClienteEntity findById(long id);
}
