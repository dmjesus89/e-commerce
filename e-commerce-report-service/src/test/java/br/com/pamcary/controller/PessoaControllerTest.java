package br.com.pamcary.controller;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.pamcary.dto.UserDTO;
import br.com.pamcary.entity.UserEntity;
import br.com.pamcary.service.UserService;

//@TestComponent
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class PessoaControllerTest {

	final String ENDPOINT_PESSOA = "/api/pessoas";

	@InjectMocks
	private UserController controller;

	@MockBean
	private UserService service;

	@Autowired
	ObjectMapper objectMapper;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void testGetById() throws Exception {
		UserEntity pessoaEntity = new UserEntity();
		pessoaEntity.setId(1L);
		pessoaEntity.setCpf("04064842529");
		pessoaEntity.setDataNascimento(LocalDateTime.now());
		pessoaEntity.setNome("Diego");

		Long id = 1L;

		when(service.findById(id)).thenReturn(pessoaEntity);

		MvcResult mvcResult = this.mockMvc.perform(
				MockMvcRequestBuilders.get(ENDPOINT_PESSOA + "/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		MockHttpServletResponse mockResponse = mvcResult.getResponse();
		Collection<String> responseHeaders = mockResponse.getHeaderNames();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		UserEntity pessoaReturned = mapper.readValue(mockResponse.getContentAsByteArray(),
				UserEntity.class);

		assertThat(mockResponse.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
		assertNotNull(responseHeaders);
		assertEquals(1, responseHeaders.size());
		assertEquals("Check for Content-Type header", "Content-Type", responseHeaders.iterator().next());
		assertEquals(pessoaReturned.getId(), pessoaEntity.getId());

		verify(service, times(1)).findById(id);
		verifyNoMoreInteractions(service);

	}

	@Test
	public void testSave() throws Exception {

		UserEntity pessoaToSave = new UserEntity();
		pessoaToSave.setId(1L);
		pessoaToSave.setNome("Diego Oliveira");
		pessoaToSave.setCpf("04064842529");
		pessoaToSave.setDataNascimento(LocalDateTime.now());

		when(service.save(any(UserDTO.class))).thenReturn(pessoaToSave);

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post(ENDPOINT_PESSOA).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new UserEntity())))
				.andExpect(status().isCreated()).andReturn();

		MockHttpServletResponse mockResponse = mvcResult.getResponse();
		Collection<String> responseHeaders = mockResponse.getHeaderNames();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		UserEntity pessoaReturned = mapper.readValue(mockResponse.getContentAsByteArray(),
				UserEntity.class);

		assertThat(mockResponse.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8_VALUE);
		assertNotNull(responseHeaders);
		assertEquals(4, responseHeaders.size());
		assertTrue(responseHeaders.contains("Location"));
		assertTrue(mockResponse.getHeader("Location").contains(ENDPOINT_PESSOA + "/" + pessoaToSave.getId()));
		assertTrue(pessoaReturned.getId() == pessoaToSave.getId());

		verify(service, times(1)).save(any(UserDTO.class));
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testDelete() throws Exception {

		service.delete(1L);

		MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete(ENDPOINT_PESSOA + "/{id}", 1L))
				.andExpect(status().isNoContent()).andReturn();

		MockHttpServletResponse mockResponse = mvcResult.getResponse();
		Collection<String> responseHeaders = mockResponse.getHeaderNames();

		assertThat(mockResponse.getContentAsString()).isEqualTo("");
		assertThat(mockResponse.getContentType()).isEqualTo(null);
		assertNotNull(responseHeaders);

		assertThat(mvcResult.getResponse().getHeader("X-ePamcaryBackendApp-alert")).contains("deletada");

//		verify(service, times(2)).delete(1L);
//		verifyNoMoreInteractions(service);
	}

	@Test
	public void testUpdate() throws Exception {
		UserEntity pessoa = new UserEntity();
		pessoa.setId(1L);
		pessoa.setNome("Diego");
		pessoa.setCpf("04064842529");
		pessoa.setDataNascimento(LocalDateTime.now());
		when(service.save(any(UserDTO.class))).thenReturn(pessoa);
		
		

		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post(ENDPOINT_PESSOA).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new UserEntity())))
				.andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print())
				.andReturn();
		
		
		
	
		 
	
	}

}
