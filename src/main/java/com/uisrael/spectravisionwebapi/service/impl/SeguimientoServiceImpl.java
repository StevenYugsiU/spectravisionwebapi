package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.SeguimientoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.SeguimientoResponseDto;
import com.uisrael.spectravisionwebapi.service.ISeguimientoService;

@Service
public class SeguimientoServiceImpl implements ISeguimientoService {

	private final WebClient webclient;

	public SeguimientoServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<SeguimientoResponseDto> listarSeguimientos() {
		return webclient.get().uri("/seguimiento").retrieve()
				.bodyToFlux(SeguimientoResponseDto.class).collectList().block();
	}

	@Override
	public SeguimientoResponseDto buscarSeguimientoPorId(int idSeguimiento) {
		return webclient.get().uri("/seguimiento/{idSeguimiento}", idSeguimiento).retrieve()
				.bodyToMono(SeguimientoResponseDto.class).block();
	}

	@Override
	public void guardarSeguimiento(SeguimientoRequestDto nuevoSeguimiento) {
		webclient.post().uri("/seguimiento").bodyValue(nuevoSeguimiento).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarSeguimiento(int idSeguimiento, SeguimientoRequestDto seguimiento) {
		webclient.put().uri("/seguimiento/{idSeguimiento}", idSeguimiento).bodyValue(seguimiento).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void eliminarSeguimiento(int idSeguimiento) {
		webclient.delete().uri("/seguimiento/{idSeguimiento}", idSeguimiento).retrieve()
				.toBodilessEntity().block();
	}

}
