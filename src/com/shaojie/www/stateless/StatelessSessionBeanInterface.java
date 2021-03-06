package com.shaojie.www.stateless;

import javax.ejb.Remote;
import javax.ejb.Remove;

@Remote
public interface StatelessSessionBeanInterface {
	
	public String greeting(String name);
	
	public String excludeIntercepted();
	
	@Remove
	public void remove();
	
}
