package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.CertificadoRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;

public interface ICertificadoService {

	List<CertificadoResponseDto> listarCertificados();

	CertificadoResponseDto buscarCertificadoPorId(int idCertificado);

	void guardarCertificado(CertificadoRequestDto nuevoCertificado);

	void actualizarCertificado(int idCertificado, CertificadoRequestDto certificado);

	void eliminarCertificado(int idCertificado);
}
