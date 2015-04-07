package com.shaojie.www.singleton;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

import com.shaojie.www.TopicMessageDrivenExample.Exception.MyUncheckedApplicationException;
import com.shaojie.www.interceptor.LogInterceptor;



@Startup
@Singleton
@Interceptors(LogInterceptor.class)
public class SingletonSessionBeanA {
	private final static String BEAN_NAME = "SingletonSessionBeanA";

	private String mStoredMessage = "[no message set]";
	
	@Resource
	private TimerService mTimerService;


	@PostConstruct
	static void initialize() {
		System.out.println("**********" + BEAN_NAME + " - Initialized static");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("**********" + BEAN_NAME + " - Destroyed");
	}

	@Remove
	public void remove() {
		System.out.println("*********" + BEAN_NAME + " - Removed");
	}

	public String retrieveMessage() throws MyUncheckedApplicationException{
		Date currentTime = new Date();
		String theMessage = "Message from " + BEAN_NAME + " - " + mStoredMessage + " " + currentTime;
		System.out.println("SingletonSessionBeanA - retrieveMessage" + theMessage);
		mTimerService.createTimer(1000,"retriveMessage timeout");
		return theMessage;
	}

	public void storeMessage(final String inputMessage) {
		this.mStoredMessage = inputMessage;
	}
	
	@Timeout
	private void timeoutCall(Timer inTimer){
		System.out.println("SingletonSessionBeanA - timeoutCall " + inTimer.getInfo());
	}

	@AroundInvoke
	private Object internalInvoke(InvocationContext inInvocationContext) throws Exception{
		Object obj = inInvocationContext.proceed();
		System.out.println("SingletonSessionBeanA - internalInvoke " + inInvocationContext.getMethod().getName());
		return obj;
	}
	
}
