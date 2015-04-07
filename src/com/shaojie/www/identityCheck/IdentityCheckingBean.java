package com.shaojie.www.identityCheck;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class IdentityCheckingBean {
	
	@EJB
	private StatelessSessionBean mStateless1;
	@EJB
	private StatelessSessionBean mStateless2;
	@EJB
	private StatefulSessionBean mStateful1;
	@EJB
	private StatefulSessionBean mStateful2;
	@EJB
	private SingletonSessionBean mSingleton1;
	@EJB
	private SingletonSessionBean mSingleton2;
	@EJB
	private StatelessSessionBeanLocalBusiness mStatelessLocalBusiness;
	@EJB
	private StatelessSessionBeanLocalBusiness mStatelessLocalBusiness2;


	public void checkBeanIdentities() {
		/* Compare stateless bean with stateless bean. */
		System.out.println("\n***** Compare stateless bean with stateless bean:");
		doIdentityCheck(mStateless1, mStateless1,"Stateless1 equals stateless1");
		doIdentityCheck(mStateless1, mStateless2,"Stateless1 equals stateless2");
		doIdentityCheck(mStateless2, mStateless1,"Stateless2 equals stateless1");
		doIdentityCheck(mStateless1, mStatelessLocalBusiness,"Stateless1 equals stateless local business:");
		doIdentityCheck(mStatelessLocalBusiness2, mStatelessLocalBusiness,"mStatelessLocalBusiness2 equals mStatelessLocalBusiness:");

		/* Compare stateless bean with other kinds of beans. */
		System.out.println("\n***** Compare stateless bean with other kinds of beans:");
		doIdentityCheck(mStateless1, mStateful1, "Stateless1 equals stateful1");
		doIdentityCheck(mStateless1, mSingleton1,"Stateless1 equals singleton1");
		/* Compare stateful bean with stateful bean. */
		System.out.println("\n***** Compare stateful bean with stateful bean:");
		doIdentityCheck(mStateful1, mStateful1, "Stateful1 equals stateful1");
		doIdentityCheck(mStateful1, mStateful2, "Stateful1 equals stateful2");
		doIdentityCheck(mStateful2, mStateful1, "Stateful2 equals stateful1");
		/* Compare stateful bean with other kinds of beans. */
		System.out.println("\n***** Compare stateless bean with other kinds of beans:");
		doIdentityCheck(mStateful1, mSingleton1, "Stateful1 equals singleton1");
		/* Compare singleton bean with singleton bean. */
		System.out.println("\n***** Compare singelton bean with singleton bean:");
		doIdentityCheck(mSingleton1, mSingleton1,"Singleton1 equals singleton1");
		doIdentityCheck(mSingleton1, mSingleton2,"Singleton1 equals singleton2");
		doIdentityCheck(mSingleton2, mSingleton1,"Singleton2 equals singelton1");
	}

	public void checkBeanHashCodes() {
		/* Hash codes of stateless beans. */
		System.out.println("\n***** Stateless bean hash codes:");
		doBeanHashCode(mStateless1, "Stateless1");
		doBeanHashCode(mStateless2, "Stateless2");
		doBeanHashCode(mStatelessLocalBusiness, "Stateless local business");
		doBeanHashCode(mStatelessLocalBusiness2, "Stateless local business 2");
		/* Hash codes of stateful beans. */
		System.out.println("\n***** Stateful bean hash codes:");
		doBeanHashCode(mStateful1, "Stateful1");
		doBeanHashCode(mStateful2, "Stateful2");
		/* Hash codes of singleton beans. */
		System.out.println("\n***** Singelton bean hash codes:");
		doBeanHashCode(mSingleton1, "Singleton1");
		doBeanHashCode(mSingleton2, "Singleton2");
	}

	private void doIdentityCheck(Object inBean1, Object inBean2, String inMessage) {
		boolean theIdentityFlag;
		theIdentityFlag = inBean1.equals(inBean2);
		System.out.println(" " + inMessage + " " + theIdentityFlag);
	}

	private void doBeanHashCode(Object inBean, String inMessage) {
		int theHashCode = inBean.hashCode();
		System.out.println(" " + inMessage + " hash code: " + theHashCode);
	}
}