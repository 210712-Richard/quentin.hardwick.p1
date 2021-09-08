package com.revature.controllers;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Form;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.FormService;
import com.revature.services.FormServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

@Log
public class UserControllerImpl implements UserController {
	private static Logger log = LogManager.getLogger(UserControllerImpl.class);
	private UserService us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	private FormService fs = (FormService) BeanFactory.getFactory().get(FormService.class, FormServiceImpl.class);
	

	@Override
	public void login(Context ctx) {
		log.trace("Login method called");
		log.debug(ctx.body());
		
		User u = ctx.bodyAsClass(User.class);
		log.debug(u);
		
		u = us.login(u.getUsername());
		log.debug(u);
		
		if (u != null) {
			ctx.sessionAttribute("loggedUser", u);
			ctx.json(u);
			return;
		}
		ctx.status(401);
		ctx.html("Invalid username");
	}

	@Override
	public void logout(Context ctx) {
		ctx.req.getSession().invalidate();
		ctx.status(204);
	}

	@Override
	public void getAvailableCompensation(Context ctx) {
		String username = ctx.pathParam("username");
		User loggedUser = (User) ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		ctx.json(loggedUser.getAvailableReimbursement());
	}
	
	@Override
	public void generateForm(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		String username = ctx.pathParam("username");
		if (!loggedUser.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		Form form = ctx.bodyAsClass(Form.class);
		log.debug(form);
		form = fs.submitForm(form);
		loggedUser.getForms().add(form);
		log.debug("LoggedUser's Form: " + loggedUser.getForms());
		if(loggedUser.getType().equals(UserType.DEPARTMENT_HEAD)) {
			log.debug("LoggedUser is a Department Head");
			form.setSupervisorApproval(true);
			form.setDepartmentApproval(true);
			//fs.updateForm(form);
			User u = us.getUser("sol");
			u.getAwaitingApproval().add(form);
		}
		log.debug(loggedUser);
		us.submitForm(loggedUser);
		ctx.json(form);
	}
	
	@Override
	public void supervisorApproval(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		String username = ctx.pathParam("username");
		if (!loggedUser.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		
		String id = ctx.pathParam("id");
		Form form = fs.getForm(UUID.fromString(id));
		form.setSupervisorApproval(true);
		if (loggedUser.getType().equals(UserType.DEPARTMENT_HEAD)) {
			form.setDepartmentApproval(true);
		}
		if(loggedUser.getType().equals(UserType.BENCO)) {
			form.setBencoApproval(true);
			User u = us.getUserByName(form.getName().split(" ")[1]);
			if (u.getAvailableReimbursement()>= form.getCost()) {
				u.setAvailableReimbursement(u.getAvailableReimbursement() - form.getCost());
			}
			else {
				u.setAvailableReimbursement(0.00);
			}
			us.updateUser(u);
		}
		//fs.updateForm(form);
		ctx.json(form);
		
	}

	@Override
	public void departmentApproval(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		String username = ctx.pathParam("username");
		if (!loggedUser.getUsername().equals(username) || !loggedUser.getType().equals(UserType.DEPARTMENT_HEAD)) {
			ctx.status(403);
			return;
		}
		
		
	}

	@Override
	public void bencoApproval(Context ctx) {
		User loggedUser = ctx.sessionAttribute("loggedUser");
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		String username = ctx.pathParam("username");
		if (!loggedUser.getUsername().equals(username) || !loggedUser.getType().equals(UserType.BENCO)) {
			ctx.status(403);
			return;
		}
		String id = ctx.pathParam("id");
		Form form = fs.getForm(UUID.fromString(id));
		form.setBencoApproval(true);
		
	}



}
