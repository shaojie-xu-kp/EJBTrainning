package com.shaojie.www.Transaction;

import java.rmi.RemoteException;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Simplest possible stateless session bean exposing a local, no-interface view.
 * The EJB has container managed transactions.
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StatefulSessionBeanTranc implements SessionSynchronization {
	private static int sCurrentInstanceNumber = 1;
	private int mInstanceNumber;
	private int mCallCounter;
	
	@Resource
	private SessionContext mBeanContext;

	@PostConstruct
	public void initialize() {
		mInstanceNumber = sCurrentInstanceNumber++;
		System.out.println("*** StatelessSession1Bean " + mInstanceNumber + " created: " + new Date());
	}

	/**
	 * Creates a greeting to the person with the supplied name.
	 *
	 * @param inName  Name of person to greet.
	 * @return Greeting.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String greeting(final String inName) {
		Date theCurrentTime = new Date();
		String theMessage = "";
		mCallCounter++;
		/* Do what is to be done inside the transaction. */
		theMessage = "Hello " + inName + ", I am stateless session bean " + mInstanceNumber + ". The time is now: " + theCurrentTime;
		/*
		 * Every third call to the same session bean instance the transaction
		 * will be marked for rollback. Marking a transaction for rollback
		 * should be done before throwing application exceptions that have not
		 * been marked as causing transaction rollback when being thrown using
		 * the @ApplicationException annotation or corresponding ejb-jar.xml
		 * deployment descriptor element.
		 */
		if (mCallCounter % 3 == 0) {
			System.out.println("*** Transaction rollback.");
			mBeanContext.setRollbackOnly();
		}
		System.out.println("*** Transaction marked for rollback: " + mBeanContext.getRollbackOnly());
		return theMessage;
	}

	/**
	 * Notifies the stateful session bean that a new transaction has begun. This
	 * method executes in a transaction context.
	 *
	 * @throws EJBException
	 *             If error occurred.
	 * @throws RemoteException
	 *             Not used.
	 */
	@Override
	public void afterBegin() throws EJBException, RemoteException {
		System.out.println("*** StatefulSessionBean.afterBegin");
	}

	/**
	 * Notifies the stateful session bean that a transaction has been completed
	 * and whether the transaction was committed or not. This method executes
	 * outside any transaction context.
	 *
	 * @param inCommitted
	 *            True if transaction committed, false otherwise.
	 * @throws EJBException
	 *             If error occurred.
	 * @throws RemoteException
	 *             Not used.
	 */
	@Override
	public void afterCompletion(boolean inCommitted) throws EJBException, RemoteException {
		System.out.println("*** StatefulSessionBean.afterCompletion: " + inCommitted);
	}

	/**
	 * Notifies the stateful session bean that a transaction is about to be
	 * completed. This method executes in a transaction context.
	 *
	 * @throws EJBException
	 *             If error occurred.
	 * @throws RemoteException
	 *             Not used.
	 */
	@Override
	public void beforeCompletion() throws EJBException, RemoteException {
		System.out.println("*** StatefulSessionBean.beforeCompletion");
	}
}