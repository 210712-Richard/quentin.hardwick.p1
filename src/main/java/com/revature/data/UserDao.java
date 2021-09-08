package com.revature.data;

import java.util.List;
import java.util.UUID;

import com.revature.beans.User;

public interface UserDao {
	
	void addUser(User u);
	
	User getUser(String username);
	
	List<User> getUsers();
	
	void updateUser(User u);
	
	List<UUID> getPendingForms(String username);
	
	List<UUID> getCompletedForms(String username);
	
	List<UUID> getAwaitingApproval(String username);

	void submitForm(UUID id);

	User getUserByName(String name);
}
