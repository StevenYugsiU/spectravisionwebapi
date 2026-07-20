package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class ExamenVisualResponseDto {

	private int idExamen;
	private int idHistoria;
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
