package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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

}
