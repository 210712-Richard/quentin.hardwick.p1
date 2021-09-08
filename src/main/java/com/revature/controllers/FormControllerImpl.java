package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Form;
import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.FormService;
import com.revature.services.FormServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

@Log
public class FormControllerImpl implements FormController{
	
	private FormService formService = (FormService) BeanFactory.getFactory().get(FormService.class,
			FormServiceImpl.class);
	private UserService userService = (UserService) BeanFactory.getFactory().get(UserService.class,
			UserServiceImpl.class);
	private static Logger log = LogManager.getLogger(FormControllerImpl.class);

	@Override
	public void updateForm(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadAttachment(Context ctx) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAttachment(Context ctx) {
		// TODO Auto-generated method stub
		
	}
	

}
