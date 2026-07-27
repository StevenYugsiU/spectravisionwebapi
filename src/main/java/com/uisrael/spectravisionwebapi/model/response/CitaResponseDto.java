package com.uisrael.spectravisionwebapi.model.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class CitaResponseDto {

	private int idCita;
	private ClienteResponseDto fkCliente;
	private LocalDate fecha;
	private LocalTime hora;
	private String tipoCita;
	private String estado;
}
