package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.ClienteRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ClienteResponseDto;
import com.uisrael.spectravisionwebapi.service.IClienteService;

@Service
public class ClienteServiceImpl implements IClienteService {

	private final WebClient webclient;

	public ClienteServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<ClienteResponseDto> listarClientes() {
		return webclient.get().uri("/cliente").retrieve()
				.bodyToFlux(ClienteResponseDto.class).collectList().block();
	}

	@Override
	public ClienteResponseDto buscarClientePorId(int idCliente) {
		return webclient.get().uri("/cliente/{idCliente}", idCliente).retrieve()
				.bodyToMono(ClienteResponseDto.class).block();
	}

	@Override
	public void guardarCliente(ClienteRequestDto nuevoCliente) {
		webclient.post().uri("/cliente").bodyValue(nuevoCliente).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarCliente(int idCliente, ClienteRequestDto cliente) {
		webclient.put().uri("/cliente/{idCliente}", idCliente).bodyValue(cliente).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void eliminarCliente(int idCliente) {
		webclient.delete().uri("/cliente/{idCliente}", idCliente).retrieve()
				.toBodilessEntity().block();
	}

}
