package com.shaojie.www.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

public class LogInterceptor {
	
	public LogInterceptor() {
		System.out.println("LogInterceptor - Constructor");
	}

	@AroundInvoke
	public Object logMethodEntryExit(InvocationContext inInvocationContext)
			throws Exception {
		//System.out.println(" LogInterceptor - Entering method: " + inInvocationContext.getMethod().getDeclaringClass() + " : " + inInvocationContext.getMethod().getName());
		/* Invoke the intercepted method on the EJB and save the result. */
		Object theResult = inInvocationContext.proceed();
		System.out.println(" LogInterceptor - Exiting method: " + inInvocationContext.getMethod().getDeclaringClass() + " : " +  inInvocationContext.getMethod().getName());
		/* Return the result from the intercepted method. */
		return theResult;
	}
	
	@AroundTimeout
	public final Object logTimeout(InvocationContext inInvocationContext)
			throws Exception {
		System.out.println(" LogInterceptor - Entering timeout : " + inInvocationContext.getMethod().getName());
		Object theResult = inInvocationContext.proceed();
		System.out.println(" LogInterceptor - Exiting timeout : " + inInvocationContext.getMethod().getName());
		return theResult;
	}
}