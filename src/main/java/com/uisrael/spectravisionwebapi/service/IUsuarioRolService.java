package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.UsuarioRolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioRolResponseDto;

public interface IUsuarioRolService {

	List<UsuarioRolResponseDto> listarUsuarioRoles();

	void guardarUsuarioRol(UsuarioRolRequestDto nuevoUsuarioRol);

	void eliminarUsuarioRol(int idUsuarioRol);
}
