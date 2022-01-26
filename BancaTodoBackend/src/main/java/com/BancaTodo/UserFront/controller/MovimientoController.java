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

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/productos")
public class MovimientoController {

	@Autowired
	private MovimientoService movimientoService;

	@Autowired
	private ProductoService productoService;

	@GetMapping("/{cuentaId}/movimientos")
	public List<MovimientoEntity> listar(@PathVariable("cuentaId") long cuentaId) {
		return movimientoService.findBycuentaId(cuentaId);
	}

	@PostMapping("/{idProducto}/agregar")
	public ResponseEntity<?> agregarSaldo(@RequestBody MovimientoEntity movimiento,
			@PathVariable("idProducto") long idProducto) {
		HttpStatus estadoHttp = null;
		String mensaje = null;	
		Optional<ProductoEntity> producto = null;
		
		try {
			producto = productoService.findById(idProducto);
			movimiento.setSaldoInicial(producto.get().getSaldo());
			producto.get().setSaldo(producto.get().getSaldo() + movimiento.getValor());//consignación
			movimiento.setSaldoFinal(producto.get().getSaldo());
			movimiento.setTipoMovimiento("consiginacion");
			movimiento.setTipoDebito("credito");
			movimiento.setFecha(LocalDate.now());
			movimiento.setCuentaId(idProducto);
			movimiento.setCuentaDestino((long) 0);
			productoService.add(producto.get());
			movimientoService.add(movimiento);
			
			estadoHttp = HttpStatus.CREATED;	
			mensaje = "Movimiento creado exitosamente";
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
		}
		
		return new ResponseEntity<>(mensaje, estadoHttp);
	}

	@PostMapping("/{idProducto}/retirar")
	public ResponseEntity<?> retirarSaldo(@RequestBody MovimientoEntity movimientoOrigen,
			@PathVariable("idProducto") long idCuenta) {
		HttpStatus estadoHttp = null;
		String mensaje = null;	
		Optional<ProductoEntity> productoOrigen = null;
		try {
			productoOrigen = productoService.findById(idCuenta);
			double valorTransaccion = movimientoOrigen.getValor();
			double saldo = productoOrigen.get().getSaldo() - valorTransaccion;
			double saldoGMF = productoOrigen.get().getSaldo() - valorTransaccion
					- valorTransaccion * 0.004;
			
			boolean saldoValidado = validarSaldo(productoOrigen, valorTransaccion);
			
			if (productoOrigen.get().getTipoCuenta().equals("ahorro") && productoOrigen.get().getEstado().equals("activo")
					&& saldoValidado) {
				realizarRetiro(productoOrigen, movimientoOrigen);
			
			}
			if (productoOrigen.get().getTipoCuenta().equals("corriente") && productoOrigen.get().getEstado().equals("activo") && saldoValidado) {
				realizarRetiro(productoOrigen, movimientoOrigen);
			}
						
			if (productoOrigen.get().getEstado().equals("inactivo")) {
				mensaje = "El producto está inactivo";
				estadoHttp = HttpStatus.CONFLICT;
			} else if (productoOrigen.get().getEstado().equals("cancelado")) {
				mensaje = "El producto está cancelado";
				estadoHttp = HttpStatus.CONFLICT;
			} else if (productoOrigen.get().getTipoCuenta().equals("ahorro") && saldoGMF <= 0) {
				mensaje = "Saldo insuficiente";
				estadoHttp = HttpStatus.CONFLICT;
			} else if (productoOrigen.get().getTipoCuenta().equals("corriente") && saldoGMF <= (-2000000)) {
				mensaje = "La cuenta corriente no puede sobregirarse a más de 2000000";
				estadoHttp = HttpStatus.CONFLICT;
			} else {
				mensaje = "Operación realizada con éxito";
				estadoHttp = HttpStatus.CREATED;
			}
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
					}
		return new ResponseEntity<>(mensaje, estadoHttp);		
	}

