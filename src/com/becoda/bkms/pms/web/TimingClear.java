package com.becoda.bkms.pms.web;

import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class TimingClear {
	
	Timer timer = new Timer();
	
	public void clear(HttpServletRequest request,String name){
		
		final String userName = name;
		final HttpSession session = request.getSession();
		final ServletContext application = session.getServletContext();
		
	    timer.schedule(new TimerTask() {
			@Override
			public void run() {
				application.removeAttribute(userName);
				session.setMaxInactiveInterval(0);
			}
		}, 1800000);  //三十分鐘
		
	}
	
	public void cancel(){
		timer.cancel();
		timer=null;
	}
}
