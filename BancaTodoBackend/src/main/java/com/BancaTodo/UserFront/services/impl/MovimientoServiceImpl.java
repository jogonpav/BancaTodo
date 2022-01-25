package com.BancaTodo.UserFront.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.dao.IMovimientoDao;
import com.BancaTodo.UserFront.entity.MovimientoEntity;
import com.BancaTodo.UserFront.services.MovimientoService;

@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private IMovimientoDao movimientoDao;
	
	@Override
	public List<MovimientoEntity> findBycuentaId(long cuentaId) {		
		return (List<MovimientoEntity>) movimientoDao.findBycuentaId(cuentaId);
	}

	@Override
	public void add(MovimientoEntity movimiento) {
		movimientoDao.save(movimiento);
		
	}

}
