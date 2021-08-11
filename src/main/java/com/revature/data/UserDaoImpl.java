package com.revature.data;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class UserDaoImpl implements UserDao{
	
	private CqlSession session = CassandraUtil.getInstance().getSession();

	@Override
	public void addUser(User u) {
		
		String query = "INSERT INTO User (username, type, lastName, firstName, email, supervisor, departmentHead, availableReimbursement)"
				+ " values(?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s)
				.bind(u.getUsername(), u.getType().toString(), u.getLastName(), 
						u.getFirstName(), u.getEmail(), u.getSupervisor(), 
						u.getDepartmentHead(), u.getAvailableReimbursement());
		session.execute(bound);
	}

	@Override
	public User getUser(String username) {
		
		String query = "Select username, type, lastName, firstName, email, supervisor, departmentHead, availableReimbursement from user where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		User u = new User();
		u.setUsername(row.getString("username"));
		u.setType(UserType.valueOf(row.getString("type")));
		u.setFirstName(row.getString("firstName"));
		u.setLastName(row.getString("lastName"));
		u.setEmail(row.getString("email"));
		u.setSupervisor(row.getString("supervisor"));
		u.setDepartmentHead(row.getString("departmentHead"));
		u.setAvailableReimbursement(row.getDouble("availableReimbursement"));
		return u;
	}

	@Override
	public void updateUser() {
		// TODO Auto-generated method stub
		
	}

}
