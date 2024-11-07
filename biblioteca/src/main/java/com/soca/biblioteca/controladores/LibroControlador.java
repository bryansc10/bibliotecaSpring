package com.soca.biblioteca.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soca.biblioteca.entidades.Autor;
import com.soca.biblioteca.entidades.Editorial;
import com.soca.biblioteca.entidades.Libro;
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
	public String registrar(ModelMap modelo) {
		List<Autor> autores = autorServicio.listarAutor();
		List<Editorial> editoriales = editorialServicio.listarEditorial();
		
		modelo.addAttribute("autores", autores);
		modelo.addAttribute("editoriales", editoriales);
		
		return "libro_form.html";
	}

	@PostMapping("/registro")
	public String registro(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam(required = false, defaultValue = "0") Integer ejemplares,
			@RequestParam(defaultValue = "") String idAutor, @RequestParam(defaultValue = "") String idEditorial, ModelMap modelo) {
		try {
			System.out.println(titulo);
			System.out.println(ejemplares);
			System.out.println(idAutor);
			System.out.println(idEditorial);
			libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
			modelo.put("exito", "El libro fue cargado correctamente.");
			
		} catch (MiException e) {
			modelo.put("error", e.getMessage());
			return "libro_form.html";
		}
		return "index.html";
	}
	
	@GetMapping("/lista")
	public String listar(ModelMap modelo) {
		List<Libro> libros = libroServicio.listarLibros();
		modelo.addAttribute("libros", libros);
		return "libro_list.html";
	}
	
	@GetMapping("/modificar/{isbn}")
	public String modificar(@PathVariable Long isbn, ModelMap modelo) {
		List<Autor> autores = autorServicio.listarAutor();
		List<Editorial> editoriales = editorialServicio.listarEditorial();
		
		modelo.addAttribute("autores", autores);
		modelo.addAttribute("editoriales", editoriales);
		modelo.put("libro", libroServicio.getOne(isbn));
		
		return "libro_modificar.html";
	}
	
	@PostMapping("/modificar/{isbn}")
	public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String id_autor, String id_editorial, ModelMap modelo) {
		try {
			libroServicio.modificarLibro(titulo, ejemplares, id_autor, id_editorial, isbn);
			return "redirect:../lista";
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "libro_modificar.html";
		}
	}
}
