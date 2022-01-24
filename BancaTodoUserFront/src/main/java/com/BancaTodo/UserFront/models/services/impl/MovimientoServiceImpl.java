package com.BancaTodo.UserFront.models.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.models.dao.IMovimientoDao;
import com.BancaTodo.UserFront.models.entity.Movimiento;
import com.BancaTodo.UserFront.models.services.IMovimientoService;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Override
	public List<Movimiento> findBycuentaId(long cuentaId) {		
		return (List<Movimiento>) movimientoDao.findBycuentaId(cuentaId);
	}

	@Override
	public void add(Movimiento movimiento) {
		movimientoDao.save(movimiento);
		
	}

}
