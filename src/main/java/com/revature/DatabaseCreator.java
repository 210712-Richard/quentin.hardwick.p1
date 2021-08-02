package com.revature;

import com.revature.util.CassandraUtil;

public class DatabaseCreator {
	
	public static void createTables() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("username text PRIMARY KEY, type text, currency bigint, ")
				.append("birthday date, lastCheckIn date, email text, inventory list<uuid> );");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
				}
}
