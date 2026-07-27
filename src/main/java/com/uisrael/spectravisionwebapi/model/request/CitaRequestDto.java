package com.uisrael.spectravisionwebapi.model.request;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CitaRequestDto {

	private int idCita;
	private int idCliente;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fecha;
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime hora;
	private String tipoCita;
	private String estado;
}
