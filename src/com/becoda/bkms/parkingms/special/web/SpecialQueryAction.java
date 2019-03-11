package com.becoda.bkms.parkingms.special.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.parkingms.special.SpecialConstants;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;
import com.becoda.bkms.parkingms.special.ucc.ISpecialUCC;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.util.BkmsContext;


public class SpecialQueryAction extends GenericPageAction {
	
    private  ParmsSpecialInfoVo special;

	public ParmsSpecialInfoVo getSpecial() {
		return special;
	}

	public void setSpecial(ParmsSpecialInfoVo special) {
		this.special = special;
	}

	public String querySpecialList(){
		HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Constants.USER_INFO);
        if(user==null){
        	return "usernull";
        }
		try {
			String state = request.getParameter("state");
			ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
			ParmsSpecialInfoVo sepVo = new ParmsSpecialInfoVo();
			List list = user.getUserRoleList();
			String roleId = "";
			for (int i = 0; i < list.size(); i++) {
				RoleInfoBO bo =  (RoleInfoBO)list.get(i);
				roleId += bo.getRoleId()+",";
			}
			//如果是区管理处处员 按userid检索
			if(roleId.indexOf(SpecialConstants.GLCCY)>-1){
				if(state.equals("0")||state.equals("99")){
					sepVo.setCheckUserId(user.getUserId());
				}
			}
			//如果是区管理处处长   交通委处员  交通委处长   按机构检索
			if(roleId.indexOf(SpecialConstants.GLCCZ)>-1||
				roleId.indexOf(SpecialConstants.JTWCY)>-1||
				roleId.indexOf(SpecialConstants.JTWCZ)>-1){
				sepVo.setCheckOrgId(user.getOrgId());
			}
			
			//退回查询处理
			if(state.equals("9")){
				//按照角色不同 查询不同状态
				if(roleId.indexOf(SpecialConstants.GLCCY)>-1){
					state = "9";
				}
				if(roleId.indexOf(SpecialConstants.GLCCZ)>-1){
					state = "10";
				}
				if(roleId.indexOf(SpecialConstants.TCBCY)>-1){
					state = "11";
				}
				if(roleId.indexOf(SpecialConstants.JTWCY)>-1){
					state = "12";
				}
				
			}
			
			sepVo.setState(state);
            List specialList = specUCC.querySpecailList(vo, sepVo);
            request.setAttribute("specialList", specialList);
            request.setAttribute("state", state);
            request.setAttribute("message", request.getParameter("message"));
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "list";
	}
	
}
