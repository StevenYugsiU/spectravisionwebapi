package com.uisrael.spectravisionwebapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.request.ClienteRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ClienteResponseDto;
import com.uisrael.spectravisionwebapi.service.IClienteService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private IClienteService servicioCliente;

	@GetMapping
	public String leerPagina(Model model) {
		List<ClienteResponseDto> listaClientes = servicioCliente.listarClientes();
		model.addAttribute("listaclientes", listaClientes);
		return "/cliente/listarclientes";
	}

	@GetMapping("/nuevo")
	public String nuevoCliente(Model model) {
		model.addAttribute("cliente", new ClienteRequestDto());
		return "/cliente/formulariocliente";
	}

	@PostMapping("/guardar")
	public String guardarCliente(@ModelAttribute ClienteRequestDto cliente) {
		servicioCliente.guardarCliente(cliente);
		return "redirect:/cliente";
	}

}
