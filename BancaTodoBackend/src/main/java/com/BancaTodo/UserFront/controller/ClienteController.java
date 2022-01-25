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

import com.BancaTodo.UserFront.dto.ClienteDto;
import com.BancaTodo.UserFront.dto.Mensaje;
import com.BancaTodo.UserFront.entity.ClienteEntity;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.ClienteService;
import com.BancaTodo.UserFront.services.ProductoService;

@CrossOrigin(origins = { "http://localhost:4200" })
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
		List<ClienteEntity> list = clienteService.findAll();
		return new ResponseEntity<List<ClienteEntity>>(list, HttpStatus.OK);
	}

	// Retorna un cliente por id
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") long id) {
		ClienteEntity cliente = clienteService.getById(id);
		return new ResponseEntity(cliente, HttpStatus.OK);
	}

	// Crea un nuevo cliente
	@PostMapping("")
	public ResponseEntity<?> create(@RequestBody ClienteEntity cliente) {
		cliente.setFechaCreacion(LocalDate.now());
		System.out.println(cliente.getFechaCreacion());
		clienteService.add(cliente);
		return new ResponseEntity(new Mensaje("Cliente creado"), HttpStatus.OK);
	}

	// Actualiza un cliente por id
	@PutMapping("/{id}/update")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody ClienteEntity cliente) {
		cliente.setId(id);
		clienteService.add(cliente);
		return new ResponseEntity(new Mensaje("Producto Actualizado"), HttpStatus.OK);
	}

	// Elimina un cliente
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") long id) {
		List<ProductoEntity> productoCliente = productoService.findByclienteId(id);
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
					new Mensaje("Cliente No puede ser eliminado, ya que hacen falta productos por cancelar"),
					HttpStatus.CONFLICT);
		}

	}
}
