package com.shaojie.www.singleton;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;

import com.shaojie.www.interceptor.LogInterceptor;


@LocalBean
@Startup
@Singleton
@DependsOn("SingletonSessionBeanB")
@Interceptors(LogInterceptor.class)
public class SingletonSessionBeanA {
	private final static String BEAN_NAME = "SingletonSessionBeanA";

	private String mStoredMessage = "[no message set]";

	@PostConstruct
	public static void initialize() {
		System.out.println("**********" + BEAN_NAME + " - Initialized Static");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("**********" + BEAN_NAME + " - Destroyed");
	}

	@Remove
	public void remove() {
		System.out.println("*********" + BEAN_NAME + " - Removed");
	}

	public String retrieveMessage() {
		Date currentTime = new Date();
		String theMessage = "Message from " + BEAN_NAME + " - "
				+ mStoredMessage + " " + currentTime;
		return theMessage;
	}

	public void storeMessage(final String inputMessage) {
		this.mStoredMessage = inputMessage;
	}

	//@Schedule(second = "*/10", minute = "*", hour = "*")
	public void doPeriodic() {
		System.out.println("*** Do periodic: " + (new Date()));
	}

}
