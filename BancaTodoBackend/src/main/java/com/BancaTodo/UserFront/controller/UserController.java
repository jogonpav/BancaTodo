package com.BancaTodo.UserFront.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BancaTodo.UserFront.dto.GeneralResponse;
import com.BancaTodo.UserFront.dto.UserDto;
import com.BancaTodo.UserFront.entity.UserEntity;
import com.BancaTodo.UserFront.services.UserService;
import com.BancaTodo.UserFront.util.JwtUtils;

import io.swagger.annotations.ApiOperation;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	
	@GetMapping
	public ResponseEntity<GeneralResponse<UserEntity>> get(@PathParam(value = "userName") String userName){
		GeneralResponse<UserEntity> response = new GeneralResponse<>();		
		UserEntity user = null;
		String mensaje = null;
		HttpStatus status = null;
		
		try {					
			user = userService.findUserByUserName(userName);
			user.setPassword(null);
			mensaje = "0 - User found";
			response.setMensaje(mensaje);
			response.setPeticionExitosa(true);
			response.setDatos(user);
			status = HttpStatus.OK;
			
		}		
		catch (Exception e) {
			mensaje = "There was an error. Contact the administrator.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
		}	
		
		return new ResponseEntity<>(response, status);	
		
	}
	
	@PutMapping
	public ResponseEntity<GeneralResponse<UserEntity>> update(@RequestBody UserEntity user){
		GeneralResponse<UserEntity> response = new GeneralResponse<>();		
		
		String mensaje = null;
		HttpStatus status = null;
		
		try {
			
			UserEntity userDetails = userService.findUserByUserName(user.getUserName());
			user.setId(userDetails.getId());		
			System.out.println("por aca1");			
			userService.updateUserById(user);
			userDetails = userService.findUserByUserName(user.getUserName());
			
			userDetails.setPassword("");
			
			mensaje = "0 - User updated successfully";
			response.setMensaje(mensaje);
			response.setPeticionExitosa(true);
			response.setDatos(userDetails);
			status = HttpStatus.OK;			
		}		
		catch (Exception e) {
			mensaje = "There was an error. Contact the administrator.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMensaje(mensaje +e);
			response.setPeticionExitosa(false);
		}	
		
		return new ResponseEntity<>(response, status);	
		
	}
	
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
			mensaje = "0 - Successful login for the user " + user.getUserName() + ".";
			
			response.setMensaje(mensaje);
			response.setPeticionExitosa(true);
			response.setDatos(user);
			status = HttpStatus.OK;
			
		} catch (AuthenticationException e) {
			
			mensaje = "Wrong username or password.";
			status = HttpStatus.FORBIDDEN;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
	
			
		} catch (Exception e) {
			
			mensaje = "There was an error. Contact the administrator.";
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
			mensaje = "0 - User created successfully";
			
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
	
	@ApiOperation(value = "reset password", response = ResponseEntity.class)
	@PutMapping("/resetpassword")
	public ResponseEntity<GeneralResponse<UserEntity>> resetPassword(@RequestBody UserDto userDto ) {
		
		GeneralResponse<UserEntity> response = new GeneralResponse<>();
		UserEntity user = null;
		String mensaje = null;
		HttpStatus status = null;
		
		try {					
			user = userService.findUserByUserName(userDto.getUser().getUserName());			
			if (user != null) {	
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(userDto.getUser().getUserName(), userDto.getUser().getPassword())
						);	
				user.setPassword(bCryptPasswordEncoder.encode(userDto.getNewPassword()));
				//atenticar de nuevo con la nueva contraseña y resetear token
				user = userService.add(user);
				user.setPassword(null);	
				mensaje = "0 - Password changed successfully";
			} else {
				mensaje = "1 - User not found, could not change password";
				status = HttpStatus.NOT_MODIFIED;
			}
			
			response.setMensaje(mensaje);
			response.setPeticionExitosa(true);
			response.setDatos(user);
			status = HttpStatus.OK;
			
		} catch (AuthenticationException e) {
			
			mensaje = "1 - Password does not match old password.";
			status = HttpStatus.FORBIDDEN;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
	
			
		}
		
		catch (Exception e) {
			
			mensaje = "There was an error. Contact the administrator.";
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			response.setMensaje(mensaje);
			response.setPeticionExitosa(false);
			
		}

		return new ResponseEntity<>(response, status);
	}
	

}
