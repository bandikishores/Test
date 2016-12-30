package com.sometest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;


public class LogTest {
    
    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

	public static void main(String[] args) throws InterruptedException {
		if(true) {    		
    	    for(int i = 0 ; i < 1000; i++) {
    	    	String str2 = "someText";
				StringBuilder str = new StringBuilder(str2); 
    	    	for(int j = 0; j < 10000; j++) {
    	    		str.append(str2);
    	    	}
    	    	logger.info(str.toString());
    	    	Thread.sleep(2000);
    	    }
    		return;
    	}
	}

}