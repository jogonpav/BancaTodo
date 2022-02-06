package com.BancaTodo.UserFront.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.dto.GeneralResponse;
import com.BancaTodo.UserFront.dto.Mensaje;
import com.BancaTodo.UserFront.entity.ClienteEntity;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.ClienteService;
import com.BancaTodo.UserFront.services.ProductoService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProductoService productoService;

	// Retornar lista de clientes
	//@CrossOrigin(origins = "*")
	@GetMapping("")
	public ResponseEntity<GeneralResponse<List<ClienteEntity>> > listar() {
		GeneralResponse <List<ClienteEntity>> respuesta = new GeneralResponse<>();
		List<ClienteEntity> datos = null;
		String mensaje = null;
		HttpStatus estadoHttp = null;
		
		try {
			datos = clienteService.findAll();
			mensaje = "0 - Se encontró " + datos.size() + " clientes";
			
			if (datos.isEmpty()) {
				mensaje = "1 - No se encontró clientes registrados";
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
	/*public ResponseEntity<List<ClienteEntity>> listar() {
	List<ClienteEntity> list = clienteService.findAll();
	return new ResponseEntity<List<ClienteEntity>>(list, HttpStatus.OK);
}*/

	// Retorna un cliente por id
	@GetMapping("/{id}")
	public ResponseEntity<GeneralResponse <ClienteEntity>> getById(@PathVariable("id") long id) {
	GeneralResponse <ClienteEntity> respuesta = new GeneralResponse<>();
	ClienteEntity datos = null;
	String mensaje = null;
	HttpStatus estadoHttp = null;
	
	try {	
		datos = clienteService.getById(id);
		mensaje = "0 - Se encontró el cliente seleccionado";
		
		if (datos == null) {
			mensaje = "1 - Cliente no encontrado";					
		}
		
		respuesta.setDatos(datos);
		respuesta.setMensaje(mensaje);
		respuesta.setPeticionExitosa(true);	
		
		estadoHttp = HttpStatus.OK;
		
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
	public ResponseEntity<GeneralResponse<ClienteEntity>> create(@RequestBody ClienteEntity cliente) {
		GeneralResponse<ClienteEntity> respuesta = new GeneralResponse<>();
		ClienteEntity datos = null;
		String mensaje = null;	
		HttpStatus estadoHttp = null;
		
		try {			
			cliente.setFechaCreacion(LocalDate.now());
			datos = clienteService.add(cliente);
			mensaje = "0 - Cliente creado exitoxamente";
			
			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			estadoHttp = HttpStatus.CREATED;
			
		} catch (Exception e) {
			
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			
		}
		
		return new ResponseEntity<>(respuesta, estadoHttp);
	}
	
	/*public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
		cliente.setFechaCreacion(LocalDate.now());		
		clienteService.add(cliente);
		return new ResponseEntity<Object>(new Mensaje("Cliente creado"), HttpStatus.OK);
	}*/

	// Actualiza un cliente por id
	@PutMapping("/{id}/update")
	public ResponseEntity<GeneralResponse <Long>> update(@PathVariable("id") long id, @RequestBody ClienteEntity cliente) {
		GeneralResponse <Long> respuesta = new GeneralResponse<>();
		HttpStatus estadoHttp = null;
		String mensaje = null;
		
		try {
			cliente.setId(id);						
			clienteService.add(cliente);
						
			mensaje = "0 - Cliente Actualizado";
			estadoHttp = HttpStatus.OK;
			respuesta.setDatos(id);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
		}
		return new ResponseEntity<>(respuesta, estadoHttp);
	}

	// Elimina un cliente
	@DeleteMapping("/{id}/delete")	
	public ResponseEntity<GeneralResponse <Long>> delete(@PathVariable("id") long id) {	
		GeneralResponse <Long> respuesta = new GeneralResponse<>();
		HttpStatus estadoHttp = null;
		String mensaje = null;
		
		try {
			boolean estadoBandera = existeProductosActivos(id);
			if (!estadoBandera) {				
				clienteService.delete(id);
				mensaje = "0 - Cliente eliminado exitosamente";	
				estadoHttp = HttpStatus.OK;
				
			}else {
				mensaje ="1 - Cliente no pudo ser eliminado, todos los productos deben estar cancelados";
				estadoHttp = HttpStatus.OK;
			}
			respuesta.setDatos(id);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			
			
			} catch (Exception e) {
				estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
				mensaje = "Hubo un fallo. Contacte al administrador";
				respuesta.setMensaje(mensaje);
				respuesta.setPeticionExitosa(false);
				
			}			
		 
			return new ResponseEntity<>(respuesta, estadoHttp);		
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
	
	/*public ResponseEntity<?> delete(@PathVariable("id") long id)  {
		List<ProductoEntity> productoCliente=null;
		try {
			productoCliente = productoService.findByclienteId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean estadoBandera = existeProductosActivos(productoCliente);				
		ResponseEntity<?> respuesta =  deleteCliente(id, estadoBandera);		
		return respuesta;
	}
	
	public boolean existeProductosActivos(List<ProductoEntity> productoCliente) {
		boolean estadoBandera = false;
		for (int i = 0; i < productoCliente.size(); i++) {
			if (productoCliente.get(i).getEstado().equals("activo")
					|| productoCliente.get(i).getEstado().equals("inactivo")) {
				estadoBandera = true;
				break;
			}
		}
		return estadoBandera;
	}
	
	public ResponseEntity<?> deleteCliente(long id, boolean estadoBandera) {
		if (!estadoBandera) {
			clienteService.delete(id);
			return new ResponseEntity<Object>(new Mensaje("Cliente eliminado"), HttpStatus.OK);
		} else {
			return new ResponseEntity(
					"Cliente No puede ser eliminado, ya que hacen falta productos por cancelar",
					HttpStatus.CONFLICT);
		}

	}*/
	
	

	
	

}
