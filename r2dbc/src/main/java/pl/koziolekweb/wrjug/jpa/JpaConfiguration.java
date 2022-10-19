package pl.koziolekweb.wrjug.jpa;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "pl.koziolekweb.wrjug.jpa")
@EnableTransactionManagement
@EntityScan(basePackages = "pl.koziolekweb.wrjug.jpa")
@ComponentScan(basePackages = "pl.koziolekweb.wrjug.jpa")
@Slf4j
class JpaConfiguration {


	private static final String ENV_HIBERNATE_DIALECT = "hibernate.dialect";
	private static final String ENV_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
	private static final String ENV_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
	private static final String ENV_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";

	@Bean
	public DataSource dataSource(@Autowired Environment env) {
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(env.getProperty("datasource.jdbc-url"));
		ds.setUsername(env.getProperty("datasource.username"));
		ds.setPassword(env.getProperty("datasource.password"));
		ds.setDriverClassName(env.getProperty("datasource.driver-class-name"));
		ds.setPoolName(env.getProperty("datasource.pool-name"));
		ds.setMaximumPoolSize(env.getProperty("datasource.maximum-pool-size", Integer.class));
		ds.setMaxLifetime(env.getProperty("datasource.max-lifetime", Integer.class));
		ds.setSchema(env.getProperty("datasource.schema", String.class));
		return ds;
	}

	@Bean
	public JpaTransactionManager transactionManager(DataSource dataSource) {
		var txManager = new JpaTransactionManager();
		txManager.setDataSource(dataSource);
		return txManager;
	}
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(Member.class.getPackage().getName());
		emf.setPersistenceProvider(new HibernatePersistenceProvider());
		emf.setJpaProperties(jpaProperties(env));
		return emf;
	}

	private Properties jpaProperties(Environment env) {
		Properties extraProperties = new Properties();
		extraProperties.put(ENV_HIBERNATE_FORMAT_SQL, env.getProperty(ENV_HIBERNATE_FORMAT_SQL));
		extraProperties.put(ENV_HIBERNATE_SHOW_SQL, env.getProperty(ENV_HIBERNATE_SHOW_SQL));
		extraProperties.put(ENV_HIBERNATE_HBM2DDL_AUTO, env.getProperty(ENV_HIBERNATE_HBM2DDL_AUTO));
		if (log.isDebugEnabled()) {
			log.debug(" hibernate.dialect @" + env.getProperty(ENV_HIBERNATE_DIALECT));
		}
		if (env.getProperty(ENV_HIBERNATE_DIALECT) != null) {
			extraProperties.put(ENV_HIBERNATE_DIALECT, env.getProperty(ENV_HIBERNATE_DIALECT));
		}
		return extraProperties;
	}
}
