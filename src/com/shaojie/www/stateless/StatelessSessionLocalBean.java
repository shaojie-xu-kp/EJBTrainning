package com.shaojie.www.stateless;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;

import com.shaojie.www.interceptor.LogInterceptor;

@Stateless(name="SLSB")
public class StatelessSessionLocalBean implements StatelessSessionBeanInterface, StatelessSessionBeanLocal {

	private static int sCurrentinstanceNumber = 1;
	
	private Logger logger = Logger.getLogger("StatelessSessionLocalBean");

	private int mInstanceNumber;

	@PostConstruct
	private void initialize() {
		mInstanceNumber = sCurrentinstanceNumber++;
		System.out.println("**********StatelessSession1Bean " + mInstanceNumber
				+ " creted.");
	}

	@PreDestroy
	private void destroy() {
		System.out.println("**********StatelessSession1Bean " + mInstanceNumber
				+ " destroyed.");
	}

	@Remove
	public void remove() {
		System.out.println("**********StatelessSession1Bean " + mInstanceNumber
				+ " removed.");
	}

	@Interceptors(LogInterceptor.class)
	@Override
	public String greeting(String name) {

		Date currentTime = new Date();
		String theMessage = "Hello "+ name +" I am stateless session bean "
				+ mInstanceNumber + ". The time now is : " + currentTime;
		return theMessage;

	}
	
	public String toBeinvoked(String name) {

		Date currentTime = new Date();
		String theMessage = "Hello "+ name +" I am stateless session bean "
				+ mInstanceNumber + ". The time now is : " + currentTime;
		System.out.println(theMessage);
		return theMessage;

	}
	
	
	@AroundInvoke
	public Object internaleMethod(InvocationContext context)
			throws Exception {
		System.out.println(" InternalInterceptor - Entering method: " + context.getMethod().getDeclaringClass() + " : " + context.getMethod().getName());

		Object parameters[] = context.getParameters();
		
		for(int i = 0; i < parameters.length ; i++){
			
				System.out.println(String.format("before : %s", (String)parameters[i]));
				parameters[i] = ((String)parameters[i]).toUpperCase().trim();
				System.out.println(String.format("after : %s", (String)parameters[i]));

		}
		
		context.setParameters(parameters);
		
		((StatelessSessionLocalBean)context.getTarget()).toBeinvoked("Interceptor");
		
		Annotation annotations[] = context.getMethod().getAnnotations();
		for(Annotation annotation : annotations){
			System.out.println(annotation.getClass().getSimpleName());
		}
		
		
		/* Invoke the intercepted method on the EJB and save the result. */
		Object theResult = context.proceed();
		System.out.println(" InternalInterceptor - Exiting method: " + context.getMethod().getDeclaringClass() + " : " +  context.getMethod().getName());
		/* Return the result from the intercepted method. */
		return theResult;
	}

	@Override
	public String excludeIntercepted() {
        logger.info("method excludeIntercepted invoked.");
		return null;
	}


}
