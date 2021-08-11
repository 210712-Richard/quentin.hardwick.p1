package com.revature.beans;

public enum EventType {
	COURSE(0.8), SEMINAR(0.6), CERT_PREP_CLASS(0.75), CERTIFICATION(1.0), 
	TRAINING(0.9), OTHER(0.3);
	
	private Double coverage;
	
	EventType(Double coverage){
		this.coverage = coverage;
	}
	
	public Double getCoverage() {
		return this.coverage;
	}
}
