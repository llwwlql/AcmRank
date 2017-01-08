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
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间  
        service.scheduleAtFixedRate(allUserInfo, 1, 2, TimeUnit.MINUTES);
	}
}
