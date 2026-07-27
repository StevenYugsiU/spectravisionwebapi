package com.uisrael.spectravisionwebapi.configuration;

import java.util.List;
import java.util.Set;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SesionInterceptor implements HandlerInterceptor {

	private static final Set<String> RUTAS_ADMINISTRADOR = Set.of("/usuario", "/usuariorol", "/rol");
	private static final String ROL_ADMINISTRADOR = "Administrador";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession(false);
		boolean autenticado = session != null && session.getAttribute("idUsuario") != null;

		if (!autenticado) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		String uri = request.getRequestURI();
		boolean esRutaAdministrador = RUTAS_ADMINISTRADOR.stream().anyMatch(uri::startsWith);

		if (esRutaAdministrador) {
			@SuppressWarnings("unchecked")
			List<String> roles = (List<String>) session.getAttribute("roles");
			boolean esAdministrador = roles != null && roles.contains(ROL_ADMINISTRADOR);
			if (!esAdministrador) {
				response.sendRedirect(request.getContextPath() + "/cliente");
				return false;
			}
		}

		return true;
	}

}
