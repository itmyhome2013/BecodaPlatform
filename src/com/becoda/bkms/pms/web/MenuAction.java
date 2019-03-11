package com.becoda.bkms.pms.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
//import com.becoda.bkms.common.web.GenericEventAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.pojo.bo.OperateBO;
import com.becoda.bkms.pms.ucc.IOperateUCC;
import com.becoda.bkms.sys.ucc.ILoginLogUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ActionMessage;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.Enumeration;
import java.util.List;

import org.apache.struts2.components.ActionMessage;
import org.apache.struts2.ServletActionContext;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-4
 * Time: 16:20:23
 * To change this template use File | Settings | File Templates.
 */
public class MenuAction extends GenericAction {

    //查询所有模块
    public String findAllModule() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest(); 
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            List list = ucc.queryAllModule();
            request.setAttribute("moduleList", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    /**
     * 清除登录账号信息
     * @return
     * @throws BkmsException
     */
    public String clearAccountInfo() throws BkmsException{
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ActionContext ctx=ActionContext.getContext();
        ServletContext application = session.getServletContext();
        try {
            ILoginLogUCC log = (ILoginLogUCC) BkmsContext.getBean("login_LogUCC");
            String sessionId = session.getId();
            Timestamp stmp = new Timestamp(session.getLastAccessedTime());
            
            Enumeration<?> enumeration = application.getAttributeNames();
         	while (enumeration.hasMoreElements()) {
         		String name = enumeration.nextElement().toString();
         		Object value = application.getAttribute(name);
         		//System.out.println(name.length() +  ",<B>" + name + "</B>=" + value + "<br>/n");
         		if(name.length() < 10 && !"管理员".equals(name)){
         			log.removeLog(sessionId, stmp); //记录退出日志
                    //session.removeAttribute(user.getName() + ":count");
                    application.removeAttribute(name);//系统退出，移除该用户的sessionId
         		}
         	}
            
        } catch (BkmsException he) {
            he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), he, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        } catch (Exception e) {
            //将action中抛出的所有非BkmsException异常包装成一个BkmsException异常
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("出现错误").toString(), e, this.getClass());
            ctx.put("errorsMsg", " <script> alert('"+he.getFlag()+he.getCause().getMessage()+"') </script>");
        }
        
        
    	return "clearAccountInfo";
    }
    
    /**
     * 清除所有账号信息
     * @return
     * @throws BkmsException
     */
    public String clear() throws BkmsException{
    	HttpServletRequest request = ServletActionContext.getRequest();
        HttpSession session = request.getSession();
        ActionContext ctx=ActionContext.getContext();
        ServletContext application = session.getServletContext();
        try {
            ILoginLogUCC log = (ILoginLogUCC) BkmsContext.getBean("login_LogUCC");
            String sessionId = session.getId();
            Timestamp stmp = new Timestamp(session.getLastAccessedTime());
            
            Enumeration<?> enumeration = application.getAttributeNames();
         	while (enumeration.hasMoreElements()) {
         		String name = enumeration.nextElement().toString();
         		Object value = application.getAttribute(name);
         		//System.out.println(name.length() +  ",<B>" + name + "</B>=" + value + "<br>/n");
         		if(name.length() < 10){
         			log.removeLog(sessionId, stmp); //记录退出日志
                    application.removeAttribute(name);//系统退出，移除该用户的sessionId
         		}
         	}
            
        } catch (BkmsException he) {
            he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), he, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        } catch (Exception e) {
            //将action中抛出的所有非BkmsException异常包装成一个BkmsException异常
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("出现错误").toString(), e, this.getClass());
            ctx.put("errorsMsg", " <script> alert('"+he.getFlag()+he.getCause().getMessage()+"') </script>");
        }
        
        
    	return "success";
    }

    //获得模块下的所有菜单
    public String menuList() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String treeId = request.getParameter("setId");
            List list = ucc.queryMenusByTreeId(treeId);
            OperateBO bo = ucc.getOperateByTreeId(treeId);
            request.setAttribute("menuList", list);
            request.setAttribute("moduleBo", bo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "menuList";
    }

    //删除模块
    public String deleteModule() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String[] keys = request.getParameterValues("chk");
            if (keys != null && !"".equals(keys)) {
                for (int i = 0; i < keys.length; i++) {
                    List list = ucc.queryMenusByOpeId(keys[i]);
                    if (list != null) {
                        if (list.size() == 1) {
                            ucc.deleteOperateById(keys[i], user);
                        } else {
                            this.showMessage("模块下有菜单，不能进行删除操作！");
                        }
                    }
                }
            }
