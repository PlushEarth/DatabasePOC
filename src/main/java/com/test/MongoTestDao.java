package com.test;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.test.entity.TestDate;

public class MongoTestDao {

	private MongoTemplate mongoTemplate;

	@Autowired
	public MongoTestDao(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public void testUpdate() {
		Query query = new Query(Criteria.where("_id").is("").and("createDate").lte(""));
		String uuid = UUID.randomUUID().toString();
		Update update = new Update();
		update.set("lastModifiedBy", uuid);
		update.set("lastModifiedDate", new Date());
		mongoTemplate.updateMulti(query, update, TestDate.class);
	}
}
