package com.shaojie.www.Stateful;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

@Stateful
public class StatefulSession2RemoteBeanImplt implements StatefulSession2RemoteBean {

	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatefulSession2RemoteBean " + mInstanceNumber
				+ " created.");
	}

	@Override
	public String greeting(final String inName) {
		Date theCurrentTime = new Date();
		String theMessage = "Hello " + inName + ", I am StatefulSession2RemoteBeanImplt " + mInstanceNumber + ". The time is now: " + theCurrentTime;
		return theMessage;
	}

	@Override
	public void processList(List<String> list) {
		list.add("EJB added in the end");
		
	}
}
