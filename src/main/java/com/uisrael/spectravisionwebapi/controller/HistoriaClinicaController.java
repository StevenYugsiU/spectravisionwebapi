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
import org.springframework.web.bind.annotation.RequestParam;

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
	public String nuevaHistoriaClinica(@RequestParam(required = false) Integer idCliente, Model model) {
		HistoriaClinicaRequestDto historiaclinica = new HistoriaClinicaRequestDto();
		if (idCliente != null) {
			historiaclinica.setIdCliente(idCliente);
		}
		model.addAttribute("historiaclinica", historiaclinica);
		return "/historiaclinica/formulariohistoriaclinica";
	}

	@PostMapping("/guardar")
	public String guardarHistoriaClinica(@ModelAttribute HistoriaClinicaRequestDto historiaclinica) {
		servicioHistoriaClinica.guardarHistoriaClinica(historiaclinica);
		return "redirect:/historiaclinica";
	}

	@GetMapping("/editar/{idHistoriaClinica}")
	public String editarHistoriaClinica(@PathVariable int idHistoriaClinica, Model model) {
		HistoriaClinicaResponseDto encontrada = servicioHistoriaClinica.buscarHistoriaClinicaPorId(idHistoriaClinica);

		HistoriaClinicaRequestDto historiaclinica = new HistoriaClinicaRequestDto();
		historiaclinica.setIdHistoriaClinica(encontrada.getIdHistoriaClinica());
		historiaclinica.setIdCliente(encontrada.getIdCliente());
		historiaclinica.setFechaApertura(encontrada.getFechaApertura());
		historiaclinica.setAntecedentes(encontrada.getAntecedentes());
		historiaclinica.setObservacionesGenerales(encontrada.getObservacionesGenerales());
		historiaclinica.setEstado(encontrada.getEstado());

		model.addAttribute("historiaclinica", historiaclinica);
		return "/historiaclinica/formulariohistoriaclinica";
	}

	@PostMapping("/actualizar/{idHistoriaClinica}")
	public String actualizarHistoriaClinica(@PathVariable int idHistoriaClinica,
			@ModelAttribute HistoriaClinicaRequestDto historiaclinica) {
		servicioHistoriaClinica.actualizarHistoriaClinica(idHistoriaClinica, historiaclinica);
		return "redirect:/historiaclinica";
	}

	@PostMapping("/eliminar/{idHistoriaClinica}")
	public String eliminarHistoriaClinica(@PathVariable int idHistoriaClinica) {
		servicioHistoriaClinica.eliminarHistoriaClinica(idHistoriaClinica);
		return "redirect:/historiaclinica";
	}

}
