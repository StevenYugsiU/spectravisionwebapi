package com.uisrael.spectravisionwebapi.model.request;

import lombok.Data;

@Data
public class OlvideContrasenaRequestDto {

	private String usuario;
	private String resetPasswordUrl;
}
