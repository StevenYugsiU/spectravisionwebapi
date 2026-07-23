package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.EntregaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.EntregaResponseDto;
import com.uisrael.spectravisionwebapi.service.IEntregaService;

@Service
public class EntregaServiceImpl implements IEntregaService {

	private final WebClient webclient;

	public EntregaServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<EntregaResponseDto> listarEntregas() {
		return webclient.get().uri("/entrega").retrieve()
				.bodyToFlux(EntregaResponseDto.class).collectList().block();
	}

	@Override
	public EntregaResponseDto buscarEntregaPorId(int idEntrega) {
		return webclient.get().uri("/entrega/{idEntrega}", idEntrega).retrieve()
				.bodyToMono(EntregaResponseDto.class).block();
	}

	@Override
	public void guardarEntrega(EntregaRequestDto nuevaEntrega) {
		webclient.post().uri("/entrega").bodyValue(nuevaEntrega).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarEntrega(int idEntrega, EntregaRequestDto entrega) {
		webclient.put().uri("/entrega/{idEntrega}", idEntrega).bodyValue(entrega).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void eliminarEntrega(int idEntrega) {
		webclient.delete().uri("/entrega/{idEntrega}", idEntrega).retrieve()
				.toBodilessEntity().block();
	}

}
