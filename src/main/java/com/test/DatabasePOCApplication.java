package com.test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.test.entity.TestDate;

@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@PropertySource({"classpath:prop.properties"})
public class DatabasePOCApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabasePOCApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner testQuery(DatabaseTestDao dao, MongoTestDao mongo) {
		return args -> {
			TestDate testDate = new TestDate(Instant.now(), "test8");
			dao.testInsert(testDate);
			TestDate testDate2 = dao.testRowMapperTestDate("test8");
			ZonedDateTime nyTime = ZonedDateTime.ofInstant(testDate.getCreateDate(), ZoneId.of("America/New_York"));
			System.out.println(testDate.getCreateDate());
			System.out.println(testDate2.getCreateDate());
			System.out.println(testDate2.getDate2());
			System.out.println(testDate2.getDate3());
			System.out.println(nyTime);
			
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			dao.testQuery("SELECT * FROM TestDate", parameters);

			parameters.addValue("createDate", LocalDateTime.now().toString());
			dao.testQuery("SELECT * FROM TestDate WHERE createDate <= :createDate", parameters);


		};
	}
}
