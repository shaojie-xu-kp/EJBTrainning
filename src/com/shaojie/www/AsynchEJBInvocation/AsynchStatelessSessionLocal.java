package com.shaojie.www.AsynchEJBInvocation;

import java.util.concurrent.Future;

import javax.ejb.Asynchronous;
import javax.ejb.Local;

@Local
public interface AsynchStatelessSessionLocal {
	
	@Asynchronous
	Future<String> asynchCall();

}
