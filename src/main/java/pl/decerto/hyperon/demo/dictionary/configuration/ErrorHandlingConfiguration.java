package pl.decerto.hyperon.demo.dictionary.configuration;

import org.smartparam.engine.core.ParameterValueNotFoundException;
import org.smartparam.engine.core.output.UnknownLevelNameException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import pl.decerto.hyperon.demo.dictionary.dom.AttributeNotFoundException;
import pl.decerto.hyperon.demo.dictionary.endpoint.EntryNotFoundException;
import pl.decerto.hyperon.demo.dictionary.engine.IncorrectDomainPathException;

import java.util.List;
import java.util.Properties;

@Configuration
public class ErrorHandlingConfiguration {

	public static final List<Class<? extends Exception>> NOT_FOUND_EXCEPTIONS = List.of(
			IncorrectDomainPathException.class,
			AttributeNotFoundException.class,
			UnknownLevelNameException.class,
			ParameterValueNotFoundException.class,
			EntryNotFoundException.class
	);

	private static final String SERVER_FAULT = SoapFaultDefinition.SERVER.toString();
	private static final String CLIENT_FAULT = SoapFaultDefinition.CLIENT.toString();

	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver(SoapFaultAnnotationExceptionResolver annotationResolver) {
		SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

		SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
		faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
		exceptionResolver.setDefaultFault(faultDefinition);

		exceptionResolver.setExceptionMappings(getErrorMapping());
		if (annotationResolver != null) {
			// must process exception before default annotation resolver marks it as processed
			exceptionResolver.setOrder(annotationResolver.getOrder() - 1);
		}
		return exceptionResolver;
	}

	private Properties getErrorMapping() {
		Properties mapping = new Properties();
		NOT_FOUND_EXCEPTIONS.forEach(exceptionClass -> mapping.setProperty(exceptionClass.getName(), CLIENT_FAULT));
		mapping.setProperty(Exception.class.getName(), SERVER_FAULT);

		return mapping;
	}

}
