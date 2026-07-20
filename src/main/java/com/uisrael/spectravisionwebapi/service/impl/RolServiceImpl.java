package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.response.RolResponseDto;
import com.uisrael.spectravisionwebapi.service.IRolService;

@Service
public class RolServiceImpl implements IRolService {

	private final WebClient webclient;

	public RolServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<RolResponseDto> listarRoles() {
		return webclient.get().uri("/rol").retrieve()
				.bodyToFlux(RolResponseDto.class).collectList().block();
	}

}
