package com.soca.biblioteca.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soca.biblioteca.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, UUID> { }
