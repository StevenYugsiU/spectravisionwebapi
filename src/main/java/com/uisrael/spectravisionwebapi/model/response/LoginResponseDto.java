package com.uisrael.spectravisionwebapi.model.response;

import java.util.List;

import lombok.Data;

@Data
public class LoginResponseDto {

	private int idUsuario;
	private String usuario;
	private String nombres;
	private String apellidos;
	private List<String> roles;
}
