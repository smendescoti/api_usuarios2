package br.com.cotiinformatica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.AutenticarRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.RecuperarSenhaRequestDTO;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiUsuariosApplicationTests {

	@Autowired // objeto utilizado para executar os endpoints da API
	private MockMvc mock;

	@Autowired // objeto para envio e retorno de dados da API
	private ObjectMapper objectMapper;
	
	//atributos
	private static String email;
	private static String senha;

	@Test
	@Order(1)
	public void testCriarConta() throws Exception {
		
		//dados que serão enviados para a API
		CriarUsuarioRequestDTO dto = new CriarUsuarioRequestDTO();
		Faker faker = new Faker();
		
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSenha("@Teste1234");
		
		//executando o serviço de cadastro de conta de usuário
		mock.perform(post("/api/criar-usuario")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isCreated());
		
		email = dto.getEmail();
		senha = dto.getSenha();
	}

	@Test
	@Order(2)
	public void testAutenticar() throws Exception{
		
		//dados que serão enviados para a API
		AutenticarRequestDTO dto = new AutenticarRequestDTO();
		dto.setEmail(email);
		dto.setSenha(senha);
		
		//executando o serviço de cadastro de conta de usuário
		mock.perform(post("/api/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk());		
	}

	@Test
	@Order(3)
	public void testRecuperarSenha() throws Exception {
		
		//dados que serão enviados para a API
		RecuperarSenhaRequestDTO dto = new RecuperarSenhaRequestDTO();
		dto.setEmail(email);
		
		//executando o serviço de recuperação de senha do usuário
		mock.perform(post("/api/recuperar-senha")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk());
	}
}