	@PostMapping("/{idCuenta}/transferencia")
	public ResponseEntity<?> addSaldo(@RequestBody MovimientoEntity movimientoOrigen, @PathVariable("idCuenta") long idCuenta,
			@PathParam("cuentaDestino") Long idCuentaDestino, MovimientoEntity movimientoDestino) {
		
		Optional<ProductoEntity> productoOrigen = null;
		Optional<ProductoEntity> productoDestino = null;
		
		HttpStatus estadoHttp = null;
		String mensaje = null;	
		
		try {
			productoOrigen = productoService.findById(idCuenta);
			productoDestino = productoService.findById(idCuentaDestino);
			
			double valorTransaccion = movimientoOrigen.getValor();
			
			boolean saldoValidado = validarSaldo(productoOrigen, valorTransaccion);
			
			GeneralResponse mensajeRespuesta = validarEstadosOperacion(productoOrigen, productoDestino, saldoValidado);
			
			//ResponseEntity<?> respuesta = validarEstadosOperacion(productoOrigen, productoDestino, saldoValidado);
			ResponseEntity<?> respuesta;
			if (saldoValidado && !mensajeRespuesta.isPeticionExitosa()) {
				movimientoOrigen.setCuentaDestino(idCuentaDestino);
				realizarTransferenciaDebito(productoOrigen, movimientoOrigen);
				movimientoDestino.setCuentaDestino(productoOrigen.get().getId());			
				realizarTransferenciaCredito(productoDestino, movimientoDestino,valorTransaccion);
				mensaje = mensajeRespuesta.getMensaje() + " (Transferencia)";
				estadoHttp = HttpStatus.CREATED;
				
					
			}else {
				mensaje = mensajeRespuesta.getMensaje() + "(Transferencia Error)";
				estadoHttp = HttpStatus.CONFLICT;
			}		
			
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			mensaje = "Hubo un fallo. Contacte al administrador";
		}
		
		return new ResponseEntity<>(mensaje, estadoHttp);
	}
	
	private GeneralResponse validarEstadosOperacion(Optional<ProductoEntity> productoOrigen,
			Optional<ProductoEntity> productoDestino, boolean saldoValidado) {
		
		GeneralResponse mensajeRespuesta = new GeneralResponse();
		
		if (productoOrigen.get().getEstado().equals("inactivo")) {
			mensajeRespuesta.setMensaje("1 - El producto origen está inactivo");
			mensajeRespuesta.setPeticionExitosa(true);
			return mensajeRespuesta;			
		} else if (productoOrigen.get().getEstado().equals("cancelado")) {
			mensajeRespuesta.setMensaje("1- El producto de origen está cancelado");
			mensajeRespuesta.setPeticionExitosa(true);
			return mensajeRespuesta;
		}else if (productoDestino.get().getEstado().equals("cancelado")) {
			mensajeRespuesta.setMensaje("1- El producto de destino está cancelado");
			mensajeRespuesta.setPeticionExitosa(true);
			return mensajeRespuesta;
		}else if (productoDestino.get().getEstado().equals("inactivo")) {
			mensajeRespuesta.setMensaje("1- El producto de destino está inactivo");
			mensajeRespuesta.setPeticionExitosa(true);
			return mensajeRespuesta;
		} else if (productoOrigen.get().getTipoCuenta().equals("ahorro") && !saldoValidado) {
			mensajeRespuesta.setMensaje("2 - fondos insuficiente");
			mensajeRespuesta.setPeticionExitosa(true);
			return mensajeRespuesta;

		} else if (productoOrigen.get().getTipoCuenta().equals("corriente") && !saldoValidado) {
			mensajeRespuesta.setMensaje("2 - Fondos insuficientes - La cuenta corriente no puede sobregirarse a más de $2000000");
			mensajeRespuesta.setPeticionExitosa(true);
			return mensajeRespuesta;
		} else {
			mensajeRespuesta.setMensaje("0 - Operación realizada Exitosamente");
			mensajeRespuesta.setPeticionExitosa(false);		
			return mensajeRespuesta;
			}		
	}
	/*private ResponseEntity<?> validarEstadosOperacion(Optional<ProductoEntity> productoOrigen,
			Optional<ProductoEntity> productoDestino, boolean saldoValidado) {
		if (productoOrigen.get().getEstado().equals("inactivo")) {
			return new ResponseEntity<>("1 - El producto origen está inactivo", HttpStatus.BAD_REQUEST);
		} else if (productoOrigen.get().getEstado().equals("cancelado")) {
			return new ResponseEntity<>("1- El producto de origen está cancelado", HttpStatus.BAD_REQUEST);
		}else if (productoDestino.get().getEstado().equals("cancelado")) {
			return new ResponseEntity<>("1- El producto de destino está cancelado", HttpStatus.BAD_REQUEST);
		}else if (productoDestino.get().getEstado().equals("inactivo")) {
			return new ResponseEntity<>("1- El producto de destino está inactivo", HttpStatus.BAD_REQUEST);
		} else if (productoOrigen.get().getTipoCuenta().equals("ahorro") && !saldoValidado) {
			return new ResponseEntity<>("2 - fondos insuficiente", HttpStatus.BAD_REQUEST);

		} else if (productoOrigen.get().getTipoCuenta().equals("corriente") && !saldoValidado) {
			return new ResponseEntity<>("2 - Fondos insuficientes - La cuenta corriente no puede sobregirarse a más de $2000000",
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Operación realizada exitozamente", HttpStatus.OK);
		}
	}
*/
	private boolean validarSaldo(Optional<ProductoEntity> productoOrigen, double valorTransaccion) {
		boolean saldoValidado = false;
		
		double saldo = productoOrigen.get().getSaldo() - valorTransaccion;
		double saldoGMF = productoOrigen.get().getSaldo() - valorTransaccion
				- valorTransaccion * 0.004;

		if (productoOrigen.get().getTipoCuenta().equals("ahorro") && productoOrigen.get().getEstado().equals("activo")
				&& saldoGMF >= 0) {
			saldoValidado = true;
		}
		if (productoOrigen.get().getTipoCuenta().equals("corriente")
				&& productoOrigen.get().getEstado().equals("activo") && saldoGMF >= (-2000000)) {
			saldoValidado = true;
		}	
		return saldoValidado;
	}

