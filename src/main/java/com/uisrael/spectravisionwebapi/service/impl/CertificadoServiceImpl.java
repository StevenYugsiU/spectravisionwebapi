package com.uisrael.spectravisionwebapi.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.CertificadoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;
import com.uisrael.spectravisionwebapi.service.ICertificadoService;

@Service
public class CertificadoServiceImpl implements ICertificadoService {

	private final WebClient webclient;

	public CertificadoServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public List<CertificadoResponseDto> listarCertificados() {
		return webclient.get().uri("/certificado").retrieve()
				.bodyToFlux(CertificadoResponseDto.class).collectList().block();
	}

	@Override
	public void guardarCertificado(CertificadoRequestDto nuevoCertificado) {
		webclient.post().uri("/certificado").bodyValue(nuevoCertificado).retrieve()
				.toBodilessEntity().block();
	}

}
