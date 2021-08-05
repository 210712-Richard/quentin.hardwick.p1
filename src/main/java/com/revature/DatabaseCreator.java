package com.revature;

import com.revature.util.CassandraUtil;

public class DatabaseCreator {
	
	public static void dropTables() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS User;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}
	
	public static void createTables() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS User");	
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("username text PRIMARY KEY, type text, last_name text, first_name text, ")
				.append("email text, supervisor text, department_head text, available_Reimbursement double );");
		
		CassandraUtil.getInstance().getSession().execute(sb.toString());
				}
	
	public static void populateUserTable() {
		
	}
}
