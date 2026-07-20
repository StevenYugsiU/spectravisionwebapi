package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.response.SeguimientoResponseDto;
import com.uisrael.spectravisionwebapi.service.ISeguimientoService;

@Controller
@RequestMapping("/seguimiento")
public class SeguimientoController {

	@Autowired
	private ISeguimientoService servicioSeguimiento;

	@GetMapping
	public String leerPagina(Model model) {
		List<SeguimientoResponseDto> listaSeguimientos = servicioSeguimiento.listarSeguimientos();
		model.addAttribute("listaseguimientos", listaSeguimientos);
		return "/seguimiento/listarseguimientos";
	}

}
