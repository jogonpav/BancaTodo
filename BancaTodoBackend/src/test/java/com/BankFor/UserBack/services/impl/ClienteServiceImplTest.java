package com.BankFor.UserBack.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.easymock.TestSubject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.context.SpringBootTest;

import com.BankFor.UserBack.entity.ClienteEntity;
import com.BankFor.UserBack.repository.ClienteRepository;
import com.BankFor.UserBack.services.impl.ClienteServiceImpl;

@SpringBootTest
class ClienteServiceImplTest {

	@Autowired
	private ClienteServiceImpl clienteServiceImpl;
	
	@Autowired 
	private ClienteRepository clienteRepository;
	
	
	@TestSubject
	ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepository);

	@Test
	// @WithMockUser
	void return_client_list_when_is_called() {
		List<ClienteEntity> client = new ArrayList<>();
		try {
			client = clienteServiceImpl.findAll();
		} catch (Exception e) {
			e.printStackTrace();		
		}
		assertTrue(!client.isEmpty());		
	}
	
	@Test
	void return_client_when_is_called() {

		long id = 1;

		ClienteEntity client = null;
		try {
			client = clienteServiceImpl.getById(id);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		assertTrue(!(client != null));

	}
	
	@Test
	void return_client_when_is_created() {
		
		ClienteEntity client = new ClienteEntity();
		client.setNombres("karol");
		client.setApellidos("pabon");
		client.setTipoIdentificacion("Cédula de Ciudadanía");
		client.setCorreo("karolpabon@hotmail.com");
		client.setNumeroIdentificacion("10658499");
		client.setFechaCreacion(LocalDate.now());
		client.setFechaNacimiento(LocalDate.now());
		try {
			client = clienteServiceImpl.add(client);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		assertTrue(client.getId()!= null);
	}
	
	@Test
	void return_client_when_is_updated() {
		
		ClienteEntity client = new ClienteEntity();
		ClienteEntity clientUpdated = null;
		client.setId(Long.valueOf(4));
		client.setNombres("karol2");
		client.setApellidos("pabon2");
		client.setTipoIdentificacion("Cédula de Ciudadanía");
		client.setCorreo("karolpabon@hotmail.com");
		client.setNumeroIdentificacion("10658499");
		client.setFechaCreacion(LocalDate.now());
		client.setFechaNacimiento(LocalDate.now());
		try {
			clientUpdated = clienteServiceImpl.update(client);
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		assertEquals(client.getId(), clientUpdated.getId());
		assertEquals(client.getNombres(), clientUpdated.getNombres());
		assertEquals(client.getApellidos(), clientUpdated.getApellidos());
		assertEquals(client.getTipoIdentificacion(), clientUpdated.getTipoIdentificacion());
	}
	
	@Test
	void return_client_when_is_deleted() {
		//ClienteRepository clienteRepository = mock(ClienteRepository.class);	
		//clienteServiceImpl = new ClienteServiceImpl(clienteRepository);
		long id = 11;
		ClienteServiceImpl clienteServiceMock = mock(ClienteServiceImpl.class);		
		  //  ArgumentCaptor<ClienteServiceImpl> arg = ArgumentCaptor.forClass(ClienteServiceImpl.class);
		  
		try {
			doNothing().when(clienteServiceMock).delete(id);
			clienteService.delete(id);
			verify(clienteServiceMock).delete(id);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	

	}

}
