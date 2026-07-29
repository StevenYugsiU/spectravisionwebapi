package com.uisrael.spectravisionwebapi.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErroresController implements ErrorController {

	@RequestMapping("/error")
	public String manejarError(HttpServletRequest request, Model model) {
		Object codigoEstado = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		int estado = codigoEstado != null ? Integer.parseInt(codigoEstado.toString()) : 500;

		model.addAttribute("status", estado);

		if (estado == 404) {
			return "/errores/paginanoencontrada";
		}
		if (estado == 500) {
			return "/errores/errorservidor";
		}
		return "/errores/errorgeneral";
	}

}
