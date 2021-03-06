package com.BankFor.UserBack.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.BankFor.UserBack.entity.ProductoEntity;

public interface ProductoService {
	
	public List<ProductoEntity> findByclienteId(long clienteId) throws Exception;
	public Optional<ProductoEntity> findById(long id) throws Exception;
	boolean existsBynumeroCuenta(String numeroCuenta) throws Exception;
	public void add(ProductoEntity producto) throws Exception;
	public void update(ProductoEntity producto) throws Exception;
	public List<ProductoEntity> findProductosByIdClienteDistintctId(long idProducto, long idCliente) throws Exception;
	public List<ProductoEntity> findByDistintctIdCliente(long clienteId) throws Exception;
	
	
}
