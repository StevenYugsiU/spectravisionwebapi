package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.request.ExamenVisualRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;

@Controller
@RequestMapping("/examenvisual")
public class ExamenVisualController {

	@Autowired
	private IExamenVisualService servicioExamenVisual;

	@GetMapping
	public String leerPagina(Model model) {
		List<ExamenVisualResponseDto> listaExamenesVisuales = servicioExamenVisual.listarExamenesVisuales();
		model.addAttribute("listaexamenesvisuales", listaExamenesVisuales);
		return "/examenvisual/listarexamenesvisuales";
	}

	@GetMapping("/nuevo")
	public String nuevoExamenVisual(Model model) {
		model.addAttribute("examenvisual", new ExamenVisualRequestDto());
		return "/examenvisual/formularioexamenvisual";
	}

	@PostMapping("/guardar")
	public String guardarExamenVisual(@ModelAttribute ExamenVisualRequestDto examenvisual) {
		servicioExamenVisual.guardarExamenVisual(examenvisual);
		return "redirect:/examenvisual";
	}

}
