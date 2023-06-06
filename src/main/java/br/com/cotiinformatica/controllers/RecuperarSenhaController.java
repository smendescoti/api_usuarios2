package br.com.cotiinformatica.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.RecuperarSenhaRequestDTO;
import br.com.cotiinformatica.dtos.RecuperarSenhaResponseDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/recuperar-senha")
public class RecuperarSenhaController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
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
				//TODO implementar a recuperação da senha do usuário
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
