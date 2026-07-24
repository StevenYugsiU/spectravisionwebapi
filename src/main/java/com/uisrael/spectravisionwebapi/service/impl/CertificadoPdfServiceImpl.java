package com.uisrael.spectravisionwebapi.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import com.uisrael.spectravisionwebapi.model.response.CertificadoResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ClienteResponseDto;
import com.uisrael.spectravisionwebapi.model.response.ExamenVisualResponseDto;
import com.uisrael.spectravisionwebapi.model.response.HistoriaClinicaResponseDto;
import com.uisrael.spectravisionwebapi.service.ICertificadoPdfService;
import com.uisrael.spectravisionwebapi.service.ICertificadoService;
import com.uisrael.spectravisionwebapi.service.IClienteService;
import com.uisrael.spectravisionwebapi.service.IExamenVisualService;
import com.uisrael.spectravisionwebapi.service.IHistoriaClinicaService;

@Service
public class CertificadoPdfServiceImpl implements ICertificadoPdfService {

	private final SpringTemplateEngine templateEngine;
	private final ICertificadoService servicioCertificado;
	private final IExamenVisualService servicioExamenVisual;
	private final IHistoriaClinicaService servicioHistoriaClinica;
	private final IClienteService servicioCliente;

	public CertificadoPdfServiceImpl(SpringTemplateEngine templateEngine, ICertificadoService servicioCertificado,
			IExamenVisualService servicioExamenVisual, IHistoriaClinicaService servicioHistoriaClinica,
			IClienteService servicioCliente) {
		this.templateEngine = templateEngine;
		this.servicioCertificado = servicioCertificado;
		this.servicioExamenVisual = servicioExamenVisual;
		this.servicioHistoriaClinica = servicioHistoriaClinica;
		this.servicioCliente = servicioCliente;
	}

	@Override
	public byte[] generarPdf(int idCertificado) {
		// Certificado -> Examen Visual -> Historia Clínica -> Cliente
		CertificadoResponseDto certificado = servicioCertificado.buscarCertificadoPorId(idCertificado);
		ExamenVisualResponseDto examen = servicioExamenVisual.buscarExamenVisualPorId(certificado.getIdExamen());
		HistoriaClinicaResponseDto historia = servicioHistoriaClinica
				.buscarHistoriaClinicaPorId(examen.getIdHistoria());
		ClienteResponseDto cliente = servicioCliente.buscarClientePorId(historia.getIdCliente());

		SimpleDateFormat formatoFecha = new SimpleDateFormat("d 'de' MMMM 'del' yyyy", new Locale("es", "ES"));

		Context contexto = new Context();
		contexto.setVariable("cliente", cliente);
		contexto.setVariable("examen", examen);
		contexto.setVariable("certificado", certificado);
		contexto.setVariable("fechaGeneracion",
				certificado.getFechaGeneracion() != null ? formatoFecha.format(certificado.getFechaGeneracion())
						: "");

		String html = templateEngine.process("certificado/certificadopdf", contexto);

		try (ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
			PdfRendererBuilder builder = new PdfRendererBuilder();
			builder.useFastMode();
			builder.withHtmlContent(html, null);
			builder.toStream(salida);
			builder.run();
			return salida.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("No se pudo generar el PDF del certificado", e);
		}
	}

}
