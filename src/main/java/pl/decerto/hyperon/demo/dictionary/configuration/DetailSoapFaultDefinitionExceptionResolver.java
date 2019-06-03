package pl.decerto.hyperon.demo.dictionary.configuration;

import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import javax.xml.namespace.QName;

import static pl.decerto.hyperon.demo.dictionary.configuration.ErrorHandlingConfiguration.NOT_FOUND_EXCEPTIONS;

public class DetailSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

	private static final QName CODE = new QName("statusCode");
	private static final QName MESSAGE = new QName("message");
	private static final String NOT_FOUND = "NOT_FOUND";
	private static final String NOT_FOUND_MESSAGE = "Requested item was not found";

	@Override
	protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
		logger.warn("SOAP Request processing error", ex);
		for (Class<? extends Exception> exClass: NOT_FOUND_EXCEPTIONS) {
			if (exClass.isInstance(ex)) {
				var detail = fault.addFaultDetail();
				detail.addFaultDetailElement(CODE).addText(NOT_FOUND);
				detail.addFaultDetailElement(MESSAGE).addText(NOT_FOUND_MESSAGE);
				return;
			}
		}
	}
}
