package com.BancaTodo.UserFront.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/default")
public class TestApi {
	

@GetMapping  
public String getXml() {
   return "Hola estas conectado al recurso inventarios desde mi API";
}

	   


}