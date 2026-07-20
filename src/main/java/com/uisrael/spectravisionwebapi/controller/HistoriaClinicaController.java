package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
