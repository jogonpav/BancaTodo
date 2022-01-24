package com.BancaTodo.UserFront.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.BancaTodo.UserFront.models.entity.Producto;

public interface IProductoService {
	
	public List<Producto> findByclienteId(long clienteId);
	public Optional<Producto> findById(long id);
	public void add(Producto producto);
	public void update(Producto producto);
	public List<Producto> findProductosByIdClienteDistintctId(long idProducto, long idCliente);
	public List<Producto> findByDistintctIdCliente(long clienteId);
	
	
}
