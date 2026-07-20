package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.request.EntregaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.EntregaResponseDto;
import com.uisrael.spectravisionwebapi.service.IEntregaService;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

	@Autowired
	private IEntregaService servicioEntrega;

	@GetMapping
	public String leerPagina(Model model) {
		List<EntregaResponseDto> listaEntregas = servicioEntrega.listarEntregas();
		model.addAttribute("listaentregas", listaEntregas);
		return "/entrega/listarentregas";
	}

	@GetMapping("/nuevo")
	public String nuevaEntrega(Model model) {
		model.addAttribute("entrega", new EntregaRequestDto());
		return "/entrega/formularioentrega";
	}

	@PostMapping("/guardar")
	public String guardarEntrega(@ModelAttribute EntregaRequestDto entrega) {
		servicioEntrega.guardarEntrega(entrega);
		return "redirect:/entrega";
	}

}
