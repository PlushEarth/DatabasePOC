package com.test;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DatabaseConfig {

	@Value("${db.poolName}")
	private String poolName;
	@Value("${db.driver}")
	private String driver;
	@Value("${db.username}")
	private String username;
	@Value("${db.password}")
	private String password;
	@Value("${db.url}")
	private String url;
	@Value("${db.maxPoolSize}")
	private int maxPoolSize;
	@Value("${db.isolation}")
	private String isolation;
	@Value("${db.autoCommit}")
	private boolean autoCommit;

	@Bean
	@Primary
	public HikariDataSource dataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setPoolName(poolName);
		hikariConfig.setConnectionTestQuery("SELECT 1");
		hikariConfig.setDriverClassName(driver);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);
		hikariConfig.setMaximumPoolSize(maxPoolSize);
//		hikariConfig.setTransactionIsolation(isolation);
		hikariConfig.setAutoCommit(autoCommit);

		return new HikariDataSource(hikariConfig);
	}
	
	@Bean
	@Primary
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager manager = new DataSourceTransactionManager();
		manager.setDataSource(dataSource());
		return manager;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}
	
	@Bean
	public SimpleJdbcCall simpleJdbcCall() {
		return new SimpleJdbcCall(dataSource()).withoutProcedureColumnMetaDataAccess()
				.withProcedureName("sprocName").declareParameters(
						new SqlParameter("inputVar", Types.CHAR),
						new SqlOutParameter("outputVar", Types.INTEGER));
	}
}
