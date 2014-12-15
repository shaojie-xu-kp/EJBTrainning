package com.shaojie.www.SessionBeanConcurrentAccess;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Stateless;

@Stateless
@AccessTimeout(10000000)
public class SessionBeanA {
	
	private static AtomicInteger beanNumber = new AtomicInteger(0);
	
	@PostConstruct
	public void initialize () {
		System.out.println(Thread.currentThread().getName() + " : " + SessionBeanA.class.getName() + "instance : " +beanNumber.incrementAndGet() + " created. ");
	}
	
	public void slowMethod() {
		System.out.println(Thread.currentThread().getName() + " Entering slowMethod " + this);
		waitSomeTime(10);
		System.out.println(Thread.currentThread().getName() + " Exiting slowMethod " + this);
	}

	public void fastMethod() {
		System.out.println(Thread.currentThread().getName() + " Entering fastMethod " + this);
		waitSomeTime(1);
		System.out.println(Thread.currentThread().getName() + " Exiting fastMethod " + this);
	}

	private void waitSomeTime(final long inSecondsDelay) {
		try {
			Thread.sleep(1000L * inSecondsDelay);
		} catch (InterruptedException e) {
		}
	}
}