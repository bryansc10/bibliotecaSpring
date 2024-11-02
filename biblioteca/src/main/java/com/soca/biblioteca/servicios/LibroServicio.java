package com.soca.biblioteca.servicios;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soca.biblioteca.entidades.Autor;
import com.soca.biblioteca.entidades.Editorial;
import com.soca.biblioteca.entidades.Libro;
import com.soca.biblioteca.excepciones.MiException;
import com.soca.biblioteca.repositorios.AutorRepositorio;
import com.soca.biblioteca.repositorios.EditorialRepositorio;
import com.soca.biblioteca.repositorios.LibroRepositorio;

@Service
public class LibroServicio {
	@Autowired
	private LibroRepositorio libroRepositorio;
	@Autowired
	private AutorRepositorio autorRepositorio;
	@Autowired
	private EditorialRepositorio editorialRepositorio;

	@Transactional
	public void crearLibro(String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
		validar(titulo, ejemplares, idAutor, idEditorial);

		Libro libro = new Libro();
		Autor autor = autorRepositorio.findById(UUID.fromString(idAutor)).get();
		Editorial editorial = editorialRepositorio.findById(UUID.fromString(idEditorial)).get();

		libro.setTitulo(titulo);
		libro.setEjemplares(ejemplares);
		libro.setAlta(Date.valueOf(LocalDate.now()));
		libro.setAutor(autor);
		libro.setEditorial(editorial);

		libroRepositorio.save(libro);

	}

	@Transactional(readOnly = true)
	public List<Libro> listarLibros() {
		List<Libro> libros = new ArrayList<Libro>();

		libros = libroRepositorio.findAll();
		return libros;
	}

	@Transactional
	public void modificarLibro(String titulo, Integer ejemplares, String idAutor, String idEditorial, Long id)
			throws MiException {
		validar(titulo, ejemplares, idAutor, idEditorial);

		Optional<Libro> respuesta = libroRepositorio.findById(id);
		Optional<Editorial> repuestaEditorial = editorialRepositorio.findById(UUID.fromString(idEditorial));
		Optional<Autor> respuestaAutor = autorRepositorio.findById(UUID.fromString(idAutor));

		if (respuesta.isPresent() && repuestaEditorial.isPresent() && respuestaAutor.isPresent()) {
			Libro libro = respuesta.get();

			libro.setTitulo(titulo);
			libro.setEjemplares(ejemplares);
			libro.setAutor(respuestaAutor.get());
			libro.setEditorial(repuestaEditorial.get());

			libroRepositorio.save(libro);
		}
	}

	private void validar(String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
		if (titulo.isEmpty() || titulo == null) {
			throw new MiException("El título ingresado no puede ser nulo o vacío.");
		}
		if (ejemplares == null) {
			throw new MiException("La cantidad de ejemplares ingresado no puede ser nulo.");
		}
		if (idAutor.isEmpty() || idAutor == null) {
			throw new MiException("El id de autor ingresado no puede ser nulo.");
		}
		if (idEditorial.isEmpty() || idEditorial == null) {
			throw new MiException("El id de editorial ingresado no puede ser nulo.");
		}
	}
}
