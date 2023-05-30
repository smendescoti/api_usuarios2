package br.com.cotiinformatica.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.AutenticarRequestDTO;
import br.com.cotiinformatica.dtos.AutenticarResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/autenticar")
public class AutenticarController {

	@PostMapping
	public ResponseEntity<AutenticarResponseDTO> post(@RequestBody @Valid AutenticarRequestDTO dto) {
		// TODO
		return null;
	}
}
