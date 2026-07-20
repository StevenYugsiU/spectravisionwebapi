package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.CitaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CitaResponseDto;
import com.uisrael.spectravisionwebapi.service.ICitaService;

@Service
public class CitaServiceImpl implements ICitaService {

	private final WebClient webclient;

	public CitaServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<CitaResponseDto> listarCitas() {
		return webclient.get().uri("/cita").retrieve()
				.bodyToFlux(CitaResponseDto.class).collectList().block();
	}

	@Override
	public void guardarCita(CitaRequestDto nuevaCita) {
		webclient.post().uri("/cita").bodyValue(nuevaCita).retrieve()
				.toBodilessEntity().block();
	}

}
