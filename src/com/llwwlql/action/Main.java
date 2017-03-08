package com.llwwlql.action;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.llwwlql.computeRanting.AllUserInfo;

public class Main {
	public static void main(String[] args) {
		
		AllUserInfo allUserInfo = new AllUserInfo();
		ScheduledExecutorService service = Executors  
                .newSingleThreadScheduledExecutor(); 
        service.scheduleAtFixedRate(allUserInfo, 0, 15, TimeUnit.MINUTES);
	}
}