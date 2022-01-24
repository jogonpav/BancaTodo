package com.BancaTodo.UserFront.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.BancaTodo.UserFront.models.entity.Producto;

public interface IProductoDao extends CrudRepository<Producto, Long> {
	
	List<Producto> findByclienteId(long id);
	
	//listar producto diferente al seleccionado para un cliente id especificado
	@Query(value = "SELECT * FROM productos WHERE id != ?1 AND cliente_id=?2", nativeQuery = true)
	List<Producto> findProductosByIdClienteDistintctId(long idProducto, long idCliente );
	
	//listar producto diferente al seleccionado para un cliente id dirferente al especificado
	@Query(value = "SELECT * FROM productos WHERE cliente_id!=?2 ", nativeQuery = true)
	List<Producto> findByDistintctIdCliente(long clienteId);
	
}
