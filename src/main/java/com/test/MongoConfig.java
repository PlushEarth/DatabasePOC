package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;

@Configuration
public class MongoConfig {

	private static final Logger LOG = LoggerFactory.getLogger(MongoConfig.class);
	private static final String MAJORITY = "majority";

	@Value("#{'${nosql.mongodb.server.addresses}'.split(',')}")
	private List<String> serverAddresses;

	@Value("${nosql.mongodb.server.portNumber}")
	private int serverPort;

	@Value("${nosql.mongodb.database}")
	private String database;

	@Value("${nosql.mongodb.authDB:#{null}}")
	private String authDB;

	@Value("${nosql.mongodb.username:#{null}}")
	private String username;

	@Value("${nosql.mongodb.password:#{null}}")
	private String password;

	@Value("${retry.max.tries}")
	private int maxTries;

	@Value("${nosql.mongodb.writeConcern}")
	private String writeConcern;

	@Value("${nosql.mongodb.wTimeout}")
	private int wTimeout;

//	@Bean
//	public MongoTemplate postTradeMongoTemplate() throws Exception {
//		List<ServerAddress> servers = new ArrayList<>();
//		for (String address : serverAddresses) {
//			servers.add(new ServerAddress(address, serverPort));
//		}
//		MongoClient mongoClient = getConnection(servers);
//		return new MongoTemplate(new SimpleMongoDbFactory(mongoClient, database));
//	}
//
//	@Bean
//	public MongoClient getConnection(List<ServerAddress> servers) throws Exception {
//		Exception excp = null;
//
//		MongoClientOptions options;
//		if (writeConcern.equals(MAJORITY)) {
//			options = new MongoClientOptions.Builder().writeConcern(
//					new WriteConcern(writeConcern).withWTimeout(wTimeout, TimeUnit.SECONDS)).build();
//		} else {
//			options = new MongoClientOptions.Builder().writeConcern(
//					new WriteConcern(Integer.parseInt(writeConcern)).withWTimeout(wTimeout, TimeUnit.SECONDS)).build();
//		}
//		for (int attempts = 1; attempts <= maxTries; attempts++) {
//			try {
//				if (authDB != null) {
//					MongoCredential mongoCredential = MongoCredential.createCredential(username, authDB,
//							password.toCharArray());
//					return new MongoClient(servers, mongoCredential, options);
//				} else {
//					return new MongoClient(servers, options);
//				}
//			} catch (Exception e) {
//				excp = e;
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException ie) {
//				}
//			}
//		}
//
//		LOG.error("Unable to connect to MongoDB {}", servers, excp);
//		throw excp;
//	}
}
