package com.BankFor.UserBack.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankFor.UserBack.entity.MovimientoEntity;
import com.BankFor.UserBack.repository.MovimientoRepository;
import com.BankFor.UserBack.services.MovimientoService;

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
