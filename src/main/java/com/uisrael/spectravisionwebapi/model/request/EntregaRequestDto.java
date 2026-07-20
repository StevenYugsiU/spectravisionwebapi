package com.uisrael.spectravisionwebapi.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EntregaRequestDto {

	private int idEntrega;
	private int idCliente;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEntrega;
	private String observaciones;
	private String estado;
}
