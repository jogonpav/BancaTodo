package com.BancaTodo.UserFront.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.BancaTodo.UserFront.entity.ProductoEntity;

public interface ProductoService {
	
	public List<ProductoEntity> findByclienteId(long clienteId);
	public Optional<ProductoEntity> findById(long id);
	public void add(ProductoEntity producto);
	public void update(ProductoEntity producto);
	public List<ProductoEntity> findProductosByIdClienteDistintctId(long idProducto, long idCliente);
	public List<ProductoEntity> findByDistintctIdCliente(long clienteId);
	
	
}
