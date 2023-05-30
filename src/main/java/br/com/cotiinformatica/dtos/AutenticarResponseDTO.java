package br.com.cotiinformatica.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AutenticarResponseDTO {

	private Integer status;
	private String mensagem;
	private Integer idUsuario;
	private String nome;
	private String email;
	private String accessToken;
}
