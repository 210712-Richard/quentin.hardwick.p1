package com.revature.controllers;

import io.javalin.http.Context;

public interface UserController {
	
	void login(Context ctx);
	
	void logout(Context ctx);
	
	void getAvailableCompensation(Context ctx);
	
	void generateForm(Context ctx);

	void supervisorApproval(Context ctx);

	void departmentApproval(Context ctx);

	void bencoApproval(Context ctx);
}
