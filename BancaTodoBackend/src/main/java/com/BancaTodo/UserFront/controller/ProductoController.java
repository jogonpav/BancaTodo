package com.BancaTodo.UserFront.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.dto.Mensaje;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.ProductoService;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/APIproductos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	//Lista productos por clienteId 
	@GetMapping("/cliente/{idCliente}")
	public List<ProductoEntity> listar(@PathVariable("idCliente") long idCliente){		
		return productoService.findByclienteId(idCliente);
	}
	//obtiene producto por id 
	@GetMapping("/producto/{id}")
	public Optional<ProductoEntity> getById(@PathVariable("id") long id){		
		return productoService.findById(id);
	}
	//obtiene productos para cliente específico, distinto producto id
	@GetMapping("/producto/{idProducto}/cliente/{idCliente}")
	public List<ProductoEntity>findProductosByIdClienteDistintctId(@PathVariable("idProducto") long idProducto, @PathVariable("idCliente") long idCliente){
		return productoService.findProductosByIdClienteDistintctId(idProducto, idCliente);		
	}
	//obtiene los productos diferente a cliente id
	@GetMapping("/distintc/cliente/{idCliente}")
	public List<ProductoEntity>findByDistintctIdCliente(@PathVariable("idCliente") long idCliente){
		return productoService.findByDistintctIdCliente(idCliente);		
	}
	//crea producto para cliente específico
	@PostMapping("/Cliente/{idCliente}/create")
	public ResponseEntity<?> add(@RequestBody ProductoEntity producto, @PathVariable("idCliente") long idCliente){
		producto.setClienteId(idCliente);
		productoService.add(producto);		
		return new ResponseEntity(new Mensaje("Producto Creado"), HttpStatus.OK);	
	}
	@PutMapping("/update/{idProducto}")
	public ResponseEntity<?> update(@PathVariable("idProducto") long idProducto, @RequestBody ProductoEntity producto){
		producto.setId(idProducto);
		productoService.update(producto);		
		return new ResponseEntity(new Mensaje("Producto Actualizado"), HttpStatus.OK);	
	}
}
