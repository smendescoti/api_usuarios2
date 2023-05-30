package br.com.cotiinformatica.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class CriarUsuarioRequestDTO {

	@Size(min = 8, max = 150, message = "Por favor, informe de 8 a 150 caracteres.")
	@NotBlank(message = "Por favor, informe o nome do usuário.")
	private String nome;

	@Email(message = "Por favor, informe um endereço de email válido.")
	@NotBlank(message = "Por favor, informe o email do usuário.")
	private String email;

	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
			message = "Por favor, informe uma senha com letras maiúsculas, mínúsculas, números, símbolos e no mínimo 8 caracteres.")
	@NotBlank(message = "Por favor, informe a senha do usuário.")
	private String senha;
}
