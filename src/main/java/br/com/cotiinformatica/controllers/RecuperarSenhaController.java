package br.com.cotiinformatica.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import br.com.cotiinformatica.dtos.RecuperarSenhaRequestDTO;
import br.com.cotiinformatica.dtos.RecuperarSenhaResponseDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.services.EmailService;
import br.com.cotiinformatica.services.MD5Service;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/recuperar-senha")
public class RecuperarSenhaController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private MD5Service md5Service;
	
	@PostMapping
	public ResponseEntity<RecuperarSenhaResponseDTO> post(@RequestBody @Valid RecuperarSenhaRequestDTO dto) {

		RecuperarSenhaResponseDTO response = new RecuperarSenhaResponseDTO();
		
		try {			
			//buscando o usuário no banco de dados através do email
			Optional<Usuario> optional = usuarioRepository.findByEmail(dto.getEmail());
					
			//verificando se o usuário não foi encontrado
			if(optional.isEmpty()) {
				//HTTP 400 (BAD REQUEST)
				response.setStatus(400);
				response.setMensagem("Usuário não encontrado, verifique o email informado.");
			}
			else {

				//capturando o usuário obtido banco de dados
				Usuario usuario = optional.get();
				
				//gerando uma nova senha para o usuário
				String novaSenha = new Faker().internet().password(8, 10, true, true, true);
				
				//enviando um email para o usuário com a nova senha
				String to = usuario.getEmail();
				String subject = "Recuperação de senha de usuários - API Spring Boot";
				String body = "Prezado " + usuario.getNome()
						    + "\n\nUma nova senha foi gerada com sucesso. Utilize a senha: " + novaSenha
						    + "\n\nAtt"
						    + "\nEquipe COTI Informática";				
				emailService.send(to, subject, body);
				
				//atualizando a senha do usuário no banco de dados
				usuario.setSenha(md5Service.encrypt(novaSenha));
				usuarioRepository.save(usuario);
				
				//retornando a resposta
				response.setStatus(200);
				response.setMensagem("Recuperação de senha realizada com sucesso. Verifique sua conta de email.");
			}
		}
		catch(Exception e) {
			//HTTP 500 (INTERNAL SERVER ERROR)
			response.setStatus(500);
			response.setMensagem("Falha ao recuperar senha do usuário: " + e.getMessage());
		}
		
		return ResponseEntity.status(response.getStatus())
				.body(response);
	}
}
