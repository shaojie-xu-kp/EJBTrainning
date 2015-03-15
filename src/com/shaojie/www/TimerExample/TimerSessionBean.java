package com.shaojie.www.TimerExample;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

@Singleton
@Startup
public class TimerSessionBean {
	
    @Resource
    TimerService timerService;

    private long intervalDuration = 1000;
    private Logger logger = Logger.getLogger("TimerSessionBean");
    
    @PostConstruct
    public void setTimer() {
        logger.info("Setting a programmatic timeout for "
                + intervalDuration + " milliseconds from now.");
        Timer timer = timerService.createTimer(intervalDuration, 
                "Created new programmatic timer");
    }
    
    
    @Timeout
    public void programmaticTimeout(Timer timer) {
        logger.info("Programmatic timeout occurred.");
    }

    
    //@Schedule(second="0/10" , minute="*", hour="*")
    public void automaticTimeout() {
        logger.info("Automatic timeout occured");
    }




}
