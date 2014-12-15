package com.shaojie.www.Transaction.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.Transaction.StatefulSessionBeanTranc;

@WebServlet(name = "TopicMessageProducerServlet", urlPatterns = "/testTransaction.do")
public class TransactionTestServlet extends HttpServlet {

	/* Constant(s): */
	private static final long serialVersionUID = 1647640647915937983L;
	
	@EJB
	StatefulSessionBeanTranc statefulBean;

	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		PrintWriter theResponseWriter = inResponse.getWriter();
		theResponseWriter.println(statefulBean.greeting("Shaojie"));
		theResponseWriter.println("A message was sent at " + new Date());
	}

}
