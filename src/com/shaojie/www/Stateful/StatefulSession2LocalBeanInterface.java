package com.shaojie.www.Stateful;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remove;

@Local
public interface StatefulSession2LocalBeanInterface {
	public String greeting(final String inName);
	
	public void processList(List<String> list);
	
	@Remove
	public void remove();

}
