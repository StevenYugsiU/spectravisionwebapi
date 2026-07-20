package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.ExamenVisualRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;

public interface IExamenVisualService {

	List<ExamenVisualResponseDto> listarExamenesVisuales();

	void guardarExamenVisual(ExamenVisualRequestDto nuevoExamenVisual);
}
