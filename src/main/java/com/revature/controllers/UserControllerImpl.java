package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;

@Log
public class UserControllerImpl implements UserController {
	private static Logger log = LogManager.getLogger(UserControllerImpl.class);
	private UserService us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);

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
	
	


}
