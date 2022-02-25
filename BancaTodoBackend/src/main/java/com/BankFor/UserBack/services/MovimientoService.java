package com.BankFor.UserBack.services;

import java.util.List;

import com.BankFor.UserBack.entity.MovimientoEntity;

public interface MovimientoService {
	
	public List<MovimientoEntity> findBycuentaId(long cuentaId) throws Exception;
	public void add(MovimientoEntity movimiento) throws Exception;

}
