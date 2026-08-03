package com.uisrael.spectravisionwebapi.service;

import com.uisrael.spectravisionwebapi.model.response.LoginResponseDto;

public interface IAuthService {

	LoginResponseDto login(String usuario, String contrasena);

	void solicitarRecuperacion(String usuario, String resetPasswordUrl);

	void restablecerContrasena(String token, String nuevaContrasena);
}
