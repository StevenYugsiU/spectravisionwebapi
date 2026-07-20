package com.uisrael.spectravisionwebapi.model.response;

import java.time.LocalTime;
import java.util.Date;

import lombok.Data;

@Data
public class CitaResponseDto {

	private int idCita;
	private int idCliente;
	private Date fecha;
	private LocalTime hora;
	private String tipoCita;
	private String estado;
}
