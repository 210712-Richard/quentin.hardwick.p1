package com.revature.services;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Form;

public interface FormService {
	
	Form submitForm(Form form);
	
	void updateForm(Form form);
	
	Form getForm(UUID id);
	
	Form getCompletedForm(UUID id);
	
	List<Form> getFormsByName(String name);
	
}
