package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uisrael.spectravisionwebapi.model.request.ExamenVisualRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;
import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;
import com.uisrael.spectravisionwebapi.service.IExamenVisualPdfService;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;
import com.uisrael.spectravisionwebapi.service.IHistoriaClinicaService;

@Controller
@RequestMapping("/examenvisual")
public class ExamenVisualController {

	@Autowired
	private IExamenVisualService servicioExamenVisual;

	@Autowired
	private IHistoriaClinicaService servicioHistoriaClinica;

	@Autowired
	private IExamenVisualPdfService servicioExamenVisualPdf;

	@GetMapping
	public String leerPagina(Model model) {
		List<ExamenVisualResponseDto> listaExamenesVisuales = servicioExamenVisual.listarExamenesVisuales();
		model.addAttribute("listaExamenesVisuales", listaExamenesVisuales);
		return "/examenvisual/listarexamenesvisuales";
	}

	@GetMapping("/nuevo")
	public String nuevoExamenVisual(@RequestParam(required = false) Integer idHistoria,
			@RequestParam(required = false) Boolean fromHistoria, Model model,
			RedirectAttributes redirectAttributes) {
		ExamenVisualRequestDto examenvisual = new ExamenVisualRequestDto();
		if (idHistoria != null) {
			examenvisual.setIdHistoria(idHistoria);

			HistoriaClinicaResponseDto historia;
			try {
				historia = servicioHistoriaClinica.buscarHistoriaClinicaPorId(idHistoria);
			} catch (WebClientResponseException ex) {
				redirectAttributes.addFlashAttribute("error",
						extraerMensajeError(ex, "No se encontró la historia clínica solicitada."));
				return "redirect:/historiaclinica";
			}
			model.addAttribute("clienteSeleccionado", historia.getFkCliente());
		}
		model.addAttribute("examenvisual", examenvisual);
		model.addAttribute("fromHistoria", fromHistoria);

		model.addAttribute("listaHistorias", servicioHistoriaClinica.listarHistoriasClinicas());
		return "/examenvisual/formularioexamenvisual";
	}

	@PostMapping("/guardar")
	public String guardarExamenVisual(@ModelAttribute ExamenVisualRequestDto examenvisual,
			@RequestParam(required = false) Boolean fromHistoria, RedirectAttributes redirectAttributes) {
		String destino = Boolean.TRUE.equals(fromHistoria)
				? "redirect:/historiaclinica/detalle/" + examenvisual.getIdHistoria()
				: "redirect:/examenvisual";
		try {
			servicioExamenVisual.guardarExamenVisual(examenvisual);
			redirectAttributes.addFlashAttribute("mensaje", "Examen visual creado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear el examen visual."));
		}
		return destino;
	}

	@GetMapping("/editar/{idExamen}")
	public String editarExamenVisual(@PathVariable int idExamen,
			@RequestParam(required = false) Boolean fromHistoria, Model model,
			RedirectAttributes redirectAttributes) {
		ExamenVisualResponseDto encontrado;
		try {
			encontrado = servicioExamenVisual.buscarExamenVisualPorId(idExamen);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró el examen visual solicitado."));
			return "redirect:/examenvisual";
		}

		ExamenVisualRequestDto examenvisual = new ExamenVisualRequestDto();
		examenvisual.setIdExamen(encontrado.getIdExamen());
		examenvisual.setIdHistoria(encontrado.getFkHistoriaClinica().getIdHistoriaClinica());
		examenvisual.setFechaExamen(encontrado.getFechaExamen());
		examenvisual.setUltimoControlVisual(encontrado.getUltimoControlVisual());
		examenvisual.setMotivoConsulta(encontrado.getMotivoConsulta());
		examenvisual.setAvOd(encontrado.getAvOd());
		examenvisual.setAvOi(encontrado.getAvOi());
		examenvisual.setSphOd(encontrado.getSphOd());
		examenvisual.setCylOd(encontrado.getCylOd());
		examenvisual.setEjeOd(encontrado.getEjeOd());
		examenvisual.setSphOi(encontrado.getSphOi());
		examenvisual.setCylOi(encontrado.getCylOi());
		examenvisual.setEjeOi(encontrado.getEjeOi());
		examenvisual.setAddValor(encontrado.getAddValor());
		examenvisual.setDnp(encontrado.getDnp());
		examenvisual.setAltura(encontrado.getAltura());
		examenvisual.setBiomicroscopia(encontrado.getBiomicroscopia());
		examenvisual.setRecomentaciones(encontrado.getRecomentaciones());
		examenvisual.setProximaConsulta(encontrado.getProximaConsulta());
		examenvisual.setDiagnostico(encontrado.getDiagnostico());

		model.addAttribute("examenvisual", examenvisual);
		model.addAttribute("fromHistoria", fromHistoria);

		model.addAttribute("listaHistorias", servicioHistoriaClinica.listarHistoriasClinicas());
		return "/examenvisual/formularioexamenvisual";
	}

	@PostMapping("/actualizar/{idExamen}")
	public String actualizarExamenVisual(@PathVariable int idExamen,
			@ModelAttribute ExamenVisualRequestDto examenvisual,
			@RequestParam(required = false) Boolean fromHistoria, RedirectAttributes redirectAttributes) {
		String destino = Boolean.TRUE.equals(fromHistoria)
				? "redirect:/historiaclinica/detalle/" + examenvisual.getIdHistoria()
				: "redirect:/examenvisual";
		try {
			servicioExamenVisual.actualizarExamenVisual(idExamen, examenvisual);
			redirectAttributes.addFlashAttribute("mensaje", "Examen visual actualizado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo actualizar el examen visual."));
		}
		return destino;
	}

	@PostMapping("/eliminar/{idExamen}")
	public String eliminarExamenVisual(@PathVariable int idExamen,
			@RequestParam(required = false) Integer idHistoria, RedirectAttributes redirectAttributes) {
		String destino = idHistoria != null ? "redirect:/historiaclinica/detalle/" + idHistoria
				: "redirect:/examenvisual";
		try {
			servicioExamenVisual.eliminarExamenVisual(idExamen);
			redirectAttributes.addFlashAttribute("mensaje", "Examen visual eliminado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar el examen visual."));
		}
		return destino;
	}

	@GetMapping("/{idExamen}/pdf")
	@ResponseBody
	public ResponseEntity<byte[]> descargarPdf(@PathVariable int idExamen) {
		byte[] pdf = servicioExamenVisualPdf.generarPdf(idExamen);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=examen-visual-" + idExamen + ".pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdf);
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
