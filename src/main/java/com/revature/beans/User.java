package com.revature.beans;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String firstName;
	String lastName;
	String username;
	String email;
	Double availableReimbursement;
	String supervisor;
	String departmentHead;
	UserType type;
	//position;  (just for fun)
	
	public User() {
		super();
		this.availableReimbursement = 1000.00;
		this.type = UserType.EMPLOYEE;
	}
	
	public User(String fname, String lname) {
		this();
		this.firstName = fname;
		this.lastName = lname;
	}
	
	public User(String fname, String lname, String username, String email) {
		this();
		this.firstName = fname;
		this.lastName = lname;
		this.username = username;
		this.email = email;
	}
	
	public User(String fname, String lname, String username, String email, String supervisor, String departmentHead) {
		this();
		this.firstName = fname;
		this.lastName = lname;
		this.username = username;
		this.email = email;
		this.supervisor = supervisor;
		this.departmentHead = departmentHead;
	}
//********************   Getters and Setters   *********************************
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String fname) {
		this.firstName = fname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lname) {
		this.lastName = lname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getAvailableReimbursement() {
		return availableReimbursement;
	}

	public void setAvailableReimbursement(Double availableReimbursement) {
		this.availableReimbursement = availableReimbursement;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getDepartmentHead() {
		return departmentHead;
	}

	public void setDepartmentHead(String departmentHead) {
		this.departmentHead = departmentHead;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}
//*********************   hashCode and equals   ********************************
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availableReimbursement == null) ? 0 : availableReimbursement.hashCode());
		result = prime * result + ((departmentHead == null) ? 0 : departmentHead.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((supervisor == null) ? 0 : supervisor.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (availableReimbursement == null) {
			if (other.availableReimbursement != null)
				return false;
		} else if (!availableReimbursement.equals(other.availableReimbursement))
			return false;
		if (departmentHead == null) {
			if (other.departmentHead != null)
				return false;
		} else if (!departmentHead.equals(other.departmentHead))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (supervisor == null) {
			if (other.supervisor != null)
				return false;
		} else if (!supervisor.equals(other.supervisor))
			return false;
		if (type != other.type)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
//******************************   toString   **********************************
	@Override
	public String toString() {
		return "User [fname=" + firstName + ", lname=" + lastName + ", username=" + username + ", email=" + email
				+ ", availableReimbursement=" + availableReimbursement + ", supervisor=" + supervisor
				+ ", departmentHead=" + departmentHead + ", type=" + type + "]";
	}
	
	
	
}
