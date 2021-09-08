package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.Form;

public interface FormDao {
	
	Form addForm(Form form);
	
	Form getForm(UUID id);
	
	Form getCompletedForm (UUID id);
	
	List<Form> getForms();
	
	List<Form> getCompletedForms();
	
	List<Form> getFormsByName(String name);
	
	List<Form> getCompletedByName(String name);

	void updateForm(Form form);
	
	//Boolean supervisorApproval(Form form);
}
