package com.shaojie.www.Stateful.client;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.Stateful.StatefulSession2LocalBeanInterface;

@WebServlet(name = "StatefulSession1Servlet", urlPatterns = "/testStatefulLocalBean.do")
public class StatefulSessaion1Servlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String STATEFUL_EJB_HTTP_SESSION_VAR = "_statefulSession1Bean";
	
	@EJB
	StatefulSession2LocalBeanInterface sfsb1;
		

	
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException{
		System.out.println(sfsb1.greeting("fuck you"));
		
	}
	
}
