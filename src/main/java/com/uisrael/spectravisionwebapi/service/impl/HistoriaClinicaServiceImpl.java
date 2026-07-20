package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;
import com.uisrael.spectravisionwebapi.service.IHistoriaClinicaService;

@Service
public class HistoriaClinicaServiceImpl implements IHistoriaClinicaService {

	private final WebClient webclient;

	public HistoriaClinicaServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<HistoriaClinicaResponseDto> listarHistoriasClinicas() {
		return webclient.get().uri("/hClinica").retrieve()
				.bodyToFlux(HistoriaClinicaResponseDto.class).collectList().block();
	}

}
