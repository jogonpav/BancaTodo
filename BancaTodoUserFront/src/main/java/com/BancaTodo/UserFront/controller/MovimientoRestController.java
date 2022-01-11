package com.BancaTodo.UserFront.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.models.entity.Movimiento;
import com.BancaTodo.UserFront.models.services.IMovimientoService;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class MovimientoRestController {
	
	@Autowired
	private IMovimientoService movimientoService;
	
	@GetMapping("/movimientos")
	public List<Movimiento> listar(){
		return movimientoService.findAll();
	}

}
