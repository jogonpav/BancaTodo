package com.BancaTodo.UserFront.models.services;

import java.util.List;

import com.BancaTodo.UserFront.models.entity.Movimiento;

public interface IMovimientoService {
	
	public List<Movimiento> findBycuentaId(long cuentaId);
	public void add(Movimiento movimiento);

}
