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
	public CertificadoResponseDto buscarCertificadoPorId(int idCertificado) {
		return webclient.get().uri("/certificado/{idCertificado}", idCertificado).retrieve()
				.bodyToMono(CertificadoResponseDto.class).block();
	}

	@Override
	public void guardarCertificado(CertificadoRequestDto nuevoCertificado) {
		webclient.post().uri("/certificado").bodyValue(nuevoCertificado).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void actualizarCertificado(int idCertificado, CertificadoRequestDto certificado) {
		webclient.put().uri("/certificado/{idCertificado}", idCertificado).bodyValue(certificado).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void eliminarCertificado(int idCertificado) {
		webclient.delete().uri("/certificado/{idCertificado}", idCertificado).retrieve()
				.toBodilessEntity().block();
	}

}
