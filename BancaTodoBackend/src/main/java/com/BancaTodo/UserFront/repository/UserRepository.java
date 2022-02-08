package com.BancaTodo.UserFront.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.BancaTodo.UserFront.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	
	UserEntity findByUserName(String userName);
	
	//actualizar un usuario sin modificar contraseña
	@Modifying
	@Transactional
	@Query(value = "UPDATE users SET first_name =?1, last_name = ?2 WHERE id =?3", nativeQuery = true)
	void updateUserById(String first_name, String last_name, long id);

}
