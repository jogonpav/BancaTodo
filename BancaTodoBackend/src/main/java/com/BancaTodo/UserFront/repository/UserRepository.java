package com.BancaTodo.UserFront.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BancaTodo.UserFront.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	UserEntity findByUserName(String userName);

}
