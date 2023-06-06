package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.CriarUsuarioRequestDTO;
import br.com.cotiinformatica.dtos.CriarUsuarioResponseDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import br.com.cotiinformatica.services.MD5Service;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/criar-usuario")
public class CriarUsuarioController {

	@Autowired // autoinicialização
	private UsuarioRepository usuarioRepository;

	@Autowired // autoinicialização
	private MD5Service md5Service;

	@PostMapping
	public ResponseEntity<CriarUsuarioResponseDTO> post(@RequestBody @Valid CriarUsuarioRequestDTO dto) {

		CriarUsuarioResponseDTO response = new CriarUsuarioResponseDTO();

		try {

			// verificar se já existe um usuário no banco de dados com o email já cadastrado
			if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
				response.setStatus(400); // HTTP 400 (BAD REQUEST)
				response.setMensagem("O email informado já está cadastrado, tente outro.");
			} else {
				// capturando os dados do usuário..
				Usuario usuario = new Usuario();
				usuario.setNome(dto.getNome());
				usuario.setEmail(dto.getEmail());
				usuario.setSenha(md5Service.encrypt(dto.getSenha()));

				// cadastrando no banco de dados
				usuarioRepository.save(usuario);

				response.setStatus(201); // HTTP 201 (CREATED)
				response.setMensagem("Usuário cadastrado com sucesso.");
				response.setIdUsuario(usuario.getIdUsuario());
				response.setNome(usuario.getNome());
				response.setEmail(usuario.getEmail());
			}
		} catch (Exception e) {
			response.setStatus(500); // HTTP 500 (INTERNAL SERVER ERROR)
			response.setMensagem("Falha ao cadastrar usuário: " + e.getMessage());
		}

		return ResponseEntity.status(response.getStatus()).body(response);
	}
}
