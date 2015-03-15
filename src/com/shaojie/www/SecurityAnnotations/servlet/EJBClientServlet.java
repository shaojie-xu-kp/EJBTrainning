package com.shaojie.www.SecurityAnnotations.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.SecurityAnnotations.StatelessSession1Bean;

/**
 * Servlet implementing an EJB client which calls a secured method of an EJB.
 */
@WebServlet(name = "EJBClientServlet", urlPatterns = "/testSecurity.do")
@ServletSecurity(httpMethodConstraints = { @HttpMethodConstraint(value = "GET") })
public class EJBClientServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = 1L;
	/* Instance variable(s): */
	@EJB
	private StatelessSession1Bean mSessionBean1;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest,
			HttpServletResponse inResponse) throws ServletException,
			IOException {
		System.out.println("*** EJBClientServlet.doGet");
		PrintWriter theResponseWriter = inResponse.getWriter();
		String theRequestNameParam = inRequest.getParameter("name");
		if (theRequestNameParam == null) {
			theRequestNameParam = "Anonymous";
		}
		String theResponse = mSessionBean1.greeting(theRequestNameParam);
		theResponseWriter.println("Response from the EJB:\n" + theResponse);
	}
}