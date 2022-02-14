package com.test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.test.entity.TestDate;
import com.test.util.SqlUtils;

@Repository
public class DatabaseTestDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private SimpleJdbcCall simpleJdbcCall;

	public void testQuery(String query, MapSqlParameterSource parameters) {
		List<Map<String, Object>> rs = namedParameterJdbcTemplate.queryForList(query, parameters);
		for (Map<String, Object> row : rs) {
			System.out.println("----------------------------------------------------------------------------");
			for (Entry<String, Object> entry : row.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
		}
	}

	public void testRowMapper(String query) {
	}

	public void testStoredProcedure() {
		char var = '?';

		Map<String, Object> result = simpleJdbcCall.execute(var);

		System.out.println(result.size());
		System.out.println(result);
	}

	public void testDate() {
		jdbcTemplate.update("INSERT INTO TestTable(time1, time2) VALUES(?, getdate())", new Date());
	}

	public void testInsert(TestDate testDate) {
		String insertStatement = SqlUtils.getInsertSQL(testDate);
		System.out.println(insertStatement);
		jdbcTemplate.update(insertStatement);
	}

	public TestDate testRowMapperTestDate(String id) {
		String sql = "SELECT * FROM TestDate WHERE id = :id";
		MapSqlParameterSource parameter = new MapSqlParameterSource();
		parameter.addValue("id", id);
		return namedParameterJdbcTemplate.queryForObject(sql, parameter,
				new BeanPropertyRowMapper<TestDate>(TestDate.class));
	}
}
