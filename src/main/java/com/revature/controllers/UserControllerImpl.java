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
		User loggedUser = ctx.sessionAttribute("loggedUser");
		if(loggedUser == null || !loggedUser.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		ctx.json(loggedUser.getAvailableReimbursement());
	}
	
	// As a User I can submit a form to apply for reimbursement
	@Override
	public void submitForm(Context ctx) {
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
		// If loggedUser is a DepartmentHead, form goes straight to BenCo
		if(loggedUser.getType().equals(UserType.DEPARTMENT_HEAD)) {
			log.debug("LoggedUser is a Department Head");
			form.setSupervisorApproval(true);
			form.setDepartmentApproval(true);
			fs.updateForm(form);
			User benco = us.getUser("sol");
			benco.getAwaitingApproval().add(form);
			us.updateUser(benco);
			us.submitForm(loggedUser);
			ctx.json(form);
			return;
		}
		// If FormSubmitter's supervisor is a DepartmentHead, supervisor approval is automatically true
		else if(loggedUser.getDepartmentHead().equals(loggedUser.getSupervisor())) {
			form.setSupervisorApproval(true);
			fs.updateForm(form);
		}
		User supervisor = us.getUserByName(loggedUser.getSupervisor());
		supervisor.getAwaitingApproval().add(form);
		us.updateUser(supervisor);
		log.debug(loggedUser);
		us.submitForm(loggedUser);
		ctx.json(form);
	}
	
	// As a supervisor I can approve the reimbursement forms of my employees and then pass them to the Department Head
	@Override
	public void approveForm(Context ctx) {
		//Authentication
		User approver = ctx.sessionAttribute("loggedUser");
		if (approver == null) {
			ctx.status(401);
			return;
		}
		String username = ctx.pathParam("username");
		if (!approver.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		// Implementation
		String id = ctx.pathParam("id");
		Form form = fs.getForm(UUID.fromString(id));
		approver.getAwaitingApproval().remove(form);
		us.updateUser(approver);
		// If approver is just the direct supervisor
		if (!approver.getType().equals(UserType.DEPARTMENT_HEAD) && !approver.getType().equals(UserType.BENCO)) {
			form.setSupervisorApproval(true);
			User departmentHead = us.getUserByName(approver.getDepartmentHead());
			departmentHead.getAwaitingApproval().add(form);
			us.updateUser(departmentHead);
		}
		// If the approver is a department head but not a benco
		if (approver.getType().equals(UserType.DEPARTMENT_HEAD) && !approver.getType().equals(UserType.BENCO)) {
			form.setDepartmentApproval(true);
			User benco = us.getUser("sol");
			benco.getAwaitingApproval().add(form);
			us.updateUser(benco);
		}
		// if the approver is a BenCo
		if (approver.getType().equals(UserType.BENCO)) {
			form.setBencoApproval(true);
			User formSubmitter = us.getUserByName(form.getName());
			if (formSubmitter.getAvailableReimbursement() >= form.getCompensation()) {
				formSubmitter.setAvailableReimbursement(formSubmitter.getAvailableReimbursement() - form.getCompensation());
			}
			else {
				formSubmitter.setAvailableReimbursement(0.00);
			}
			formSubmitter.getAwaitingApproval().remove(form);
			formSubmitter.getCompletedForms().add(form);
			us.updateUser(formSubmitter);
		}
		
		fs.updateForm(form);
		ctx.json(form);
	}
	
	@Override
	public void viewAwaitingApprovals(Context ctx) {
		String username = ctx.pathParam("username");
		User loggedUser = ctx.sessionAttribute("loggedUser");
		
		if (loggedUser == null) {
			ctx.status(401);
			return;
		}
		if (!loggedUser.getUsername().equals(username)) {
			ctx.status(403);
			return;
		}
		
		ctx.json(loggedUser.getAwaitingApproval());
	}

}
