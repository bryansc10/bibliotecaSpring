package com.soca.biblioteca.controladores;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soca.biblioteca.entidades.Editorial;
import com.soca.biblioteca.excepciones.MiException;
import com.soca.biblioteca.servicios.EditorialServicio;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
	@Autowired
	private EditorialServicio editorialServicio;

	@GetMapping("/registrar")
	public String registrar() {
		return "editorial_form.html";
	}
	
	@PostMapping("/registro")
	public String registro(@RequestParam String nombre, ModelMap modelo) {
		try {
			editorialServicio.crearEditorial(nombre);
			modelo.put("exito", "La editorial fue cargado correctamente.");
			
		} catch (MiException e) {
			modelo.put("error", e.getMessage());
			Logger.getLogger(EditorialServicio.class.getName()).log(Level.SEVERE, null, e);
			return "editorial_form.html";
		}
		return "index.html";
	}
	
	@GetMapping("/lista")
	public String listar(ModelMap modelo) {
		List<Editorial> editoriales = editorialServicio.listarEditorial();
		modelo.addAttribute("editoriales", editoriales);
		return "editorial_list.html";
	}
	
	@GetMapping("/modificar/{id}")
	public String modificar(@PathVariable String id, ModelMap modelo) {
		modelo.put("editorial", editorialServicio.getOne(UUID.fromString(id)));
		return "editorial_modificar.html";
	}
	
	@PostMapping("/modificar/{id}")
	public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
		try {
			System.out.println(id);
			System.out.println(nombre);
			editorialServicio.modificarEditorial(nombre, UUID.fromString(id));
			return "redirect:../lista";
			
		} catch (Exception e) {
			modelo.put("error", e.getMessage());
			return "editorial_modificar.html";
		}
	}
}
