package com.shaojie.www.TimerExample;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimedObject;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Singleton session bean that programmatically starts a timer at the startup of
 * the application in which the EJB is deployed. Note that the class implements
 * the TimedObject interface, which could have been replaced by using the @Timeout
 * annotation.
 *
 * The TimerService object used for programmatic scheduling can be obtained
 * either from the EJB context or it can be injected directly, as seen with the
 * second instance variable below.
 *
 * Timer(s) created programmatically in an EJB will invoke one single callback
 * method, either the ejbTimeout method from the TimedObject interface or a
 * method annotated with @Timeout.
 */
@Singleton
@LocalBean
@Startup
public class ProgrammaticTimerStartedBean implements TimedObject {

	
	@Resource
	private SessionContext mBeanContext;
	
	@Resource
	private TimerService mTimerService;

	/**
	 * Initializes the EJB and starts the single-action timer that is to be
	 * invoked on this EJB. Programmatic creation of timers can be done in a
	 * method with any transaction attribute (container managed transactions).
	 */
	@PostConstruct
	@TransactionAttribute(TransactionAttributeType.NEVER)
	public void initialize() {
		System.out.println(" ProgrammaticTimerStartedBean.initialize: " + new Date());
		System.out.println(" Context: " + mBeanContext);
//		System.out.println(" Rollback only: " + mBeanContext.getRollbackOnly());
		/*
		 * A TimerConfig object may be used to supply additional information
		 * that will be enclosed in the Timer object when the timeout callback
		 * method is invoked on the bean. The "Timer info" string in the example
		 * below is the arbitrary serializable object that we later can retrieve
		 * when the timeout callback method is invoked.
		 */
		TimerConfig theTimerConfig = new TimerConfig("Timer info", false);
		/*
		 * Retrieve the timer service object from the bean context and create a
		 * timer. We could have used the timer service object in the instance
		 * variable mTimerService with the same effect.
		 */
		mTimerService.createIntervalTimer(5000000, 5000000, theTimerConfig);
	}

	/**
	 * Callback method in the TimedObject interface that is invoked when a timer
	 * associated with the instance expires. Such a method in an EJB with
	 * container managed transactions may only have the REQUIRED or REQUIRES_NEW
	 * transaction attributes. Successful execution of a timer callback method
	 * means that the transaction in which the method executes is committed.
	 *
	 * @param inTimer
	 *            Timer that expired and caused invocation of the callback
	 *            method.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void ejbTimeout(final Timer inTimer) {
		/*
		 * Output the time at which the method was invoked and the arbitrary
		 * serializable data enclosed when creating the TimerConfig and the
		 * timer.
		 */
		System.out.println("ProgrammaticTimerStartedBean.ejbTimeout: " + new Date() + ", info: " + inTimer.getInfo());
		/* Timer method always execute with an unauthorized principal. */
		System.out.println(" Security principal: " + mBeanContext.getCallerPrincipal());
		/*
		 * Retrieves the number of milliseconds remaining until the next
		 * invocation of the timeout callback method.
		 */
		System.out.println(" Time remaining: " + inTimer.getTimeRemaining());
		/*
		 * Sometimes the transaction in which the timer callback is executed
		 * rolls back. This causes the container to retry the timer and, with
		 * GlassFish, eventually cancel the timer if a certain number of
		 * consecutive rollbacks occur.
		 */
		if (Math.random() > 0.8) {
			System.out.println(" Rolling back!");
			mBeanContext.setRollbackOnly();
		}
		System.out.println(" Rollback only: " + mBeanContext.getRollbackOnly());
	}
}