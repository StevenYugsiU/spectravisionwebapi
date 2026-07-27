package com.uisrael.spectravisionwebapi.model.request;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class CitaRequestDto {

	private int idCita;
	private int idCliente;
	private LocalDate fecha;
	private LocalTime hora;
	private String tipoCita;
	private String estado;
}
