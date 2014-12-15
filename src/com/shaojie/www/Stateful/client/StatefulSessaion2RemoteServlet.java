package com.shaojie.www.Stateful.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.Stateful.StatefulSession2RemoteBean;

@WebServlet(name = "StatefulSession2RemoteServlet", urlPatterns = "/testStatefulRemoteBean.do")
public class StatefulSessaion2RemoteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public static final String STATEFUL_REMOTE_EJB_HTTP_SESSION_VAR = "_statefulSession2RemoteBean";

	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException{
		StatefulSession2RemoteBean theStatefulRemoteSessionBean = null;
		theStatefulRemoteSessionBean = (StatefulSession2RemoteBean)inRequest.getSession().getAttribute(STATEFUL_REMOTE_EJB_HTTP_SESSION_VAR);
		PrintWriter theResponseWriter = inResponse.getWriter();
		if(theStatefulRemoteSessionBean != null){
			String theRequestNameParam = inRequest.getParameter("name");
			if(theRequestNameParam == null){
				theRequestNameParam = "Anonymous Coward";
			}
			String theResponse = theStatefulRemoteSessionBean.greeting(theRequestNameParam);
			List<String> list = new ArrayList<String>();
			list.add("String 1");
			list.add("String 2");
			list.add("Last String");
			theStatefulRemoteSessionBean.processList(list);
			
			for(String st : list){
				theResponseWriter.println(st);
			}

			theResponseWriter.println("Response from the Remote EJB : "+theResponse);
		}else{
			theResponseWriter.print("Unable to retrive an instance of the stateful session bean.");
		}
	}


}
