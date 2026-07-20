package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class CertificadoResponseDto {

	private int idCertificado;
	private int idExamen;
	private Date fechaGeneracion;
	private String observaciones;
}