//            this.showMessage("删除成功！");
            List list = ucc.queryAllModule();
            request.setAttribute("moduleList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "success";
    }
  //启用模块
    public String moduleOpen() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String[] keys = request.getParameterValues("chk");
            if (keys != null && !"".equals(keys)) {
                for (int i = 0; i < keys.length; i++) {
                    List list = ucc.queryMenusByOpeId(keys[i]);
                    if (list != null) {
                            ucc.moduleOpen(keys[i], user);
                    }
                }
            }
//            this.showMessage("删除成功！");
            List list = ucc.queryAllModule();
            request.setAttribute("moduleList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "success";
    }
  //禁用模块
    public String moduleClose() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String[] keys = request.getParameterValues("chk");
            if (keys != null && !"".equals(keys)) {
                for (int i = 0; i < keys.length; i++) {
                    List list = ucc.queryMenusByOpeId(keys[i]);
                    if (list != null) {
                            ucc.moduleClose(keys[i], user);
                    }
                }
            }
//            this.showMessage("删除成功！");
            List list = ucc.queryAllModule();
            request.setAttribute("moduleList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "success";
    }
    //删除菜单
    public String deleteMenu() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String[] keys = request.getParameterValues("chk");
            if (keys != null && !"".equals(keys)) {
                for (int i = 0; i < keys.length; i++) {
                    List list = ucc.queryMenusByOpeId(keys[i]);
                    if (list != null) {
                        if (list.size() == 1) {
                            ucc.deleteOperateById(keys[i], user);
                        } else {
                            this.showMessage("菜单下有子菜单，不能进行删除操作！");
                        }
                    }
                }
            }
            String treeId = Tools.filterNull(request.getParameter("treeId"));
