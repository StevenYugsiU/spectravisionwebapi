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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uisrael.spectravisionwebapi.model.request.CertificadoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.service.ICertificadoPdfService;
import com.uisrael.spectravisionwebapi.service.ICertificadoService;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;

@Controller
@RequestMapping("/certificado")
public class CertificadoController {

	@Autowired
	private ICertificadoService servicioCertificado;

	@Autowired
	private ICertificadoPdfService servicioCertificadoPdf;

	@Autowired
	private IExamenVisualService servicioExamenVisual;

	@GetMapping
	public String leerPagina(Model model) {
		List<CertificadoResponseDto> listaCertificados = servicioCertificado.listarCertificados();
		model.addAttribute("listaCertificados", listaCertificados);
		return "/certificado/listarcertificados";
	}

	@GetMapping("/nuevo")
	public String nuevoCertificado(Model model) {
		model.addAttribute("certificado", new CertificadoRequestDto());
		model.addAttribute("listaExamenes", servicioExamenVisual.listarExamenesVisuales());
		return "/certificado/formulariocertificado";
	}

	@PostMapping("/guardar")
	public String guardarCertificado(@ModelAttribute CertificadoRequestDto certificado,
			RedirectAttributes redirectAttributes) {
		try {
			servicioCertificado.guardarCertificado(certificado);
			redirectAttributes.addFlashAttribute("mensaje", "Certificado creado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo crear el certificado."));
		}
		return "redirect:/certificado";
	}

	@GetMapping("/editar/{idCertificado}")
	public String editarCertificado(@PathVariable int idCertificado, Model model,
			RedirectAttributes redirectAttributes) {
		CertificadoResponseDto encontrado;
		try {
			encontrado = servicioCertificado.buscarCertificadoPorId(idCertificado);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró el certificado solicitado."));
			return "redirect:/certificado";
		}

		CertificadoRequestDto certificado = new CertificadoRequestDto();
		certificado.setIdCertificado(encontrado.getIdCertificado());
		certificado.setIdExamen(encontrado.getFkExamenVisual().getIdExamen());
		certificado.setFechaGeneracion(encontrado.getFechaGeneracion());
		certificado.setObservaciones(encontrado.getObservaciones());

		model.addAttribute("certificado", certificado);
		model.addAttribute("listaExamenes", servicioExamenVisual.listarExamenesVisuales());
		return "/certificado/formulariocertificado";
	}

	@PostMapping("/actualizar/{idCertificado}")
	public String actualizarCertificado(@PathVariable int idCertificado,
			@ModelAttribute CertificadoRequestDto certificado, RedirectAttributes redirectAttributes) {
		try {
			servicioCertificado.actualizarCertificado(idCertificado, certificado);
			redirectAttributes.addFlashAttribute("mensaje", "Certificado actualizado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo actualizar el certificado."));
		}
		return "redirect:/certificado";
	}

	@PostMapping("/eliminar/{idCertificado}")
	public String eliminarCertificado(@PathVariable int idCertificado, RedirectAttributes redirectAttributes) {
		try {
			servicioCertificado.eliminarCertificado(idCertificado);
			redirectAttributes.addFlashAttribute("mensaje", "Certificado eliminado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se pudo eliminar el certificado."));
		}
		return "redirect:/certificado";
	}

	@GetMapping("/{idCertificado}/pdf")
	@ResponseBody
	public ResponseEntity<byte[]> descargarPdf(@PathVariable int idCertificado) {
		byte[] pdf = servicioCertificadoPdf.generarPdf(idCertificado);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=certificado-" + idCertificado + ".pdf")
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
