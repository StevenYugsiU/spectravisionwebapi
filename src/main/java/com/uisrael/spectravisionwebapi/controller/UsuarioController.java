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

import com.uisrael.spectravisionwebapi.model.request.UsuarioRequestDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioResponseDto;
import com.uisrael.spectravisionwebapi.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private IUsuarioService servicioUsuario;

	@GetMapping
	public String leerPagina(Model model) {
		List<UsuarioResponseDto> listaUsuarios = servicioUsuario.listarUsuarios();
		model.addAttribute("listausuarios", listaUsuarios);
		return "/usuario/listarusuarios";
	}

	@GetMapping("/nuevo")
	public String nuevoUsuario(Model model) {
		model.addAttribute("usuario", new UsuarioRequestDto());
		return "/usuario/formulariousuario";
	}

	@PostMapping("/guardar")
	public String guardarUsuario(@ModelAttribute UsuarioRequestDto usuario) {
		servicioUsuario.guardarUsuario(usuario);
		return "redirect:/usuario";
	}

	@GetMapping("/editar/{idUsuario}")
	public String editarUsuario(@PathVariable int idUsuario, Model model) {
		UsuarioResponseDto encontrado = servicioUsuario.buscarUsuarioPorId(idUsuario);

		UsuarioRequestDto usuario = new UsuarioRequestDto();
		usuario.setIdUsuario(encontrado.getIdUsuario());
		usuario.setNombres(encontrado.getNombres());
		usuario.setApellidos(encontrado.getApellidos());
		usuario.setUsuario(encontrado.getUsuario());
		usuario.setEstado(encontrado.getEstado());

		model.addAttribute("usuario", usuario);
		return "/usuario/formulariousuario";
	}

	@PostMapping("/actualizar/{idUsuario}")
	public String actualizarUsuario(@PathVariable int idUsuario, @ModelAttribute UsuarioRequestDto usuario) {
		servicioUsuario.actualizarUsuario(idUsuario, usuario);
		return "redirect:/usuario";
	}

	@PostMapping("/eliminar/{idUsuario}")
	public String eliminarUsuario(@PathVariable int idUsuario) {
		servicioUsuario.eliminarUsuario(idUsuario);
		return "redirect:/usuario";
	}

}
