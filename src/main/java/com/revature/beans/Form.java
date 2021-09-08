package com.revature.beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Form implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String name;
	private LocalDate date;
	private LocalTime time;
	private String location;
	private String description;
	private Double cost;
	private String grade;
	private Event type;
	private String justification;
	private String attachment;
	private Boolean supervisorApproval;
	private Boolean departmentApproval;
	private Boolean bencoApproval;
	private Double compensation;
	
	public Form() {
		super();
		this.attachment = "";
		this.id = UUID.randomUUID();
		this.supervisorApproval = false;
		this.departmentApproval = false;
		this.bencoApproval = false;
	}
	
	public Form(String name, LocalDate date, LocalTime time, String location, String type, String description, String justification, Double cost) {
		this();
		this.name = name;
		this.date = date;
		this.time = time;
		this.location = location;
		this.type = Event.valueOf(type);
		this.grade = Event.valueOf(type).getGradingFormat();
		this.description = description;
		this.cost = cost;
		this.justification = justification;
		this.cost = cost;
	}
	
	public Form(String name, LocalDate date, LocalTime time, String location, String type, String description, String justification, Double cost, String attachment) {
		this();
		this.name = name;
		this.date = date;
		this.time = time;
		this.location = location;
		this.type = Event.valueOf(type);
		this.grade = Event.valueOf(type).getGradingFormat();
		this.description = description;
		this.cost = cost;
		this.justification = justification;
		this.cost = cost;
		this.attachment = attachment;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public String getGrade() {
		return this.type.getGradingFormat();
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Event getType() {
		return type;
	}
	public void setType(Event type) {
		this.type = type;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public Boolean getSupervisorApproval() {
		return supervisorApproval;
	}
	public void setSupervisorApproval(Boolean supervisorApproval) {
		this.supervisorApproval = supervisorApproval;
	}
	public Boolean getDepartmentApproval() {
		return departmentApproval;
	}
	public void setDepartmentApproval(Boolean departmentApproval) {
		this.departmentApproval = departmentApproval;
	}
	public Boolean getBencoApproval() {
		return bencoApproval;
	}
	public void setBencoApproval(Boolean bencoApproval) {
		this.bencoApproval = bencoApproval;
	}
	public Double getCompensation() {
		return this.type.getCoverage()*this.cost;
	}

	public void setCompensation(Double compensation) {
		this.compensation = compensation;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachment == null) ? 0 : attachment.hashCode());
		result = prime * result + ((bencoApproval == null) ? 0 : bencoApproval.hashCode());
		result = prime * result + ((compensation == null) ? 0 : compensation.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((departmentApproval == null) ? 0 : departmentApproval.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((justification == null) ? 0 : justification.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((supervisorApproval == null) ? 0 : supervisorApproval.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Form other = (Form) obj;
		if (attachment == null) {
			if (other.attachment != null)
				return false;
		} else if (!attachment.equals(other.attachment))
			return false;
		if (bencoApproval == null) {
			if (other.bencoApproval != null)
				return false;
		} else if (!bencoApproval.equals(other.bencoApproval))
			return false;
		if (compensation == null) {
			if (other.compensation != null)
				return false;
		} else if (!compensation.equals(other.compensation))
			return false;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (departmentApproval == null) {
			if (other.departmentApproval != null)
				return false;
		} else if (!departmentApproval.equals(other.departmentApproval))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (justification == null) {
			if (other.justification != null)
				return false;
		} else if (!justification.equals(other.justification))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (supervisorApproval == null) {
			if (other.supervisorApproval != null)
				return false;
		} else if (!supervisorApproval.equals(other.supervisorApproval))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Form [id=" + id + ", name=" + name + ", date=" + date + ", time="
				+ time + ", location=" + location + ", description=" + description + ", cost=" + cost + ", grade="
				+ grade + ", type=" + type + ", justification=" + justification + ", attachment=" + attachment
				+ ", supervisorApproval=" + supervisorApproval + ", departmentApproval=" + departmentApproval
				+ ", bencoApproval=" + bencoApproval + ", compensation=" + compensation + "]";
	}
}
