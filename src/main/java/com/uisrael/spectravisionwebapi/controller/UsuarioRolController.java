package com.uisrael.spectravisionwebapi.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uisrael.spectravisionwebapi.model.request.UsuarioRolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.RolResponseDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioResponseDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioRolResponseDto;
import com.uisrael.spectravisionwebapi.service.IRolService;
import com.uisrael.spectravisionwebapi.service.IUsuarioRolService;
import com.uisrael.spectravisionwebapi.service.IUsuarioService;

@Controller
@RequestMapping("/usuariorol")
public class UsuarioRolController {

	@Autowired
	private IUsuarioRolService servicioUsuarioRol;

	@Autowired
	private IUsuarioService servicioUsuario;

	@Autowired
	private IRolService servicioRol;

	@GetMapping
	public String leerPagina(Model model) {
		List<UsuarioRolResponseDto> listaUsuarioRoles = servicioUsuarioRol.listarUsuarioRoles();
		model.addAttribute("listaUsuarioRoles", listaUsuarioRoles);

		Map<Integer, String> nombresUsuarios = servicioUsuario.listarUsuarios().stream()
				.collect(Collectors.toMap(UsuarioResponseDto::getIdUsuario,
						u -> u.getNombres() + " " + u.getApellidos()));
		Map<Integer, String> nombresRoles = servicioRol.listarRoles().stream()
				.collect(Collectors.toMap(RolResponseDto::getIdRol, RolResponseDto::getNombre));
		model.addAttribute("nombresUsuarios", nombresUsuarios);
		model.addAttribute("nombresRoles", nombresRoles);

		return "/usuariorol/listarusuarioroles";
	}

	@GetMapping("/nuevo")
	public String nuevoUsuarioRol(Model model) {
		model.addAttribute("usuariorol", new UsuarioRolRequestDto());
		model.addAttribute("listaUsuarios", servicioUsuario.listarUsuarios());
		model.addAttribute("listaRoles", servicioRol.listarRoles());
		return "/usuariorol/formulariousuariorol";
	}

	@PostMapping("/guardar")
	public String guardarUsuarioRol(@ModelAttribute UsuarioRolRequestDto usuariorol) {
		servicioUsuarioRol.guardarUsuarioRol(usuariorol);
		return "redirect:/usuariorol";
	}

	@GetMapping("/editar/{idUsuarioRol}")
	public String editarUsuarioRol(@PathVariable int idUsuarioRol, Model model) {
		UsuarioRolResponseDto encontrado = servicioUsuarioRol.buscarUsuarioRolPorId(idUsuarioRol);

		UsuarioRolRequestDto usuariorol = new UsuarioRolRequestDto();
		usuariorol.setIdUsuarioRol(encontrado.getIdUsuarioRol());
		usuariorol.setIdUsuario(encontrado.getIdUsuario());
		usuariorol.setIdRol(encontrado.getIdRol());

		model.addAttribute("usuariorol", usuariorol);
		model.addAttribute("listaUsuarios", servicioUsuario.listarUsuarios());
		model.addAttribute("listaRoles", servicioRol.listarRoles());
		return "/usuariorol/formulariousuariorol";
	}

	@PostMapping("/actualizar/{idUsuarioRol}")
	public String actualizarUsuarioRol(@PathVariable int idUsuarioRol,
			@ModelAttribute UsuarioRolRequestDto usuariorol) {
		servicioUsuarioRol.actualizarUsuarioRol(idUsuarioRol, usuariorol);
		return "redirect:/usuariorol";
	}

	@PostMapping("/eliminar/{idUsuarioRol}")
	public String eliminarUsuarioRol(@PathVariable int idUsuarioRol) {
		servicioUsuarioRol.eliminarUsuarioRol(idUsuarioRol);
		return "redirect:/usuariorol";
	}

}
