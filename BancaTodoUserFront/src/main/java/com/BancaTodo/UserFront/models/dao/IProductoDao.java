package com.BancaTodo.UserFront.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {
	
}
