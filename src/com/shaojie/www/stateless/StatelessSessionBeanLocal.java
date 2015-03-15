package com.shaojie.www.stateless;

import javax.ejb.Local;
import javax.ejb.Remove;

@Local
public interface StatelessSessionBeanLocal {
	
	public String greeting(String name);
	
	public String excludeIntercepted();
	
	@Remove
	public void remove();


}
