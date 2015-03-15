package com.shaojie.www.AsynchEJBInvocation.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.AsynchEJBInvocation.AsynchStatefulSessionBean;

@WebServlet(name = "AsynchClientServlet", urlPatterns = "/asynch.do")
public class AsynchClientServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private AsynchStatefulSessionBean mAsynchBean;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		System.out.println("**** Entering AsynchClientServlet");
		Future<String> theSlowAsynchResult = null;
		Future<String> theAsynchWithExceptionResult = null;
		Future<String> theCanBeCancelledResult = null;
		PrintWriter theResponseWriter = inResponse.getWriter();
		try {
			/* Call void asynchronous method. */
			System.out.println("***** AsynchClientServlet - About to call slowOneWayAsynch");
			mAsynchBean.slowOneWayAsynch();
			System.out.println("***** AsynchClientServlet - Finished calling slowOneWayAsynch");
			/* Call slow asynchronous method. */
			System.out.println("***** AsynchClientServlet - About to call slowAsynch");
			theSlowAsynchResult = mAsynchBean.slowAsynch();
			System.out.println("***** AsynchClientServlet - Finished calling slowAsynch");
			/* Call asynchronous method that will throw an exception. */
			System.out.println("***** AsynchClientServlet - About to call asynchWithException");
			theAsynchWithExceptionResult = mAsynchBean.asynchWithException();
			System.out.println("***** AsynchClientServlet - Finished calling asynchWithException");
			/* Call asynchronous method that can be canceled and cancel it. */
			System.out.println("***** AsynchClientServlet - About to call canBeCancelled");
			theCanBeCancelledResult = mAsynchBean.canBeCancelled();
			System.out.println("***** AsynchClientServlet - Finished calling canBeCancelled");
			waitSomeTime((long) (Math.random() * 1000) + 1000L);
			System.out.println("***** AsynchClientServlet - About to cancel canBeCancelled");
			theCanBeCancelledResult.cancel(true);
			System.out.println("***** AsynchClientServlet - Cancelled canBeCancelled");
		} catch (Exception theException) {
			System.out.println("***** AsynchClientServlet - An exeption was thrown: " + theException.getMessage());
		}
		
		
		try {
			/* Retrieve results from asynchronous invocations. */
			System.out.println("\n***** AsynchClientServlet - slowAsynch result: " + theSlowAsynchResult.get());
			System.out.println("***** AsynchClientServlet - canBeCancelled result: " + theCanBeCancelledResult.get());
			/* Wait for asynchWithException to complete. */
			while (!theAsynchWithExceptionResult.isDone()) {
				System.out.println(" Waiting...");
			}
			System.out.println("***** AsynchClientServlet - asynchWithException result: " + theAsynchWithExceptionResult.get());
			theAsynchWithExceptionResult.get();
		} catch (InterruptedException e) {
			System.out.println("***** AsynchClientServlet - " + "An InterruptedException was thrown");
		} catch (ExecutionException e) {
			System.out.println("***** AsynchClientServlet - " + "An ExecutionException was thrown: " + e);
		}
		
		System.out.println("**** Exiting AsynchClientServlet");
		theResponseWriter.println("Finished invoking asynchronous session bean!");
	}

	private void waitSomeTime(long inDelayInMillisec) {
		try {
			Thread.sleep(inDelayInMillisec);
		} catch (InterruptedException theException) {
			// Ignore exceptions.
		}
	}
}