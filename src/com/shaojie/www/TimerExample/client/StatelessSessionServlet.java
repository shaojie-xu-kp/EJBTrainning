package com.shaojie.www.TimerExample.client;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.TimerExample.TimerServiceExampleBean;

@WebServlet(name = "TimerServlet", urlPatterns = "/testTimer.do")
public class StatelessSessionServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@EJB
	TimerServiceExampleBean timerService;
	
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException{
		PrintWriter theResponseWriter = inResponse.getWriter();
		if(timerService != null){
			timerService.setTimer();
			theResponseWriter.println("Timer created");

		}else{
			theResponseWriter.print("Unable to retrive an instance of timer bean.");
		}
	}
	
}
