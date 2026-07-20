package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.response.CitaResponseDto;
import com.uisrael.spectravisionwebapi.service.ICitaService;

@Controller
@RequestMapping("/cita")
public class CitaController {

	@Autowired
	private ICitaService servicioCita;

	@GetMapping
	public String leerPagina(Model model) {
		List<CitaResponseDto> listaCitas = servicioCita.listarCitas();
		model.addAttribute("listacitas", listaCitas);
		return "/cita/listarcitas";
	}

}
