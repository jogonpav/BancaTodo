package com.BancaTodo.UserFront.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BancaTodo.UserFront.models.dao.IProductoDao;
import com.BancaTodo.UserFront.models.entity.Producto;


@Service
public class ProductoServiceImpl implements IProductoService {
	
	@Autowired
	public IProductoDao productoDao;

	@Override
	public List<Producto> listar() {
		// TODO Auto-generated method stub
		return (List<Producto>) productoDao.findAll();
	}

}
