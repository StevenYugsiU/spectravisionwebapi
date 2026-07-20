package com.uisrael.spectravisionwebapi.model.response;

import java.util.Date;

import lombok.Data;

@Data
public class ClienteResponseDto {

	private int idCliente;
	private String cedula;
	private String nombres;
	private String apellidos;
	private Date fechaNacimiento;
	private int edad;
	private String ocupacion;
	private String celular;
	private String correo;
	private Date fechaRegistro;
	private Boolean estado;
}
