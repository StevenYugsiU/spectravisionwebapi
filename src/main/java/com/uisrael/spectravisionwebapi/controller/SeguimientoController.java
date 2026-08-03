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

import com.uisrael.spectravisionwebapi.model.request.SeguimientoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.SeguimientoResponseDto;

import com.uisrael.spectravisionwebapi.service.IEntregaService;
import com.uisrael.spectravisionwebapi.service.ISeguimientoService;

@Controller
@RequestMapping("/seguimiento")
public class SeguimientoController {

	@Autowired
	private ISeguimientoService servicioSeguimiento;

	@Autowired
	private IEntregaService servicioEntrega;

	@GetMapping
	public String leerPagina(Model model) {
		List<SeguimientoResponseDto> listaSeguimientos = servicioSeguimiento.listarSeguimientos();
		model.addAttribute("listaSeguimientos", listaSeguimientos);
		return "/seguimiento/listarseguimientos";
	}

	@GetMapping("/alertas")
	public String verAlertas(@RequestParam(defaultValue = "3") int dias, Model model) {
		model.addAttribute("listaAlertas", servicioSeguimiento.buscarAlertas(dias));
		model.addAttribute("dias", dias);
		return "/seguimiento/alertas";
	}

	@GetMapping("/nuevo")
	public String nuevoSeguimiento(Model model) {
		model.addAttribute("seguimiento", new SeguimientoRequestDto());

		model.addAttribute("listaEntregas", servicioEntrega.listarEntregas());
		return "/seguimiento/formularioseguimiento";
	}

	@PostMapping("/guardar")
	public String guardarSeguimiento(@ModelAttribute SeguimientoRequestDto seguimiento,
			RedirectAttributes redirectAttributes) {
		try {
			servicioSeguimiento.guardarSeguimiento(seguimiento);
			redirectAttributes.addFlashAttribute("mensaje", "Seguimiento creado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear el seguimiento."));
		}
		return "redirect:/seguimiento";
	}

	@GetMapping("/editar/{idSeguimiento}")
	public String editarSeguimiento(@PathVariable int idSeguimiento, Model model,
			RedirectAttributes redirectAttributes) {
		SeguimientoResponseDto encontrado;
		try {
			encontrado = servicioSeguimiento.buscarSeguimientoPorId(idSeguimiento);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró el seguimiento solicitado."));
			return "redirect:/seguimiento";
		}

		SeguimientoRequestDto seguimiento = new SeguimientoRequestDto();
		seguimiento.setIdSeguimiento(encontrado.getIdSeguimiento());
		seguimiento.setIdEntrega(encontrado.getFkEntrega().getIdEntrega());
		seguimiento.setFechaSeguimiento(encontrado.getFechaSeguimiento());
		seguimiento.setObservaciones(encontrado.getObservaciones());
		seguimiento.setEstado(encontrado.getEstado());

		model.addAttribute("seguimiento", seguimiento);

		model.addAttribute("listaEntregas", servicioEntrega.listarEntregas());
		return "/seguimiento/formularioseguimiento";
	}

	@PostMapping("/actualizar/{idSeguimiento}")
	public String actualizarSeguimiento(@PathVariable int idSeguimiento,
			@ModelAttribute SeguimientoRequestDto seguimiento, RedirectAttributes redirectAttributes) {
		try {
			servicioSeguimiento.actualizarSeguimiento(idSeguimiento, seguimiento);
			redirectAttributes.addFlashAttribute("mensaje", "Seguimiento actualizado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo actualizar el seguimiento."));
		}
		return "redirect:/seguimiento";
	}

	@PostMapping("/eliminar/{idSeguimiento}")
	public String eliminarSeguimiento(@PathVariable int idSeguimiento, RedirectAttributes redirectAttributes) {
		try {
			servicioSeguimiento.eliminarSeguimiento(idSeguimiento);
			redirectAttributes.addFlashAttribute("mensaje", "Seguimiento eliminado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo eliminar el seguimiento."));
		}
		return "redirect:/seguimiento";
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
