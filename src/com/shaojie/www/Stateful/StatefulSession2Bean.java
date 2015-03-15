package com.shaojie.www.Stateful;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;


@Stateful(mappedName="StatefulSessionBeanSecond")
public class StatefulSession2Bean implements StatefulSession2RemoteBeanInterface, StatefulSession2LocalBeanInterface {


	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("***  " + this.getClass().getName() +" number : "+ mInstanceNumber
				+ " created.");
	}

	@Override
	public String greeting(final String inName) {
		Date theCurrentTime = new Date();
		String theMessage = "Hello " + inName + ", I am stateful session bean "
				+ mInstanceNumber + ". The time is now: " + theCurrentTime;
		return theMessage;
	}

	@Override
	public void processList(List<String> list) {
		list.add("EJB added in the end");
		
	}

	@Override
	public void remove() {
		System.out.println("One instance of StatefulSession2Bean has been removed");
		
	}
}
