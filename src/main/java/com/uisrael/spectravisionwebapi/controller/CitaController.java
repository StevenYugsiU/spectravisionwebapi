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

	@GetMapping("/editar/{idCita}")
	public String editarCita(@PathVariable int idCita, Model model) {
		CitaResponseDto encontrada = servicioCita.buscarCitaPorId(idCita);

		CitaRequestDto cita = new CitaRequestDto();
		cita.setIdCita(encontrada.getIdCita());
		cita.setIdCliente(encontrada.getIdCliente());
		cita.setFecha(encontrada.getFecha());
		cita.setHora(encontrada.getHora());
		cita.setTipoCita(encontrada.getTipoCita());
		cita.setEstado(encontrada.getEstado());

		model.addAttribute("cita", cita);
		return "/cita/formulariocita";
	}

	@PostMapping("/actualizar/{idCita}")
	public String actualizarCita(@PathVariable int idCita, @ModelAttribute CitaRequestDto cita) {
		servicioCita.actualizarCita(idCita, cita);
		return "redirect:/cita";
	}

	@PostMapping("/eliminar/{idCita}")
	public String eliminarCita(@PathVariable int idCita) {
		servicioCita.eliminarCita(idCita);
		return "redirect:/cita";
	}

}
