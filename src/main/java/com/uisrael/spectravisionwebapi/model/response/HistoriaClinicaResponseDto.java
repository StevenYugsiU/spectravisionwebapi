package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class HistoriaClinicaResponseDto {

	private int idHistoriaClinica;
	private int idCliente;
	private Date fechaApertura;
	private String antecedentes;
	private String observacionesGenerales;
	private Boolean estado;
}
