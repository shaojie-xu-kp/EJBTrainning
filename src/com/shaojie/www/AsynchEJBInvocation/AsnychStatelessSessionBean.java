package com.shaojie.www.AsynchEJBInvocation;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsnychStatelessSessionBean implements AsynchStatelessSessionLocal{
	
	@Asynchronous
	public Future<String> asynchCall(){
		this.waitSomeTime(8000);
		System.out.println("AsnychStatelessSessionBean - asynchCall");
		return new AsyncResult<String>("Fuck you");
	}
	
	private void waitSomeTime(long inDelayInMillisec) {
		try {
			Thread.sleep(inDelayInMillisec);
		} catch (InterruptedException theException) {
			// Ignore exceptions.
		}
	}

}
