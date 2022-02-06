package com.BancaTodo.UserFront.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.BancaTodo.UserFront.entity.ProductoEntity;

public interface ProductoRepository extends CrudRepository<ProductoEntity, Long> {
	
	List<ProductoEntity> findByclienteId(long clienteId);
	
	//listar producto diferente al seleccionado para un cliente id especificado
	@Query(value = "SELECT * FROM productos WHERE id != ?1 AND cliente_id=?2", nativeQuery = true)
	List<ProductoEntity> findProductosByIdClienteDistintctId(long idProducto, long idCliente );
	
	//listar producto diferente al seleccionado para un cliente id dirferente al especificado
	@Query(value = "SELECT * FROM productos WHERE cliente_id!=?1 ", nativeQuery = true)
	List<ProductoEntity> findByDistintctIdCliente(long clienteId);
	
}
