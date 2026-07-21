package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.RolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.RolResponseDto;

public interface IRolService {

	List<RolResponseDto> listarRoles();

	void guardarRol(RolRequestDto nuevoRol);
}
