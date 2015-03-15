package com.shaojie.www.interceptor;

import javax.annotation.PostConstruct;
import javax.interceptor.InvocationContext;

public class MyDefaultInterceptor {

	public Object aroundInvoke(InvocationContext inInvocationContext)
			throws Exception {
		System.out.println(" Global Interceptor Before Invoke: "
				+ inInvocationContext.getMethod().getDeclaringClass().getName()
				+ "." + inInvocationContext.getMethod().getName());
		Object result = inInvocationContext.proceed();
		System.out.println(" Global Interceptor After Invoke: "
				+ inInvocationContext.getMethod().getDeclaringClass().getName()
				+ "." + inInvocationContext.getMethod().getName());
		return result;
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