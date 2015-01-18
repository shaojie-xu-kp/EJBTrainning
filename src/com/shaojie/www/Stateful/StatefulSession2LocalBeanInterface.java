package com.shaojie.www.Stateful;

import java.util.List;

import javax.ejb.Local;

@Local
public interface StatefulSession2LocalBeanInterface {
	public String greeting(final String inName);
	
	public void processList(List<String> list);

}
