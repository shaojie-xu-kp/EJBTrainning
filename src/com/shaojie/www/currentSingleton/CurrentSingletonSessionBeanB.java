package com.shaojie.www.currentSingleton;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

import com.shaojie.www.interceptor.LogInterceptor;

/**
* This class implements a singleton session bean with bean-managed
* concurrency.
*/
@Singleton
@LocalBean
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@Interceptors(LogInterceptor.class)
public class CurrentSingletonSessionBeanB {

	private final static String BEAN_NAME = "CurrentSingletonSessionBeanB";

	@PostConstruct
	public void initialize() {
		System.out.println("**********" + BEAN_NAME + " - Initialized");
	}

	@PreDestroy
	public void destroy() {
		System.out.println("**********" + BEAN_NAME + " - Destroyed");
	}

	@Remove
	public void remove() {
		System.out.println("*********" + BEAN_NAME + " - Removed");
	}

	public  void slowMethod() {
		System.out.println(Thread.currentThread().getName()+ " "+ BEAN_NAME +"- Entering slowMethod");
		waitSomeTime(10);
		System.out.println(Thread.currentThread().getName()+ " "+ BEAN_NAME +"- Exiting slowMethod");
	}

	public  void fastMethod() {
		System.out.println(Thread.currentThread().getName()+ " "+ BEAN_NAME +"- Entering fastMethod");
		waitSomeTime(1);
		System.out.println(Thread.currentThread().getName()+ " "+ BEAN_NAME +"- Exiting fastMethod");
	}

	private void waitSomeTime(final long inSecondsDelay) {
		try {
			Thread.sleep(1000L * inSecondsDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
