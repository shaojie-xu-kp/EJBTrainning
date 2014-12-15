package com.shaojie.www.TimerExample;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.Timer;

/**
 * Stateless session bean that contains two timer callback methods that are
 * scheduled using the @Schedule annotation. An EJB may contain any number of
 * methods annotated with @Schedule that all are invoked according to their own
 * schedule.
 */
@Stateless
public class ScheduledStatelessSessionBean {
	/* Constant(s): */
	/* Class variable(s): */
	private static int sCurrentInstanceNo = 1;
	/* Instance variable(s): */
	private int mInvocationCounter;
	private int mInstanceNo = sCurrentInstanceNo++;
	@Resource
	private SessionContext mBeanContext;

	@PostConstruct
	public void initialize() {
		/* Log the session bean instance creation. */
		System.out.println("ScheduledStatelessSessionBean created: "
				+ mInstanceNo + " at: " + new Date());
	}

	/**
	 * Scheduled method invoked every 20th and 45th second every minute between
	 * 6 o'clock in the morning and 22 o'clock in the evening.
	 *
	 * @param inTimer Timer that caused the timeout callback invocation.
	 */
//	@Schedule(second = "20, 45", minute = "*", hour = "6-22", dayOfWeek = "Mon-Fri", dayOfMonth = "*", month = "*", year = "*", info = "MyTimer")
	private void scheduledMethod1(final Timer inTimer) {
		System.out.println("ScheduledStatelessSessionBean.scheduledMethod1: " + mInstanceNo + " entering at: " + new Date());
		System.out.println(" Rollback only: " + mBeanContext.getRollbackOnly());
		System.out.println(" Timer info: " + inTimer.getInfo());
		/*
		 * Wait some time to show what happens with multiple timer callback
		 * methods being invoked on a session bean that has container managed
		 * concurrency.
		 */
		waitSeconds(15);
		cancelOverdue(inTimer);
		System.out.println("ScheduledStatelessSessionBean.scheduledMethod1: " + mInstanceNo + " exiting at: " + new Date());
	}

	/**
	 * Scheduled method invoked every 10th second within the minute starting at
	 * 15th second. Timeout callback methods need not take a Timer object, as is
	 * the case with this method.
	 */
//	@Schedule(second = "15/10", minute = "*", hour = "*")
	private void scheduledMethod2() {
		System.out.println("ScheduledStatelessSessionBean.scheduledMethod2: " + mInstanceNo + " entering at: " + new Date());
		/*
		 * Wait some time to show what happens with multiple timer callback
		 * methods being invoked on a session bean that has container managed
		 * concurrency.
		 */
		waitSeconds(2);
		System.out.println("ScheduledStatelessSessionBean.scheduledMethod2: " + mInstanceNo + " exiting at: " + new Date());
	}

	private void cancelOverdue(final Timer inTimer)
			throws IllegalStateException, NoSuchObjectLocalException,
			EJBException {
		/* Cancel timer after certain number of invocations. */
		if (mInvocationCounter++ > 5) {
			System.out.println("Cancelling " + mInstanceNo + "...");
			inTimer.cancel();
		}
	}

	private void waitSeconds(final long inSeconds) {
		try {
			Thread.sleep(inSeconds * 1000L);
		} catch (InterruptedException theException) {
			// Ignore exceptions.
		}
	}
}