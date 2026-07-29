package com.uisrael.spectravisionwebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.spectravisionwebapi.model.response.LoginResponseDto;
import com.uisrael.spectravisionwebapi.service.IAuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private IAuthService servicioAuth;

	@GetMapping("/login")
	public String leerPagina(HttpSession session, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");

		if (session.getAttribute("idUsuario") != null) {
			return "redirect:/cliente";
		}
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
			model.addAttribute("error", "Credenciales inválidas.");
			return "/login/login";
		}
	}

	@GetMapping("/logout")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

}
