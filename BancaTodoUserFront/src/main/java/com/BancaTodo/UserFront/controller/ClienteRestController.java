package com.BancaTodo.UserFront.controller;

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

import com.BancaTodo.UserFront.dto.ClienteDto;
import com.BancaTodo.UserFront.dto.Mensaje;
import com.BancaTodo.UserFront.models.entity.Cliente;
import com.BancaTodo.UserFront.models.entity.Producto;
import com.BancaTodo.UserFront.models.services.IClienteService;
import com.BancaTodo.UserFront.models.services.IProductoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/APICliente")
public class ClienteRestController {

	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IProductoService productoService;
	
	//Retornar lista de clientes
	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> listar() {
		List<Cliente> list = clienteService.findAll();
		return new ResponseEntity<List<Cliente>>(list, HttpStatus.OK);
	}
	
	//Retorna un cliente por id
	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		Cliente cliente = clienteService.getById(id);
		return new ResponseEntity(cliente, HttpStatus.OK);
	}
	/*
	 * public Cliente getById(@PathVariable("id") long id) { return
	 * clienteService.getById(id); }
	 */
	
	//Crea un nuevo cliente
	@PostMapping("/cliente/create")
	public ResponseEntity<?> create(@RequestBody Cliente cliente) {
		clienteService.add(cliente);
		return new ResponseEntity(new Mensaje("Producto creado"), HttpStatus.OK);
	}
	
	//Actualiza un cliente por id
	@PutMapping("/cliente/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Cliente cliente) {	
		cliente.setId(id);
		clienteService.add(cliente);
		return new ResponseEntity(new Mensaje("Producto Actualizado"), HttpStatus.OK);
	}

	//Elimina un cliente
	@DeleteMapping("/cliente/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		List<Producto> productoCliente = productoService.findByclienteId(id);
		boolean estadoBandera = false;
		for (int i=0; i<productoCliente.size();i++) {
			if (productoCliente.get(i).getEstado().equals("activo") || productoCliente.get(i).getEstado().equals("inactivo")) {
				estadoBandera =true;
				break;
				}			
		}
		if (!estadoBandera) {
			clienteService.delete(id);
			return new ResponseEntity(new Mensaje("Cliente eliminado"), HttpStatus.OK);			
		}else {
			return new ResponseEntity(new Mensaje("Cliente No puede ser eliminado, ya que hacen falta productos por cancelar"), HttpStatus.OK);		
		}
	}
}
