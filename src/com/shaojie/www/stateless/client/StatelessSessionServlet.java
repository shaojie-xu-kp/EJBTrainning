package com.shaojie.www.stateless.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.stateless.StatelesNoInterfaceLocalBean;
import com.shaojie.www.stateless.StatelessSessionBeanInterface;
import com.shaojie.www.stateless.StatelessSessionBeanLocal;

@WebServlet(name = "StatelessSessionServlet", urlPatterns = "/testStatelessLocalBean.do")
public class StatelessSessionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@EJB
	StatelessSessionBeanInterface statelessSessionBean1;
	
	@EJB
	StatelessSessionBeanInterface statelessSessionBean2;
	
	@EJB
	StatelessSessionBeanLocal statelessSessionBeanLocal1;
	
	@EJB
	StatelessSessionBeanLocal statelessSessionBeanLocal2;
	
	@EJB
	StatelesNoInterfaceLocalBean statelessNoInterfaceLocal;
	
	

	
	
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException{
		PrintWriter theResponseWriter = inResponse.getWriter();
		if(statelessSessionBean1 != null){
			String theRequestNameParam = inRequest.getParameter("name");
			if(theRequestNameParam == null){
				theRequestNameParam = "Anonymous Coward";
			}
			String theResponse = statelessSessionBean1.greeting(theRequestNameParam);
			statelessSessionBean1.excludeIntercepted();
			theResponseWriter.println("Response from the statelessSessionBean : "+theResponse);
			System.out.println("statelessSessionBean1 = statelessSessionBean2 ? " + statelessSessionBean1.equals(statelessSessionBean2));
			System.out.println("statelessSessionBeanLocal1 = statelessSessionBeanLocal2 ? " + statelessSessionBeanLocal1.equals(statelessSessionBeanLocal2));
			statelessNoInterfaceLocal.foo();
		}else{
			theResponseWriter.print("Unable to retrive an instance of the stateless session bean.");
		}
	}
	
}
