package com.becoda.bkms.pms.web;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.pms.pojo.vo.UserForm;
import com.becoda.bkms.pms.ucc.IUserManageUCC;
import com.becoda.bkms.sys.ucc.ILoginLogUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.DateUtilSchedule;
import com.becoda.bkms.util.LocalUtils;
import com.becoda.bkms.util.Md5Util;
import com.becoda.bkms.util.Tools;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-17
 * Time: 11:37:46
 * To change this template use File | Settings | File Templates.
 */
public class UserLogonAction extends ActionSupport {
    private UserForm userForm;
    private String msg;
    private String isLogin;
    private static int count = 1;
    private String loginName;
    private String password;
    public static Map<String, String> loginUsMp; //登录次数
    public static Map<String, String> loginIpMp; //登录IP
    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
    }


    public String loginUser() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();

        HttpSession session = request.getSession();
        User user=new User();
        ActionContext ctx=ActionContext.getContext();
        IUserManageUCC userManager = (IUserManageUCC) BkmsContext.getBean("pms_userManageUCC");
        String action ="";
        try {
            String personCode = this.loginName;
            String password = this.password;
            String rand = (String)session.getAttribute("rand");
            String input = request.getParameter("rand");
            String usip = LocalUtils.getLocalIP();//ip地址
            if(!vldLock()){
				String dtstr = loginUsMp.get(personCode).split(",")[1];
				this.setMsg("账号"+personCode+"已锁定，请在"+dtstr+"后登录！");
				return "errors";
			}
            User users=userManager.findUserByUserName(personCode);
            if(validateUserLogin(users, rand,input)){
            user = userManager.makeUnionformVerifyLogon(personCode,password, session);  
            /*if(loginIpMp!=null && loginIpMp.get(personCode)!=null){
            	String ip = loginIpMp.get(personCode);
            	if(usip.equals(ip)){
    				this.setMsg("账号"+personCode+"已登陆！");
    				return "errors";
                }
            }else if(loginIpMp!=null && loginIpMp.get(personCode)==null ){
            	loginIpMp.put(personCode, usip);//登录用户名，ip
            }else{
            	loginIpMp = new HashMap<String, String>();
            	loginIpMp.put(personCode, usip);
            }       */ 
            
            ////////// start 不允许同一账号同时登录

    		// 获取application对象
            //System.out.println("########## : " + request.getRequestURL());
    		ServletContext application = session.getServletContext();
    		String sid = (String) application.getAttribute(user.getName()); //获取登录用户的 sessionId
    		
    		String s = (String)session.getAttribute(user.getName() + ":count");
    		
    		if(s == null){ //不同瀏覽器
    			if (sid!=null && !"".equals(sid)) {
        			this.setMsg("该账号已登录，请您更换账号或三十分钟后再进行登录！");
        			return "errors";
        		} else {
        			application.setAttribute(user.getName(), session.getId());//设置登录用户的 sessionId
        			
        			new TimingClear().clear(request,user.getName());
        			
        		}
    			session.setAttribute(user.getName() + ":count", "1" );
    		}else{ //同一個瀏覽器
    			Integer c = Integer.valueOf(s);
    			
    			if(c != 1){
    				if (sid!=null && !"".equals(sid)) {
            			this.setMsg("该账号已登录，请您更换账号或三十分钟后再进行登录！");
            			return "errors";
            		} else {
            			application.setAttribute(user.getName(), session.getId());//设置登录用户的 sessionId
            			new TimingClear().clear(request,user.getName());
            		}
    			}
    		}
    		
    		/*
    		if(s != null){
    			Integer c = Integer.valueOf(s);
    			if(c != 1){
    				if (sid!=null && !"".equals(sid)) {
            			this.setMsg("该账号已登录，请您更换账号进行登录！");
            			return "errors";
            		} else {
            			application.setAttribute(user.getName(), session.getId());//设置登录用户的 sessionId
            		}
    			}else{
    				session.setAttribute(user.getName() + ":count", session.getAttribute(user.getName() + ":count") + String.valueOf(c) );
    			}
    		}else{
    			if (sid!=null && !"".equals(sid)) {
        			this.setMsg("该账号已登录，请您更换账号进行登录！");
        			return "errors";
        		} else {
        			application.setAttribute(user.getName(), session.getId());//设置登录用户的 sessionId
        		}
    			session.setAttribute(user.getName() + ":count", "1" );
    		}
    		
    		/*if(c != null){
    			if (sid!=null && !"".equals(sid)) {
        			this.setMsg("该账号已登录，请您更换账号进行登录！");
        			return "errors";
        		} else {
        			application.setAttribute(user.getName(), session.getId());//设置登录用户的 sessionId
        		}
    		}else {
    			session.setAttribute(user.getName() + ":count", session.getAttribute(user.getName() + ":count") + "1" );
    		}*/
    		
            ////////// end
            
            //将user对象放入Session中
            session.setAttribute(Constants.USER_INFO, user);
            //决定登录到那个主页面
            action = "selfsys";  //普通无权限页面
            if (user.getPmsMenus() != null && user.getPmsMenus().values().size() > 0) {
                action = "businesssys";  //登录业务系统
            } else if (user.isHrLeader()) {
                action = "hrsys"; //登录领导查询
            }
            //无权限用户不让登录系统
            if(action.equals("selfsys")){
            	throw new RollbackableException("用户无系统权限", this.getClass());
            }
            //记登陆日志  start
            ILoginLogUCC log = (ILoginLogUCC) BkmsContext.getBean("login_LogUCC");
            String sessionId = session.getId();
            Timestamp tsmp = new Timestamp(System.currentTimeMillis());
            log.createLog(sessionId, user.getUserId(), user.getName(), user.getLoginName(), request.getRemoteHost(), request.getRemoteAddr(), tsmp);
            //记登陆日志  end
            }else{
				int n = 1;
				String vl = "";
				if(loginUsMp==null || loginUsMp.isEmpty() || loginUsMp.get(personCode)==null){
					if(loginUsMp!=null && loginUsMp.get(personCode)==null  ){
						vl = "1,0";
					}else{
						vl = "1,0";
						loginUsMp = new HashMap<String, String>();	
					}									
				}else{
					n = Integer.valueOf(loginUsMp.get(personCode).split(",")[0])+1; //登录次数
					if(n<4){
						vl = String.valueOf(n)+",0";
					}else{
						vl = String.valueOf(n)+","+DateUtilSchedule.getTimeByMinute(20);//锁定20分钟
					}
				}
				loginUsMp.put(personCode, vl);//登录失败次数，登录时间，登录锁定时间，逗号分隔
				return "errors";
			}
        }  catch (Exception e) {
            e.printStackTrace();
            //将action中抛出的所有非BkmsException异常包装成一个BkmsException异常
            //e.printStackTrace();
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("出现错误").toString(), e, this.getClass());
            ctx.put("errorsMsg", " <script> alert('"+he.getFlag()+he.getCause().getMessage()+"') </script>");
            return "errors";
        }
        
        //session.setMaxInactiveInterval(120);  
        return action;
    }

    //登录验证码
