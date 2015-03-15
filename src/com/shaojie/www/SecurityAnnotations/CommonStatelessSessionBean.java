package com.shaojie.www.SecurityAnnotations;

import java.security.Principal;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.SessionContext;

/**
 * Class gathering common properties of the stateless session beans in this example.
 */
public class CommonStatelessSessionBean {
	/* Class variable(s): */
	protected static int sCurrentInstanceNumber = 1;
	
	/* Instance variable(s): */
	@Resource
	protected SessionContext mSessionContext;
	
	protected int mInstanceNumber;

	/**
	 * Initializes session bean instance number.
	 */
	public CommonStatelessSessionBean() {
		mInstanceNumber = sCurrentInstanceNumber++;
	}

	/**
	 * Prints security information from the session context, if available.
	 */
	protected void printSecurityInfo() {
		if (mSessionContext != null) {
			/*
			 * Container may never return a null principal, so we don't need to
			 * make sure it is not null.
			 */
			Principal theCallerPrincipal = mSessionContext.getCallerPrincipal();
			System.out.println(" Principal name: " + theCallerPrincipal.getName());
			System.out.println(" Principal object: " + theCallerPrincipal);
			System.out.println(" Principal type: " + theCallerPrincipal.getClass());
			testCallerRole("superusers");
			testCallerRole("plainusers");
			testCallerRole("anonymous");
		} else {
			System.out.println(" No session context available.");
		}
	}

	/**
	 * Tests whether the caller is in the supplied role.
	 */
	private void testCallerRole(String inRoleName) {
		try {
			System.out.println(" Caller in '" + inRoleName + "' role? " + mSessionContext.isCallerInRole(inRoleName));
		} catch (Throwable theException) {
			System.out.println(" Cannot determine caller role: '" + inRoleName + "'");
		}
	}

	/**
	 * Creates a greeting for supplied name and bean with supplied name.
	 *
	 * @param inName
	 *            Name of person to greet.
	 * @param inBeanName
	 *            Bean name doing the greeting.
	 * @return Greeting.
	 */
	protected String assembleGreeting(String inName, String inBeanName) {
		Date theCurrentTime = new Date();
		return "Hello " + inName + ", " + inBeanName + ", instance: " + mInstanceNumber + ". The time is now: " + theCurrentTime;
	}
}