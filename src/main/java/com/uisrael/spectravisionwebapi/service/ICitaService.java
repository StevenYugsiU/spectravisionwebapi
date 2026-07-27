package com.uisrael.spectravisionwebapi.service;

import java.time.LocalDate;
import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.CitaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.CitaResponseDto;

public interface ICitaService {

	List<CitaResponseDto> listarCitas();

	List<CitaResponseDto> buscarCitasPorFecha(LocalDate fecha);

	CitaResponseDto buscarCitaPorId(int idCita);

	void guardarCita(CitaRequestDto nuevaCita);

	void actualizarCita(int idCita, CitaRequestDto cita);

	void cancelarCita(int idCita);

	void eliminarCita(int idCita);
}
