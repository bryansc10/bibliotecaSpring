package com.soca.biblioteca.controladores;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soca.biblioteca.excepciones.MiException;
import com.soca.biblioteca.servicios.AutorServicio;
import com.soca.biblioteca.servicios.EditorialServicio;
import com.soca.biblioteca.servicios.LibroServicio;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
	@Autowired
	private LibroServicio libroServicio;
	@Autowired
	private AutorServicio autorServicio;
	@Autowired
	private EditorialServicio editorialServicio;

	@GetMapping("/registrar")
	public String registrar() {
		return "libro_form.html";
	}

	@PostMapping("/registro")
	public String registro(@RequestParam String titulo, @RequestParam(required = false, defaultValue = "0") Integer ejemplares,
			@RequestParam(defaultValue = "") String idAutor, @RequestParam(defaultValue = "") String idEditorial, ModelMap modelo) {
		try {
			System.out.println(titulo);
			System.out.println(ejemplares);
			System.out.println(idAutor);
			System.out.println(idEditorial);
			libroServicio.crearLibro(titulo, ejemplares, idAutor, idEditorial);
			modelo.put("exito", "El libro fue cargado correctamente.");
			
		} catch (MiException e) {
			modelo.put("error", e.getMessage());
			return "libro_form.html";
		}
		return "index.html";
	}
}
