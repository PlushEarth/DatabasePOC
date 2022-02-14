package com.test.entity;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

public class TestDate {

	private Instant createDate;
	private LocalDateTime date2;
	private Timestamp date3;
	private String id;

	public TestDate() {
	}

	public TestDate(Instant createDate, String id) {
		super();
		this.createDate = createDate;
		this.id = id;
	}

	public Instant getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Instant createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getDate2() {
		return date2;
	}

	public void setDate2(LocalDateTime date2) {
		this.date2 = date2;
	}

	public Timestamp getDate3() {
		return date3;
	}

	public void setDate3(Timestamp date3) {
		this.date3 = date3;
	}
}