//            this.showMessage("删除成功！");
            OperateBO bo = ucc.getOperateByTreeId(treeId);
            request.setAttribute("moduleBo", bo);
            List list = ucc.queryMenusByTreeId(treeId);
            request.setAttribute("menuList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "menuList";
    }

    //编辑模块
    public String editModule() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            OperateBO bo = null;
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String operateId = request.getParameter("setId");
            String flag = request.getParameter("isUpdate");
            String opeName = Tools.filterNull(request.getParameter("opeName"));
            String opeModule = Tools.filterNull(request.getParameter("opeModule"));
            String opeFlag = Tools.filterNull(request.getParameter("opeFlag"));
            String abbreviation = Tools.filterNull(request.getParameter("abbreviation"));
            String moduleIntro = Tools.filterNull(request.getParameter("moduleIntro"));
            String moduleNum = Tools.filterNull(request.getParameter("moduleNum"));
            String opeUrl = Tools.filterNull(request.getParameter("opeUrl"));
            if ("".equals(operateId) && "isUpdate".equals(flag)) {//创建模块
                bo = new OperateBO();
                bo.setOperateName(opeName);
                bo.setModuleID(opeModule);
                bo.setOperateType(PmsConstants.MENU_TYPE_MENU);
                bo.setSysFlag(opeFlag);
                bo.setAbbreviation(abbreviation);
                bo.setModuleIntro(moduleIntro);
                bo.setModuleNum(moduleNum);
                bo.setUrl(opeUrl);
                bo.setModuleStatus("0");
                ucc.saveOperate(bo, true, user);
                this.showMessage("创建成功！");
                request.setAttribute("isUpdate", "isUpdate");
            } else if ("isUpdate".equals(flag) && operateId != null) {//update模块
                bo = ucc.getOperateById(operateId);
                bo.setOperateId(operateId);
                bo.setOperateName(opeName);
                bo.setModuleID(opeModule);
                bo.setOperateType(PmsConstants.MENU_TYPE_MENU);
                bo.setSysFlag(opeFlag);
                bo.setAbbreviation(abbreviation);
                bo.setModuleIntro(moduleIntro);
                bo.setModuleNum(moduleNum);
                bo.setUrl(opeUrl);
                ucc.updateOperate(bo, user);
                this.showMessage("保存成功！");
                request.setAttribute("isUpdate", "isUpdate");
            } else {//获得模块
                bo = ucc.getOperateById(operateId);
            }
            request.setAttribute("OperateBO", bo);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "moduleEdit";
    }

    //跳转到菜单编辑页面
    public String gotoEditMenu() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String operateId = Tools.filterNull(request.getParameter("setId"));
            String superId = Tools.filterNull(request.getParameter("superId"));
            OperateBO menuBo = null;
            //更新
            if (null != operateId && !"".equals(operateId)) {
                menuBo = ucc.getOperateById(operateId);
                OperateBO superMenuBo = ucc.getOperateById(menuBo.getSuperId());
                OperateBO moduleBo = ucc.getOperateById(superId);
                request.setAttribute("superMenuBo", superMenuBo);
                request.setAttribute("moduleBo", moduleBo);
            } else {
                //新建
                menuBo = new OperateBO();
                OperateBO moduleBo = ucc.getOperateById(superId);
                menuBo.setModuleID(moduleBo.getModuleID());
                menuBo.setSuperId(moduleBo.getOperateId());
                menuBo.setSysFlag(moduleBo.getSysFlag());
                request.setAttribute("moduleBo", moduleBo);
            }
            request.setAttribute("menuBo", menuBo);
            List list = ucc.queryAllModule();
            request.setAttribute("moduleList", list);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "menuEdit";
    }

    //创建或更新菜单
    public String saveOrUpdateMenu() throws BkmsException {
        HttpServletRequest request = ServletActionContext.getRequest();
        try {
            String opeName = Tools.filterNull(request.getParameter("opeName"));
            String opeModule = Tools.filterNull(request.getParameter("opeModule"));
            String opeType = Tools.filterNull(request.getParameter("opeType"));
            String opeUrl = Tools.filterNull(request.getParameter("opeUrl"));
            String opeSuperId = Tools.filterNull(request.getParameter("opeSuperId"));
            String sysflag = Tools.filterNull(request.getParameter("sysflag"));
            String treeId = Tools.filterNull(request.getParameter("treeId"));
            String moduleBoTreeId = Tools.filterNull(request.getParameter("moduleBo"));

            IOperateUCC ucc = (IOperateUCC) BkmsContext.getBean("pms_operateUCC");
            String operateId = request.getParameter("setId");
            OperateBO bo = ucc.getOperateById(operateId);
            if (bo == null) {
                OperateBO oBo = new OperateBO();
                oBo.setOperateName(opeName);
                oBo.setModuleID(opeModule);
                oBo.setUrl(opeUrl);
                oBo.setSuperId(opeSuperId);
                oBo.setSysFlag(sysflag);
                if (opeType == null || "".equals(opeType)) {
                    oBo.setOperateType(PmsConstants.MENU_TYPE_MENU);//菜单为1，按钮为2
                } else {
                    oBo.setOperateType(opeType);
                }
                ucc.saveOperate(oBo, false, user);
                this.showMessage("创建成功！");
                request.setAttribute("OperateBO", oBo);
                List list = ucc.queryMenusByTreeId(treeId);
                request.setAttribute("menuList", list);
                OperateBO moduleBo = ucc.getOperateByTreeId(treeId);
                request.setAttribute("moduleBo", moduleBo);
            } else {
                bo = ucc.updateOperate(operateId, opeName, opeModule, opeUrl, opeSuperId, sysflag, treeId, opeType, user);
                this.showMessage("保存成功！");
                request.setAttribute("OperateBO", bo);
                List list = ucc.queryMenusByTreeId(moduleBoTreeId);
                request.setAttribute("menuList", list);
                OperateBO moduleBo = ucc.getOperateByTreeId(treeId);
                request.setAttribute("moduleBo", moduleBo);
            }
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "menuList";
    }
}
