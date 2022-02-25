package com.BankFor.UserBack.controller;

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

import com.BankFor.UserBack.dto.GeneralResponse;
import com.BankFor.UserBack.entity.ProductoEntity;
import com.BankFor.UserBack.services.ProductoService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	

	// Lista productos por clienteId
	@GetMapping
	public ResponseEntity<GeneralResponse<List<ProductoEntity>>> listar(@PathParam("clienteId") Long clienteId) {
		GeneralResponse<List<ProductoEntity>> respuesta = new GeneralResponse<>();
		List<ProductoEntity> datos = null;
		String mensaje = null;
		HttpStatus estadoHttp = null;
		try {
			datos = productoService.findByclienteId(clienteId);
			mensaje = "0 - found " + datos.size() + " accounts";

			if (datos.isEmpty()) {
				mensaje = "1 - No registered accounts found";
			}
			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);

			estadoHttp = HttpStatus.OK;

		} catch (Exception e) {

			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(respuesta, estadoHttp);
	}

	// obtiene producto por id
	@GetMapping("/{id}")
	public ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> getById(@PathVariable("id") long id) {
		GeneralResponse<Optional<ProductoEntity>> respuesta = new GeneralResponse<>();
		Optional<ProductoEntity> datos = null;
		String mensaje = null;
		HttpStatus estadoHttp = null;

		try {
			datos = productoService.findById(id);
			mensaje = "0 - Account found.";
			if (datos.isEmpty()) {
				mensaje = "1 - Not accounts found.";
			}
			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			estadoHttp = HttpStatus.OK;
		} catch (Exception e) {
			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<>(respuesta, estadoHttp);
	}

	// obtiene productos para cliente específico, distinto idProducto  (Transferencia entre productos del mismo cliente)
	@GetMapping("/{idProducto}/cliente/{idCliente}")
	public ResponseEntity<GeneralResponse<List<ProductoEntity>>> findProductosByIdClienteDistintctId(
			@PathVariable("idProducto") long idProducto, @PathVariable("idCliente") long idCliente) {

		GeneralResponse<List<ProductoEntity>> respuesta = new GeneralResponse<>();
		List<ProductoEntity> datos = null;
		String mensaje = null;
		HttpStatus estadoHttp = null;

		try {
			datos = productoService.findProductosByIdClienteDistintctId(idProducto, idCliente);			
			mensaje = "0 - found " + datos.size() + " accounts";

			if (datos.isEmpty()) {
				mensaje = "1 - Not accounts found.";
			}
			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);

			estadoHttp = HttpStatus.OK;

		} catch (Exception e) {

			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(respuesta, estadoHttp);
	}

	// obtiene los productos diferente a cliente id
	@GetMapping("/cliente/{idCliente}/distintc")
	public ResponseEntity<GeneralResponse<List<ProductoEntity>>> findByDistintctIdCliente(@PathVariable("idCliente") long clienteId) {
		
		GeneralResponse<List<ProductoEntity>> respuesta = new GeneralResponse<>();
		List<ProductoEntity> datos = null;
		String mensaje = null;
		HttpStatus estadoHttp = null;

		try {
			datos = productoService.findByDistintctIdCliente(clienteId);			
			mensaje = "0 - found " + datos.size() + " accounts";

			if (datos.isEmpty()) {
				mensaje = "1 - Not accounts found.";
			}
			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);

			estadoHttp = HttpStatus.OK;

		} catch (Exception e) {

			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>(respuesta, estadoHttp);
		
		
	}

	// crea producto para cliente específico
	@PostMapping
	public ResponseEntity<GeneralResponse<Integer>> add(@PathParam("clienteId") long clienteId,
			@RequestBody ProductoEntity producto) {
		GeneralResponse<Integer> respuesta = new GeneralResponse<>();
		Integer datos = null;
		HttpStatus estadoHttp = null;
		String mensaje = null;

		try {
			producto.setNumeroCuenta(createProductNumber());
			producto.setSaldo((double) 0);
			producto.setClienteId(clienteId);
			producto.setEstado("enabled");
			producto.setFechaApertura(LocalDate.now());

			productoService.add(producto);
			datos = 0;
			mensaje = "0 - Account created successfully";

			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			estadoHttp = HttpStatus.CREATED;

		} catch (Exception e) {

			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);

		}
		System.out.print(estadoHttp);
		return new ResponseEntity<>(respuesta, estadoHttp);
	}

	@PutMapping("/{idProducto}/activate")
	public ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> activate(
			@PathVariable("idProducto") Long idProducto) {
		String tipoEstado = "enabled";
		ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> respuesta = cambiarEstado(tipoEstado, idProducto);
		return respuesta;
	}

	@PutMapping("/{idProducto}/inactivate")
	public ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> inactivate(
			@PathVariable("idProducto") Long idProducto) {
		String tipoEstado = "disabled";
		ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> respuesta = cambiarEstado(tipoEstado, idProducto);
		return respuesta;
	}

	@PutMapping("/{idProducto}/cancel")
	public ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> cancel(
			@PathVariable("idProducto") Long idProducto) {
		String tipoEstado = "cancelled";
		ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> respuesta = cambiarEstado(tipoEstado, idProducto);
		return respuesta;
	}

	private ResponseEntity<GeneralResponse<Optional<ProductoEntity>>> cambiarEstado(String tipoEstado,
			Long idProducto) {
		GeneralResponse<Optional<ProductoEntity>> respuesta = new GeneralResponse<>();
		Optional<ProductoEntity> datos = null;

		String mensaje = null;
		HttpStatus estadoHttp = null;
		try {
			datos = productoService.findById(idProducto);

			switch (tipoEstado) {
			case "enabled":
				if (!datos.get().getEstado().toLowerCase().equals("cancelled")) {
					datos.get().setEstado(tipoEstado);
					mensaje = "0 - Account enabled";
					break;
				} else {
					mensaje = "1 - The product cannot be activated, the product was canceled";
				}
				break;
			case "disabled":
				datos.get().setEstado(tipoEstado);
				mensaje = "0 - Account disabled";
				break;
			case "cancelled":
				if (datos.get().getSaldo() == 0) {
					datos.get().setEstado(tipoEstado);
					mensaje = "0 - Account cancelled";
				} else {
					mensaje = "1 - Account cannot be cancelled, balance must be US$0";
				}
				break;
			}
			productoService.update(datos.get());
			respuesta.setDatos(datos);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			estadoHttp = HttpStatus.OK;

		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
		}
		return new ResponseEntity<>(respuesta, estadoHttp);

	}
	
	
	String createProductNumber() throws Exception {		
		boolean existProductByNumber = true;
		String number = "1266169510251041";	
		existProductByNumber = productoService.existsBynumeroCuenta(number);
		number = "";
		System.out.println(existProductByNumber);
		while(existProductByNumber) {					
			for (int i = 0; i<4; i++) {			
				Integer x = (int)(Math.random()*1000+1000);	
				number = number + Integer.toString(x);
			}			
			existProductByNumber = productoService.existsBynumeroCuenta(number);
			System.out.println(existProductByNumber);
		}
		return number;
	}
	

}
