package com.uisrael.spectravisionwebapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.LoginRequestDto;
import com.uisrael.spectravisionwebapi.model.request.OlvideContrasenaRequestDto;
import com.uisrael.spectravisionwebapi.model.request.RestablecerContrasenaRequestDto;
import com.uisrael.spectravisionwebapi.model.response.LoginResponseDto;
import com.uisrael.spectravisionwebapi.service.IAuthService;

@Service
public class AuthServiceImpl implements IAuthService {

	private final WebClient webclient;

	public AuthServiceImpl(WebClient webclient) {
		this.webclient = webclient;
	}

	@Override
	public LoginResponseDto login(String usuario, String contrasena) {
		LoginRequestDto request = new LoginRequestDto();
		request.setUsuario(usuario);
		request.setContrasena(contrasena);

		return webclient.post().uri("/auth/login").bodyValue(request).retrieve()
				.bodyToMono(LoginResponseDto.class).block();
	}

	@Override
	public void solicitarRecuperacion(String usuario) {
		OlvideContrasenaRequestDto request = new OlvideContrasenaRequestDto();
		request.setUsuario(usuario);

		webclient.post().uri("/auth/olvide-contrasena").bodyValue(request).retrieve()
				.toBodilessEntity().block();
	}

	@Override
	public void restablecerContrasena(String token, String nuevaContrasena) {
		RestablecerContrasenaRequestDto request = new RestablecerContrasenaRequestDto();
		request.setToken(token);
		request.setNuevaContrasena(nuevaContrasena);

		webclient.post().uri("/auth/restablecer-contrasena").bodyValue(request).retrieve()
				.toBodilessEntity().block();
	}

}
