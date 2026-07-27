package com.uisrael.spectravisionwebapi.service;

import com.uisrael.spectravisionwebapi.model.response.LoginResponseDto;

public interface IAuthService {

	LoginResponseDto login(String usuario, String contrasena);
}
