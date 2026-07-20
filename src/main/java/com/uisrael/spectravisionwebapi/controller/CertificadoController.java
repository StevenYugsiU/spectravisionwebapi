package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;
import com.uisrael.spectravisionwebapi.service.ICertificadoService;

@Controller
@RequestMapping("/certificado")
public class CertificadoController {

	@Autowired
	private ICertificadoService servicioCertificado;

	@GetMapping
	public String leerPagina(Model model) {
		List<CertificadoResponseDto> listaCertificados = servicioCertificado.listarCertificados();
		model.addAttribute("listacertificados", listaCertificados);
		return "/certificado/listarcertificados";
	}

}
