package com.revature.beans;

public class User {
	
	String fname;
	String lname;
	Double availableReimbursement;
	User supervisor;
	User departmentHead;
	UserType type;
	//position;  (just for fun)
	
	public User() {
		super();
		availableReimbursement = 1000.00;
		type = UserType.EMPLOYEE;
	}
	
	public User(String fname, String lname) {
		this();
		this.fname = fname;
		this.lname = lname;
	}
	
	public User(String fname, String lname, User supervisor, User departmentHead) {
		this();
		this.fname = fname;
		this.lname = lname;
		this.supervisor = supervisor;
		this.departmentHead = departmentHead;
	}
	
}
