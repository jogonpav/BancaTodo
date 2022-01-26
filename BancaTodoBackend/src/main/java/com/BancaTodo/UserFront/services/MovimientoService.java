package com.BancaTodo.UserFront.services;

import java.util.List;

import com.BancaTodo.UserFront.entity.MovimientoEntity;

public interface MovimientoService {
	
	public List<MovimientoEntity> findBycuentaId(long cuentaId);
	public void add(MovimientoEntity movimiento) throws Exception;

}
