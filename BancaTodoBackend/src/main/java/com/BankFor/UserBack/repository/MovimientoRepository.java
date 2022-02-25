package com.BankFor.UserBack.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.BankFor.UserBack.entity.MovimientoEntity;


public interface MovimientoRepository extends CrudRepository<MovimientoEntity, Long>{
	
	List<MovimientoEntity> findBycuentaId(long cuentaId);

}
