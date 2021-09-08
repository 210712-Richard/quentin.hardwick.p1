package com.revature.services;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Form;
import com.revature.data.FormDao;
import com.revature.data.FormDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class FormServiceImpl implements FormService {
	private static Logger log = LogManager.getLogger(FormServiceImpl.class);
	public FormDao fd = (FormDao) BeanFactory.getFactory().get(FormDao.class, FormDaoImpl.class);

	@Override
	public Form submitForm(Form form) {
		fd.addForm(form);
		return form;
	}

	@Override
	public void updateForm(Form form) {
		fd.updateForm(form);
	}

	@Override
	public Form getForm(UUID id) {
		Form form = fd.getForm(id);
		return form;
	}

	@Override
	public List<Form> getFormsByName(String name) {
		List<Form> forms = fd.getFormsByName(name);
		return forms;
	}

	@Override
	public Form getCompletedForm(UUID id) {
		Form form = fd.getCompletedForm(id);
		return form;
	}
	
	
}
