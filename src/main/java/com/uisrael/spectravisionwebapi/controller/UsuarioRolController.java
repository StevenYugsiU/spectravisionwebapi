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

import com.uisrael.spectravisionwebapi.model.request.UsuarioRolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioRolResponseDto;
import com.uisrael.spectravisionwebapi.service.IUsuarioRolService;

@Controller
@RequestMapping("/usuariorol")
public class UsuarioRolController {

	@Autowired
	private IUsuarioRolService servicioUsuarioRol;

	@GetMapping
	public String leerPagina(Model model) {
		List<UsuarioRolResponseDto> listaUsuarioRoles = servicioUsuarioRol.listarUsuarioRoles();
		model.addAttribute("listausuarioroles", listaUsuarioRoles);
		return "/usuariorol/listarusuarioroles";
	}

	@GetMapping("/nuevo")
	public String nuevoUsuarioRol(Model model) {
		model.addAttribute("usuariorol", new UsuarioRolRequestDto());
		return "/usuariorol/formulariousuariorol";
	}

	@PostMapping("/guardar")
	public String guardarUsuarioRol(@ModelAttribute UsuarioRolRequestDto usuariorol) {
		servicioUsuarioRol.guardarUsuarioRol(usuariorol);
		return "redirect:/usuariorol";
	}

	@PostMapping("/eliminar/{idUsuarioRol}")
	public String eliminarUsuarioRol(@PathVariable int idUsuarioRol) {
		servicioUsuarioRol.eliminarUsuarioRol(idUsuarioRol);
		return "redirect:/usuariorol";
	}

}
