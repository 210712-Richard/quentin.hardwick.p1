package com.revature;

import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.util.CassandraUtil;

public class DatabaseCreator {
	private static UserDao ud = new UserDaoImpl();
	
	public static void dropTables() {
		StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS User;");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("DROP TABLE IF EXISTS PendingForms");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
		sb = new StringBuilder("DROP TABLE IF EXISTS CompletedForms");
		CassandraUtil.getInstance().getSession().execute(sb.toString());
	}
	
	public static void createTables() {
//******************   User Table   ********************************************		
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("username text, type text, lastName text, firstName text, ")
				.append("email text, supervisor text, departmentHead text, availableReimbursement double, ")
				.append("pendingForms list<uuid>, completedForms list<uuid>, awaitingapproval list<uuid>, PRIMARY KEY(username, lastName) );");
		
		CassandraUtil.getInstance().getSession().execute(sb.toString());
//*******************   Pending Forms Table   **********************************
		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS PendingForms (")
				.append("id uuid, name text, date date, time time, location text, ")
				.append("description text, cost double, grade text, type text, justification text, ")
				.append("attachment text, supervisorApproval boolean, departmentHeadApproval boolean, ")
				.append("bencoApproval boolean, PRIMARY KEY(id, name)); ");
		
		CassandraUtil.getInstance().getSession().execute(sb.toString());
//*******************   Completed Forms Table   ********************************
		sb = new StringBuilder("CREATE TABLE IF NOT EXISTS CompletedForms (")
				.append("id uuid, name text, date date, time time, location text, ")
				.append("description text, cost double, grade text, type text, justification text, ")
				.append("attachment text, supervisorApproval boolean, departmentHeadApproval boolean, ")
				.append("bencoApproval boolean, PRIMARY KEY(id, name)); ");
		
		CassandraUtil.getInstance().getSession().execute(sb.toString());
				}
	
	public static void populateUserTable() {
		//                   Firstname  Lastname  username        email
		User benco1 = new User("Seth", "Bullock", "seth", "seth@hardware.store");
		benco1.setType(UserType.BENCO);
		benco1.setSupervisor(benco1.getFirstName() + " " + benco1.getLastName());
		benco1.setDepartmentHead(benco1.getFirstName() + " " + benco1.getLastName());
		User benco2 = new User("Solomon", "Star", "sol", "sol@hardware.store");
		benco2.setType(UserType.BENCO);
		benco2.setSupervisor(benco1.getFirstName() + " " + benco1.getLastName());
		benco2.setDepartmentHead(benco1.getFirstName() + " " + benco1.getLastName());
		benco1.setSupervisor(benco2.getFirstName() + " " + benco2.getLastName());
		ud.addUser(benco1);
		ud.addUser(benco2);
		User al = new User("Albert", "Swearengen", "al", "al@gem.theater");
		al.setSupervisor(al.getFirstName() + " " + al.getLastName());
		al.setDepartmentHead(al.getFirstName() + " " + al.getLastName());
		al.setType(UserType.DEPARTMENT_HEAD);
		ud.addUser(al);
		User cy = new User("Cyrus", "Tolliver", "cy", "cy@bella.union");
		cy.setSupervisor(cy.getFirstName() + " " + cy.getLastName());
		cy.setDepartmentHead(cy.getFirstName() + " " + cy.getLastName());
		cy.setType(UserType.DEPARTMENT_HEAD);
		ud.addUser(cy);
		User dan = new User("Daniel", "Dority", "dan", "dan@gem.theater");
		dan.setSupervisor(al.getFirstName() + " " + al.getLastName());
		dan.setDepartmentHead(al.getFirstName() + " " + al.getLastName());
		ud.addUser(dan);
		User joanie = new User("Joanie", "Stubbs", "joanie", "joanie@bella.union");
		joanie.setSupervisor(cy.getFirstName() + " " + cy.getLastName());
		joanie.setDepartmentHead(cy.getFirstName() + " " + cy.getLastName());
		ud.addUser(joanie);
		User jewel = new User("Geri", "Jewell", "jewel", "jewel@gem.theater");
		jewel.setSupervisor(dan.getFirstName() + " " + dan.getLastName());
		jewel.setDepartmentHead(al.getFirstName() + " " + al.getLastName());
		ud.addUser(jewel);
		User eddie = new User("Edward", "Sawyer", "eddie", "eddy@bella.union");
		eddie.setSupervisor(joanie.getFirstName() + " " + joanie.getLastName());
		eddie.setDepartmentHead(cy.getFirstName() + " " + cy.getLastName());
		ud.addUser(eddie);
	}
}
