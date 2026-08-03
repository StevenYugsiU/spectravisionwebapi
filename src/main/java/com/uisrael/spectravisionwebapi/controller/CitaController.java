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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uisrael.spectravisionwebapi.model.request.CitaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CitaResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
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

	public String leerPagina(Model model) {
		List<CitaResponseDto> listaCitas = servicioCita.listarCitas();
		model.addAttribute("listaCitas", listaCitas);
		return "/cita/listarcitas";
	}

	@GetMapping("/nuevo")
	public String nuevaCita(Model model) {
		model.addAttribute("cita", new CitaRequestDto());
		model.addAttribute("listaClientes", servicioCliente.listarClientes());
		return "/cita/formulariocita";
	}

	@PostMapping("/guardar")
	public String guardarCita(@ModelAttribute CitaRequestDto cita, RedirectAttributes redirectAttributes) {
		try {
			servicioCita.guardarCita(cita);
			redirectAttributes.addFlashAttribute("mensaje", "Cita creada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear la cita."));
		}
		return "redirect:/cita";
	}

	@GetMapping("/editar/{idCita}")
	public String editarCita(@PathVariable int idCita, Model model, RedirectAttributes redirectAttributes) {
		CitaResponseDto encontrada;
		try {
			encontrada = servicioCita.buscarCitaPorId(idCita);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se encontró la cita solicitada."));
			return "redirect:/cita";
		}

		CitaRequestDto cita = new CitaRequestDto();
		cita.setIdCita(encontrada.getIdCita());
		cita.setIdCliente(encontrada.getFkCliente().getIdCliente());
		cita.setFecha(encontrada.getFecha());
		cita.setHora(encontrada.getHora());
		cita.setTipoCita(encontrada.getTipoCita());
		cita.setEstado(encontrada.getEstado());

		model.addAttribute("cita", cita);
		model.addAttribute("listaClientes", servicioCliente.listarClientes());
		return "/cita/formulariocita";
	}

	@PostMapping("/actualizar/{idCita}")
	public String actualizarCita(@PathVariable int idCita, @ModelAttribute CitaRequestDto cita,
			RedirectAttributes redirectAttributes) {
		try {
			servicioCita.actualizarCita(idCita, cita);
			redirectAttributes.addFlashAttribute("mensaje", "Cita actualizada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo actualizar la cita."));
		}
		return "redirect:/cita";
	}

	@PostMapping("/cancelar/{idCita}")
	public String cancelarCita(@PathVariable int idCita, RedirectAttributes redirectAttributes) {
		try {
			servicioCita.cancelarCita(idCita);
			redirectAttributes.addFlashAttribute("mensaje", "Cita cancelada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo cancelar la cita."));
		}
		return "redirect:/cita";
	}

	@PostMapping("/eliminar/{idCita}")
	public String eliminarCita(@PathVariable int idCita, RedirectAttributes redirectAttributes) {
		try {
			servicioCita.eliminarCita(idCita);
			redirectAttributes.addFlashAttribute("mensaje", "Cita eliminada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar la cita."));
		}
		return "redirect:/cita";
	}

	private String extraerMensajeError(WebClientResponseException ex, String mensajeGenerico) {
		try {
			ErrorResponseDto error = ex.getResponseBodyAs(ErrorResponseDto.class);
			if (error != null && error.getMessage() != null) {
				return error.getMessage();
			}
		} catch (Exception e) {
		}
		return mensajeGenerico;
	}

}
