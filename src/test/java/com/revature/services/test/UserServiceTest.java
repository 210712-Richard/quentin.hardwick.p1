package com.revature.services.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import com.revature.beans.User;
import com.revature.data.UserDao;
import com.revature.services.UserServiceImpl;

public class UserServiceTest {
	private static UserServiceImpl service;
	private static User u;
	
	@BeforeAll
	public static void setUpClass() {
		u = new User();
		u.setUsername("Test");
	}
	
	@BeforeEach
	public static void setUpTests() {
		service = new UserServiceImpl();
		u.setAvailableReimbursement(1000.0);
		service.ud = Mockito.mock(UserDao.class);
	}
	
	public static void testUpdateUser() {
	
	}
}
