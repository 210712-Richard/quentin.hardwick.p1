package com.revature.controllers;

import io.javalin.http.Context;

public interface FormController {
	
	void updateForm(Context ctx);
	
	void uploadAttachment(Context ctx);
	
	void getAttachment(Context ctx);
}
