package com.uisrael.spectravisionwebapi.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.uisrael.spectravisionwebapi.model.response.ClienteResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;
import com.uisrael.spectravisionwebapi.service.IExamenVisualPdfService;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;

@Service
public class ExamenVisualPdfServiceImpl implements IExamenVisualPdfService {

	private final SpringTemplateEngine templateEngine;
	private final IExamenVisualService servicioExamenVisual;

	public ExamenVisualPdfServiceImpl(SpringTemplateEngine templateEngine, IExamenVisualService servicioExamenVisual) {
		this.templateEngine = templateEngine;
		this.servicioExamenVisual = servicioExamenVisual;
	}

	@Override
	public byte[] generarPdf(int idExamen) {
		// Examen Visual -> Historia Clínica -> Cliente
		ExamenVisualResponseDto examen = servicioExamenVisual.buscarExamenVisualPorId(idExamen);
		ClienteResponseDto cliente = examen.getFkHistoriaClinica().getFkCliente();

		SimpleDateFormat formatoFecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy", new Locale("es", "ES"));

		Context contexto = new Context();
		contexto.setVariable("cliente", cliente);
		contexto.setVariable("examen", examen);
		contexto.setVariable("fechaExamen",
				examen.getFechaExamen() != null ? formatoFecha.format(examen.getFechaExamen()) : "");

		String html = templateEngine.process("examenvisual/examenvisualpdf", contexto);

		try (ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withHtmlContent(html, null);
			builder.toStream(salida);
			builder.run();
			return salida.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("No se pudo generar el PDF del examen visual", e);
		}
	}

}
