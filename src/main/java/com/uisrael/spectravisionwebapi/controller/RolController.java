package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		model.addAttribute("listaroles", listaRoles);
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

}
