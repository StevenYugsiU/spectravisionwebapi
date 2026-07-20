package com.uisrael.spectravisionwebapi.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SeguimientoRequestDto {

	private int idSeguimiento;
	private int idEntrega;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaSeguimiento;
	private String observaciones;
	private String estado;
}
