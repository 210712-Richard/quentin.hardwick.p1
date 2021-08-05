package com.revature.beans;

public class User {
	
	String fname;
	String lname;
	String username;
	String email;
	Double availableReimbursement;
	User supervisor;
	User departmentHead;
	UserType type;
	//position;  (just for fun)
	
	public User() {
		super();
		this.availableReimbursement = 1000.00;
		this.type = UserType.EMPLOYEE;
	}
	
	public User(String fname, String lname) {
		this();
		this.fname = fname;
		this.lname = lname;
	}
	
	public User(String fname, String lname, String username, String email, User supervisor, User departmentHead) {
		this();
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.email = email;
		this.supervisor = supervisor;
		this.departmentHead = departmentHead;
	}
//********************   Getters and Setters   *********************************
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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

	public User getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(User supervisor) {
		this.supervisor = supervisor;
	}

	public User getDepartmentHead() {
		return departmentHead;
	}

	public void setDepartmentHead(User departmentHead) {
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
		result = prime * result + ((fname == null) ? 0 : fname.hashCode());
		result = prime * result + ((lname == null) ? 0 : lname.hashCode());
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
		if (fname == null) {
			if (other.fname != null)
				return false;
		} else if (!fname.equals(other.fname))
			return false;
		if (lname == null) {
			if (other.lname != null)
				return false;
		} else if (!lname.equals(other.lname))
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
		return "User [fname=" + fname + ", lname=" + lname + ", username=" + username + ", email=" + email
				+ ", availableReimbursement=" + availableReimbursement + ", supervisor=" + supervisor
				+ ", departmentHead=" + departmentHead + ", type=" + type + "]";
	}
	
	
	
}
