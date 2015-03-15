package com.shaojie.www.identityCheck.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.identityCheck.IdentityCheckingBean;

@WebServlet(name = "IdentityCheckingServlet", urlPatterns = "/checkidentity.do")
public class IdentityCheckingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	private IdentityCheckingBean mIdentityChecker;

	@Override
	protected void doGet(HttpServletRequest inRequest,
			HttpServletResponse inResponse) throws ServletException,
			IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		mIdentityChecker.checkBeanIdentities();
		mIdentityChecker.checkBeanHashCodes();
		theResponseWriter.println("Identity checks and hash code checks result printed to console.");
	}
}