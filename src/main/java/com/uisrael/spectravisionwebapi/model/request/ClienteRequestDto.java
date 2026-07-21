package com.uisrael.spectravisionwebapi.model.request;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ClienteRequestDto {

	private int idCliente;
	private String cedula;
	private String nombres;
	private String apellidos;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaNacimiento;
	private int edad;
	private String ocupacion;
	private String celular;
	private String correo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;
	private Boolean estado;
}
