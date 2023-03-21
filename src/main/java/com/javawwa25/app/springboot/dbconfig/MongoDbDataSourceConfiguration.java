package com.javawwa25.app.springboot.dbconfig;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MongoDbDataSourceConfiguration {

//	@Bean
	public DataSource mongoDataSource() {
		return DataSourceBuilder.create().build();
	}
}
