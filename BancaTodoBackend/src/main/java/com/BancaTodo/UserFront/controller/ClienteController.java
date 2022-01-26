package com.BancaTodo.UserFront.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.dto.GeneralResponse;
import com.BancaTodo.UserFront.entity.ClienteEntity;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.ClienteService;
import com.BancaTodo.UserFront.services.ProductoService;

//@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProductoService productoService;

	// Retornar lista de clientes
	@GetMapping("")
	public ResponseEntity<List<ClienteEntity>> listar() {
		List<ClienteEntity> list = null;
		HttpStatus estadoHttp = null;
		
		try {
			list = clienteService.findAll();
			estadoHttp = HttpStatus.OK;
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<List<ClienteEntity>>(list, estadoHttp);
	}

	// Retorna un cliente por id
	@GetMapping("/{id}")	
	
	public ResponseEntity<GeneralResponse <ClienteEntity>> getById(@PathVariable("id") long id) {
	GeneralResponse <ClienteEntity> respuesta = new GeneralResponse<>();
	HttpStatus estadoHttp = null;
	String mensaje = null;
	ClienteEntity datos = null;
	
	try {	
		datos = clienteService.getById(id);
		mensaje = "Se encontró";		
		respuesta.setDatos(datos);
		respuesta.setMensaje(mensaje);
		respuesta.setPeticionExitosa(true);
		estadoHttp = HttpStatus.OK;	
		
		if (respuesta.getDatos() == null) {
			mensaje = "Cliente no encontrado";			
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			estadoHttp = HttpStatus.NOT_FOUND;			
		}		
	}catch (Exception e){
		mensaje = "Ha fallado el sistema. Contacte al administrador";			
		respuesta.setMensaje(mensaje);
		respuesta.setPeticionExitosa(false);
		estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
	}
				
	return new ResponseEntity<>(respuesta, estadoHttp);
}
	
	// Crea un nuevo cliente
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
		HttpStatus estadoHttp = null;
		String mensaje = null;	
		
		try {
			
			cliente.setFechaCreacion(LocalDate.now());	
			clienteService.add(cliente);
			mensaje = "Cliente creado exitoxamente";
			estadoHttp = HttpStatus.CREATED;
			
		} catch (Exception e) {
			
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
			
		}
		
		return new ResponseEntity<>(mensaje, estadoHttp);
	}
	
	/*public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
		cliente.setFechaCreacion(LocalDate.now());		
		clienteService.add(cliente);
		return new ResponseEntity<Object>(new Mensaje("Cliente creado"), HttpStatus.OK);
	}*/

	// Actualiza un cliente por id
	@PutMapping("/{id}/update")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody ClienteEntity cliente) {
		HttpStatus estadoHttp = null;
		String mensaje = null;
		
		try {
			cliente.setId(id);
			clienteService.add(cliente);
			mensaje = "Cliente Actualizado";
			estadoHttp = HttpStatus.OK;
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
		}
		return new ResponseEntity<>(mensaje, estadoHttp);
	}

	// Elimina un cliente
	@DeleteMapping("/{id}/delete")	
	public ResponseEntity<?> delete(@PathVariable("id") long id) {	
		HttpStatus estadoHttp = null;
		String mensaje = null;
		
		try {
			boolean estadoBandera = existeProductosActivos(id);
			if (!estadoBandera) {				
				clienteService.delete(id);
				mensaje = "Cliente eliminado";
				estadoHttp = HttpStatus.OK;
				
			}else {
				estadoHttp = HttpStatus.CONFLICT;
				mensaje ="Cliente No puede ser eliminado, todos los productos deben estar cancelado";
			}
				
			} catch (Exception e) {
				estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
				mensaje = "Hubo un fallo. Contacte al administrador";
			}			
		 
			return new ResponseEntity<>(mensaje, estadoHttp);		
	}

	public boolean existeProductosActivos(long id) {
		List<ProductoEntity> productoCliente = null;
		HttpStatus estadoHttp = null;
		boolean estadoBandera = false;		
		try {
			productoCliente = productoService.findByclienteId(id);
			estadoHttp = HttpStatus.OK;			
			for (int i = 0; i < productoCliente.size(); i++) {
				if (productoCliente.get(i).getEstado().equals("activo")
						|| productoCliente.get(i).getEstado().equals("inactivo")) {
					estadoBandera = true;
					break;
				}
			}
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
		}	
				
		return estadoBandera;
	}

	
	
	
}
