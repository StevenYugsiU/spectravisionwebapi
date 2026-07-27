package com.uisrael.spectravisionwebapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uisrael.spectravisionwebapi.model.request.LoginRequestDto;
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

}
