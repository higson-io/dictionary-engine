package pl.decerto.hyperon.demo.dictionary.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.annotation.PostConstruct;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {

	public static final String NAMESPACE_URI = "http://hyperon.io/demos/dictionary-engine";

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		var servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, "/ws/*");
	}

	@Bean(name = "dictionaries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema dictionariesSchema) {
		var definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("DictionariesPort");
		definition.setLocationUri("/ws");
		definition.setTargetNamespace(NAMESPACE_URI);
		definition.setSchema(dictionariesSchema);
		return definition;
	}

	@Bean
	public XsdSchema dictionariesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("dictionaries.xsd"));
	}

	@PostConstruct
	private void configureSoapMetaFactory() {
		System.setProperty("javax.xml.soap.SAAJMetaFactory", "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
	}
}
