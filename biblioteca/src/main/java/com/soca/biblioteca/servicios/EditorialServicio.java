package com.soca.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soca.biblioteca.entidades.Editorial;
import com.soca.biblioteca.excepciones.MiException;
import com.soca.biblioteca.repositorios.EditorialRepositorio;

@Service
public class EditorialServicio {
	@Autowired
	private EditorialRepositorio editorialRepositorio;

	@Transactional
	public void crearEditorial(String nombre) throws MiException {
		validar(nombre);
		Editorial editorial = new Editorial();
		editorial.setNombre(nombre);
		editorialRepositorio.save(editorial);
	}

	@Transactional(readOnly = true)
	public List<Editorial> listarEditorial() {
		List<Editorial> editoriales = new ArrayList<Editorial>();

		editoriales = editorialRepositorio.findAll();
		return editoriales;
	}

	@Transactional
	public void modificarEditorial(String nombre, UUID id) throws MiException {
		validar(nombre);
		Optional<Editorial> respuesta = editorialRepositorio.findById(id);

		if (respuesta.isPresent()) {
			Editorial editorial = respuesta.get();
			editorial.setNombre(nombre);
			editorialRepositorio.save(editorial);
		}
	}

	@Transactional(readOnly = true)
	public Editorial getOne(UUID id) {
		return editorialRepositorio.getReferenceById(id);
	}

	private void validar(String nombre) throws MiException {
		if (nombre.isEmpty() || nombre == null) {
			throw new MiException("El nombre de la editorial no puede se nulo o vacío.");
		}
	}
}
