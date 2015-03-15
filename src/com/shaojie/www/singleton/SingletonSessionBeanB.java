package com.shaojie.www.singleton;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

import com.shaojie.www.interceptor.LogInterceptor;

@Singleton
@LocalBean
@Interceptors(LogInterceptor.class)
public class SingletonSessionBeanB {
	
	private final static String BEAN_NAME = "SingletonSessionBeanB";
	
	private String mStoredMessage = "[no message set]";
	
	@PostConstruct
	public void initialize(){
		System.out.println("**********" +BEAN_NAME+ " - Initialized");
	}
	
	@PreDestroy
	public void destroy(){
		System.out.println("**********"+BEAN_NAME+ " - Destroyed");
	}
	
	@Remove
	public void remove(){
		System.out.println("*********"+BEAN_NAME+ " - Removed");
	}

	public String retrieveMessage(){
		Date currentTime = new Date();
		String theMessage = "Message from "+BEAN_NAME+ " - " + mStoredMessage + " " + currentTime;
		return theMessage;

	}
	
	public void storeMessage(final String inputMessage){
		this.mStoredMessage = inputMessage;
	}


}
