package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;

public interface ICertificadoService {

	List<CertificadoResponseDto> listarCertificados();
}
