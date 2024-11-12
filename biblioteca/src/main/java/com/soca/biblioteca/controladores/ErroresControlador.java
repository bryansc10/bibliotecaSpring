package com.soca.biblioteca.controladores;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresControlador implements ErrorController {
	@RequestMapping(value = "/error", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
		ModelAndView errorPage = new ModelAndView("error");
		String errorMsg = "";
		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
		case 400 -> errorMsg = "El recurso solicitado no exite.";
		case 403 -> errorMsg = "No tiene permiso para acceder a los recursos.";
		case 401 -> errorMsg = "No se encuentra autorizado.";
		case 404 -> errorMsg = "El recurso solicitado no fue encontrado.";
		case 500 -> errorMsg = "Ocurrió un error interno";
		}
		
		errorPage.addObject("codigo", httpErrorCode);
		errorPage.addObject("mensaje", errorMsg);
		
		return errorPage;
	}

	// Obtener el código de error
	private int getErrorCode(HttpServletRequest httpRequest) {
		Integer errorCode = (Integer) httpRequest.getAttribute("jakarta.servlet.error.status_code");
		return (errorCode != null) ? errorCode.intValue() : 0;
	}

	// Devolver la ruta de la página error
	private String getErrorPath() {
		return "/error.html";
	}
}
