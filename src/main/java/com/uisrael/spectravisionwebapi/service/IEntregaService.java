package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.EntregaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.EntregaResponseDto;

public interface IEntregaService {

	List<EntregaResponseDto> listarEntregas();

	void guardarEntrega(EntregaRequestDto nuevaEntrega);
}
