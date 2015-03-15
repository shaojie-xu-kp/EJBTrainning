package com.shaojie.www.AsynchEJBInvocation;

import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.AccessTimeout;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

/**
 * Stateful session bean with asynchronous methods.
 */
@Stateful
@LocalBean
@AccessTimeout(value = 10, unit = TimeUnit.MINUTES)
public class AsynchStatefulSessionBean {
	@Resource
	private SessionContext mSessionContext;

	@PostConstruct
	public void initialize() {
		System.out.println("***** AsynchStatefulSessionBean initialized");
	}

	@Asynchronous
	public Future<String> asynchWithException() throws Exception {
		/* Trick the compiler to accept the method always throws an exception. */
		int i = 1;
		if (i < 2) {
			System.out.println("***** AsynchStatefulSessionBean asynchWithException throwing exception");
			throw new Exception("Exception from AsynchStatefulSessionBean");
		}
		return new AsyncResult<String>("Never happens");
	}

	@Asynchronous
	public Future<String> slowAsynch() {
		System.out.println("***** AsynchStatefulSessionBean Entering slowAsynch");
		String theResult = (new Date()).toString();
		waitSomeTime(5000L);
		System.out.println("***** AsynchStatefulSessionBean Exiting slowAsynch");
		return new AsyncResult<String>(theResult);
	}

	@Asynchronous
	public void slowOneWayAsynch() throws Exception{
		waitSomeTime(5000L);
		System.out.println("***** AsynchStatefulSessionBean Exiting slowOneWayAsynch");
	}

	@Asynchronous
	public Future<String> canBeCancelled() {
		String theResult = "Not cancelled " + new Date();
		for (int i = 1; i < 100; i++) {
			waitSomeTime(100L);
			System.out.println("***** AsynchStatefulSessionBean canBeCancelled waited " + i);
			/* Check if client attempted to cancel the method. */
			if (mSessionContext.wasCancelCalled()) {
				theResult = "Cancelled " + new Date();
				break;
			}
		}
		return new AsyncResult<String>(theResult);
	}

	private void waitSomeTime(long inDelayInMillisec) {
		try {
			Thread.sleep(inDelayInMillisec);
		} catch (InterruptedException theException) {
			// Ignore exceptions.
		}
	}
}