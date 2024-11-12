package com.soca.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.soca.biblioteca.entidades.Usuario;
import com.soca.biblioteca.enumeraciones.Rol;
import com.soca.biblioteca.excepciones.MiException;
import com.soca.biblioteca.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {
	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Transactional
	public void registrar(String nombre, String email, String password, String passwordRepeat) throws MiException {
		
		validar(nombre, email, password, passwordRepeat);
		Usuario usuario = new Usuario();
		
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setPassword(new BCryptPasswordEncoder().encode(password));
		usuario.setRol(Rol.USER);
		
		usuarioRepositorio.save(usuario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepositorio.buscarPorEmail(email);
		
		if (usuario!=null) {
			List<GrantedAuthority> permisos = new ArrayList<GrantedAuthority>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+usuario.getRol().toString());
			permisos.add(p);
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session=attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);
			
			return new User(usuario.getEmail(), usuario.getPassword(), permisos);
		} else {
			return null;
		}
	}
	
	private void validar(String nombre, String email, String password, String passwordRepeat) throws MiException {
		
		if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("El email no puede ser nulo o estar vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }
        if (!password.equals(passwordRepeat)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }
	}
}
