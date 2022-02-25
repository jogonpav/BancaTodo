package com.BankFor.UserBack.controller;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import com.BankFor.UserBack.controller.ClienteController;
import com.BankFor.UserBack.entity.ClienteEntity;
import com.BankFor.UserBack.services.ClienteService;
import com.BankFor.UserBack.services.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(ClienteController.class)
//@SpringBootTest
class ClienteControllerTest {
	/*@Autowired
	private ClienteService clienteService;	
	
	@Autowired
	private ClienteController clienteController;
	
	@Test
	void return_clients_list_when_is_called() throws Exception {		
		List<ClienteEntity> listCliente = new ArrayList<>();
		
		listCliente = clienteController.listar();
		assertTrue(!listCliente.isEmpty());
		System.out.println(listCliente.size());
		
	}*/
	

	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ClienteService clienteService;	
	
	@MockBean
	private ProductoService productoService;	
	
	

	@Test
	void return_clients_list_when_is_called() throws Exception {
		List<ClienteEntity> listCliente = new ArrayList<>();
		listCliente.add(new ClienteEntity("1", "CC","jose","pabon", "jose@hotmail.com", LocalDate.parse("2007-11-01"), LocalDate.parse("2007-11-01")));
				
		Mockito.when(clienteService.findAll()).thenReturn(listCliente);
		String url = "/clientes";
		mockMvc.perform(get(url)).andExpect(status().isOk());
		
		System.out.println(listCliente.size());
		
		
	}

}
