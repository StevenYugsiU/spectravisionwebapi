package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.response.UsuarioResponseDto;
import com.uisrael.spectravisionwebapi.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService servicioUsuario;

	@GetMapping
	public String leerPagina(Model model) {
		List<UsuarioResponseDto> listaUsuarios = servicioUsuario.listarUsuarios();
		model.addAttribute("listausuarios", listaUsuarios);
		return "/usuario/listarusuarios";
	}

}
