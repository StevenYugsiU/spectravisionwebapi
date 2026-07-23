package com.uisrael.spectravisionwebapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String leerPagina() {
		return "/login/login";
	}

}
