package com.soca.biblioteca.repositorios;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soca.biblioteca.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, UUID> { }
