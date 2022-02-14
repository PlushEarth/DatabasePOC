package com.test.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

import com.test.entity.TestDate;

public class SqlUtils {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(
			ZoneId.of("America/New_York"));

	/**
	 * Assumptions : Name of the table corresponds to the name of the entity : Name
	 * of the columns correspond to the name of the properties
	 * 
	 * @param entity
	 * @return
	 */

	public static String getInsertSQL(TestDate testDate) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(testDate.getClass().getSimpleName()); // Name of the table corresponds to the name of the entity
		sb.append("(");

		SqlNameValuePair sqlNVP = getSqlNV(testDate);
		sb.append(sqlNVP.getName());
		sb.append(") VALUES (");
		sb.append(sqlNVP.getValue());
		sb.append(")");
		return sb.toString();
	}

	/**
	 *
	 * @param obj
	 * @return key -> insert statement, value --> the values of the insert statement
	 */
	private static SqlNameValuePair getSqlNV(Object obj) {
		StringBuilder fn = new StringBuilder();
		StringBuilder fv = new StringBuilder();

		addFields(fn, fv, obj.getClass().getSuperclass(), obj);
		addFields(fn, fv, obj.getClass(), obj);

		String fns = fn.toString();
		if (fns.endsWith(",")) {
			fns = fns.substring(0, fns.length() - 1);
		}
		String fvs = fv.toString();

		if (fvs.endsWith(",")) {
			fvs = fvs.substring(0, fvs.length() - 1);
		}

		return new SqlNameValuePair(fns, fvs);
	}

	private static void addFields(StringBuilder fn, StringBuilder fv, Class<?> clz, Object obj) {
		for (Field f : clz.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.get(obj) != null && !Modifier.isStatic(f.getModifiers())
						&& !Collection.class.isAssignableFrom(f.getType())) {
					fn.append('[').append(f.getName()).append("],");
					if (f.getType().isAssignableFrom(Character.class)) {
						fv.append("\'").append(f.get(obj)).append("\'");
					} else if (f.getType().isAssignableFrom(String.class)) {
						String string = (String) f.get(obj);
						fv.append("\'").append(string.replace("'", "''")).append("\'");
					} else if (f.getType().isAssignableFrom(Date.class)) {
						Date date = (Date) f.get(obj);
						fv.append("\'").append(new Timestamp(date.getTime())).append("\'");
					} else if (f.getType().isAssignableFrom(Instant.class)) {
						Instant date = (Instant) f.get(obj);
						fv.append("\'").append(formatter.format(date)).append("\'");
					} else if (f.getType().isAssignableFrom(LocalDateTime.class)) {
						LocalDateTime date = (LocalDateTime) f.get(obj);
						fv.append("\'").append(formatter.format(date)).append("\'");
					} else {
						fv.append(f.get(obj));
					}
					fv.append(","); // this "," append used to be outside of the
									// null check.
				}
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
