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
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uisrael.spectravisionwebapi.model.request.HistoriaClinicaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;
import com.uisrael.spectravisionwebapi.service.IClienteService;
import com.uisrael.spectravisionwebapi.service.IHistoriaClinicaService;

@Controller
@RequestMapping("/historiaclinica")
public class HistoriaClinicaController {

	@Autowired
	private IHistoriaClinicaService servicioHistoriaClinica;

	@Autowired
	private IClienteService servicioCliente;

	@GetMapping
	public String leerPagina(Model model) {
		List<HistoriaClinicaResponseDto> listaHistoriasClinicas = servicioHistoriaClinica.listarHistoriasClinicas();
		model.addAttribute("listaHistoriasClinicas", listaHistoriasClinicas);
		return "/historiaclinica/listarhistoriasclinicas";
	}

	@GetMapping("/nuevo")
	public String nuevaHistoriaClinica(@RequestParam(required = false) Integer idCliente, Model model) {
		HistoriaClinicaRequestDto historiaclinica = new HistoriaClinicaRequestDto();
		if (idCliente != null) {
			historiaclinica.setIdCliente(idCliente);
		}
		model.addAttribute("historiaclinica", historiaclinica);

		model.addAttribute("listaClientes", servicioCliente.listarClientes());
		return "/historiaclinica/formulariohistoriaclinica";
	}

	@PostMapping("/guardar")
	public String guardarHistoriaClinica(@ModelAttribute HistoriaClinicaRequestDto historiaclinica,
			RedirectAttributes redirectAttributes) {
		try {
			servicioHistoriaClinica.guardarHistoriaClinica(historiaclinica);
			redirectAttributes.addFlashAttribute("mensaje", "Historia clínica creada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear la historia clínica."));
		}
		return "redirect:/historiaclinica";
	}

	@GetMapping("/editar/{idHistoriaClinica}")
	public String editarHistoriaClinica(@PathVariable int idHistoriaClinica, Model model,
			RedirectAttributes redirectAttributes) {
		HistoriaClinicaResponseDto encontrada;
		try {
			encontrada = servicioHistoriaClinica.buscarHistoriaClinicaPorId(idHistoriaClinica);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró la historia clínica solicitada."));
			return "redirect:/historiaclinica";
		}

		HistoriaClinicaRequestDto historiaclinica = new HistoriaClinicaRequestDto();
		historiaclinica.setIdHistoriaClinica(encontrada.getIdHistoriaClinica());
		historiaclinica.setIdCliente(encontrada.getFkCliente().getIdCliente());
		historiaclinica.setFechaApertura(encontrada.getFechaApertura());
		historiaclinica.setAntecedentes(encontrada.getAntecedentes());
		historiaclinica.setObservacionesGenerales(encontrada.getObservacionesGenerales());
		historiaclinica.setEstado(encontrada.getEstado());

		model.addAttribute("historiaclinica", historiaclinica);

		model.addAttribute("listaClientes", servicioCliente.listarClientes());
		return "/historiaclinica/formulariohistoriaclinica";
	}

	@GetMapping("/detalle/{idHistoriaClinica}")
	public String verDetalleHistoriaClinica(@PathVariable int idHistoriaClinica, Model model,
			RedirectAttributes redirectAttributes) {
		HistoriaClinicaResponseDto historiaclinica;
		try {
			historiaclinica = servicioHistoriaClinica.buscarHistoriaClinicaPorId(idHistoriaClinica);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró la historia clínica solicitada."));
			return "redirect:/historiaclinica";
		}

		model.addAttribute("historiaclinica", historiaclinica);
		model.addAttribute("examenesVisuales", historiaclinica.getExamenesVisuales());
		return "/historiaclinica/detallehistoriaclinica";
	}

	@PostMapping("/actualizar/{idHistoriaClinica}")
	public String actualizarHistoriaClinica(@PathVariable int idHistoriaClinica,
			@ModelAttribute HistoriaClinicaRequestDto historiaclinica, RedirectAttributes redirectAttributes) {
		try {
			servicioHistoriaClinica.actualizarHistoriaClinica(idHistoriaClinica, historiaclinica);
			redirectAttributes.addFlashAttribute("mensaje", "Historia clínica actualizada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo actualizar la historia clínica."));
		}
		return "redirect:/historiaclinica";
	}

	@PostMapping("/eliminar/{idHistoriaClinica}")
	public String eliminarHistoriaClinica(@PathVariable int idHistoriaClinica, RedirectAttributes redirectAttributes) {
		try {
			servicioHistoriaClinica.eliminarHistoriaClinica(idHistoriaClinica);
			redirectAttributes.addFlashAttribute("mensaje", "Historia clínica eliminada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo eliminar la historia clínica."));
		}
		return "redirect:/historiaclinica";
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
