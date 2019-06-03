package pl.decerto.hyperon.demo.dictionary.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	private final BuildProperties buildProperties;

	@Autowired
	public SwaggerConfiguration(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title("Dictionary engine")
						.description("Hyperon demo application")
						.license("Apache 2.0")
						.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
						.version(buildProperties.getVersion())
						.build())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.regex("/(dictionaries|entries).*"))
				.build();
	}
}
