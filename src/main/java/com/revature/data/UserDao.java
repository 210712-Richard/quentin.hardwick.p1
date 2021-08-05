package com.revature.data;

import com.revature.beans.User;

public interface UserDao {
	
	void addUser(User u);
	
	User getUser(String username);
	
	void updateUser();
}
