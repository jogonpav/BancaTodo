package com.BancaTodo.UserFront.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.models.entity.Movimiento;


public interface IMovimientoDao extends CrudRepository<Movimiento, Long>{
	
	List<Movimiento> findBycuentaId(long cuentaId);

}
