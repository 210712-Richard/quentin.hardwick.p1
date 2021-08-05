package com.revature.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.User;
import com.revature.util.CassandraUtil;


public class UserDaoImpl implements UserDao{
	
	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addUser(User u) {
		
		String query = "INSERT INTO User (username, type, last_name, first_name, email, supervisor, department_head, available_reimbursement)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(u.getUsername(), u.getType().toString(), u.getLname(), 
						u.getFname(), u.getEmail(), u.getSupervisor(), 
						u.getDepartmentHead(), u.getAvailableReimbursement());
		session.execute(bound);
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser() {
		// TODO Auto-generated method stub
		
	}

}
