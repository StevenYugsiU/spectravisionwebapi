package com.uisrael.spectravisionwebapi.model.response;

import lombok.Data;

@Data
public class UsuarioResponseDto {

	private int idUsuario;
	private String nombres;
	private String apellidos;
	private String usuario;
	private String contrasena;
	private Boolean estado;
}
