package com.shaojie.www.stateless;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class StatelesNoInterfaceLocalBean {

	public void foo(){
		System.out.println("It is "+ StatelesNoInterfaceLocalBean.class.getName());
	}
	
	
	@PostConstruct
	private final static void init(){
		System.out.println("Initiating final static : "+ StatelesNoInterfaceLocalBean.class.getName());
	}
}
