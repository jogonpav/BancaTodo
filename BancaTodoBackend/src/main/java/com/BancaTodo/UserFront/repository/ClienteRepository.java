package com.BancaTodo.UserFront.repository;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.entity.ClienteEntity;


public interface ClienteRepository extends CrudRepository<ClienteEntity, Long>{
	ClienteEntity findById(long id);
}
