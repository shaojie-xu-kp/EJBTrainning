package com.shaojie.www.Stateful;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.ExcludeClassInterceptors;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Stateful
@LocalBean
@StatefulTimeout(value=5, unit=TimeUnit.HOURS)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StatefulSession1Bean {
	
	private static int sCurrentinstanceNumber = 1;
	
	// to properly inject it into environment entry, it has to be configured as well in deployment descriptor file ejb-jar.xml
	@Resource
	public Integer mInstanceNumber = 12;
	
	@Resource
	SessionContext sc;
	
	@PostConstruct
	private void initialize(){
		mInstanceNumber = sCurrentinstanceNumber++;
		System.out.println("**********StatefulSession1Bean " +mInstanceNumber+" creted.");
	}
	
	@PreDestroy
	private final void destroy(){
		System.out.println("**********StatefulSession1Bean " +mInstanceNumber+" destroyed.");
	}
	
	@Remove
	private void remove(){
		System.out.println("**********StatefulSession1Bean " +mInstanceNumber+" removed.");
	}

	@ExcludeClassInterceptors
	public String greeting(final String inName){
		
		try {
			Context ctx = new InitialContext();
			
			// access the injected environment entry by JNDI interface
			System.out.println("The parameter injected is : " + (Integer)ctx.lookup("java:comp/env/mInstanceNumber"));
			// access the injected environment entry by session context
			System.out.println(sc.lookup("mInstanceNumber"));
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Date currentTime = new Date();
		String theMessage = "Hello " +inName + ", I am stateful session bean " + mInstanceNumber + ". The time now is : " + currentTime;
		return theMessage;
	}
	
	public void processList(List<String> list){
		list.add("EJB added in the end");
	}

}
