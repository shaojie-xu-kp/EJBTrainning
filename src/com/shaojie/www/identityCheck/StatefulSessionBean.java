package com.shaojie.www.identityCheck;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class StatefulSessionBean {
	public StatefulSessionBean() {
		System.out.println("***** StatefulSessionBean created");
	}

	@PostConstruct
	public void initialize() {
		System.out.println("***** StatefulSessionBean initialized");
	}
}