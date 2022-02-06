package com.BancaTodo.UserFront.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.dto.GeneralResponse;
import com.BancaTodo.UserFront.entity.UserEntity;
import com.BancaTodo.UserFront.services.UserService;
import com.BancaTodo.UserFront.util.JwtUtils;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@ApiOperation(value = "Login width userName and password", response = ResponseEntity.class)
	@PostMapping("/auth")
	public ResponseEntity<GeneralResponse<UserEntity>> login(@RequestBody UserEntity user) {
		
		GeneralResponse<UserEntity> response = new GeneralResponse<>();
		String mensaje = null;
		HttpStatus status = null;
		
		try {
			
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
					);
			user.setJwt(jwtUtils.generateToken(user.getUserName()));
			
			user.setPassword(null);
			mensaje = "0 - Login exitoso para el usuario " + user.getUserName() + ".";
			
			response.setMensaje(mensaje);
			response.setPeticionExitosa(true);
			response.setDatos(user);
			status = HttpStatus.OK;
			
		} catch (AuthenticationException e) {
			
			mensaje = "Usuario o clave incorrectos.";
			status = HttpStatus.FORBIDDEN;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
			System.out.print(e);
			
		} catch (Exception e) {
			
			mensaje = "Ha fallado el sistema. Contacte al administrador";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
			
		}

		return new ResponseEntity<>(response, status);
	}
	
	@ApiOperation(value = "Add users.", response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<GeneralResponse<UserEntity>> save(@RequestBody UserEntity user) {
		
		GeneralResponse<UserEntity> response = new GeneralResponse<>();
		HttpStatus status = null;
		UserEntity data = null; 
		String mensaje = null;
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		try {

			data = userService.add(user);
			mensaje = "0 - Usuario creado con éxito";
			
			response.setMensaje(mensaje);
			response.setPeticionExitosa(true);
			response.setDatos(data);
			status = HttpStatus.OK;
			
		} catch (Exception e) {
			
			mensaje = "Something has failed. Please contact suuport.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
			
		}

		return new ResponseEntity<>(response, status);
	}
	

}
