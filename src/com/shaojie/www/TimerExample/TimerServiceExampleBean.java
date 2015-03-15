package com.shaojie.www.TimerExample;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

@Stateless
public class TimerServiceExampleBean {
	
	@Resource
	TimerService timerService;
	
	private Logger logger = Logger.getLogger("TimerServiceExampleBean");
	
	private long duration = 2000;
	
    public void setTimer() {
        logger.info("Setting a programmatic timeout for "
                + duration + " milliseconds from now.");
        ScheduleExpression se = new ScheduleExpression();
        GregorianCalendar startTime = new GregorianCalendar(2015, Calendar.FEBRUARY, 1, 12,33); 
        se.second("0/30");
        se.minute("*");
        se.hour("*");
        se.start(startTime.getTime());
        Timer timer = timerService.createCalendarTimer(se, new TimerConfig("Timer Name",false));
    }
    
    
    @Timeout
    public void programmaticTimeout(Timer timer) {
    	logger.info("Timer Info : " + timer.getInfo());
    	logger.info("Timer Next Invoke Time : " + timer.getNextTimeout());
    }

    //@AroundTimeout
    public Object timeoutInterceptorMethod(InvocationContext ctx) throws Exception { 
    	logger.info("Intercepting timeout before");
    	Timer timer = (Timer)ctx.getTimer();
    	logger.info("Timer : " + timer.getInfo());
    	Object object = ctx.proceed();
    	logger.info("Intercepting timeout finish");
    	logger.info("/n");
    	return object;
    }

}
