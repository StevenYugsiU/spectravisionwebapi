package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.response.SeguimientoResponseDto;

public interface ISeguimientoService {

	List<SeguimientoResponseDto> listarSeguimientos();
}
