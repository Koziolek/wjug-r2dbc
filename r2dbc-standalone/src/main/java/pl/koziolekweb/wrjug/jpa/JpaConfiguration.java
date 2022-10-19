package pl.koziolekweb.wrjug.jpa;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.koziolekweb.wrjug.jpa")
@EntityScan(basePackages = "pl.koziolekweb.wrjug.jpa.model")
@ComponentScan(basePackages = "pl.koziolekweb.wrjug.jpa")
class JpaConfiguration {

    @Bean
    public DataSource dataSource(@Autowired Environment env) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
        ds.setUsername(env.getProperty("spring.datasource.hikari.username"));
        ds.setPassword(env.getProperty("spring.datasource.hikari.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
        ds.setPoolName(env.getProperty("spring.datasource.hikari.pool-name"));
        ds.setMaximumPoolSize(env.getProperty("spring.datasource.hikari.maximum-pool-size", Integer.class));
        ds.setMaxLifetime(env.getProperty("spring.datasource.hikari.max-lifetime", Integer.class));
        ds.setSchema(env.getProperty("spring.datasource.hikari.schema", String.class));
        return ds;
    }

    @Bean
    public JpaTransactionManager transactionManager(DataSource dataSource) {
        var txManager = new JpaTransactionManager();
        txManager.setDataSource(dataSource);
        return txManager;
    }
}

