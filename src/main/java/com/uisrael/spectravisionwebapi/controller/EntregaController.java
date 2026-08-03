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

import com.uisrael.spectravisionwebapi.model.request.EntregaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.EntregaResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.service.IClienteService;
import com.uisrael.spectravisionwebapi.service.IEntregaService;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

	@Autowired
	private IEntregaService servicioEntrega;

	@Autowired
	private IClienteService servicioCliente;

	@GetMapping
	public String leerPagina(Model model) {
		List<EntregaResponseDto> listaEntregas = servicioEntrega.listarEntregas();
		model.addAttribute("listaEntregas", listaEntregas);
		return "/entrega/listarentregas";
	}

	@GetMapping("/nuevo")
	public String nuevaEntrega(Model model) {
		model.addAttribute("entrega", new EntregaRequestDto());
		model.addAttribute("listaClientes", servicioCliente.listarClientes());
		return "/entrega/formularioentrega";
	}

	@PostMapping("/guardar")
	public String guardarEntrega(@ModelAttribute EntregaRequestDto entrega, RedirectAttributes redirectAttributes) {
		try {
			servicioEntrega.guardarEntrega(entrega);
			redirectAttributes.addFlashAttribute("mensaje", "Entrega creada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear la entrega."));
		}
		return "redirect:/entrega";
	}

	@GetMapping("/editar/{idEntrega}")
	public String editarEntrega(@PathVariable int idEntrega, Model model, RedirectAttributes redirectAttributes) {
		EntregaResponseDto encontrada;
		try {
			encontrada = servicioEntrega.buscarEntregaPorId(idEntrega);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró la entrega solicitada."));
			return "redirect:/entrega";
		}

		EntregaRequestDto entrega = new EntregaRequestDto();
		entrega.setIdEntrega(encontrada.getIdEntrega());
		entrega.setIdCliente(encontrada.getFkCliente().getIdCliente());
		entrega.setFechaEntrega(encontrada.getFechaEntrega());
		entrega.setObservaciones(encontrada.getObservaciones());
		entrega.setEstado(encontrada.getEstado());

		model.addAttribute("entrega", entrega);
		model.addAttribute("listaClientes", servicioCliente.listarClientes());
		return "/entrega/formularioentrega";
	}

	@PostMapping("/actualizar/{idEntrega}")
	public String actualizarEntrega(@PathVariable int idEntrega, @ModelAttribute EntregaRequestDto entrega,
			RedirectAttributes redirectAttributes) {
		try {
			servicioEntrega.actualizarEntrega(idEntrega, entrega);
			redirectAttributes.addFlashAttribute("mensaje", "Entrega actualizada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo actualizar la entrega."));
		}
		return "redirect:/entrega";
	}

	@PostMapping("/eliminar/{idEntrega}")
	public String eliminarEntrega(@PathVariable int idEntrega, RedirectAttributes redirectAttributes) {
		try {
			servicioEntrega.eliminarEntrega(idEntrega);
			redirectAttributes.addFlashAttribute("mensaje", "Entrega eliminada correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar la entrega."));
		}
		return "redirect:/entrega";
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
