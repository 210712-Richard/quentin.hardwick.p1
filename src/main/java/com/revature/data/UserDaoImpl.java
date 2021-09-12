package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private static Logger log = LogManager.getLogger(UserDaoImpl.class);
	
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
		
		String query = "Select * FROM user where username = ?";
		//String query = "Select username, type, lastName, firstName, email, supervisor, departmentHead, availableReimbursement from user where username = ?";
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
	public User getUserByName(String name) {
		String query = "Select * FROM user where lastname = ? ALLOW FILTERING";
		//String query = "Select username, type, lastName, firstName, email, supervisor, departmentHead, availableReimbursement from user where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(name);
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
	public List<User> getUsers() {
		String query = "Select username, type, lastName, firstName, email, supervisor, departmentHead, availableReimbursement from user";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<User> users = new ArrayList<>();
		rs.forEach(row -> {
			User u = new User();
			u.setUsername(row.getString("username"));
			u.setType(UserType.valueOf(row.getString("type")));
			u.setFirstName(row.getString("firstName"));
			u.setLastName(row.getString("lastName"));
			u.setEmail(row.getString("email"));
			u.setSupervisor(row.getString("supervisor"));
			u.setDepartmentHead(row.getString("departmentHead"));
			u.setAvailableReimbursement(row.getDouble("availableReimbursement"));
			
			users.add(u);
		});
		return users;
	}
	
	@Override
	public void updateUser(User u) {
		log.debug("UserDao update method called. Generating query");
		String query = "Update user set type = ?, firstName = ?, email = ?, "
				+ "supervisor = ?, departmentHead = ?, availableReimbursement = ?, "
				+ "pendingforms = ?, completedforms = ?, awaitingapproval = ? where username = ? and lastName = ?;";
		log.debug("Iterating through pending forms");
		List<UUID> forms = u.getForms().stream()
				.filter(f -> f != null)
				.map(f -> f.getId())
				.collect(Collectors.toList());
		log.debug("Iterating through Completed Forms");
		List<UUID> completedforms = u.getCompletedForms().stream()
				.filter(f -> f != null)
				.map(f -> f.getId())
				.collect(Collectors.toList());
		log.debug("Iterating through Awaiting Approval");
		List<UUID> awaitingapproval = u.getAwaitingApproval().stream()
				.filter(f -> f != null)
				.map(f -> f.getId())
				.collect(Collectors.toList());
		log.debug("Generating SimpleStatement");
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		log.debug("User: " + u);
		log.debug("Generating BoundStatement");
		BoundStatement bound = session.prepare(s)
				.bind(u.getType().toString(), u.getFirstName(), u.getEmail(), u.getSupervisor(), 
						u.getDepartmentHead(), u.getAvailableReimbursement(), forms, completedforms, 
						awaitingapproval, u.getUsername(), u.getLastName());
		log.debug("Bound Statement: " + bound);
		log.debug("Executing");
		session.execute(bound);
		
		
	}

	@Override
	public List<UUID> getPendingForms(String username) {
		String query = "Select pendingforms from User where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		List<UUID> forms = row.getList("pendingforms", UUID.class);
		return forms;
	}

	@Override
	public List<UUID> getCompletedForms(String username) {
		String query = "Select completedforms from User where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		List<UUID> forms = row.getList("completedforms", UUID.class);
		return forms;
	}

	@Override
	public List<UUID> getAwaitingApproval(String username) {
		String query = "Select awaitingapproval from User where username = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(username);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		List<UUID> forms = row.getList("awaitingapproval", UUID.class);
		return forms;
	}

	@Override
	public void submitForm(UUID id) {
		// TODO Auto-generated method stub
		
	}

}
