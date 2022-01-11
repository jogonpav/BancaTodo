package com.BancaTodo.UserFront.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.models.dao.IMovimientoDao;
import com.BancaTodo.UserFront.models.entity.Movimiento;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Override
	public List<Movimiento> findAll() {
		return (List<Movimiento>) movimientoDao.findAll();
	}

}