//    public void admValidateCode(){
//    		HttpServletRequest request = ServletActionContext.getRequest();
//    		HttpServletResponse respose = ServletActionContext.getResponse();
//         	HttpSession session = request.getSession();
//         	
//    	 	String randString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
//		    /* 
//		     * private String randString = "0123456789";//随机产生只有数字的字符串 private String 
//	         * randString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";//随机产生只有字母的字符串 
//		   */
//			StringBuffer buf=new StringBuffer();
//			Random rm= new Random();
//			this.getResponse().setContentType("image/jpeg");
//			BufferedImage im = new BufferedImage(40,24,BufferedImage.TYPE_INT_RGB);
//			Graphics g = im.getGraphics();
//			g.fillRect(0, 0, 40, 24);
//	
//
//			
//			for(int i=0;i<4;i++){
//				
//				g.setColor(new Color(rm.nextInt(255),rm.nextInt(255),rm.nextInt(255)));
//				g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,16));
//				String n=String.valueOf(randString.charAt(rm.nextInt(randString.length())));
//				buf.append(n);
//				g.drawString(""+n, i*10,19);
//				
//         }
//    }
    /**
	 * 校验账号是否锁定
	 * @return
	 */
	public boolean vldLock() {
		int n = 0;
		String timeFlg = ""; // 锁定时间/锁定时间标志，0-未锁定
		String usip = LocalUtils.getLocalIP();
		String loginName2=this.loginName;
		String vl = "";
		if (loginUsMp == null) {
			return true;
		} else {
			vl = loginUsMp.get(loginName2);
			if (vl == null || "".equals(vl.trim())) {
				return true;
			} else {
				n = Integer.valueOf(loginUsMp.get(loginName2).split(",")[0]);
				timeFlg = loginUsMp.get(loginName2).split(",")[1];
			}
			if (n < 5) {
				return true;
			} else {
				Date date1 = new Date();
				Date date2 = DateUtilSchedule.parse(timeFlg,DateUtilSchedule.Format_DateTime);
				int v = date1.compareTo(date2);
				if (v == 1) {
					loginUsMp.remove(loginName2);//登录失败锁定时间到达后，删除map中此usip记录
					return true; //date1>date2
				} else {
					return false; //date1<=date2   -1	0 
				}
			}
		}
	}

/**
	 * 登录验证
 * @throws Exception 
	 */
	public boolean validateUserLogin(User users, String rand,String input) throws Exception {
		boolean flag = true;
		int n = 1;
		String usip = LocalUtils.getLocalIP();//获取ip
		String loginName2=users.getLoginName();
		if (loginUsMp != null && !loginUsMp.isEmpty() && loginUsMp.get(loginName2)!=null) {
			n = Integer.valueOf(loginUsMp.get(loginName2).split(",")[0])+1; //登录失败次数
		}
		if (users == null || users.getLoginName() == null  || loginName == null || rand == null) {
			this.setMsg(this.getText("用户名错误！")+"您还有"+(5-n)+"次尝试机会");
			flag = false;
			return flag;
		} else {
			// 用户名是否正确
			if (!users.getLoginName().equals(this.loginName)) {
				this.setMsg(this.getText("用户名错误！")+"您还有"+(5-n)+"次尝试机会");
				flag = false;
				return flag;
			}
			/*// 如果用户状态为"0",表示该用户已经删除
			if (users.getSjzt().equals("0")) {
				this.setMsg(this.getText("msg.login.deleted"));
				flag = false;
				return flag;
			}*/
			// 密码是否正确
			String ss=Tools.md5(this.password.trim());
			if (users.getPassword() == null || !users.getPassword().equals(Tools.md5(this.password.trim()))) {
				//this.setMsg(this.getText("密码错误！")+"您还有"+(5-n)+"次尝试机会");
				//flag = false;
				flag = true;
				return flag;
			}
			// 验证码是否正确
			if (!rand.equals(input)) {
				this.setMsg(this.getText("验证码错误！")+"您还有"+(5-n)+"次尝试机会");
				flag = false;
				return flag;
			}
		}
		return flag;
	}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public String getLoginName() {
	return loginName;
}

public void setLoginName(String loginName) {
	this.loginName = loginName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public String getIsLogin() {
	return isLogin;
}

public void setIsLogin(String isLogin) {
	this.isLogin = isLogin;
}

public int getCount() {
	return count;
}

public void setCount(int count) {
	this.count = count;
}

	
}
