package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.uisrael.spectravisionwebapi.model.request.HistoriaClinicaRequestDto;
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

	@Override
	public HistoriaClinicaResponseDto buscarHistoriaClinicaPorId(int idHistoriaClinica) {
		return webclient.get().uri("/hClinica/{idHistoriaClinica}", idHistoriaClinica).retrieve()
				.bodyToMono(HistoriaClinicaResponseDto.class).block();
	}

	@Override
	public HistoriaClinicaResponseDto buscarHistoriaClinicaPorIdCliente(int idCliente) {
		// El backend responde 500 (no 404) cuando el cliente no tiene historia
		// clínica todavía, así que lo tratamos como "no encontrada".
		try {
			return webclient.get().uri("/hClinica/cliente/{idCliente}", idCliente).retrieve()
					.bodyToMono(HistoriaClinicaResponseDto.class).block();
		} catch (WebClientResponseException ex) {
			return null;
		}
	}

	@Override
	public void guardarHistoriaClinica(HistoriaClinicaRequestDto nuevaHistoriaClinica) {
		webclient.post().uri("/hClinica").bodyValue(nuevaHistoriaClinica).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarHistoriaClinica(int idHistoriaClinica, HistoriaClinicaRequestDto historiaClinica) {
		webclient.put().uri("/hClinica/{idHistoriaClinica}", idHistoriaClinica).bodyValue(historiaClinica).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void eliminarHistoriaClinica(int idHistoriaClinica) {
		webclient.delete().uri("/hClinica/{idHistoriaClinica}", idHistoriaClinica).retrieve()
				.toBodilessEntity().block();
	}

}
