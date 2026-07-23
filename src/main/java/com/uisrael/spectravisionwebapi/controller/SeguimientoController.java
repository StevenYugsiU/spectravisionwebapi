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

import com.uisrael.spectravisionwebapi.model.request.SeguimientoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.SeguimientoResponseDto;
import com.uisrael.spectravisionwebapi.service.ISeguimientoService;

@Controller
@RequestMapping("/seguimiento")
public class SeguimientoController {

	@Autowired
	private ISeguimientoService servicioSeguimiento;

	@GetMapping
	public String leerPagina(Model model) {
		List<SeguimientoResponseDto> listaSeguimientos = servicioSeguimiento.listarSeguimientos();
		model.addAttribute("listaseguimientos", listaSeguimientos);
		return "/seguimiento/listarseguimientos";
	}

	@GetMapping("/nuevo")
	public String nuevoSeguimiento(Model model) {
		model.addAttribute("seguimiento", new SeguimientoRequestDto());
		return "/seguimiento/formularioseguimiento";
	}

	@PostMapping("/guardar")
	public String guardarSeguimiento(@ModelAttribute SeguimientoRequestDto seguimiento) {
		servicioSeguimiento.guardarSeguimiento(seguimiento);
		return "redirect:/seguimiento";
	}

	@GetMapping("/editar/{idSeguimiento}")
	public String editarSeguimiento(@PathVariable int idSeguimiento, Model model) {
		SeguimientoResponseDto encontrado = servicioSeguimiento.buscarSeguimientoPorId(idSeguimiento);

		SeguimientoRequestDto seguimiento = new SeguimientoRequestDto();
		seguimiento.setIdSeguimiento(encontrado.getIdSeguimiento());
		seguimiento.setIdEntrega(encontrado.getIdEntrega());
		seguimiento.setFechaSeguimiento(encontrado.getFechaSeguimiento());
		seguimiento.setObservaciones(encontrado.getObservaciones());
		seguimiento.setEstado(encontrado.getEstado());

		model.addAttribute("seguimiento", seguimiento);
		return "/seguimiento/formularioseguimiento";
	}

	@PostMapping("/actualizar/{idSeguimiento}")
	public String actualizarSeguimiento(@PathVariable int idSeguimiento,
			@ModelAttribute SeguimientoRequestDto seguimiento) {
		servicioSeguimiento.actualizarSeguimiento(idSeguimiento, seguimiento);
		return "redirect:/seguimiento";
	}

	@PostMapping("/eliminar/{idSeguimiento}")
	public String eliminarSeguimiento(@PathVariable int idSeguimiento) {
		servicioSeguimiento.eliminarSeguimiento(idSeguimiento);
		return "redirect:/seguimiento";
	}

}
