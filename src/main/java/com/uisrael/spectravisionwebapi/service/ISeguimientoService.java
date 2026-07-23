package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.SeguimientoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.SeguimientoResponseDto;

public interface ISeguimientoService {

	List<SeguimientoResponseDto> listarSeguimientos();

	SeguimientoResponseDto buscarSeguimientoPorId(int idSeguimiento);

	void guardarSeguimiento(SeguimientoRequestDto nuevoSeguimiento);

	void actualizarSeguimiento(int idSeguimiento, SeguimientoRequestDto seguimiento);

	void eliminarSeguimiento(int idSeguimiento);
}
