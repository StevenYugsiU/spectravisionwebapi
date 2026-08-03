package com.uisrael.spectravisionwebapi.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uisrael.spectravisionwebapi.model.request.UsuarioRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.RolResponseDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioResponseDto;
import com.uisrael.spectravisionwebapi.service.IRolService;
import com.uisrael.spectravisionwebapi.service.IUsuarioRolService;
import com.uisrael.spectravisionwebapi.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService servicioUsuario;

	@Autowired
	private IRolService servicioRol;

	@Autowired
	private IUsuarioRolService servicioUsuarioRol;

	@GetMapping
	public String leerPagina(Model model) {
		List<UsuarioResponseDto> listaUsuarios = servicioUsuario.listarUsuarios();
		model.addAttribute("listaUsuarios", listaUsuarios);
		model.addAttribute("idsAdministradores", idsUsuariosAdministradores());
		return "/usuario/listarusuarios";
	}

	@GetMapping("/nuevo")
	public String nuevoUsuario(Model model) {
		model.addAttribute("usuario", new UsuarioRequestDto());
		return "/usuario/formulariousuario";
	}

	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute UsuarioRequestDto usuario, RedirectAttributes redirectAttributes) {
		try {
			servicioUsuario.guardarUsuario(usuario);
			redirectAttributes.addFlashAttribute("mensaje", "Usuario creado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear el usuario."));
		}
		return "redirect:/usuario";
	}

	@GetMapping("/editar/{idUsuario}")
	public String editarUsuario(@PathVariable int idUsuario, Model model, RedirectAttributes redirectAttributes) {
		UsuarioResponseDto encontrado;
		try {
			encontrado = servicioUsuario.buscarUsuarioPorId(idUsuario);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró el usuario solicitado."));
			return "redirect:/usuario";
		}

		UsuarioRequestDto usuario = new UsuarioRequestDto();
		usuario.setIdUsuario(encontrado.getIdUsuario());
		usuario.setNombres(encontrado.getNombres());
		usuario.setApellidos(encontrado.getApellidos());
		usuario.setUsuario(encontrado.getUsuario());
		usuario.setCorreo(encontrado.getCorreo());
		usuario.setEstado(encontrado.getEstado());

		model.addAttribute("usuario", usuario);
		return "/usuario/formulariousuario";
	}

	@PostMapping("/actualizar/{idUsuario}")
	public String actualizarUsuario(@PathVariable int idUsuario, @ModelAttribute UsuarioRequestDto usuario,
			RedirectAttributes redirectAttributes) {
		try {
			servicioUsuario.actualizarUsuario(idUsuario, usuario);
			redirectAttributes.addFlashAttribute("mensaje", "Usuario actualizado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo actualizar el usuario."));
		}
		return "redirect:/usuario";
	}

	@PostMapping("/eliminar/{idUsuario}")
	public String eliminarUsuario(@PathVariable int idUsuario, RedirectAttributes redirectAttributes) {
		if (idsUsuariosAdministradores().contains(idUsuario)) {
			redirectAttributes.addFlashAttribute("error", "El usuario administrador no se puede eliminar.");
			return "redirect:/usuario";
		}
		try {
			servicioUsuario.eliminarUsuario(idUsuario);
			redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar el usuario."));
		}
		return "redirect:/usuario";
	}

	private Set<Integer> idsUsuariosAdministradores() {
		return servicioRol.listarRoles().stream()
				.filter(rol -> "Administrador".equalsIgnoreCase(rol.getNombre()))
				.findFirst()
				.map(RolResponseDto::getIdRol)
				.map(idRolAdministrador -> servicioUsuarioRol.listarUsuarioRoles().stream()
						.filter(ur -> ur.getIdRol() == idRolAdministrador)
						.map(ur -> ur.getIdUsuario())
						.collect(Collectors.toSet()))
				.orElse(Set.of());
	}

	private String extraerMensajeError(WebClientResponseException ex, String mensajeGenerico) {
		try {
			ErrorResponseDto error = ex.getResponseBodyAs(ErrorResponseDto.class);
			if (error != null && error.getMessage() != null) {
				return error.getMessage();
			}
		} catch (Exception e) {
		}
		return mensajeGenerico;
	}

}
