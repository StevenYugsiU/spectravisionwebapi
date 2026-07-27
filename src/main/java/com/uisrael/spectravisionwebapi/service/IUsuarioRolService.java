package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.UsuarioRolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioRolResponseDto;

public interface IUsuarioRolService {

	List<UsuarioRolResponseDto> listarUsuarioRoles();

	UsuarioRolResponseDto buscarUsuarioRolPorId(int idUsuarioRol);

	void guardarUsuarioRol(UsuarioRolRequestDto nuevoUsuarioRol);

	void actualizarUsuarioRol(int idUsuarioRol, UsuarioRolRequestDto usuarioRol);

	void eliminarUsuarioRol(int idUsuarioRol);
}
