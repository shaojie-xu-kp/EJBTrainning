package com.shaojie.www.Stateful;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;


@Stateful
@Local(StatefulSession2LocalBeanInterface.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StatefulSession2Bean  {


	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;
	
	@Resource
	SessionContext sct;
	
	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("***  " + this.getClass().getName() +" number : "+ mInstanceNumber
				+ " created.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String greeting(final String inName)  {
		sct.getRollbackOnly();
		Date theCurrentTime = new Date();
		String theMessage = "Hello " + inName + ", I am StatefulSession2Bean " + mInstanceNumber + ". The time is now: " + theCurrentTime;
		return theMessage;
	}

	public void processList(List<String> list)  {
		list.add("EJB added in the end");
		System.out.println(list.get(0));
		
	}

	public void remove() {
		System.out.println("One instance of StatefulSession2Bean has been removed");
		
	}
}
