package com.BancaTodo.UserFront.models.services;

import java.util.List;

import com.BancaTodo.UserFront.models.entity.Movimiento;

public interface IMovimientoService {
	
	public List<Movimiento> findAll();

}
