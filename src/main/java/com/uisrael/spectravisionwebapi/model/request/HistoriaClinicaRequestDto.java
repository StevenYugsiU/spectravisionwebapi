package com.uisrael.spectravisionwebapi.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class HistoriaClinicaRequestDto {

	private int idHistoriaClinica;
	private int idCliente;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaApertura;
	private String antecedentes;
	private String observacionesGenerales;
	private Boolean estado;
}
