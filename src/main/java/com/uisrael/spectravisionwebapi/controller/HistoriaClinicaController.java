package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.request.HistoriaClinicaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;
import com.uisrael.spectravisionwebapi.service.IHistoriaClinicaService;

@Controller
@RequestMapping("/historiaclinica")
public class HistoriaClinicaController {

	@Autowired
	private IHistoriaClinicaService servicioHistoriaClinica;

	@GetMapping
	public String leerPagina(Model model) {
		List<HistoriaClinicaResponseDto> listaHistoriasClinicas = servicioHistoriaClinica.listarHistoriasClinicas();
		model.addAttribute("listahistoriasclinicas", listaHistoriasClinicas);
		return "/historiaclinica/listarhistoriasclinicas";
	}

	@GetMapping("/nuevo")
	public String nuevaHistoriaClinica(Model model) {
		model.addAttribute("historiaclinica", new HistoriaClinicaRequestDto());
		return "/historiaclinica/formulariohistoriaclinica";
	}

	@PostMapping("/guardar")
	public String guardarHistoriaClinica(@ModelAttribute HistoriaClinicaRequestDto historiaclinica) {
		servicioHistoriaClinica.guardarHistoriaClinica(historiaclinica);
		return "redirect:/historiaclinica";
	}

}
