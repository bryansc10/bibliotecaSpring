package com.soca.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.soca.biblioteca.excepciones.MiException;
import com.soca.biblioteca.servicios.UsuarioServicio;

@Controller
@RequestMapping("/")
public class PortalControlador {
	@Autowired
	private UsuarioServicio usuarioServicio;

	@GetMapping("/")
	public String index() {
		return "index.html";
	}

	@GetMapping("/registrar")
	public String registrar() {
		return "registro.html";
	}

	@GetMapping("/login")
	public String log() {
		return "login.html";
	}

	@PostMapping("/registro")
	public String registrarUsuario(@RequestParam String nombre, @RequestParam String email,
			@RequestParam String password, @RequestParam String passwordRepeat, ModelMap modelo) {
		try {
			usuarioServicio.registrar(nombre, email, password, passwordRepeat);
			modelo.put("exito", "Registro de manera exitosa.");
			return "index.html";
		} catch (MiException e) {
			modelo.put("error", "Registro fallido.");
			return "registro.html";
		}
	}
}
