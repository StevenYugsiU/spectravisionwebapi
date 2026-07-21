package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class SeguimientoResponseDto {

	private int idSeguimiento;
	private int idEntrega;
	private Date fechaSeguimiento;
	private String observaciones;
	private String estado;
}
