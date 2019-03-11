package com.becoda.bkms.east.zdsb.web;
import java.text.ParseException;
import java.util.TimerTask;
import javax.servlet.ServletContext;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.east.ssjc.web.ZbjcAction;
public class MyTask extends TimerTask {  
  private static boolean isRunning = false; 
  private ServletContext context = null; 

  public MyTask() { 
  } 
  public MyTask(ServletContext context) { 
    this.context = context; 
  } 
  @Override
  public void run() { 
    if (!isRunning) {     
        isRunning = true; 
        context.log("开始执行指定任务");    
        try {
			try {
				new ZbjcAction().addSbscNextMin();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (BkmsException e) {
			e.printStackTrace();
		}
        isRunning = false; 
        context.log("指定任务执行结束");   
    } 
    else { 
      context.log("上一次任务执行还未结束"); 
    } 
  } 

} 