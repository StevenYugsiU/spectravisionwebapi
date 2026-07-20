package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.UsuarioRolRequestDto;
import com.uisrael.spectravisionwebapi.model.response.UsuarioRolResponseDto;
import com.uisrael.spectravisionwebapi.service.IUsuarioRolService;

@Service
public class UsuarioRolServiceImpl implements IUsuarioRolService {

	private final WebClient webclient;

	public UsuarioRolServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<UsuarioRolResponseDto> listarUsuarioRoles() {
		return webclient.get().uri("/usuarioRol").retrieve()
				.bodyToFlux(UsuarioRolResponseDto.class).collectList().block();
	}

	@Override
	public void guardarUsuarioRol(UsuarioRolRequestDto nuevoUsuarioRol) {
		webclient.post().uri("/usuarioRol").bodyValue(nuevoUsuarioRol).retrieve()
				.toBodilessEntity().block();
	}

}
