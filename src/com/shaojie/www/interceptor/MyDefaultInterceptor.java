package com.shaojie.www.interceptor;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;

public class MyDefaultInterceptor {

	public Object aroundInvoke(InvocationContext inInvocationContext)
			throws Exception {
		System.out.println(" MyDefaultInterceptor intercepting: "
				+ inInvocationContext.getTarget().getClass().getSimpleName()
				+ "." + inInvocationContext.getMethod().getName());
		return inInvocationContext.proceed();
	}

	@PostConstruct
	public void postConstruct(InvocationContext inInvocationContext) throws Exception {
		System.out.println(" MyDefaultInterceptor.postConstruct");
		/*
		 * Important! Must call proceed, in order for the other interceptor
		 * methods in the interceptor chain to become invoked.
		 */
		inInvocationContext.proceed();
	}
}