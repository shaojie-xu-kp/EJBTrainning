package com.shaojie.www.TopicMessageDrivenExample.Exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class MyUncheckedApplicationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
