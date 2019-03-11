package com.becoda.bkms.qry.web;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.StringUtils;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.Stack;

public class QueryOrgSettingAction extends GenericAction {
    /**
     * 本方法对应查询定义的查询按钮<BR>
     * 应该先调用updateQueryVO方法.更新session的vo.<BR>
     * 本方法调用ucc的传queryVo查询方法,查询结果放到session中
     *
     * @return 返回值为autoClose的页面.
     */
    public String forQuery() throws BkmsException {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            String flag = request.getParameter("flag");
            if ("querylist".equals(flag)) {
                //从查询列表中直接查询需要根据qryId读取queryvo到session中
                String qryId = request.getParameter("qryId");
                QueryVO queryvo = qm.findQueryVO(qryId);
                
                String addedCondition = request.getParameter("addedCondition");
                queryvo.setAddedCondition(addedCondition);
                
                request.setAttribute("QUERY_VO", queryvo);
            } else {
                //从查询编辑页面进入则需要更新queryvo
                QueryVO queryvo = updateQueryVO(null, session, request);
                request.setAttribute("QUERY_VO", queryvo);
            }
            QueryVO vo = (QueryVO) request.getAttribute("QUERY_VO");
            if (vo == null) {
//                 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
//                 this.addActionError(he.getFlag()+he.getCause().getMessage());
                return null;
            }

            //rebuild sql
            Hashtable tm = qm.buildQuerySqlHash(null, vo);
            Hashtable hsSql = new Hashtable();
            String select = (String) tm.get(QryConstants.SQL_SELECT_PART);
            String from = (String) tm.get(QryConstants.SQL_FROM_PART);
            String where = (String) tm.get(QryConstants.SQL_WHERE_PART);

            String order = (String) tm.get(QryConstants.SQL_ORDER_PART);
            String sql = "select " + select + " from " + from
                    + ("".equals(where) ? "" : " where " + where)
                    + ("".equals(order) ? "" : " order by " + order);
            hsSql.put(QryConstants.QRYSQL_qrySql, sql);
            hsSql.put(QryConstants.QRYSQL_showField, select);
            hsSql.put(QryConstants.QRYSQL_from, from);
            hsSql.put(QryConstants.QRYSQL_where, StringUtils.trimToEmpty(where));
            hsSql.put(QryConstants.QRYSQL_order, StringUtils.trimToEmpty(order));
            //序列化这个可以抽取成一个方法
            String isStaticVO = request.getParameter("isStaticVO");
            if ("1".equals(isStaticVO)) hsSql.put(QryConstants.QRYSQL_staicVO, vo.getStatics());
            request.setAttribute(QryConstants.QRYSQL_hash, QryTools.ser(hsSql));
            request.setAttribute(QryConstants.QRYSQL_qryVO, QryTools.ser(vo));

            if ("Y".equals(request.getParameter("pop")))
                return "close";
            else
                return "queryresult";
        } catch (Exception e) {
            this.showMessageDetail(e.getMessage());
            return "org";
        }
    }

    public String forSave() throws BkmsException {
        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            QueryVO vo = updateQueryVO(null, session, request);
            if (vo == null)
                throw new BkmsException("查询定义为空，无法保存", null, this.getClass());

            QueryVO newVO = qm.createQuery(vo);
            request.setAttribute("QUERY_VO", newVO);
            super.showMessageDetail("保存成功");
        } catch (Exception e) {
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return "";
    }

    public String list() throws BkmsException {
        initQueryVO(session, request);
        return "org";
    }

    public QueryVO updateQueryVO(String flag, HttpSession session, HttpServletRequest request) throws BkmsException {
        QueryVO vo = initQueryVO(session, request);
        String classId = request.getParameter("classId");
        String qsType = request.getParameter("qsType");
        String setType = request.getParameter("setType");
        String qryName = request.getParameter("qryName");
        String sysFlag = request.getParameter("sysFlag");
        String qryId = request.getParameter("qryId");
        String unitType = request.getParameter("unitType");
        String addedCondition = request.getParameter("addedCondition");
        String historySet = Tools.filterNull(request.getParameter("historySet"));
        vo.setCreateDate(Tools.getSysDate("yyyy-MM-dd"));
        User user = (User) session.getAttribute(Constants.USER_INFO);
        vo.setCreateUser(user.getUserId());
        vo.setQryId(qryId);
        vo.setHistorySet(historySet);
        if (sysFlag != null)
            vo.setSysFlag(sysFlag);
        if (qryName != null)
            vo.setName(qryName);
        if (classId != null)
            vo.setClassId(classId);
        if (addedCondition != null)
            vo.setAddedCondition(addedCondition);
        if (qsType != null)
            vo.setQsType(qsType);
        if (setType != null)
            vo.setSetType(setType);
        if (unitType != null)
            vo.setUnitType(unitType);

        {
            //机构范围设置页面
            String orgIds = request.getParameter("orgIds");
            String orgNames = request.getParameter("orgNames");
            vo.setOrgIds(orgIds);
            vo.setOrgNames(orgNames);
            if ("2".equals(request.getParameter("operFlag")))
                vo.setOperFlag("2");
            else
                vo.setOperFlag("1");

        }
        {
            String[] operIds = request.getParameterValues("operId");
            String[] itemIds = request.getParameterValues("itemId");
            String[] groupIds = request.getParameterValues("groupId");
            String[] operValues = request.getParameterValues("operValue");
            String[] operHidden = request.getParameterValues("operHidden");
            String groupShow = request.getParameter("groupShow");
            String group = request.getParameter("group");
            String staticId = request.getParameter("staticId");
            String staticName = request.getParameter("staticName");

            QueryConditionBO[] bos = null;
            if (operIds == null) {
                if (staticId == null || "".equals(staticId)) {
                    vo.getStatics()[0].setCondi(null);
                    vo.getStatics()[0].getStatics().setGroup(null);
                    vo.getStatics()[0].getStatics().setGroupShow(null);
                } else {
                    for (int i = 0; i < vo.getStatics().length; i++) {
                        if (Tools.filterNull(staticId).equals(vo.getStatics()[i].getStatics().getStaticId())) {
                            vo.getStatics()[i].setCondi(null);
                            vo.getStatics()[i].getStatics().setGroup(null);
                            vo.getStatics()[i].getStatics().setGroupShow(null);
                        }
                    }
                }
            } else {
                bos = new QueryConditionBO[operIds.length];
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
            }
            StaticVO svo = null;
            if (staticId == null || "".equals(staticId))
                svo = vo.getStatics()[0];
            for (int i = 0; i < vo.getStatics().length; i++) {
                if (staticId.equals(vo.getStatics()[i].getStatics().getStaticId()))
                    svo = vo.getStatics()[i];
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
        }
        {
            String[] showItems = request.getParameterValues("showItem");
            String[] orderItems = request.getParameterValues("orderItem");
            Hashtable ordertable = new Hashtable();
            if (orderItems != null && orderItems.length > 0) {
                for (int i = 0; i < orderItems.length; i++) {
                    String orderItemId = orderItems[i].substring(2);
                    String orderFlag = orderItems[i].substring(0, 1);
                    ordertable.put(orderItemId, orderFlag + "|" + i);
                }
            }

            if (showItems != null && showItems.length > 0) {
                QueryItemBO[] bos = new QueryItemBO[showItems.length];
                for (int i = 0; i < showItems.length; i++) {
                    QueryItemBO bo = new QueryItemBO();
                    String showItem = showItems[i];
                    if (showItem.indexOf("SHOWID") != -1) {
                        //显示id
                        bo.setItemId(showItem.substring(6));
                        bo.setShowId(1);
                        bo.setSetId(bo.getItemId().substring(0, 4));
                    } else {
                        bo.setItemId(showItem);
                        bo.setSetId(showItem.substring(0, 4));
                        bo.setShowId(0);
                    }
                    if (bo.getSetId().equals(historySet))
                        bo.setShowHistory(1);
                    //set order
                    String tmp = (String) ordertable.get(bo.getItemId());
                    if (tmp == null)
                        bo.setOrderFlag(null);
                    else {
                        bo.setOrderFlag(String.valueOf(tmp.charAt(0)));
                        bo.setOrderSeq(Integer.parseInt(String.valueOf(tmp.charAt(2))));
                        ordertable.remove(bo.getItemId());
                    }
                    bos[i] = bo;
                }
                vo.setItem(bos);
            } else
                throw new BkmsException("显示项不能为空", null, this.getClass());
        }
        return vo;
    }


    /**
     * 本方法判断session中有否queryVO 若有则清除该vo后重新建立新的queryVO,<BR>
     * 建立新的queryvo需要从数据库中读取默认显示项的定义,默认显示类别从settingBb的属性里里取.<BR>
     * 初始化static等.
     */

    public QueryVO initQueryVO(HttpSession session, HttpServletRequest request) {
        String qryId = Tools.filterNull(request.getParameter("qryId"));
        //此处参数flag改为flag2,在页面中赋值为与flag一致,仅用于在查询中判断是否第一次进入(flag参数改为标识步骤)
        String flag2 = Tools.filterNull(request.getParameter("flag2"));
        String resetFlag = Tools.filterNull(request.getParameter("resetFlag"));

        try {
            IQueryUCC qm = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
            if (!"".equals(qryId)) {
                //load queryvo
                QueryVO vo = qm.findQueryVO(qryId);
                request.setAttribute("QUERY_VO", vo);
                return vo;
            } else if ("Y".equalsIgnoreCase(resetFlag) || request.getAttribute("QUERY_VO") == null) {
                String qsType = request.getParameter("qsType");
                String setType = request.getParameter("setType");
                String classId = request.getParameter("classId");
                request.setAttribute("first", "Y");   //新建query
                QueryVO defaultVO = qm.findDefaultQueryVO(setType, qsType, classId);
                //新建vo时写入创建人id
                User user = (User) session.getAttribute(Constants.USER_INFO);
                if (user != null)
                    defaultVO.setCreateUser(user.getUserId());
                request.setAttribute("QUERY_VO", defaultVO);
                return defaultVO;

            }
        } catch (Exception e) {
             BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        return null;
    }
}
