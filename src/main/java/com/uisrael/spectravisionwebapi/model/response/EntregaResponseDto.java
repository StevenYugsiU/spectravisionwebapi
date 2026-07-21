package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class EntregaResponseDto {

	private int idEntrega;
	private int idCliente;
	private Date fechaEntrega;
	private String observaciones;
	private String estado;
}
