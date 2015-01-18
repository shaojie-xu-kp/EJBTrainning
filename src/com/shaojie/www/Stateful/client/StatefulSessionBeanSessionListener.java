package com.shaojie.www.Stateful.client;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class StatefulSessionBeanSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent inHttpSessionEvent) {
		System.out.println("*** HTTP session created.");
		try {

			InitialContext theInitialContext = new InitialContext();
			Object theBeanReference = theInitialContext.lookup("java:module/StatefulSessionBeanFirst");
			Object theRemoteBeanReference = theInitialContext.lookup("java:module/StatefulSession2Bean!com.shaojie.www.Stateful.StatefulSession2RemoteBeanInterface");
			if (theBeanReference != null) {
				/* Store the reference in the HTTP session. */
				inHttpSessionEvent.getSession().setAttribute(StatefulSessaion1Servlet.STATEFUL_EJB_HTTP_SESSION_VAR, theBeanReference);
				System.out.println("*** Stateful session bean reference stored in HTTP session.");
			} else {
				System.out.println("*** Unable to find reference to stateful session bean.");
			}
			
			if (theRemoteBeanReference != null) {
				/* Store the reference in the HTTP session. */
				inHttpSessionEvent.getSession().setAttribute(StatefulSessaion2RemoteServlet.STATEFUL_REMOTE_EJB_HTTP_SESSION_VAR, theRemoteBeanReference);
				System.out.println("*** Stateful remote session bean reference stored in HTTP session.");
			} else {
				System.out.println("*** Unable to find reference to stateful remote session bean.");
			}
			
			
		} catch (final NamingException theException) {
			theException.printStackTrace();
		}

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("*** HTTP session destroyed.");
	}

}