	private void realizarTransferenciaDebito(Optional<ProductoEntity> productoOrigen, MovimientoEntity movimientoOrigen) {
		double valorTransaccion = movimientoOrigen.getValor();
		HttpStatus estadoHttp = null;
		try {
			// registro de operación sin GMF
			movimientoOrigen = realizarMovimientoDebito(productoOrigen, movimientoOrigen, valorTransaccion);	
			productoOrigen.get().setSaldo(movimientoOrigen.getSaldoFinal());
			productoService.add(productoOrigen.get());
			movimientoService.add(movimientoOrigen);		
			// registro de operación del gmf
			MovimientoEntity movimientoGMF = realizarMovimientoGMF(productoOrigen,valorTransaccion);		
			productoOrigen.get().setSaldo(movimientoGMF.getSaldoFinal());
			productoService.add(productoOrigen.get());
			movimientoService.add(movimientoGMF);
			estadoHttp = HttpStatus.CREATED;	
		} catch (Exception e) {
			estadoHttp = HttpStatus.INTERNAL_SERVER_ERROR;	
			
		}
				
	}
	

	
	private MovimientoEntity realizarMovimientoDebito(Optional<ProductoEntity> productoOrigen,
			MovimientoEntity movimientoOrigen, double valorTransaccion) {		
		movimientoOrigen.setSaldoInicial(productoOrigen.get().getSaldo());
		double saldo = productoOrigen.get().getSaldo() - valorTransaccion;	
		movimientoOrigen.setSaldoFinal(saldo);
		movimientoOrigen.setTipoMovimiento("transferencia");
		movimientoOrigen.setFecha(LocalDate.now());
		movimientoOrigen.setTipoDebito("debito");
		movimientoOrigen.setCuentaId(productoOrigen.get().getId());
		return movimientoOrigen;
	}

	private MovimientoEntity realizarMovimientoGMF(Optional<ProductoEntity> productoOrigen, double valorTransaccion) {
		MovimientoEntity movimientoGMF = new MovimientoEntity();
		movimientoGMF.setSaldoInicial(productoOrigen.get().getSaldo());
		
		double saldoGMF = productoOrigen.get().getSaldo() - valorTransaccion
				- valorTransaccion * 0.004;		
		movimientoGMF.setSaldoFinal(saldoGMF);
		movimientoGMF.setValor(valorTransaccion * 0.004);
		movimientoGMF.setFecha(LocalDate.now());
		movimientoGMF.setTipoMovimiento("GMF");
		movimientoGMF.setTipoDebito("debito");
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
			movimientoDestino.setTipoMovimiento("transferencia");
			movimientoDestino.setTipoDebito("credito");
			movimientoDestino.setDescripcion("Transferencia desde cuenta N " + productoDestino.get().getNumeroCuenta());
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
			movimientoOrigen.setTipoMovimiento("retiro");
			movimientoOrigen.setTipoDebito("debito");
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
			movimientoGMF.setTipoDebito("debito");
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
