package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.ExamenVisualRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;

@Service
public class ExamenVisualServiceImpl implements IExamenVisualService {

	private final WebClient webclient;

	public ExamenVisualServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<ExamenVisualResponseDto> listarExamenesVisuales() {
		return webclient.get().uri("/examenVisual").retrieve()
				.bodyToFlux(ExamenVisualResponseDto.class).collectList().block();
	}

	@Override
	public ExamenVisualResponseDto buscarExamenVisualPorId(int idExamen) {
		return webclient.get().uri("/examenVisual/{idExamen}", idExamen).retrieve()
				.bodyToMono(ExamenVisualResponseDto.class).block();
	}

	@Override
	public void guardarExamenVisual(ExamenVisualRequestDto nuevoExamenVisual) {
		webclient.post().uri("/examenVisual").bodyValue(nuevoExamenVisual).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarExamenVisual(int idExamen, ExamenVisualRequestDto examenVisual) {
		webclient.put().uri("/examenVisual/{idExamen}", idExamen).bodyValue(examenVisual).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void eliminarExamenVisual(int idExamen) {
		webclient.delete().uri("/examenVisual/{idExamen}", idExamen).retrieve()
				.toBodilessEntity().block();
	}

}
