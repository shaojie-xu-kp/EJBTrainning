package com.shaojie.www.Stateful;

import java.util.List;

public interface StatefulSession2RemoteBean {

	public String greeting(final String inName);
	
	public void processList(List<String> list);
}
