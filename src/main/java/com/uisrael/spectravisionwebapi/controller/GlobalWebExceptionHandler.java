package com.uisrael.spectravisionwebapi.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.ModelAndView;

import com.uisrael.spectravisionwebapi.model.response.ErrorResponseDto;

@ControllerAdvice
public class GlobalWebExceptionHandler {

	@ExceptionHandler(WebClientResponseException.class)
	public ModelAndView manejarErrorBackend(WebClientResponseException ex) {
		String mensaje = extraerMensaje(ex);

		ModelAndView mav = new ModelAndView("error-negocio");
		mav.addObject("mensaje", mensaje);
		return mav;
	}

	private String extraerMensaje(WebClientResponseException ex) {
		try {
			ErrorResponseDto error = ex.getResponseBodyAs(ErrorResponseDto.class);
			if (error != null && error.getMessage() != null) {
				return error.getMessage();
			}
		} catch (Exception ignorada) {
		}
		return "No se pudo completar la operacion. Intente nuevamente.";
	}

}
