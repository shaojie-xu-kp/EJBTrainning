package com.shaojie.www.stateless;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateless;

@Stateless
@Remote(StatelessSessionBeanInterface.class)
public class StatelessSessionLocalBean implements StatelessSessionBeanInterface {

	private static int sCurrentinstanceNumber = 1;

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
	private void remove() {
		System.out.println("**********StatelessSession1Bean " + mInstanceNumber
				+ " removed.");
	}

	@Override
	public String greeting(String name) {

		Date currentTime = new Date();
		String theMessage = "Hello , I am stateless session bean "
				+ mInstanceNumber + ". The time now is : " + currentTime;
		return theMessage;

	}

}
