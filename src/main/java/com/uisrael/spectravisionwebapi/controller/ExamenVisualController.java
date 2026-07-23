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

import com.uisrael.spectravisionwebapi.model.request.ExamenVisualRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;

@Controller
@RequestMapping("/examenvisual")
public class ExamenVisualController {

	@Autowired
	private IExamenVisualService servicioExamenVisual;

	@GetMapping
	public String leerPagina(Model model) {
		List<ExamenVisualResponseDto> listaExamenesVisuales = servicioExamenVisual.listarExamenesVisuales();
		model.addAttribute("listaexamenesvisuales", listaExamenesVisuales);
		return "/examenvisual/listarexamenesvisuales";
	}

	@GetMapping("/nuevo")
	public String nuevoExamenVisual(@RequestParam(required = false) Integer idHistoria, Model model) {
		ExamenVisualRequestDto examenvisual = new ExamenVisualRequestDto();
		if (idHistoria != null) {
			examenvisual.setIdHistoria(idHistoria);
		}
		model.addAttribute("examenvisual", examenvisual);
		return "/examenvisual/formularioexamenvisual";
	}

	@PostMapping("/guardar")
	public String guardarExamenVisual(@ModelAttribute ExamenVisualRequestDto examenvisual) {
		servicioExamenVisual.guardarExamenVisual(examenvisual);
		return "redirect:/examenvisual";
	}

	@GetMapping("/editar/{idExamen}")
	public String editarExamenVisual(@PathVariable int idExamen, Model model) {
		ExamenVisualResponseDto encontrado = servicioExamenVisual.buscarExamenVisualPorId(idExamen);

		ExamenVisualRequestDto examenvisual = new ExamenVisualRequestDto();
		examenvisual.setIdExamen(encontrado.getIdExamen());
		examenvisual.setIdHistoria(encontrado.getIdHistoria());
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
		return "/examenvisual/formularioexamenvisual";
	}

	@PostMapping("/actualizar/{idExamen}")
	public String actualizarExamenVisual(@PathVariable int idExamen,
			@ModelAttribute ExamenVisualRequestDto examenvisual) {
		servicioExamenVisual.actualizarExamenVisual(idExamen, examenvisual);
		return "redirect:/examenvisual";
	}

	@PostMapping("/eliminar/{idExamen}")
	public String eliminarExamenVisual(@PathVariable int idExamen) {
		servicioExamenVisual.eliminarExamenVisual(idExamen);
		return "redirect:/examenvisual";
	}

}
