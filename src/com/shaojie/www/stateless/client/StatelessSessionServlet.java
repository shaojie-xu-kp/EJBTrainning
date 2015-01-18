package com.shaojie.www.stateless.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.stateless.StatelessSessionBeanInterface;

@WebServlet(name = "StatelessSessionServlet", urlPatterns = "/testStatelessLocalBean.do")
public class StatelessSessionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName="java:module/SLSB")
	StatelessSessionBeanInterface statelessSessionBean;
	
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException{
		PrintWriter theResponseWriter = inResponse.getWriter();
		if(statelessSessionBean != null){
			String theRequestNameParam = inRequest.getParameter("name");
			if(theRequestNameParam == null){
				theRequestNameParam = "Anonymous Coward";
			}
			String theResponse = statelessSessionBean.greeting(theRequestNameParam);
			theResponseWriter.println("Response from the statelessSessionBean : "+theResponse);

		}else{
			theResponseWriter.print("Unable to retrive an instance of the stateless session bean.");
		}
	}
	
}
