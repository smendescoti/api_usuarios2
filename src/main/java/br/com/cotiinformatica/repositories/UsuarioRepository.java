package br.com.cotiinformatica.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	// JPQL - Java Persistence Query Language
	//SQL => select * from usuario where email = ?
	@Query("select u from Usuario u where u.email = :email")
	Optional<Usuario> findByEmail(@Param("email") String email);

	// JPQL - Java Persistence Query Language
	@Query("select u from Usuario u where u.email = :email and u.senha = :senha")
	Optional<Usuario> findByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);
}
