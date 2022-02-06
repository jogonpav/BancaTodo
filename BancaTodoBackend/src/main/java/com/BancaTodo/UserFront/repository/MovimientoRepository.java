package com.BancaTodo.UserFront.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.entity.MovimientoEntity;


public interface MovimientoRepository extends CrudRepository<MovimientoEntity, Long>{
	
	List<MovimientoEntity> findBycuentaId(long cuentaId);

}
