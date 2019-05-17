package pl.decerto.hyperon.demo.dictionary.config;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.decerto.hyperon.runtime.core.HyperonEngine;
import pl.decerto.hyperon.runtime.core.HyperonEngineFactory;
import pl.decerto.hyperon.runtime.sql.DialectRegistry;

import javax.sql.DataSource;

@Configuration
public class HyperonIntegrationConfiguration {

	@Bean
	public HyperonEngine getHyperonEngine(HyperonEngineFactory engineFactory) {
		return engineFactory.create();
	}

	/*
	Exposing HyperonEngineFactory as a Bean allows Spring to call its destroy method to shut it down gracefully
	*/
	@Bean(destroyMethod = "destroy")
	public HyperonEngineFactory getHyperonEngineFactory(
			DataSource dataSource,
			@Value("${hyperon.dev.mode:false}") boolean devMode,
			@Value("${hyperon.dev.user:}") String devUser) {

		var engineFactory = new HyperonEngineFactory(dataSource);

		if (devMode) {
			engineFactory.setDeveloperMode(true);
			engineFactory.setUsername(devUser);
		}

		return engineFactory;
	}

	@Bean(destroyMethod = "close")
	public HikariDataSource getDataSource(
			@Value("${hyperon.database.dialect}") String dialect,
			@Value("${hyperon.database.username}") String username,
			@Value("${hyperon.database.password}") String password,
			@Value("${hyperon.database.url}") String jdbcUrl) {

		var dataSource = new HikariDataSource();
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setJdbcUrl(jdbcUrl);

		configureHyperonDialect(dialect);
		var driverClassName = getDriverClassNameForConfiguredDialect();
		dataSource.setDriverClassName(driverClassName);

		return dataSource;
	}

	private void configureHyperonDialect(String dialect) {
		DialectRegistry.set(dialect);
	}

	private String getDriverClassNameForConfiguredDialect() {
		/*
		Get default driver class name corresponding to previously setup database dialect.
		It is not necessary to obtain the driver class name this way, but this method guarantees
		that the selected driver will be compatible with Hyperon.
		 */
		return DialectRegistry.getDialectTemplate().getJdbcDriverClassName();
	}

}
