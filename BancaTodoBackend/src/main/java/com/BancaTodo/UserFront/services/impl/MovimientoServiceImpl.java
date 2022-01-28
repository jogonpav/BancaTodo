package com.BancaTodo.UserFront.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.dao.MovimientoRepository;
import com.BancaTodo.UserFront.entity.MovimientoEntity;
import com.BancaTodo.UserFront.services.MovimientoService;

@Service
public class MovimientoServiceImpl implements MovimientoService {

	@Autowired
	private MovimientoRepository movimientoDao;
	
	@Override
	public List<MovimientoEntity> findBycuentaId(long cuentaId) throws Exception {		
		return (List<MovimientoEntity>) movimientoDao.findBycuentaId(cuentaId);
	}

	@Override
	public void add(MovimientoEntity movimiento) throws Exception {
		movimientoDao.save(movimiento);
		
	}

}
