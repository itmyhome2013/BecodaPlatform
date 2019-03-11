package com.becoda.bkms.east.zdsb.web;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;
public class ContextListener extends HttpServlet implements ServletContextListener {
	public ContextListener() {
	}

	private java.util.Timer timer = null;

	public void contextInitialized(ServletContextEvent event) {
		timer = new java.util.Timer(true);
		event.getServletContext().log("定时器已启动");
		//非生产环境不要 开启
		//timer.schedule(new MyTask(event.getServletContext()), 1 * 60 * 1000, 1 * 60 * 1000); //一分钟
		event.getServletContext().log("已经添加任务调度表");
	}

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
		event.getServletContext().log("定时器销毁");
	}

}
