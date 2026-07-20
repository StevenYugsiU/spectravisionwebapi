package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.response.UsuarioResponseDto;

public interface IUsuarioService {

	List<UsuarioResponseDto> listarUsuarios();
}
