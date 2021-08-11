package com.revature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.revature.controllers.UserController;
import com.revature.controllers.UserControllerImpl;
import com.revature.factory.BeanFactory;
import com.revature.util.CassandraUtil;

import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJackson;

public class Driver {
	
	private static Logger log = LogManager.getLogger(Driver.class);
	
	public static void main(String[] args) {
		//instantiateDatabase(); //create a tables in keyspace
		javalin();
	}

	private static void instantiateDatabase() {
		DatabaseCreator.dropTables();
		try {
			Thread.sleep(30000); //Wait 30 seconds to ensure AWS finishes deleting tables
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DatabaseCreator.createTables();
		try {
			Thread.sleep(20000); //Wait 20 seconds to ensure AWS finishes creating tables
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DatabaseCreator.populateUserTable();
		System.exit(0);
	}

	private static void javalin() {
		// Set up Jackson to serialize LocalDates and LocalDateTimes
		ObjectMapper jackson = new ObjectMapper();
		jackson.registerModule(new JavaTimeModule());
		jackson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		JavalinJackson.configure(jackson);
		
		// Starts the Javalin Framework
		Javalin app = Javalin.create().start(8080);
		
		UserController uc = (UserController) BeanFactory.getFactory().get(UserController.class, UserControllerImpl.class);
		
		// As a user I can log in
		app.post("/users", uc::login);
		
		//As a user I can log out
		app.delete("/users", uc::logout);
		
		//As a user I check my remaining available compensation
		app.get("/users/:username/compensation", uc::getAvailableCompensation);
	}

}
