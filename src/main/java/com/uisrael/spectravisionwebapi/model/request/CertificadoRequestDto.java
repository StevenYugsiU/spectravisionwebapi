package com.uisrael.spectravisionwebapi.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CertificadoRequestDto {

	private int idCertificado;
	private int idExamen;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaGeneracion;
	private String observaciones;
}
