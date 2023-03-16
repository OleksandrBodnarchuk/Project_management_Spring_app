package com.javawwa25.app.springboot.dbconfig;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class MySqlDataSourceConfiguration {

	@Bean
	@Primary
	@ConfigurationProperties("main.datasource")
	public DataSourceProperties mainDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("main.datasource.configuration")
	public DataSource mainDataSource() {
		return mainDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
	
}
