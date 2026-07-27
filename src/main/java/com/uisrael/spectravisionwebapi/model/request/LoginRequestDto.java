package com.uisrael.spectravisionwebapi.model.request;

import lombok.Data;

@Data
public class LoginRequestDto {

	private String usuario;
	private String contrasena;
}
