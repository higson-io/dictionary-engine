package pl.decerto.hyperon.demo.dictionary.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.decerto.hyperon.runtime.core.HyperonEngine;
import pl.decerto.hyperon.runtime.core.HyperonEngineFactory;
import pl.decerto.hyperon.runtime.sql.DialectRegistry;
import pl.decerto.hyperon.runtime.sql.DialectTemplate;

import javax.sql.DataSource;

@Configuration
public class HyperonIntegrationConfiguration {

	@Bean
	public HyperonEngine getHyperonEngine(HyperonEngineFactory hyperonEngineFactory) {
		return hyperonEngineFactory.create();
	}

	@Bean
	public HyperonEngineFactory getHyperonEngineFactory(
			DataSource dataSource,
			@Value("${hyperon.dev.mode:false}") boolean devMode,
			@Value("${hyperon.dev.user:}") String devUser) {

		var hyperonEngineFactory = new HyperonEngineFactory(dataSource);

		if (devMode) {
			hyperonEngineFactory.setDeveloperMode(true);
			hyperonEngineFactory.setUsername(devUser);
		}
		return new HyperonEngineFactory(dataSource);
	}

	@Bean(destroyMethod = "close")
	public HikariDataSource getDataSource(
			DialectTemplate dialectTemplate,
			@Value("${hyperon.database.username}") String username,
			@Value("${hyperon.database.password}") String password,
			@Value("${hyperon.database.url}") String jdbcUrl) {

		var dataSource = new HikariDataSource();
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(jdbcUrl);
		dataSource.setDriverClassName(dialectTemplate.getJdbcDriverClassName());
		return dataSource;
	}

	@Bean
	public DialectTemplate getDialectTemplate(DialectRegistry dialectRegistry) {
		return dialectRegistry.create();
	}

	@Bean
	public DialectRegistry getDialectRegistry(@Value("${hyperon.database.dialect}") String dialect) {
		var registry = new DialectRegistry();
		registry.setDialect(dialect);
		return registry;
	}

}
