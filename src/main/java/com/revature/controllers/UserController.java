package com.revature.controllers;

import io.javalin.http.Context;

public interface UserController {
	
	void login(Context ctx);
	
	void logout(Context ctx);
	
	void getAvailableCompensation(Context ctx);
	
	void submitForm(Context ctx);

	void approveForm(Context ctx);

	void viewAwaitingApprovals(Context ctx);
}
