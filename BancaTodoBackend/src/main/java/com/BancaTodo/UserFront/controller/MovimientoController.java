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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.dto.GeneralResponse;
import com.BancaTodo.UserFront.entity.MovimientoEntity;
import com.BancaTodo.UserFront.entity.ProductoEntity;
import com.BancaTodo.UserFront.services.MovimientoService;
import com.BancaTodo.UserFront.services.ProductoService;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/productos")
public class MovimientoController {

	@Autowired
	private MovimientoService movimientoService;

	@Autowired
	private ProductoService productoService;

	@GetMapping("/{cuentaId}/movimientos")
	public ResponseEntity<GeneralResponse<List<MovimientoEntity>>>  listar(@PathVariable("cuentaId") long cuentaId) {
		GeneralResponse<List<MovimientoEntity>> respuesta = new GeneralResponse<>();
		List<MovimientoEntity> datos = null;
		String mensaje = null;
		HttpStatus estadoHttp = null;
		
		try {
			datos = movimientoService.findBycuentaId(cuentaId);
			mensaje = "0 - found " + datos.size() + " movements";
			
			if (datos.isEmpty()) {
				mensaje = "1 - No registered movements were found.";
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

	@PostMapping("/{cuentaId}/consignar")
	public ResponseEntity<GeneralResponse<Integer>> agregarSaldo(@RequestBody MovimientoEntity movimiento,
			@PathVariable("cuentaId") Long cuentaId) {
		GeneralResponse<Integer> respuesta = new GeneralResponse<>();
		Integer datos = null;
		Optional<ProductoEntity> producto = null;
		String mensaje = null;			
		HttpStatus estadoHttp = null;
		
		try {
			
			producto = productoService.findById(cuentaId);
			datos = 0;
			
			if (!producto.get().getEstado().equals("cacelled")) {
				movimiento.setSaldoInicial(producto.get().getSaldo());
				producto.get().setSaldo(producto.get().getSaldo() + movimiento.getValor());//consignación
				movimiento.setSaldoFinal(producto.get().getSaldo());
				movimiento.setTipoMovimiento("deposit");
				movimiento.setTipoDebito("credit");
				movimiento.setFecha(LocalDate.now());
				movimiento.setCuentaId(cuentaId);
				movimiento.setCuentaDestino((long) 0);
				productoService.add(producto.get());
				movimientoService.add(movimiento);
				
				
				mensaje = "0 - Deposit created successfully";		
				respuesta.setDatos(datos);
				respuesta.setMensaje(mensaje);
				respuesta.setPeticionExitosa(true);
				estadoHttp = HttpStatus.CREATED;
			}else {
				
				mensaje = "1 - Deposit not made, account N°= " +producto.get().getNumeroCuenta() +" is canceled";		
				respuesta.setDatos(datos);
				respuesta.setMensaje(mensaje);
				respuesta.setPeticionExitosa(true);		
				estadoHttp = HttpStatus.OK;
			}
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
		}
		
		return new ResponseEntity<>(respuesta, estadoHttp);
	}

	@PostMapping("/{idProducto}/retirar")
	public ResponseEntity<GeneralResponse<Integer>> retirarSaldo(@RequestBody MovimientoEntity movimientoOrigen,
			@PathVariable("idProducto") long idCuenta) {
		
		GeneralResponse<Integer> mensajeRespuestaOrigen = new GeneralResponse<>();
		Integer datos = null;
		Optional<ProductoEntity> productoOrigen = null;
		String mensaje = null;
		MovimientoEntity movimientoGMF = null;
		HttpStatus estadoHttp = null;
		
		try {
			productoOrigen = productoService.findById(idCuenta);
			double valorTransaccion = movimientoOrigen.getValor();		
			
			boolean saldoValidado = validarSaldo(productoOrigen, valorTransaccion);
			
			mensajeRespuestaOrigen = valEstadosProductoOri(productoOrigen, saldoValidado); //valida estado producto/cuenta de origen
			
			if (saldoValidado && mensajeRespuestaOrigen.isPeticionExitosa()){
				movimientoOrigen.setTipoMovimiento("withdrawal");
				movimientoOrigen.setCuentaDestino(0);
				
				movimientoOrigen = realizarMovimientoDebito(productoOrigen, movimientoOrigen);
				productoOrigen.get().setSaldo(movimientoOrigen.getSaldoFinal());				
				productoService.add(productoOrigen.get());
				movimientoService.add(movimientoOrigen);
				
				movimientoGMF = realizarMovimientoGMF(productoOrigen, valorTransaccion);
				productoOrigen.get().setSaldo(movimientoGMF.getSaldoFinal());				
				productoService.add(productoOrigen.get());	
				movimientoService.add(movimientoGMF);
				
				mensaje = mensajeRespuestaOrigen.getMensaje() + " (Withdrawal)";
				estadoHttp = HttpStatus.CREATED;				
			}else if(!saldoValidado) {
				mensaje = mensajeRespuestaOrigen.getMensaje() + " (Withdrawal)";
				estadoHttp = HttpStatus.OK;				
			}
			else if(!mensajeRespuestaOrigen.isPeticionExitosa()) {
				mensaje = mensajeRespuestaOrigen.getMensaje() + " (Withdrawal)";
				estadoHttp = HttpStatus.OK;				
			}else {
				mensaje = "1 - Unidentified method error, contact support" + " (Withdrawal)";
				estadoHttp = HttpStatus.OK;					
			}
			datos = 0;
			mensajeRespuestaOrigen.setDatos(datos);
			mensajeRespuestaOrigen.setMensaje(mensaje);
			mensajeRespuestaOrigen.setPeticionExitosa(true);		
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "Hubo un fallo. Contacte al administrador";
			mensajeRespuestaOrigen.setMensaje(mensaje);
			mensajeRespuestaOrigen.setPeticionExitosa(false);
		}
		return new ResponseEntity<>(mensajeRespuestaOrigen, estadoHttp);		
	}

	@PostMapping("/{idCuenta}/transferencia")
	public ResponseEntity<GeneralResponse<Integer>> addSaldo(@RequestBody MovimientoEntity movimientoOrigen, @PathVariable("idCuenta") Long idCuenta,
			@PathParam("idCuentaDestino") Long idCuentaDestino, MovimientoEntity movimientoDestino) {
		GeneralResponse<Integer> respuesta = new GeneralResponse<>();
		Optional<ProductoEntity> productoOrigen = null;
		Optional<ProductoEntity> productoDestino = null;
		String mensaje = null;	
		HttpStatus estadoHttp = null;		
		
		try {
			productoOrigen = productoService.findById(idCuenta);
			productoDestino = productoService.findById(idCuentaDestino);
			
			double valorTransaccion = movimientoOrigen.getValor();
			
			boolean saldoValidado = validarSaldo(productoOrigen, valorTransaccion); //validar si el saldo al retirar dinero inluyendo GMF es suficiente
			GeneralResponse<?> mensajeRespuestaOrigen = valEstadosProductoOri(productoOrigen, saldoValidado); //valida estado producto/cuenta de origen
			GeneralResponse<?> mensajeRespuestaDestino = valEstadosProductoDes(productoDestino); //valida estado producto/cuenta destino

			
			if (saldoValidado && mensajeRespuestaOrigen.isPeticionExitosa() && mensajeRespuestaDestino.isPeticionExitosa()) {
				movimientoOrigen.setCuentaDestino(idCuentaDestino);
				movimientoOrigen.setTipoMovimiento("transfer");
				realizarTransferenciaDebito(productoOrigen, movimientoOrigen);
				movimientoDestino.setCuentaDestino(productoOrigen.get().getId());			
				realizarTransferenciaCredito(productoDestino, movimientoDestino,valorTransaccion);
				mensaje = mensajeRespuestaOrigen.getMensaje() + " (Transfer)";
				estadoHttp = HttpStatus.CREATED;	
			}else if (!mensajeRespuestaOrigen.isPeticionExitosa()){
				mensaje = mensajeRespuestaOrigen.getMensaje() + "(Error Transfer)";
				estadoHttp = HttpStatus.OK;				
			}else if (!mensajeRespuestaDestino.isPeticionExitosa()){
				mensaje = mensajeRespuestaDestino.getMensaje() + "(Error Transfer)";
				estadoHttp = HttpStatus.OK;				
			}
			else {
				mensaje = "1 - There was an error. Contact the administrator" + "(Error Transfer)" ;
				estadoHttp = HttpStatus.OK;
			}
			
			respuesta.setDatos(0);
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(true);
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;
			mensaje = "There was an error. Contact the administrator";
			respuesta.setMensaje(mensaje);
			respuesta.setPeticionExitosa(false);
		}
		
		return new ResponseEntity<>(respuesta, estadoHttp);
	}
	
	private GeneralResponse<Integer> valEstadosProductoOri(Optional<ProductoEntity> productoOrigen, boolean saldoValidado) {
		
		GeneralResponse<Integer> mensajeRespuesta = new GeneralResponse<>();
		
		if (productoOrigen.get().getEstado().toLowerCase().equals("desabled")) {
			mensajeRespuesta.setMensaje("1 - The account is desabled");
			mensajeRespuesta.setPeticionExitosa(false);
			return mensajeRespuesta;			
		} else if (productoOrigen.get().getEstado().toLowerCase().equals("cancelled")) {
			mensajeRespuesta.setMensaje("1- the account is canceled");
			mensajeRespuesta.setPeticionExitosa(false);
			return mensajeRespuesta;
		} else if (productoOrigen.get().getTipoCuenta().toLowerCase().equals("savings") && !saldoValidado) {
			mensajeRespuesta.setMensaje("2 - Not enought funds, the balance with the GMF must be greater than or equal to US$0.0");
			mensajeRespuesta.setPeticionExitosa(false);
			return mensajeRespuesta;
		} else if (productoOrigen.get().getTipoCuenta().toLowerCase().equals("current") && !saldoValidado) {
			mensajeRespuesta.setMensaje("2 - Not enought funds - Current account cannot be overdrawn by more than US$5,000");
			mensajeRespuesta.setPeticionExitosa(false);
			return mensajeRespuesta;
		} else {
			mensajeRespuesta.setMensaje("0 - Operation successfully");
			mensajeRespuesta.setPeticionExitosa(true);		
			return mensajeRespuesta;
			}		
	}
	private GeneralResponse<?> valEstadosProductoDes(Optional<ProductoEntity> productoDestino) {
		GeneralResponse mensajeRespuesta = new GeneralResponse<>();
		if (productoDestino.get().getEstado().toLowerCase().equals("cancelled")) {
			mensajeRespuesta.setMensaje("1- The recipient account is cancelled.");
			mensajeRespuesta.setPeticionExitosa(false);
			return mensajeRespuesta;
		}else if(productoDestino.get().getEstado().toLowerCase().equals("disabled")) {
			mensajeRespuesta.setMensaje("1- The recipient account is disabled");
			mensajeRespuesta.setPeticionExitosa(false);
			return mensajeRespuesta;
		}else {
			mensajeRespuesta.setMensaje("0 - Operation successfully");
			mensajeRespuesta.setPeticionExitosa(true);		
			return mensajeRespuesta;
		}		
		
	}
	
	private boolean validarSaldo(Optional<ProductoEntity> productoOrigen, double valorTransaccion) {
		boolean saldoValidado = false;
		
		double saldoGMF = productoOrigen.get().getSaldo() - valorTransaccion
				- valorTransaccion * 0.004;

		if (productoOrigen.get().getTipoCuenta().toLowerCase().equals("savings") && productoOrigen.get().getEstado()
				.toLowerCase().equals("enabled")
				&& saldoGMF >= 0) {
			saldoValidado = true;
		}
		if (productoOrigen.get().getTipoCuenta().toLowerCase().equals("current")
				&& productoOrigen.get().getEstado().toLowerCase().equals("enabled") && saldoGMF >= -5000) {
			saldoValidado = true;
		}	
		System.out.println(saldoGMF);
		return saldoValidado;
	}

	private void realizarTransferenciaDebito(Optional<ProductoEntity> productoOrigen, MovimientoEntity movimientoOrigen) {
		HttpStatus estadoHttp = null;
		try {
			// registro de operación sin GMF
			movimientoOrigen.setTipoMovimiento("transfer");
			movimientoOrigen = realizarMovimientoDebito(productoOrigen, movimientoOrigen);	
			productoOrigen.get().setSaldo(movimientoOrigen.getSaldoFinal());
			productoService.add(productoOrigen.get());
			movimientoService.add(movimientoOrigen);		
			// registro de operación del gmf
			MovimientoEntity movimientoGMF = realizarMovimientoGMF(productoOrigen,movimientoOrigen.getValor());		
			productoOrigen.get().setSaldo(movimientoGMF.getSaldoFinal());
			productoService.add(productoOrigen.get());
			movimientoService.add(movimientoGMF);
			estadoHttp = HttpStatus.CREATED;	
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			
		}
				
	}
	

	
	private MovimientoEntity realizarMovimientoDebito(Optional<ProductoEntity> productoOrigen,
			MovimientoEntity movimientoOrigen) {		
		movimientoOrigen.setSaldoInicial(productoOrigen.get().getSaldo());
		double saldo = productoOrigen.get().getSaldo() - movimientoOrigen.getValor();	
		movimientoOrigen.setSaldoFinal(saldo);
		movimientoOrigen.setFecha(LocalDate.now());
		movimientoOrigen.setTipoDebito("debit");
		movimientoOrigen.setCuentaId(productoOrigen.get().getId());
		return movimientoOrigen;
	}

	private MovimientoEntity realizarMovimientoGMF(Optional<ProductoEntity> productoOrigen, double valorTransaccion) {
		MovimientoEntity movimientoGMF = new MovimientoEntity();
		movimientoGMF.setSaldoInicial(productoOrigen.get().getSaldo());
		
		double saldoGMF = productoOrigen.get().getSaldo() - valorTransaccion * 0.004;		
		movimientoGMF.setSaldoFinal(saldoGMF);
		movimientoGMF.setValor(valorTransaccion * 0.004);
		movimientoGMF.setFecha(LocalDate.now());
		movimientoGMF.setTipoMovimiento("GMF");
		movimientoGMF.setTipoDebito("debit");
		movimientoGMF.setDescripcion("GMF");
		movimientoGMF.setCuentaId(productoOrigen.get().getId());
		movimientoGMF.setCuentaDestino(0);
		return movimientoGMF;
	}

	private void realizarTransferenciaCredito(Optional<ProductoEntity> productoDestino, MovimientoEntity movimientoDestino, double valorTransaccion) {	
		HttpStatus estadoHttp = null;
		try {
			// cuenta destino
			movimientoDestino.setSaldoInicial(productoDestino.get().getSaldo());
			productoDestino.get().setSaldo(productoDestino.get().getSaldo() + valorTransaccion);
			movimientoDestino.setTipoMovimiento("transfer");
			movimientoDestino.setTipoDebito("credit");
			movimientoDestino.setDescripcion("Transfer from account # " + productoDestino.get().getNumeroCuenta());
			movimientoDestino.setCuentaId(productoDestino.get().getId());		
			movimientoDestino.setFecha(LocalDate.now());
			movimientoDestino.setValor(valorTransaccion);
			movimientoDestino.setSaldoFinal(productoDestino.get().getSaldo());
			productoService.add(productoDestino.get());			
			movimientoService.add(movimientoDestino);
			estadoHttp = HttpStatus.CREATED;	
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
		}
		
	}
	

	private void realizarRetiro(Optional<ProductoEntity> productoOrigen, MovimientoEntity movimientoOrigen) {
		double saldo = productoOrigen.get().getSaldo() - movimientoOrigen.getValor();
		double saldoGMF = productoOrigen.get().getSaldo() - movimientoOrigen.getValor()
				- movimientoOrigen.getValor() * 0.004;
		
		try {
			// registro de operación sin GMF
			movimientoOrigen.setSaldoInicial(productoOrigen.get().getSaldo());
			movimientoOrigen.setSaldoFinal(saldo);
			movimientoOrigen.setTipoMovimiento("withdrawal");
			movimientoOrigen.setTipoDebito("debit");
			movimientoOrigen.setCuentaId(productoOrigen.get().getId());
			movimientoOrigen.setCuentaDestino(0);
			movimientoOrigen.setFecha(LocalDate.now());
			productoOrigen.get().setSaldo(saldo);
			productoService.add(productoOrigen.get());
			movimientoService.add(movimientoOrigen);
			// registro de operación del gmf
			MovimientoEntity movimientoGMF = new MovimientoEntity();
			movimientoGMF.setSaldoInicial(saldo);
			movimientoGMF.setSaldoFinal(saldoGMF);
			movimientoGMF.setValor(movimientoOrigen.getValor() * 0.004);
			movimientoGMF.setTipoMovimiento("GMF");
			movimientoGMF.setTipoDebito("debit");
			movimientoGMF.setDescripcion("GMF");
			movimientoGMF.setCuentaId(productoOrigen.get().getId());
			movimientoGMF.setCuentaDestino(0);
			movimientoGMF.setFecha(LocalDate.now());
			productoOrigen.get().setSaldo(saldoGMF);
			productoService.add(productoOrigen.get());
			movimientoService.add(movimientoGMF);
		} catch (Exception e) {
		
		}
		
	}

}
