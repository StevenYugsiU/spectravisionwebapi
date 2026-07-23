package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.ExamenVisualRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;

public interface IExamenVisualService {

	List<ExamenVisualResponseDto> listarExamenesVisuales();

	ExamenVisualResponseDto buscarExamenVisualPorId(int idExamen);

	void guardarExamenVisual(ExamenVisualRequestDto nuevoExamenVisual);

	void actualizarExamenVisual(int idExamen, ExamenVisualRequestDto examenVisual);

	void eliminarExamenVisual(int idExamen);
}
