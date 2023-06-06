package br.com.cotiinformatica.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {

	/*
	 * Capturar a chave secreta antifalsificação mapeada no arquivo
	 * application.properties
	 */
	@Value("${jwt.secret}")
	private String jwtSecret;

	/*
	 * Método para gerar os tokens da API para os usuários
	 * autenticados
	 */
	public String generateToken(String emailUsuario) throws Exception {
		return Jwts.builder()
				.setSubject(emailUsuario) //email do usuário autenticado
				.setIssuedAt(new Date()) //data de geração do token
				.signWith(SignatureAlgorithm.HS256, jwtSecret) //assinatura do token
				.compact(); //finalizando e retornando o valor do token
	}
}
