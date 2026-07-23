package com.uisrael.spectravisionwebapi.service;

import java.util.List;

import com.uisrael.spectravisionwebapi.model.request.ClienteRequestDto;
import com.uisrael.spectravisionwebapi.model.response.ClienteResponseDto;

public interface IClienteService {

	List<ClienteResponseDto> listarClientes();

	ClienteResponseDto buscarClientePorId(int idCliente);

	void guardarCliente(ClienteRequestDto nuevoCliente);

	void actualizarCliente(int idCliente, ClienteRequestDto cliente);

	void eliminarCliente(int idCliente);
}
