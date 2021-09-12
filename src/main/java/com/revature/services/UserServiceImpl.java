package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Form;
import com.revature.beans.User;
import com.revature.beans.UserType;
import com.revature.data.UserDao;
import com.revature.data.UserDaoImpl;
import com.revature.factory.BeanFactory;
import com.revature.factory.Log;

@Log
public class UserServiceImpl implements UserService{
	
	private static Logger log = LogManager.getLogger(UserServiceImpl.class);
	public UserDao ud = (UserDao) BeanFactory.getFactory().get(UserDao.class, UserDaoImpl.class);

	@Override
	public User login(String username) {
		log.debug("Login argument: " + username);
		User u = ud.getUser(username);
		log.debug("Returning user: " + u);
		return u;
	}
	
	@Override
	public User getUser(String username) {
		User u = ud.getUser(username);
		return u;
	}


	@Override
	public void updateUser(User user) {
		ud.updateUser(user);
	}
	
	@Override
	public User getUserByName(String name) {
		String[] nameArray = name.split(" ");
		String lastName = nameArray[1];
		return ud.getUserByName(lastName);
	}


	@Override
	public Form submitForm(User employee) {
		ud.updateUser(employee);
		// if this is the department head, send to benco for approval
		if(employee.getType().equals(UserType.DEPARTMENT_HEAD)){
			User u = ud.getUser("sol");
			u.getAwaitingApproval().add(null);
		}
		return null;
	}

}
