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

import com.uisrael.spectravisionwebapi.model.request.CertificadoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;
import com.uisrael.spectravisionwebapi.service.ICertificadoPdfService;
import com.uisrael.spectravisionwebapi.service.ICertificadoService;

@Controller
@RequestMapping("/certificado")
public class CertificadoController {

	@Autowired
	private ICertificadoService servicioCertificado;

	@Autowired
	private ICertificadoPdfService servicioCertificadoPdf;

	@GetMapping
	public String leerPagina(Model model) {
		List<CertificadoResponseDto> listaCertificados = servicioCertificado.listarCertificados();
		model.addAttribute("listacertificados", listaCertificados);
		return "/certificado/listarcertificados";
	}

	@GetMapping("/nuevo")
	public String nuevoCertificado(Model model) {
		model.addAttribute("certificado", new CertificadoRequestDto());
		return "/certificado/formulariocertificado";
	}

	@PostMapping("/guardar")
	public String guardarCertificado(@ModelAttribute CertificadoRequestDto certificado) {
		servicioCertificado.guardarCertificado(certificado);
		return "redirect:/certificado";
	}

	@GetMapping("/editar/{idCertificado}")
	public String editarCertificado(@PathVariable int idCertificado, Model model) {
		CertificadoResponseDto encontrado = servicioCertificado.buscarCertificadoPorId(idCertificado);

		CertificadoRequestDto certificado = new CertificadoRequestDto();
		certificado.setIdCertificado(encontrado.getIdCertificado());
		certificado.setIdExamen(encontrado.getIdExamen());
		certificado.setFechaGeneracion(encontrado.getFechaGeneracion());
		certificado.setObservaciones(encontrado.getObservaciones());

		model.addAttribute("certificado", certificado);
		return "/certificado/formulariocertificado";
	}

	@PostMapping("/actualizar/{idCertificado}")
	public String actualizarCertificado(@PathVariable int idCertificado,
			@ModelAttribute CertificadoRequestDto certificado) {
		servicioCertificado.actualizarCertificado(idCertificado, certificado);
		return "redirect:/certificado";
	}

	@PostMapping("/eliminar/{idCertificado}")
	public String eliminarCertificado(@PathVariable int idCertificado) {
		servicioCertificado.eliminarCertificado(idCertificado);
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

}
