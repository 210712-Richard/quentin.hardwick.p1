package com.revature.services;

import com.revature.beans.Form;
import com.revature.beans.User;

public interface UserService {
	
	User login(String username);
	
	Form submitForm(User employee);
	
	void updateUser(User user);

	User getUser(String username);

	User getUserByName(String name);
}
