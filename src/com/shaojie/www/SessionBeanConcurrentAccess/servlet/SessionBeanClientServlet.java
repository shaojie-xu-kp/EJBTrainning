package com.shaojie.www.SessionBeanConcurrentAccess.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.SessionBeanConcurrentAccess.SessionBeanA;

/**
 * Servlet acting as a client of the session bean.
 */
@WebServlet(name = "SessionBeanClientServlet", urlPatterns = "/testSessionBeanConcurrency.do")
public class SessionBeanClientServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB
	private SessionBeanA mSessionBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest,
			HttpServletResponse inResponse) throws ServletException,
			IOException {
		System.out.println("*** Entering SessionBeanClientServlet");
		testConcurrentAccess();
		System.out.println("*** Exiting SessionBeanClientServlet");
		/* Display a message on the web page. */
		PrintWriter theResponseWriter = inResponse.getWriter();
		theResponseWriter.println("Finished invoking session bean concurrency test.");
	}

	private void testConcurrentAccess() {
		System.out.println("*** Entering testConcurrentAccess");
		/*
		 * Prepare the two threads from which the different methods of the
		 * stateful session bean will be called.
		 */
		Thread theSlowMethodThread = new Thread() {
			@Override
			public void run() {
				mSessionBean.slowMethod();
			}
		};
		
		Thread theFastMethodThread = new Thread() {
			@Override
			public void run() {
				mSessionBean.fastMethod();
			}
		};
		
		System.out.println(" Calling slowMethod...");
		theSlowMethodThread.start();
		System.out.println(" Calling fastMethod...");
		theFastMethodThread.start();
		System.out.println("*** Exiting testConcurrentAccess");
	}
}