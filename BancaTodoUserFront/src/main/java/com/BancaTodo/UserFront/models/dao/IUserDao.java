package com.BancaTodo.UserFront.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.models.entity.User;

public interface IUserDao extends CrudRepository<User, Long>{

}
