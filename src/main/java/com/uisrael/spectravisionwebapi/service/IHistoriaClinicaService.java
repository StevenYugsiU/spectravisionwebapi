package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;

public interface IHistoriaClinicaService {

	List<HistoriaClinicaResponseDto> listarHistoriasClinicas();
}
