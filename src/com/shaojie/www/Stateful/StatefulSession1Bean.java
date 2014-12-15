package com.shaojie.www.Stateful;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Stateful
@LocalBean
@StatefulTimeout(value=5, unit=TimeUnit.HOURS)
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StatefulSession1Bean {
	
	private static int sCurrentinstanceNumber = 1;
	
	private int mInstanceNumber;
	
	@PostConstruct
	private void initialize(){
		mInstanceNumber = sCurrentinstanceNumber++;
		System.out.println("**********StatefulSession1Bean " +mInstanceNumber+" creted.");
	}
	
	@PreDestroy
	private void destroy(){
		System.out.println("**********StatefulSession1Bean " +mInstanceNumber+" destroyed.");
	}
	
	@Remove
	private void remove(){
		System.out.println("**********StatefulSession1Bean " +mInstanceNumber+" removed.");
	}

	public String greeting(final String inName){
		Date currentTime = new Date();
		String theMessage = "Hello " +inName + ", I am stateful session bean " + mInstanceNumber + ". The time now is : " + currentTime;
		return theMessage;
	}
	
	public void processList(List<String> list){
		list.add("EJB added in the end");
	}
}
