package com.BancaTodo.UserFront.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.dao.IProductoDao;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.ProductoService;


@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	public IProductoDao productoDao;

	@Override
	public List<ProductoEntity> findByclienteId(long clienteId) {		
		return productoDao.findByclienteId(clienteId);
	}
	
	@Override
	public Optional<ProductoEntity> findById(long id) {		
		return productoDao.findById(id);
	}

	@Override
	public void add(ProductoEntity producto) {
		productoDao.save(producto);		
	}

	@Override
	public void update(ProductoEntity producto) {
		productoDao.save(producto);	
		
	}

	@Override
	public List<ProductoEntity> findProductosByIdClienteDistintctId(long idProducto, long idCliente) {
		return productoDao.findProductosByIdClienteDistintctId(idProducto, idCliente);
	}

	@Override
	public List<ProductoEntity> findByDistintctIdCliente(long clienteId) {
		return productoDao.findByDistintctIdCliente(clienteId);
	}

	
				
	
}






