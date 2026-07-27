package com.uisrael.spectravisionwebapi;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpectravisionwebapiApplication {

	public static void main(String[] args) {
		// El backend guarda algunas fechas como medianoche UTC exacta; con la JVM
		// en hora local (UTC-5) esas fechas se mostraban un día antes en los
		// formularios de editar. Forzamos UTC para que todas las fechas se
		// interpreten y formateen de manera consistente.
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(SpectravisionwebapiApplication.class, args);
	}

}
