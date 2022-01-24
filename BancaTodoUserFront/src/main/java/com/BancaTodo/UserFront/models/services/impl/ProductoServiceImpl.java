package com.BancaTodo.UserFront.models.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.models.dao.IProductoDao;
import com.BancaTodo.UserFront.models.entity.Producto;
import com.BancaTodo.UserFront.models.services.IProductoService;


@Service
public class ProductoServiceImpl implements IProductoService {
	
	@Autowired
	public IProductoDao productoDao;

	@Override
	public List<Producto> findByclienteId(long clienteId) {		
		return productoDao.findByclienteId(clienteId);
	}
	
	@Override
	public Optional<Producto> findById(long id) {		
		return productoDao.findById(id);
	}

	@Override
	public void add(Producto producto) {
		productoDao.save(producto);		
	}

	@Override
	public void update(Producto producto) {
		productoDao.save(producto);	
		
	}

	@Override
	public List<Producto> findProductosByIdClienteDistintctId(long idProducto, long idCliente) {
		return productoDao.findProductosByIdClienteDistintctId(idProducto, idCliente);
	}

	@Override
	public List<Producto> findByDistintctIdCliente(long clienteId) {
		return productoDao.findByDistintctIdCliente(clienteId);
	}

	
				
	
}






