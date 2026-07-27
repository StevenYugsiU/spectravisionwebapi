package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.RolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.RolResponseDto;

public interface IRolService {

	List<RolResponseDto> listarRoles();

	RolResponseDto buscarRolPorId(int idRol);

	void guardarRol(RolRequestDto nuevoRol);

	void actualizarRol(int idRol, RolRequestDto rol);

	void eliminarRol(int idRol);
}
