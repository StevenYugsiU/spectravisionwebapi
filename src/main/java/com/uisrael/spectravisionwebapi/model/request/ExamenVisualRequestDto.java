package com.uisrael.spectravisionwebapi.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ExamenVisualRequestDto {

	private int idExamen;
	private int idHistoria;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaExamen;
	private String ultimoControlVisual;
	private String motivoConsulta;
	private String avOd;
	private String avOi;
	private String sphOd;
	private String cylOd;
	private String ejeOd;
	private String sphOi;
	private String cylOi;
	private String ejeOi;
	private String addValor;
	private String dnp;
	private String altura;
	private String biomicroscopia;
	private String recomentaciones;
	private String proximaConsulta;
	private String diagnostico;
}
