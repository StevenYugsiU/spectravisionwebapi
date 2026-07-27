package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class HistoriaClinicaResponseDto {

	private int idHistoriaClinica;
	private ClienteResponseDto fkCliente;
	private Date fechaApertura;
	private String antecedentes;
	private String observacionesGenerales;
	private Boolean estado;
}
