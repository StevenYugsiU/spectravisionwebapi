package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.HistoriaClinicaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;

public interface IHistoriaClinicaService {

	List<HistoriaClinicaResponseDto> listarHistoriasClinicas();

	HistoriaClinicaResponseDto buscarHistoriaClinicaPorId(int idHistoriaClinica);

	HistoriaClinicaResponseDto buscarHistoriaClinicaPorIdCliente(int idCliente);

	void guardarHistoriaClinica(HistoriaClinicaRequestDto nuevaHistoriaClinica);

	void actualizarHistoriaClinica(int idHistoriaClinica, HistoriaClinicaRequestDto historiaClinica);

	void eliminarHistoriaClinica(int idHistoriaClinica);
}
