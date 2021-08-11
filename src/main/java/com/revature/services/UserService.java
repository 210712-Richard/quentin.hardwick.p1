package com.revature.services;

import com.revature.beans.User;

public interface UserService {
	
	User login(String username);
	
	User register(String fname, String lname, String username, String email);
	
	void updateUser(User user);
}
