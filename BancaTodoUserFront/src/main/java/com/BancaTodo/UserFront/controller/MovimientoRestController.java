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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.models.entity.Movimiento;
import com.BancaTodo.UserFront.models.entity.Producto;
import com.BancaTodo.UserFront.models.services.IMovimientoService;
import com.BancaTodo.UserFront.models.services.IProductoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/APIMovimientos")
public class MovimientoRestController {

	@Autowired
	private IMovimientoService movimientoService;

	@Autowired
	private IProductoService productoService;

	@GetMapping("/movimientos/{cuentaId}")
	public List<Movimiento> listar(@PathVariable("cuentaId") long cuentaId) {
		return movimientoService.findBycuentaId(cuentaId);
	}

	@PostMapping("/consignacion/producto/{idProducto}")
	public ResponseEntity<Movimiento> agregarSaldo(@RequestBody Movimiento movimiento,
			@PathVariable("idProducto") long idProducto) {
		Optional<Producto> producto = productoService.findById(idProducto);
		movimiento.setSaldoInicial(producto.get().getSaldo());
		producto.get().setSaldo(producto.get().getSaldo() + movimiento.getValor());
		movimiento.setSaldoFinal(producto.get().getSaldo());
		movimiento.setTipoMovimiento("consiginacion");
		movimiento.setTipoDebito("credito");
		movimiento.setCuentaId(idProducto);
		movimiento.setCuentaDestino((long) 0);
		productoService.add(producto.get());
		movimientoService.add(movimiento);
		return new ResponseEntity<>(movimiento, HttpStatus.CREATED);
	}

