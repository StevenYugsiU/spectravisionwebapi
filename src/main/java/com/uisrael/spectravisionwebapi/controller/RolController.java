package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

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

import com.uisrael.spectravisionwebapi.model.request.RolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.RolResponseDto;
import com.uisrael.spectravisionwebapi.service.IRolService;

@Controller
@RequestMapping("/rol")
public class RolController {

	@Autowired
	private IRolService servicioRol;

	@GetMapping
	public String leerPagina(Model model) {
		List<RolResponseDto> listaRoles = servicioRol.listarRoles();
		model.addAttribute("listaRoles", listaRoles);
		return "/rol/listarroles";
	}

	@GetMapping("/nuevo")
	public String nuevoRol(Model model) {
		model.addAttribute("rol", new RolRequestDto());
		return "/rol/formulariorol";
	}

	@PostMapping("/guardar")
	public String guardarRol(@ModelAttribute RolRequestDto rol, RedirectAttributes redirectAttributes) {
		try {
			servicioRol.guardarRol(rol);
			redirectAttributes.addFlashAttribute("mensaje", "Rol creado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear el rol."));
		}
		return "redirect:/rol";
	}

	@GetMapping("/editar/{idRol}")
	public String editarRol(@PathVariable int idRol, Model model, RedirectAttributes redirectAttributes) {
		RolResponseDto encontrado;
		try {
			encontrado = servicioRol.buscarRolPorId(idRol);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se encontró el rol solicitado."));
			return "redirect:/rol";
		}

		RolRequestDto rol = new RolRequestDto();
		rol.setIdRol(encontrado.getIdRol());
		rol.setNombre(encontrado.getNombre());
		rol.setDescripcion(encontrado.getDescripcion());

		model.addAttribute("rol", rol);
		return "/rol/formulariorol";
	}

	@PostMapping("/actualizar/{idRol}")
	public String actualizarRol(@PathVariable int idRol, @ModelAttribute RolRequestDto rol,
			RedirectAttributes redirectAttributes) {
		try {
			servicioRol.actualizarRol(idRol, rol);
			redirectAttributes.addFlashAttribute("mensaje", "Rol actualizado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo actualizar el rol."));
		}
		return "redirect:/rol";
	}

	@PostMapping("/eliminar/{idRol}")
	public String eliminarRol(@PathVariable int idRol, RedirectAttributes redirectAttributes) {
		RolResponseDto rol;
		try {
			rol = servicioRol.buscarRolPorId(idRol);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar el rol."));
			return "redirect:/rol";
		}
		if ("Administrador".equalsIgnoreCase(rol.getNombre())) {
			redirectAttributes.addFlashAttribute("error", "El rol Administrador no se puede eliminar.");
			return "redirect:/rol";
		}
		try {
			servicioRol.eliminarRol(idRol);
			redirectAttributes.addFlashAttribute("mensaje", "Rol eliminado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar el rol."));
		}
		return "redirect:/rol";
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
