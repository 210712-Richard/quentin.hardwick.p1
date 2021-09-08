package com.revature.beans;

public enum Event {
	COURSE(0.8, "Letter Grade"), SEMINAR(0.6, "Presentation"), CERT_PREP_CLASS(0.75, "Letter Grade"), CERTIFICATION(1.0, "Pass/Fail"), 
	TRAINING(0.9, "Pass/Fail"), OTHER(0.3, "Varies");
	
	private Double coverage;
	private String gradingFormat;
	
	Event(Double coverage, String format){
		this.coverage = coverage;
		this.gradingFormat = format;
	}

	public Double getCoverage() {
		return coverage;
	}

	public void setCoverage(Double coverage) {
		this.coverage = coverage;
	}

	public String getGradingFormat() {
		return gradingFormat;
	}

	public void setGradingFormat(String gradingFormat) {
		this.gradingFormat = gradingFormat;
	}
	
	
	
}