	@PostMapping("/retiro/producto/{idProducto}")
	public ResponseEntity<?> retirarSaldo(@RequestBody Movimiento movimientoOrigen,
			@PathVariable("idProducto") long idCuenta) {
		Optional<Producto> productoOrigen = productoService.findById(idCuenta);
		double saldo = productoOrigen.get().getSaldo() - movimientoOrigen.getValor();
		double saldoGMF = productoOrigen.get().getSaldo() - movimientoOrigen.getValor()
				- movimientoOrigen.getValor() * 0.004;

		if (productoOrigen.get().getTipoCuenta().equals("ahorro") && productoOrigen.get().getEstado().equals("activo")
				&& saldoGMF > 0) {
			realizarRetiro(productoOrigen, movimientoOrigen);
			return new ResponseEntity<>("Operacion realizada", HttpStatus.OK);
		}
		if (productoOrigen.get().getTipoCuenta().equals("corriente") && productoOrigen.get().getEstado().equals("activo") && saldoGMF >= (-2000000)) {
			realizarRetiro(productoOrigen, movimientoOrigen);
			return new ResponseEntity<>("Operacion realizada", HttpStatus.OK);
		}

		if (productoOrigen.get().getEstado().equals("inactivo")) {
			return new ResponseEntity<>("El producto está inactivo", HttpStatus.BAD_REQUEST);
		} else if (productoOrigen.get().getEstado().equals("cancelado")) {
			return new ResponseEntity<>("El producto está cancelado", HttpStatus.BAD_REQUEST);
		} else if (productoOrigen.get().getTipoCuenta().equals("ahorro") && saldoGMF <= 0) {
			return new ResponseEntity<>("Saldo insuficiente", HttpStatus.BAD_REQUEST);

		} else if (productoOrigen.get().getTipoCuenta().equals("corriente") && saldoGMF <= (-2000000)) {
			return new ResponseEntity<>("La cuenta corriente no puede sobregirarse a más de 2000000",
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Operación realizada con éxito", HttpStatus.OK);

		}
	}

	@PostMapping("/transferencia/productoOrigen/{idCuenta}/productoDestino/{CuentaDestino}")
	public ResponseEntity<?> addSaldo(@RequestBody Movimiento movimientoOrigen, @PathVariable("idCuenta") long idCuenta,
			@PathVariable("CuentaDestino") long idCuentaDestino, Movimiento movimientoDestino) {
		Optional<Producto> productoOrigen = productoService.findById(idCuenta);
		Optional<Producto> productoDestino = productoService.findById(idCuentaDestino);
		double saldo = productoOrigen.get().getSaldo() - movimientoOrigen.getValor();
		double saldoGMF = productoOrigen.get().getSaldo() - movimientoOrigen.getValor()
				- movimientoOrigen.getValor() * 0.004;

		if (productoOrigen.get().getTipoCuenta().equals("ahorro") && productoOrigen.get().getEstado().equals("activo")
				&& saldoGMF >= 0) {
			realizarTransferencia(productoOrigen, productoDestino, movimientoOrigen, movimientoDestino);
			return new ResponseEntity<>("Operacion realizada", HttpStatus.OK);
		}
		if (productoOrigen.get().getTipoCuenta().equals("corriente")
				&& productoOrigen.get().getEstado().equals("activo") && saldoGMF >= (-2000000)) {
			realizarTransferencia(productoOrigen, productoDestino, movimientoOrigen, movimientoDestino);
			return new ResponseEntity<>("Operacion realizada", HttpStatus.OK);
		}

		if (productoOrigen.get().getEstado().equals("inactivo")) {
			return new ResponseEntity<>("El producto destino está inactivo", HttpStatus.BAD_REQUEST);
		} else if (productoDestino.get().getEstado().equals("inactivo")) {
			return new ResponseEntity<>("El producto de origen está inactivo", HttpStatus.BAD_REQUEST);
		} else if (productoOrigen.get().getTipoCuenta().equals("ahorro") && saldoGMF <= 0) {
			return new ResponseEntity<>("Saldo insuficiente", HttpStatus.BAD_REQUEST);

		} else if (productoOrigen.get().getTipoCuenta().equals("corriente") && saldoGMF <= (-2000000)) {
			return new ResponseEntity<>("La cuenta corriente no puede sobregirarse a más de 2000000",
					HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("Operación realizada con éxito", HttpStatus.OK);

		}

	}

	private void realizarTransferencia(Optional<Producto> productoOrigen, Optional<Producto> productoDestino,
			Movimiento movimientoOrigen, Movimiento movimientoDestino) {
		double saldo = productoOrigen.get().getSaldo() - movimientoOrigen.getValor();
		double saldoGMF = productoOrigen.get().getSaldo() - movimientoOrigen.getValor()
				- movimientoOrigen.getValor() * 0.004;
		// registro de operación sin GMF
		movimientoOrigen.setSaldoInicial(productoOrigen.get().getSaldo());
		movimientoOrigen.setSaldoFinal(saldo);
		movimientoOrigen.setTipoMovimiento("transferencia");
		movimientoOrigen.setTipoDebito("debito");
		movimientoOrigen.setCuentaId(productoOrigen.get().getId());
		movimientoOrigen.setCuentaDestino(productoDestino.get().getId());
		productoOrigen.get().setSaldo(saldo);
		productoService.add(productoOrigen.get());
		movimientoService.add(movimientoOrigen);
		// registro de operación del gmf
		Movimiento movimientoGMF = new Movimiento();
		movimientoGMF.setSaldoInicial(saldo);
		movimientoGMF.setSaldoFinal(saldoGMF);
		movimientoGMF.setValor(movimientoOrigen.getValor() * 0.004);
		movimientoGMF.setTipoMovimiento("GMF");
		movimientoGMF.setTipoDebito("debito");
		movimientoGMF.setDescripcion("GMF");
		movimientoGMF.setCuentaId(productoOrigen.get().getId());
		movimientoGMF.setCuentaDestino(0);
		movimientoGMF.setFecha(movimientoOrigen.getFecha());
		productoOrigen.get().setSaldo(saldoGMF);
		productoService.add(productoOrigen.get());
		movimientoService.add(movimientoGMF);
		// cuenta destino
		movimientoDestino.setSaldoInicial(productoDestino.get().getSaldo());
		productoDestino.get().setSaldo(productoDestino.get().getSaldo() + movimientoOrigen.getValor());
		movimientoDestino.setTipoMovimiento("transferencia");
		movimientoDestino.setTipoDebito("credito");
		movimientoDestino.setDescripcion("Transferencia desde cuenta N " + productoDestino.get().getNumeroCuenta());
		movimientoDestino.setCuentaId(productoDestino.get().getId());
		movimientoDestino.setCuentaDestino(productoOrigen.get().getId());
		movimientoDestino.setFecha(movimientoOrigen.getFecha());
		movimientoDestino.setValor(movimientoOrigen.getValor());
		movimientoDestino.setSaldoFinal(productoDestino.get().getSaldo());
		productoService.add(productoDestino.get());
		movimientoService.add(movimientoDestino);
	}

	private void realizarRetiro(Optional<Producto> productoOrigen, Movimiento movimientoOrigen) {
		double saldo = productoOrigen.get().getSaldo() - movimientoOrigen.getValor();
		double saldoGMF = productoOrigen.get().getSaldo() - movimientoOrigen.getValor()
				- movimientoOrigen.getValor() * 0.004;
		// registro de operación sin GMF
		movimientoOrigen.setSaldoInicial(productoOrigen.get().getSaldo());
		movimientoOrigen.setSaldoFinal(saldo);
		movimientoOrigen.setTipoMovimiento("retiro");
		movimientoOrigen.setTipoDebito("debito");
		movimientoOrigen.setCuentaId(productoOrigen.get().getId());
		movimientoOrigen.setCuentaDestino(0);
		productoOrigen.get().setSaldo(saldo);
		productoService.add(productoOrigen.get());
		movimientoService.add(movimientoOrigen);
		// registro de operación del gmf
		Movimiento movimientoGMF = new Movimiento();
		movimientoGMF.setSaldoInicial(saldo);
		movimientoGMF.setSaldoFinal(saldoGMF);
		movimientoGMF.setValor(movimientoOrigen.getValor() * 0.004);
		movimientoGMF.setTipoMovimiento("GMF");
		movimientoGMF.setTipoDebito("debito");
		movimientoGMF.setDescripcion("GMF");
		movimientoGMF.setCuentaId(productoOrigen.get().getId());
		movimientoGMF.setCuentaDestino(0);
		movimientoGMF.setFecha(movimientoOrigen.getFecha());
		productoOrigen.get().setSaldo(saldoGMF);
		productoService.add(productoOrigen.get());
		movimientoService.add(movimientoGMF);
	}

}
