package com.shaojie.www.singleton.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.singleton.SingletonSessionBeanA;
import com.shaojie.www.singleton.SingletonSessionBeanB;

@WebServlet(name = "SingletonClientServlet", urlPatterns = "/testSingleton.do")
public class SingletonClientServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final static String STORE_ACTION = "store";
	private final static String CLEAR_ACTION = "clear";

	@EJB
	private SingletonSessionBeanA mSingletonSessionBeanA;

	@EJB
	private SingletonSessionBeanB mSingletonSessionBeanB;
	
	@EJB
	private SingletonSessionBeanA mSingletonSessionBeanA1;

	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {		
		String theRequestNameParam = inRequest.getParameter("name");
		String theRequestActionParam = inRequest.getParameter("action");
		
		/* Set default name if none provided. */
		if (theRequestNameParam == null || theRequestNameParam.equals("")) {
			theRequestNameParam = "Anonymous Coward";
		}

		if (theRequestActionParam != null) {
			if (STORE_ACTION.equals(theRequestActionParam)) {
				mSingletonSessionBeanA.storeMessage(theRequestNameParam);
				mSingletonSessionBeanB.storeMessage(theRequestNameParam);
			}
			if (CLEAR_ACTION.equals(theRequestActionParam)) {
				mSingletonSessionBeanA.storeMessage("[CLEARED]");
				mSingletonSessionBeanB.storeMessage("[CLEARED]");
			}
		}

		/* Generate output from servlet using session beans. */
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theMessage;
		theMessage = mSingletonSessionBeanA.retrieveMessage();
		theResponseWriter.println(theMessage);
		//System.out.println("mSingletonSessionBeanA = mSingletonSessionBeanA1 ? " + mSingletonSessionBeanA.equals(mSingletonSessionBeanA1));
		theResponseWriter.println("Finished invoking singleton session beans!");

	}

}
