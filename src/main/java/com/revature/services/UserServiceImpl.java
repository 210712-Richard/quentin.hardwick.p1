package com.revature.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
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
	public User register(String fname, String lname, String username, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

}
