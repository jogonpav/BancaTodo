package com.BancaTodo.UserFront.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

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

import com.BancaTodo.UserFront.dto.GeneralResponse;
import com.BancaTodo.UserFront.dto.Mensaje;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.ProductoService;


@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	//Lista productos por clienteId 
	@GetMapping
	public ResponseEntity<GeneralResponse<List<ProductoEntity>>>  listar(@PathParam("clienteId") Long clienteId){	
		GeneralResponse<List<ProductoEntity>> respuesta = new GeneralResponse<>();
		List<ProductoEntity> datos = null;
		String mensaje = null;		
		HttpStatus estadoHttp = null;
		
		
		try {
			
			datos = productoService.findByclienteId(clienteId);
			mensaje = "0 - se encontró " + datos.size() + " productos";
			
			if (datos.isEmpty()) {
				mensaje = "1 - No se encontró productos registrados";
			}
			respuesta.setDatos(datos);			
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			
			estadoHttp = HttpStatus.OK;
			
		} catch (Exception e) {
			
			mensaje = "Ha fallado el sistema. Contacte al administrador";			
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			
			
		}
		
		return new ResponseEntity<>(respuesta, estadoHttp);
	}
	//obtiene producto por id 
	@GetMapping("/{id}")
	public ResponseEntity <Optional<ProductoEntity>> getById(@PathVariable("id") long id){	
		HttpStatus estadoHttp = null;
		Optional<ProductoEntity> producto = null;
		try {
			producto = productoService.findById(id);
			estadoHttp = HttpStatus.OK;
		} catch (Exception e) {			
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Optional<ProductoEntity>>(producto, estadoHttp);
	}
	
	//obtiene productos para cliente específico, distinto producto id
	@GetMapping("/{idProducto}/cliente/{idCliente}")
	public List<ProductoEntity>findProductosByIdClienteDistintctId(@PathVariable("idProducto") long idProducto, @PathVariable("idCliente") long idCliente){
		return productoService.findProductosByIdClienteDistintctId(idProducto, idCliente);		
	}
	
	//obtiene los productos diferente a cliente id
	@GetMapping("/cliente/{idCliente}/distintc")
	public List<ProductoEntity>findByDistintctIdCliente(@PathVariable("idCliente") long idCliente){
		return productoService.findByDistintctIdCliente(idCliente);		
	}
	
	//crea producto para cliente específico
	@PostMapping
	public ResponseEntity<?> add(@RequestBody ProductoEntity producto, @PathParam("idCliente") long idCliente){
		
		HttpStatus estadoHttp = null;
		String mensaje = null;	
		
		try {
			producto.setClienteId(idCliente);
			producto.setFechaApertura(LocalDate.now());
			
			productoService.add(producto);
			mensaje = "Producto creado exitosamente";
			estadoHttp = HttpStatus.CREATED;
			
		} catch (Exception e) {
			
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "Hubo un fallo. Contacte al administrador";
			
		}		
		return new ResponseEntity<>(mensaje, estadoHttp);	
	}
	
	@PutMapping("/{idProducto}/update")
	public ResponseEntity<?> update(@PathVariable("idProducto") long idProducto, @RequestBody ProductoEntity producto){
		
		producto.setId(idProducto);
		HttpStatus estadoHttp = null;
		String mensaje = null;	
		
		try {			
			productoService.update(producto);
			mensaje = "Producto creado exitosamente";
			estadoHttp = HttpStatus.CREATED;			
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "Hubo un fallo. Contacte al administrador";
			
		}		
		return new ResponseEntity<>(mensaje, estadoHttp);
	}
}
