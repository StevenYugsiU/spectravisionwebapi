package com.uisrael.spectravisionwebapi.model.request;

import lombok.Data;

@Data
public class RestablecerContrasenaRequestDto {

	private String token;
	private String nuevaContrasena;
}
