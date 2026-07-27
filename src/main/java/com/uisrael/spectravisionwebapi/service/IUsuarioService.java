package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.UsuarioRequestDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioResponseDto;

public interface IUsuarioService {

	List<UsuarioResponseDto> listarUsuarios();

	UsuarioResponseDto buscarUsuarioPorId(int idUsuario);

	void guardarUsuario(UsuarioRequestDto nuevoUsuario);

	void actualizarUsuario(int idUsuario, UsuarioRequestDto usuario);

	void eliminarUsuario(int idUsuario);
}
