package br.com.cotiinformatica.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AutenticarRequestDTO;
import br.com.cotiinformatica.dtos.AutenticarResponseDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.services.JwtTokenService;
import br.com.cotiinformatica.services.MD5Service;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/autenticar")
public class AutenticarController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MD5Service md5Service;
	
	@Autowired
	private JwtTokenService jwtTokenService;
	
	@PostMapping
	public ResponseEntity<AutenticarResponseDTO> post(@RequestBody @Valid AutenticarRequestDTO dto) {

		AutenticarResponseDTO response = new AutenticarResponseDTO();
		
		try {
			//pesquisar o usuário no banco de dados através do email e da senha
			Optional<Usuario> optional = usuarioRepository.findByEmailAndSenha(dto.getEmail(), md5Service.encrypt(dto.getSenha()));
			
			//verificar se o usuário não foi encontrado
			if(optional.isEmpty()) {
				//HTTP 401 (UNAUTHORIZED)
				response.setStatus(401);
				response.setMensagem("Acesso negado. Usuário não encontrado.");
			}
			else {
				Usuario usuario = optional.get();
				
				//realizando a autenticação do usuário
				response.setStatus(200);
				response.setMensagem("Usuário autenticado com sucesso.");
				response.setIdUsuario(usuario.getIdUsuario());
				response.setNome(usuario.getNome());
				response.setEmail(usuario.getEmail());
				response.setAccessToken(jwtTokenService.generateToken(usuario.getEmail()));
			}
		}
		catch(Exception e) {
			//HTTP 500 (INTERNAL SERVER ERROR)
			response.setStatus(500);
			response.setMensagem("Falha ao autenticar o usuário: " + e.getMessage());
		}
		
		return ResponseEntity.status(response.getStatus())
				.body(response);
	}
}
