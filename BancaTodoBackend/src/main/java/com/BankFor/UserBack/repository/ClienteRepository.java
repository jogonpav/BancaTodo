package com.BankFor.UserBack.repository;

import org.springframework.data.repository.CrudRepository;

import com.BankFor.UserBack.entity.ClienteEntity;


public interface ClienteRepository extends CrudRepository<ClienteEntity, Long>{
	ClienteEntity findById(long id);
}
