package com.becoda.bkms.qry.web;


import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryStaticBO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: lium
 * Date: 2015-4-19
 * Time: 16:09:02
 * To change this template use File | Settings | File Templates.
 */
public class StaticConditionSettingAction extends GenericAction {
    public String list() throws BkmsException {
        this.getStaticConditionPageInit(session, request);
        return "";
    }

    public String forSaveStaticCondition() throws BkmsException {
        this.getStaticConditionPageInit(session, request);
        String forward = doSaveStaticCondition(session, request);
        return forward;
    }

    //进入StaticCondition页面前执行

    public String getStaticConditionPageInit(HttpSession session, HttpServletRequest request) {
        try {
            String flag = request.getParameter("conditionFlag");
            String staticId = request.getParameter("staticId");
            String svos = request.getParameter("svos");
            if (svos == null) return null; //因为要提交两次，
            StaticVO[] vo = (StaticVO[]) QryTools.deSer1(svos);
            if ("addnew".equals(flag)) {
                //增加一个staticVO
                StaticVO staticvo = new StaticVO();
                QueryStaticBO bo = new QueryStaticBO();
                bo.setStaticId(String.valueOf(System.currentTimeMillis()));
                staticvo.setStatics(bo);
                staticId = bo.getStaticId();
                StaticVO[] vos = new StaticVO[vo.length + 1];
                for (int i = 0; i < vo.length; i++) {
                    vos[i] = vo[i];
                }
                vos[vos.length - 1] = staticvo;
                vo = vos;
            }
            request.setAttribute("svos", QryTools.ser(vo));
            request.setAttribute("STATIC_VO", vo);
            request.setAttribute("staticId", staticId);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return null;
    }

    public String doSaveStaticCondition(HttpSession session, HttpServletRequest request) {
        String svos = request.getParameter("svos");

        try {
            StaticVO[] vo = (StaticVO[]) QryTools.deSer1(svos);
            String[] operIds = request.getParameterValues("operId");
            String[] itemIds = request.getParameterValues("itemId");
            String[] groupIds = request.getParameterValues("groupId");
            String[] operValues = request.getParameterValues("operValue");
            String[] operHidden = request.getParameterValues("operHidden");
            String groupShow = request.getParameter("groupShow");
            String group = request.getParameter("group");
            String staticId = request.getParameter("staticId");
            String staticName = request.getParameter("staticName");

            StaticVO svo = null;
            if (staticId == null || "".equals(staticId))
                svo = vo[0];
            else {
                for (int i = 0; i < vo.length; i++) {
                    if (Tools.filterNull(staticId).equals(vo[i].getStatics().getStaticId()))
                        svo = vo[i];
                }
            }

            if (operIds != null) {
                for (int i = 0; i < operIds.length; i++) {
                    if ("allvalue".equals(operIds[i]) && operIds.length > 1)
                        throw new BkmsException("全值统计无法与其他统计条件并存,请修改", null, getClass());
                }


                for (int i = 0; i < vo.length; i++) {
                    if (Tools.filterNull(staticId).equals(vo[i].getStatics().getStaticId())) {
                        if ("allvalue".equals(operIds[0]) && i == 0)
                            throw new BkmsException("统计范围不能取全值", null, getClass());

                        vo[i].setCondi(null);
                        vo[i].getStatics().setGroup(null);
                        vo[i].getStatics().setGroupShow(null);
                    }
                }

                QueryConditionBO[] bos = new QueryConditionBO[operIds.length];
                for (int i = 0; i < operIds.length; i++) {

                    QueryConditionBO bo = new QueryConditionBO();
                    bo.setGroupId(groupIds[i]);
                    bo.setItemId(itemIds[i]);
                    bo.setOperator(operIds[i]);
                    bo.setSetId(itemIds[i].substring(0, 4));
                    bo.setStaticId(staticId);
                    bo.setText(operValues[i]);
                    bo.setValue(operHidden[i]);
                    //可以在这里增加hidden2 value2的处理
                    bos[i] = bo;
                }

                for (int i = 0; i < vo.length; i++) {
                    if (staticId.equals(vo[i].getStatics().getStaticId()))
                        svo = vo[i];
                }
                svo.setCondi(bos);
                svo.getStatics().setGroup(group);
                svo.getStatics().setGroupShow(groupShow);
                svo.getStatics().setName(staticName);

                //验证括号
                if (group != null && !"".equals(group)) {
                    Stack stack = new Stack();
                    for (int i = 0; i < group.length(); i++) {
                        if ('(' == group.charAt(i))
                            stack.push("{");
                        if (')' == group.charAt(i)) {
                            if (stack.size() == 0) {
                                throw new BkmsException("括号不匹配，请修改", null, this.getClass());
                            }
                            stack.pop();
                        }
                    }
                    if (stack.size() != 0)
                        throw new BkmsException("括号不匹配，请修改", null, this.getClass());
                }
            } else {
                svo.getStatics().setGroup(null);
                svo.getStatics().setGroupShow(null);
                svo.getStatics().setName(null);
                svo.setCondi(null);
            }
            request.setAttribute("svos", QryTools.ser(vo));
            request.setAttribute("QUERY_VO", vo);
            return "staticlist";
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return null;
        }
    }


}
