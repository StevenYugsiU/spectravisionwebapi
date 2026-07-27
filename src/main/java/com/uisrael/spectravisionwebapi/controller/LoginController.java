package com.uisrael.spectravisionwebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.LoginResponseDto;
import com.uisrael.spectravisionwebapi.service.IAuthService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private IAuthService servicioAuth;

	@GetMapping("/login")
	public String leerPagina() {
		return "/login/login";
	}

	@PostMapping("/login")
	public String procesarLogin(@RequestParam String usuario, @RequestParam String contrasena, HttpSession session,
			Model model) {
		try {
			LoginResponseDto resultado = servicioAuth.login(usuario, contrasena);

			session.setAttribute("idUsuario", resultado.getIdUsuario());
			session.setAttribute("usuario", resultado.getUsuario());
			session.setAttribute("nombreCompleto", resultado.getNombres() + " " + resultado.getApellidos());
			session.setAttribute("roles", resultado.getRoles());

			return "redirect:/cliente";
		} catch (WebClientResponseException ex) {
			model.addAttribute("error", extraerMensaje(ex));
			return "/login/login";
		}
	}

	@GetMapping("/logout")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	private String extraerMensaje(WebClientResponseException ex) {
		try {
			ErrorResponseDto error = ex.getResponseBodyAs(ErrorResponseDto.class);
			if (error != null && error.getMessage() != null) {
				return error.getMessage();
			}
		} catch (Exception ignorada) {
		}
		return "No se pudo iniciar sesion. Intente nuevamente.";
	}

}
