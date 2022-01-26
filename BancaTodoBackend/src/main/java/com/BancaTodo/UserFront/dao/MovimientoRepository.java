package com.BancaTodo.UserFront.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.entity.MovimientoEntity;


public interface MovimientoRepository extends CrudRepository<MovimientoEntity, Long>{
	
	List<MovimientoEntity> findBycuentaId(long cuentaId);

}
