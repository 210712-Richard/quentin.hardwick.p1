package com.revature.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.Event;
import com.revature.beans.Form;
import com.revature.factory.Log;
import com.revature.util.CassandraUtil;

@Log
public class FormDaoImpl implements FormDao{
	private CqlSession session = CassandraUtil.getInstance().getSession();
	private static Logger log = LogManager.getLogger(FormDaoImpl.class);

	@Override
	public Form addForm(Form form) {
		String query = "Insert into PendingForms (id, name, date, time, location, description, "
				+ "cost, grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(form.getId(), form.getName(), form.getDate(), form.getTime(),
				form.getLocation(), form.getDescription(), form.getCost(), form.getGrade(), form.getType().toString(),
				form.getJustification(), form.getAttachment(), form.getSupervisorApproval(),
				form.getDepartmentApproval(), form.getBencoApproval());
		session.execute(bound);
		return form;	
	}

	@Override
	public Form getForm(UUID id) {
		/*String query = "Select id, name, date, time, location, description, cost, "
				+ "grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval from PendingForms where id = ?";*/
		String query = "SELECT * FROM PendingForms where id = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(id);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		Form f = new Form();
		f.setId(row.getUuid("id"));
		f.setName(row.getString("name"));
		f.setDate(row.getLocalDate("date"));
		f.setTime(row.getLocalTime("time"));
		f.setLocation(row.getString("location"));
		f.setDescription(row.getString("Description"));
		f.setCost(row.getDouble("cost"));
		f.setGrade(row.getString("grade"));
		f.setType(Event.valueOf(row.getString("type")));
		f.setJustification(row.getString("justification"));
		f.setAttachment(row.getString("attachment"));
		log.debug("Form prior to getting approval values: " + f);
		f.setSupervisorApproval(row.getBoolean("supervisorApproval"));
		f.setDepartmentApproval(row.getBoolean("departmentHeadApproval"));
		f.setBencoApproval(row.getBoolean("bencoApproval"));
		return f;
	}
	
	@Override
	public List<Form> getForms() {
		String query = "Select id, name, date, time, location, description, cost, "
				+ "grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval from PendingForms";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<Form> forms = new ArrayList<>();
		rs.forEach(row -> {
			Form f = new Form();
			f.setId(row.getUuid("id"));
			f.setName(row.getString("name"));
			f.setDate(row.getLocalDate("date"));
			f.setTime(row.getLocalTime("time"));
			f.setLocation(row.getString("location"));
			f.setDescription(row.getString("Description"));
			f.setCost(row.getDouble("cost"));
			f.setGrade(row.getString("grade"));
			f.setType(Event.valueOf(row.getString("type")));
			f.setJustification(row.getString("justification"));
			f.setAttachment(row.getString("attachment"));
			f.setSupervisorApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setDepartmentApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setBencoApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			
			forms.add(f);
		});
		return forms;
	}

	@Override
	public List<Form> getFormsByName(String name) {
		String query = "Select id, name, date, time, location, description, cost, "
				+ "grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval from PendingForms where name = ?";
		BoundStatement bound = session.prepare(new SimpleStatementBuilder(query).build()).bind(name);
		ResultSet rs = session.execute(bound);
		List<Form> forms = new ArrayList<>();
		rs.forEach(row -> {
			Form f = new Form();
			f.setId(row.getUuid("id"));
			f.setName(row.getString("name"));
			f.setDate(row.getLocalDate("date"));
			f.setTime(row.getLocalTime("time"));
			f.setLocation(row.getString("location"));
			f.setDescription(row.getString("Description"));
			f.setCost(row.getDouble("cost"));
			f.setGrade(row.getString("grade"));
			f.setType(Event.valueOf(row.getString("type")));
			f.setJustification(row.getString("justification"));
			f.setAttachment(row.getString("attachment"));
			f.setSupervisorApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setDepartmentApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setBencoApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			
			forms.add(f);
		});
		return forms;
	}

	@Override
	public void updateForm(Form f) {
		String query = "Update PendingForms set date = ?, time = ?, location = ?, "
				+ "description = ?, cost = ?, grade = ?, type = ?, justification = ?, "
				+ "attachment = ?, supervisorapproval = ?, departmentheadapproval = ?, "
				+ "bencoapproval = ? where id = ? and name = ?;";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		log.debug(f);
		BoundStatement bound = session.prepare(s)
				.bind(f.getDate(), f.getTime(), f.getLocation(), f.getDescription(),
						f.getCost(), f.getGrade(), f.getType().toString(), f.getJustification(),
						f.getAttachment(), f.getSupervisorApproval(), f.getDepartmentApproval(),
						f.getBencoApproval(), f.getId(), f.getName());
		session.execute(bound);
		return;
	}

	@Override
	public Form getCompletedForm(UUID id) {
		String query = "Select id, name, date, time, location, description, cost, "
				+ "grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval from CompletedForms where id = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		BoundStatement bound = session.prepare(s).bind(id);
		ResultSet rs = session.execute(bound);
		Row row = rs.one();
		if(row == null) {
			return null;
		}
		Form f = new Form();
		f.setId(row.getUuid("id"));
		f.setName(row.getString("name"));
		f.setDate(row.getLocalDate("date"));
		f.setTime(row.getLocalTime("time"));
		f.setLocation(row.getString("location"));
		f.setDescription(row.getString("Description"));
		f.setCost(row.getDouble("cost"));
		f.setGrade(row.getString("grade"));
		f.setType(Event.valueOf(row.getString("type")));
		f.setJustification(row.getString("justification"));
		f.setAttachment(row.getString("attachment"));
		f.setSupervisorApproval(Boolean.valueOf(row.getString("supervisorApproval")));
		f.setDepartmentApproval(Boolean.valueOf(row.getString("supervisorApproval")));
		f.setBencoApproval(Boolean.valueOf(row.getString("supervisorApproval")));
		return f;
	}

	@Override
	public List<Form> getCompletedForms() {
		String query = "Select id, name, date, time, location, description, cost, "
				+ "grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval from CompletedForms";
		SimpleStatement s = new SimpleStatementBuilder(query).build();
		ResultSet rs = session.execute(s);
		List<Form> forms = new ArrayList<>();
		rs.forEach(row -> {
			Form f = new Form();
			f.setId(row.getUuid("id"));
			f.setName(row.getString("name"));
			f.setDate(row.getLocalDate("date"));
			f.setTime(row.getLocalTime("time"));
			f.setLocation(row.getString("location"));
			f.setDescription(row.getString("Description"));
			f.setCost(row.getDouble("cost"));
			f.setGrade(row.getString("grade"));
			f.setType(Event.valueOf(row.getString("type")));
			f.setJustification(row.getString("justification"));
			f.setAttachment(row.getString("attachment"));
			f.setSupervisorApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setDepartmentApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setBencoApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			
			forms.add(f);
		});
		return forms;
	}

	@Override
	public List<Form> getCompletedByName(String name) {
		String query = "Select id, name, date, time, location, description, cost, "
				+ "grade, type, justification, attachment, supervisorApproval, departmentHeadApproval, "
				+ "bencoApproval from CompletedForms where name = ?";
		BoundStatement bound = session.prepare(new SimpleStatementBuilder(query).build()).bind(name);
		ResultSet rs = session.execute(bound);
		List<Form> forms = new ArrayList<>();
		rs.forEach(row -> {
			Form f = new Form();
			f.setId(row.getUuid("id"));
			f.setName(row.getString("name"));
			f.setDate(row.getLocalDate("date"));
			f.setTime(row.getLocalTime("time"));
			f.setLocation(row.getString("location"));
			f.setDescription(row.getString("Description"));
			f.setCost(row.getDouble("cost"));
			f.setGrade(row.getString("grade"));
			f.setType(Event.valueOf(row.getString("type")));
			f.setJustification(row.getString("justification"));
			f.setAttachment(row.getString("attachment"));
			f.setSupervisorApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setDepartmentApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			f.setBencoApproval(Boolean.valueOf(row.getString("supervisorApproval")));
			
			forms.add(f);
		});
		return forms;
	}

	
}
