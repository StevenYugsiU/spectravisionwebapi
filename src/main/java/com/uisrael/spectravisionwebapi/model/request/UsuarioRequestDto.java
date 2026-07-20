package com.uisrael.spectravisionwebapi.model.request;

import lombok.Data;

@Data
public class UsuarioRequestDto {

	private int idUsuario;
	private String nombres;
	private String apellidos;
	private String usuario;
	private String contrasena;
	private Boolean estado;
}
