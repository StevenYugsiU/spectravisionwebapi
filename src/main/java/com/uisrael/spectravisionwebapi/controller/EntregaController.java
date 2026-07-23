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

import com.uisrael.spectravisionwebapi.model.request.EntregaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.EntregaResponseDto;
import com.uisrael.spectravisionwebapi.service.IEntregaService;

@Controller
@RequestMapping("/entrega")
public class EntregaController {

	@Autowired
	private IEntregaService servicioEntrega;

	@GetMapping
	public String leerPagina(Model model) {
		List<EntregaResponseDto> listaEntregas = servicioEntrega.listarEntregas();
		model.addAttribute("listaentregas", listaEntregas);
		return "/entrega/listarentregas";
	}

	@GetMapping("/nuevo")
	public String nuevaEntrega(Model model) {
		model.addAttribute("entrega", new EntregaRequestDto());
		return "/entrega/formularioentrega";
	}

	@PostMapping("/guardar")
	public String guardarEntrega(@ModelAttribute EntregaRequestDto entrega) {
		servicioEntrega.guardarEntrega(entrega);
		return "redirect:/entrega";
	}

	@GetMapping("/editar/{idEntrega}")
	public String editarEntrega(@PathVariable int idEntrega, Model model) {
		EntregaResponseDto encontrada = servicioEntrega.buscarEntregaPorId(idEntrega);

		EntregaRequestDto entrega = new EntregaRequestDto();
		entrega.setIdEntrega(encontrada.getIdEntrega());
		entrega.setIdCliente(encontrada.getIdCliente());
		entrega.setFechaEntrega(encontrada.getFechaEntrega());
		entrega.setObservaciones(encontrada.getObservaciones());
		entrega.setEstado(encontrada.getEstado());

		model.addAttribute("entrega", entrega);
		return "/entrega/formularioentrega";
	}

	@PostMapping("/actualizar/{idEntrega}")
	public String actualizarEntrega(@PathVariable int idEntrega, @ModelAttribute EntregaRequestDto entrega) {
		servicioEntrega.actualizarEntrega(idEntrega, entrega);
		return "redirect:/entrega";
	}

	@PostMapping("/eliminar/{idEntrega}")
	public String eliminarEntrega(@PathVariable int idEntrega) {
		servicioEntrega.eliminarEntrega(idEntrega);
		return "redirect:/entrega";
	}

}
