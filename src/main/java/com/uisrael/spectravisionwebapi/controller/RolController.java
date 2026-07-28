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

import com.uisrael.spectravisionwebapi.model.request.RolRequestDto;
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
	public String guardarRol(@ModelAttribute RolRequestDto rol) {
		servicioRol.guardarRol(rol);
		return "redirect:/rol";
	}

	@GetMapping("/editar/{idRol}")
	public String editarRol(@PathVariable int idRol, Model model) {
		RolResponseDto encontrado = servicioRol.buscarRolPorId(idRol);

		RolRequestDto rol = new RolRequestDto();
		rol.setIdRol(encontrado.getIdRol());
		rol.setNombre(encontrado.getNombre());
		rol.setDescripcion(encontrado.getDescripcion());

		model.addAttribute("rol", rol);
		return "/rol/formulariorol";
	}

	@PostMapping("/actualizar/{idRol}")
	public String actualizarRol(@PathVariable int idRol, @ModelAttribute RolRequestDto rol) {
		servicioRol.actualizarRol(idRol, rol);
		return "redirect:/rol";
	}

	@PostMapping("/eliminar/{idRol}")
	public String eliminarRol(@PathVariable int idRol) {
		servicioRol.eliminarRol(idRol);
		return "redirect:/rol";
	}

}
