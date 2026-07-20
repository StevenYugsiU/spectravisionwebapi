package com.uisrael.spectravisionwebapi.model.request;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CitaRequestDto {

	private int idCita;
	private int idCliente;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	private LocalTime hora;
	private String tipoCita;
	private String estado;
}
