package com.uisrael.spectravisionwebapi.controller;

import java.time.LocalDate;
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

import com.uisrael.spectravisionwebapi.model.request.CitaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CitaResponseDto;
import com.uisrael.spectravisionwebapi.service.ICitaService;
import com.uisrael.spectravisionwebapi.service.IClienteService;

@Controller
@RequestMapping("/cita")
public class CitaController {

	@Autowired
	private ICitaService servicioCita;

	@Autowired
	private IClienteService servicioCliente;

	@GetMapping
	public String leerPagina(@RequestParam(required = false) LocalDate fecha, Model model) {
		List<CitaResponseDto> listaCitas = fecha != null ? servicioCita.buscarCitasPorFecha(fecha)
				: servicioCita.listarCitas();
		model.addAttribute("listacitas", listaCitas);
		model.addAttribute("fechaFiltro", fecha != null ? fecha.toString() : "");
		return "/cita/listarcitas";
	}

	@GetMapping("/nuevo")
	public String nuevaCita(Model model) {
		model.addAttribute("cita", new CitaRequestDto());
		model.addAttribute("listaclientes", servicioCliente.listarClientes());
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
		cita.setIdCliente(encontrada.getFkCliente().getIdCliente());
		cita.setFecha(encontrada.getFecha());
		cita.setHora(encontrada.getHora());
		cita.setTipoCita(encontrada.getTipoCita());
		cita.setEstado(encontrada.getEstado());

		model.addAttribute("cita", cita);
		model.addAttribute("listaclientes", servicioCliente.listarClientes());
		return "/cita/formulariocita";
	}

	@PostMapping("/actualizar/{idCita}")
	public String actualizarCita(@PathVariable int idCita, @ModelAttribute CitaRequestDto cita) {
		servicioCita.actualizarCita(idCita, cita);
		return "redirect:/cita";
	}

	@PostMapping("/cancelar/{idCita}")
	public String cancelarCita(@PathVariable int idCita) {
		servicioCita.cancelarCita(idCita);
		return "redirect:/cita";
	}

	@PostMapping("/eliminar/{idCita}")
	public String eliminarCita(@PathVariable int idCita) {
		servicioCita.eliminarCita(idCita);
		return "redirect:/cita";
	}

}
