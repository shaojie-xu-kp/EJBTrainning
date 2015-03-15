package com.shaojie.www.Stateful.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.Stateful.StatefulSession1Bean;
import com.shaojie.www.Stateful.StatefulSession2LocalBeanInterface;

@WebServlet(name = "StatefulSession1Servlet", urlPatterns = "/testStatefulLocalBean.do")
public class StatefulSessaion1Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String STATEFUL_EJB_HTTP_SESSION_VAR = "_statefulSession1Bean";
		
	@EJB
	StatefulSession2LocalBeanInterface sfsbLocal;
	
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException{
		StatefulSession1Bean theStatefulSessionBean = null;
		theStatefulSessionBean = (StatefulSession1Bean)inRequest.getSession().getAttribute(STATEFUL_EJB_HTTP_SESSION_VAR);
		PrintWriter theResponseWriter = inResponse.getWriter();
		if(theStatefulSessionBean != null){
			String theRequestNameParam = inRequest.getParameter("name");
			if(theRequestNameParam == null){
				theRequestNameParam = "Anonymous Coward";
			}
			String theResponse = theStatefulSessionBean.greeting(theRequestNameParam);
			List<String> list = new ArrayList<String>();
			list.add("String 1");
			list.add("String 2");
			list.add("Last String");
			theStatefulSessionBean.processList(list);
			
			for(String st : list){
				theResponseWriter.println(st);
			}
			theResponseWriter.println("Response from the EJB : "+theResponse);
			//			theStatefulSessionBean.remove();
			sfsbLocal.remove();
			sfsbLocal.remove();
			
		}else{
			theResponseWriter.print("Unable to retrive an instance of the stateful session bean.");
		}
	}
	
}
