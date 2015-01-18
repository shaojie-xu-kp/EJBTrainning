package com.shaojie.www.stateless;

import javax.ejb.Remote;

@Remote
public interface StatelessSessionBeanInterface {
	public String greeting(String name);
}
