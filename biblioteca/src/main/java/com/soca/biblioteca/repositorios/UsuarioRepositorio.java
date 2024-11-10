package com.soca.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soca.biblioteca.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
	@Query("SELECT u FROM Usuario WHERE u.email = :email")
	public Usuario buscarPorEmail(@Param("email") String email);
}
