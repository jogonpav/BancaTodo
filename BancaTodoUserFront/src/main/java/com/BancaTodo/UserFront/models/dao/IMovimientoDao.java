package com.BancaTodo.UserFront.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.models.entity.Movimiento;


public interface IMovimientoDao extends CrudRepository<Movimiento, Long>{

}
