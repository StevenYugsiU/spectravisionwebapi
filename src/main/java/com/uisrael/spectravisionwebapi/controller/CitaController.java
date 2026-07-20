package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.request.CitaRequestDto;
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

	@GetMapping("/nuevo")
	public String nuevaCita(Model model) {
		model.addAttribute("cita", new CitaRequestDto());
		return "/cita/formulariocita";
	}

	@PostMapping("/guardar")
	public String guardarCita(@ModelAttribute CitaRequestDto cita) {
		servicioCita.guardarCita(cita);
		return "redirect:/cita";
	}

}
