package com.BankFor.UserBack.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankFor.UserBack.entity.ProductoEntity;
import com.BankFor.UserBack.repository.ProductoRepository;
import com.BankFor.UserBack.services.ProductoService;


@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	public ProductoRepository productoDao;

	@Override
	public List<ProductoEntity> findByclienteId(long clienteId) throws Exception{		
		return productoDao.findByclienteId(clienteId);
	}
	
	@Override
	public Optional<ProductoEntity> findById(long id) throws Exception{		
		return productoDao.findById(id);
	}

	@Override
	public void add(ProductoEntity producto) throws Exception{
		productoDao.save(producto);		
	}

	@Override
	public void update(ProductoEntity producto) throws Exception{
		productoDao.save(producto);	
		
	}

	@Override
	public List<ProductoEntity> findProductosByIdClienteDistintctId(long idProducto, long idCliente) throws Exception{
		return productoDao.findProductosByIdClienteDistintctId(idProducto, idCliente);
	}

	@Override
	public List<ProductoEntity> findByDistintctIdCliente(long clienteId) throws Exception{
		return productoDao.findByDistintctIdCliente(clienteId);
	}

	@Override
	public boolean existsBynumeroCuenta(String numeroCuenta) throws Exception {
		
		return productoDao.existsBynumeroCuenta(numeroCuenta);
	}

	
				
	
}






