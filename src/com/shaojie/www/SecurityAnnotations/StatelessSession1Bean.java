package com.shaojie.www.SecurityAnnotations;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Secured stateless session bean exposing a local, no-interface view.
 */
@Stateless
@LocalBean
/*
 * The roles that are to be used from within the code when invoking
 * isCallerInRole need to be declared using the @DeclareRoles annotation,
 * otherwise an exception will be thrown when calling isCallerInRole.
 */
@DeclareRoles({ "superusers", "plainusers","guest" })
/*
 * When applied at class-level, the @RolesAllowed annotation specifies which
 * security-roles are allowed to access all the methods in the EJB.
 * @RolesAllowed may also be used at method-level.
 */
@RolesAllowed({ "superusers", "plainusers","guest" })
public class StatelessSession1Bean extends CommonStatelessSessionBean {
	/**
	 * Creates a greeting to the person with the supplied name.
	 *
	 * @param inName  Name of person to greet.
	 * @return Greeting.
	 */
	public String greeting(final String inName) {
		System.out.println("*** StatelessSession1Bean.greeting");
		printSecurityInfo();
		String theMessage = assembleGreeting(inName, "StatelessSession1Bean");
		return theMessage;
	}
}