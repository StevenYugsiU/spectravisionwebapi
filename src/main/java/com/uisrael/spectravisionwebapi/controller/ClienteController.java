package com.uisrael.spectravisionwebapi.controller;

import java.util.Date;
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

import com.uisrael.spectravisionwebapi.model.request.ClienteRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ClienteResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;
import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;
import com.uisrael.spectravisionwebapi.service.IClienteService;
import com.uisrael.spectravisionwebapi.service.IHistoriaClinicaService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private IClienteService servicioCliente;

	@Autowired
	private IHistoriaClinicaService servicioHistoriaClinica;

	@GetMapping
	public String leerPagina(Model model) {
		List<ClienteResponseDto> listaClientes = servicioCliente.listarClientes();
		model.addAttribute("listaClientes", listaClientes);
		return "/cliente/listarclientes";
	}

	@GetMapping("/nuevo")
	public String nuevoCliente(Model model) {
		ClienteRequestDto cliente = new ClienteRequestDto();
		cliente.setFechaRegistro(new Date());
		model.addAttribute("cliente", cliente);
		return "/cliente/formulariocliente";
	}

	@PostMapping("/guardar")
	public String guardarCliente(@ModelAttribute ClienteRequestDto cliente, Model model,
			RedirectAttributes redirectAttributes) {
		if (cliente.getEdad() == null) {
			cliente.setEdad(0);
		}
		try {
			servicioCliente.guardarCliente(cliente);
		} catch (WebClientResponseException ex) {
			model.addAttribute("error", extraerMensajeError(ex, "No se pudo crear el cliente."));
			model.addAttribute("cliente", cliente);
			return "/cliente/formulariocliente";
		}
		redirectAttributes.addFlashAttribute("mensaje", "Cliente creado correctamente.");
		return "redirect:/cliente";
	}

	@GetMapping("/editar/{idCliente}")
	public String editarCliente(@PathVariable int idCliente, Model model, RedirectAttributes redirectAttributes) {
		ClienteResponseDto clienteEncontrado;
		try {
			clienteEncontrado = servicioCliente.buscarClientePorId(idCliente);
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error",
					extraerMensajeError(ex, "No se encontró el cliente solicitado."));
			return "redirect:/cliente";
		}

		ClienteRequestDto cliente = new ClienteRequestDto();
		cliente.setIdCliente(clienteEncontrado.getIdCliente());
		cliente.setCedula(clienteEncontrado.getCedula());
		cliente.setNombres(clienteEncontrado.getNombres());
		cliente.setApellidos(clienteEncontrado.getApellidos());
		cliente.setFechaNacimiento(clienteEncontrado.getFechaNacimiento());
		cliente.setEdad(clienteEncontrado.getEdad());
		cliente.setOcupacion(clienteEncontrado.getOcupacion());
		cliente.setCelular(clienteEncontrado.getCelular());
		cliente.setCorreo(clienteEncontrado.getCorreo());
		cliente.setFechaRegistro(clienteEncontrado.getFechaRegistro());
		cliente.setEstado(clienteEncontrado.getEstado());

		model.addAttribute("cliente", cliente);
		return "/cliente/formulariocliente";
	}

	@PostMapping("/actualizar/{idCliente}")
	public String actualizarCliente(@PathVariable int idCliente, @ModelAttribute ClienteRequestDto cliente,
			Model model, RedirectAttributes redirectAttributes) {
		if (cliente.getEdad() == null) {
			cliente.setEdad(0);
		}
		try {
			servicioCliente.actualizarCliente(idCliente, cliente);
		} catch (WebClientResponseException ex) {
			model.addAttribute("error", extraerMensajeError(ex, "No se pudo actualizar el cliente."));
			model.addAttribute("cliente", cliente);
			return "/cliente/formulariocliente";
		}
		redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado correctamente.");
		return "redirect:/cliente";
	}

	@PostMapping("/eliminar/{idCliente}")
	public String eliminarCliente(@PathVariable int idCliente, RedirectAttributes redirectAttributes) {
		try {
			servicioCliente.eliminarCliente(idCliente);
			redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado correctamente.");
		} catch (WebClientResponseException ex) {
			redirectAttributes.addFlashAttribute("error", extraerMensajeError(ex, "No se pudo eliminar el cliente."));
		}
		return "redirect:/cliente";
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

	@GetMapping("/{idCliente}/historiaclinica")
	public String abrirHistoriaClinica(@PathVariable int idCliente) {
		HistoriaClinicaResponseDto historia = servicioHistoriaClinica.buscarHistoriaClinicaPorIdCliente(idCliente);
		if (historia == null) {
			// El cliente todavía no tiene historia clínica: se deja crearla ya asociada a él.
			return "redirect:/historiaclinica/nuevo?idCliente=" + idCliente;
		}
		return "redirect:/historiaclinica/editar/" + historia.getIdHistoriaClinica();
	}

	@GetMapping("/{idCliente}/examen")
	public String registrarExamen(@PathVariable int idCliente) {
		HistoriaClinicaResponseDto historia = servicioHistoriaClinica.buscarHistoriaClinicaPorIdCliente(idCliente);
		if (historia == null) {
			// No se puede registrar un examen sin una historia clínica primero.
			return "redirect:/historiaclinica/nuevo?idCliente=" + idCliente;
		}
		return "redirect:/examenvisual/nuevo?idHistoria=" + historia.getIdHistoriaClinica();
	}

}
