package com.uisrael.spectravisionwebapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.LoginResponseDto;
import com.uisrael.spectravisionwebapi.service.IAuthService;

import jakarta.servlet.http.HttpServletRequest;
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

	@GetMapping("/olvide-contrasena")
	public String leerPaginaOlvideContrasena() {
		return "/login/olvidecontrasena";
	}

	@PostMapping("/olvide-contrasena")
	public String procesarOlvideContrasena(@RequestParam String usuario, Model model, HttpServletRequest request) {
		String resetPasswordUrl = ServletUriComponentsBuilder.fromContextPath(request)
				.path("/restablecer-contrasena").toUriString();
		try {
			servicioAuth.solicitarRecuperacion(usuario, resetPasswordUrl);
		} catch (WebClientResponseException ex) {
			// Se ignora el detalle: se muestra siempre el mismo mensaje generico
			// para no revelar si el usuario existe o no.
		}
		model.addAttribute("mensaje",
				"Si el usuario existe y tiene un correo registrado, se envió un enlace de recuperación.");
		return "/login/olvidecontrasena";
	}

	@GetMapping("/restablecer-contrasena")
	public String leerPaginaRestablecerContrasena(@RequestParam String token, Model model) {
		model.addAttribute("token", token);
		return "/login/restablecercontrasena";
	}

	@PostMapping("/restablecer-contrasena")
	public String procesarRestablecerContrasena(@RequestParam String token, @RequestParam String nuevaContrasena,
			@RequestParam String confirmarContrasena, Model model, RedirectAttributes redirectAttributes) {

		if (!nuevaContrasena.equals(confirmarContrasena)) {
			model.addAttribute("token", token);
			model.addAttribute("error", "Las contraseñas no coinciden.");
			return "/login/restablecercontrasena";
		}

		try {
			servicioAuth.restablecerContrasena(token, nuevaContrasena);
		} catch (WebClientResponseException ex) {
			model.addAttribute("token", token);
			model.addAttribute("error", extraerMensajeError(ex));
			return "/login/restablecercontrasena";
		}

		redirectAttributes.addFlashAttribute("mensaje", "Contraseña actualizada correctamente. Ya puedes ingresar.");
		return "redirect:/login";
	}

	private String extraerMensajeError(WebClientResponseException ex) {
		try {
			ErrorResponseDto error = ex.getResponseBodyAs(ErrorResponseDto.class);
			if (error != null && error.getMessage() != null) {
				return error.getMessage();
			}
		} catch (Exception e) {
			// Se ignora: se usa el mensaje generico de abajo.
		}
		return "No se pudo restablecer la contraseña. Intenta solicitar un nuevo enlace.";
	}

}
