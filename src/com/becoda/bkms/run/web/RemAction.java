package com.becoda.bkms.run.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.pms.ucc.IRoleManageUCC;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryStaticBO;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.qry.ucc.IQueryUCC;
import com.becoda.bkms.qry.util.QryTools;
import com.becoda.bkms.run.pojo.bo.RemBO;
import com.becoda.bkms.run.pojo.bo.RemOrgScopeBO;
import com.becoda.bkms.run.pojo.bo.RemPersonScopeBO;
import com.becoda.bkms.run.pojo.vo.RemForm;
import com.becoda.bkms.run.ucc.IRemUCC;
import com.becoda.bkms.run.util.RunTools;
import com.becoda.bkms.sys.dao.ActivePageDAO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.CodeUtil;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.StringUtils;

import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-13
 * Time: 9:53:39
 * To change this template use File | Settings | File Templates.
 */
public class RemAction extends GenericPageAction {
    private RemForm remform;

    public RemForm getRemform() {
        return remform;
    }

    public void setRemform(RemForm remform) {
        this.remform = remform;
    }//进入编辑和新增
    public String find() throws BkmsException {
        String remId = Tools.filterNull(request.getParameter("remId"));
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
//        RemForm remform = (RemForm) form;
        if (!"".equals(remId)) {//编辑
            RemBO bo = ucc.findRemById(user, remId);
            Tools.copyProperties(remform, bo);
        } else {//新增
            remform.setCreator(SysCacheTool.findPersonById(user.getUserId()).getPersonId());
            remform.setCreateDate(Tools.getSysDate("yyyy-MM-dd"));
            remform.setCreateOrg(SysCacheTool.findPersonById(user.getUserId()).getOrgId());
            remform.setValidFlag("00900");//未启用
        }
        return "find";
    }
    //保存提醒
    public String save() throws BkmsException {
        IQueryUCC queryUcc = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
        RemBO bo = new RemBO();
//        RemForm remform = (RemForm) form;
        Tools.copyProperties(bo, remform);
        QueryVO queryvo = null;
        String serqryVO = request.getParameter(QryConstants.QRYSQL_qryVO);
        if (serqryVO == null || "".equals(serqryVO)) {
            queryvo = queryUcc.findQueryVO(bo.getRemCond());
        } else {
            queryvo = (QueryVO) QryTools.deSer1(serqryVO);
        }
        queryvo.setSelectStr("A001.ID, A001001,A001701,A001705");
        QueryItemBO[]item=new QueryItemBO[3];
        item[0]=new QueryItemBO();
            item[0].setItemId("A001001");
            item[0].setSetId("A001");

        item[1]=new QueryItemBO();
            item[1].setItemId("A001701");
            item[1].setSetId("A001");

        item[2]=new QueryItemBO();
            item[2].setItemId("A001705");
            item[2].setSetId("A001");

        queryvo.setItem(item);
        String remId = Tools.filterNull(bo.getRemId());
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        if (remId == null || "".equals(remId)) { //添加
            ucc.addRem(bo, queryvo, user);
        } else {  //修改
            ucc.updateRem(bo, queryvo, user);
        }
        super.showMessage("保存成功！");
        //返回列表页面
        return this.list();
    }

    public String setRemRule() throws BkmsException {
//        RemForm form1 = (RemForm) form;
        try {
            //设置条件后返回编辑页面
            String sql = request.getParameter(QryConstants.QRYSQL_hash);
            Hashtable hsSql = QryTools.deSer(sql);
            StaticVO[] svo = (StaticVO[]) hsSql.get(QryConstants.QRYSQL_staicVO);
            //条件 数组
            QueryConditionBO[] cbo = Tools.filterNull(svo).equals("") ? null : svo[0].getCondi();  //if(("Q").equals(vo.getQsType()))查询模式
            QueryStaticBO statics = Tools.filterNull(svo).equals("") ? null : svo[0].getStatics();
            if (statics != null && cbo != null && cbo.length > 0) {
                String group = statics.getGroup();  //A1 与 A2 与 (A3 或 A4)
                int count = cbo.length;
                Hashtable hash = new Hashtable();
                Hashtable operatorHash = QryTools.getOperatorByStr(); //替换操作符
                for (int i = 0; i < count; i++) {
                    QueryConditionBO qb = cbo[i];
                    String itemId = qb.getItemId();
                    String operator = qb.getOperator();
                    String text = qb.getText();
                    String groupId = qb.getGroupId();
                    String str = "";
                    String itemName = "";
                    InfoItemBO item = SysCacheTool.findInfoItem(itemId.substring(0, 4), itemId);
                    if (item != null) itemName = item.getItemName();
                    str = itemName + operatorHash.get(operator) + text;
                    hash.put(groupId, str);  //A1  年龄 小于 25
                }
                String condition = group.replaceAll("与", "并且");
                if (hash != null && !hash.isEmpty()) {
                    Iterator it = hash.keySet().iterator();
                    while (it.hasNext()) {
                        Object groupId = it.next();
                        condition = condition.replaceAll((String) groupId, (String) hash.get(groupId));
                    }
                }
                remform.setRemCondDesc(condition);
                String qrysql = (String) hsSql.get(QryConstants.QRYSQL_qrySql);
                qrysql = Endecode.base64Encode(qrysql);
                remform.setSql(qrysql);
            }
        } catch (Exception e) {
            e.printStackTrace();
          BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
            return "";
        }
        return "find";
    }




    //首页：查看提醒结果
    public String view() throws BkmsException {
        String remId = Tools.filterNull(request.getParameter("id"));
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        RemBO rembo = ucc.findRemById(user, remId);
        String qryId = rembo.getRemCond();
        IQueryUCC queryUcc = (IQueryUCC) BkmsContext.getBean("qry_queryUCC");
        Hashtable map = queryUcc.findSQL(qryId);
        TableVO tablevo = queryUcc.executeQuery(user, map, vo, QryConstants.PMS_TYPE_PERSON);
        request.setAttribute("tableVO", tablevo);
        return "view";
    }

    //首页：查看生日提醒结果
    public String viewBrithDay() throws BkmsException {
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        String scale = RunTools.buildQuerySql(user);

        String curDay = (Tools.getSysDate("yyyy-MM-dd")).substring(5);
        String sql = "SELECT A001.ID, A001001,A001007,A001011,A001701,A001705 FROM A001 WHERE ( A001730 = '00900' and A001755 = '00900' ) "
                + " AND substr(A001011,6) = '" + curDay + "'"
                + " ORDER BY A001.A001743, A001.A001745";

        int index_1 = sql.lastIndexOf("ORDER BY");
        String newsql = sql.substring(0, index_1) + " AND " + scale + " " + sql.substring(index_1);
        //构造header
        int index_2 = newsql.indexOf("FROM");
        String selectPart = (newsql.substring(6, index_2)).trim();
        String[] columns = Tools.getStringArray(selectPart, ",");
        //CellVO[] vos = new CellVO[columns.length];
        InfoItemBO[] itbs = new InfoItemBO[columns.length];

        for (int i = 0; i < columns.length; i++) {
            if ("A001.ID".equals(columns[i])) {
                InfoItemBO bo = SysCacheTool.findInfoItem("A001", "ID");
                //Tools.copyProperties(itb, bo);
                itbs[i] = bo;
            } else {
                InfoItemBO bo = SysCacheTool.findInfoItem("", columns[i].trim());
                //Tools.copyProperties(itb, bo);
                itbs[i] = bo;
            }
        }
        //activePageService.querySql(tablevo, newsql, null, 0, 0, false);
        TableVO tableVO = ucc.queryBirthList(user, newsql, itbs, vo, false);
        request.setAttribute("tableVO", tableVO);

        return "view";
    }

    //提醒首页
    public String list() throws BkmsException {
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        RemBO[] bos = null;
        bos = ucc.queryAllRem(SysCacheTool.findPersonById(user.getUserId()).getOrgId());

        if (bos != null && bos.length > 0) {
            bos = (RemBO[]) CodeUtil.codeInterpret(bos, "validFlag,creator,createOrg", "CODE,PE,OU"); //代码翻译
        }
        request.setAttribute("bos", bos);
        return "list";
    }



    //进入范围设置
    public String scopeSet() throws BkmsException {
        String id = Tools.filterNull(request.getParameter("remId"));
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
//        RemForm remform = (RemForm) form;
        //基本信息
        RemBO bo = ucc.findRemById(user, id);
        Tools.copyProperties(remform, bo);
        //机构范围
        String orgIds = "";
        String orgNames = "";
        RemOrgScopeBO[] bos_OrgScope = ucc.queryAllRemOrgScope(id);
        if (bos_OrgScope != null) {
            for (int i = 0; i < bos_OrgScope.length; i++) {
                String orgId = bos_OrgScope[i].getOrgId();
                Org org = SysCacheTool.findOrgById(orgId);
                if ((i + 1) == bos_OrgScope.length) {
                    orgIds = orgIds + orgId;
                    orgNames = orgNames + org.getName();
                } else {
                    orgIds = orgIds + orgId + ",";
                    orgNames = orgNames + org.getName() + ",";
                }
            }
        }
        request.setAttribute("rem_selectOrgIds", orgIds);
        request.setAttribute("rem_selectOrgNames", orgNames);
        //人员范围
        List persons = new ArrayList();
        RemPersonScopeBO[] bos_PersonScope = ucc.queryAllRemPersonScope(id, "01");
        if (bos_PersonScope != null) {
            for (int i = 0; i < bos_PersonScope.length; i++) {
                String personId = bos_PersonScope[i].getToId();
                Person personbo = SysCacheTool.findPersonById(personId);
                if (personbo != null) {
                    persons.add(personbo);
                }
            }
        }
        session.setAttribute("rem_selectPersons", persons);
        //角色范围
        List roles = new ArrayList();
        RemPersonScopeBO[] bos_PersonScope_role = ucc.queryAllRemPersonScope(id, "02");
        if (bos_PersonScope_role != null) {
            for (int i = 0; i < bos_PersonScope_role.length; i++) {
                String roleId = bos_PersonScope_role[i].getToId();
                IRoleManageUCC iucc = (IRoleManageUCC) BkmsContext.getBean("pms_roleManageUCC");
                RoleInfoBO role = iucc.findRoleInfo(roleId);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        session.setAttribute("rem_selectRoles", roles);
        return "scopeSet";
    }

    //去除角色
    public String delRole() throws BkmsException {
        String ids[] = request.getParameterValues("selected_role_ids");
        List roles = (List) session.getAttribute("rem_selectRoles");
        List newRoles = new ArrayList();
        if (roles != null) {
            boolean flag = false;
            for (int i = 0; i < roles.size(); i++) {
                String roleId = ((RoleInfoBO) roles.get(i)).getRoleId();
                for (int a = 0; a < ids.length; a++) {
                    if (ids[a].equals(roleId)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    flag = false;
                    continue;
                }
                newRoles.add(roles.get(i));
            }
        }
        session.setAttribute("rem_selectRoles", newRoles);
        //机构范围
        String orgIds = Tools.filterNull(request.getParameter("orgIds"));
        String orgNames = Tools.filterNull(request.getParameter("orgNames"));
        request.setAttribute("rem_selectOrgIds", orgIds);
        request.setAttribute("rem_selectOrgNames", orgNames);
        return "scopeSet";
    }


    //启用
    public String open() throws BkmsException {
        String ids[] = request.getParameterValues("chkblltnId");
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        boolean flag = false;
        for (int i = 0; i < ids.length; i++) {
            RemBO bo = ucc.findRemById(user, ids[i]);
            RemOrgScopeBO[] bos_OrgScope = ucc.queryAllRemOrgScope(bo.getRemId());
            RemPersonScopeBO[] bos_PersonScope = ucc.queryAllRemPersonScope(bo.getRemId(), "01");
            RemPersonScopeBO[] bos_PersonScope_role = ucc.queryAllRemPersonScope(bo.getRemId(), "02");
            if (bos_OrgScope == null && bos_PersonScope == null && bos_PersonScope_role == null) {
//                this.actionErrors.add(new ActionMessage("msg",
//                        "启用失败，[" + bo.getRemName() + "]还未进行提醒范围设置！", "", ""));
                   super.showMessage("启用失败，[" + bo.getRemName() + "]还未进行提醒范围设置！");
                flag = true;
                break;
            }
        }
        if (!flag) {
            ucc.startOrStopRem(ids, "00901", user);
//            this.actionErrors.add(new ActionMessage("msg", "启用成功！", "", ""));
            super.showMessage("启用成功!");
        }
        //返回列表页面
        return this.list();
    }

    //禁用
    public String close() throws BkmsException {
        String ids[] = request.getParameterValues("chkblltnId");
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        ucc.startOrStopRem(ids, "00900", user);
//        this.actionErrors.add(new ActionMessage("msg", "禁用成功！", "", ""));
        super.showMessage("禁用成功!");
        //返回列表页面
        return this.list();
    }

    //保存范围设置
    public String saveScopeSet() throws BkmsException {
        String remId = Tools.filterNull(request.getParameter("remId"));
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        String orgIds = Tools.filterNull(request.getParameter("orgIds"));
        List persons = (List) session.getAttribute("rem_selectPersons");
        List roles = (List) session.getAttribute("rem_selectRoles");
        ucc.saveRemScope(remId, orgIds, persons, roles, user);
//        this.actionErrors.add(new ActionMessage("保存成功！", "", ""));
         super.showMessage("保存成功!");
        //返回列表页面
        return this.list();
    }

    //选择人员范围
    public String selectPerson() throws BkmsException {
        String newSelectPersonIds = Tools.filterNull(request.getParameter("newSelectPersonIds"));
        String[] new_ids = Tools.getStringArray(newSelectPersonIds, ",");  //newids
        List persons = (List) session.getAttribute("rem_selectPersons");
        if (persons == null) {
            persons = new ArrayList();
        }
        boolean flag = false;
        for (int i = 0; i < new_ids.length; i++) {
            for (int ii = 0; ii < persons.size(); ii++) {
                String personId = ((Person) persons.get(ii)).getPersonId();
                if (new_ids[i].equals(personId)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                flag = false;
                continue;
            }
            Person personbo = SysCacheTool.findPersonById(new_ids[i]);
            persons.add(personbo);
        }
        session.setAttribute("rem_selectPersons", persons);
        //机构范围
        String orgIds = Tools.filterNull(request.getParameter("orgIds"));
        String orgNames = Tools.filterNull(request.getParameter("orgNames"));
        request.setAttribute("rem_selectOrgIds", orgIds);
        request.setAttribute("rem_selectOrgNames", orgNames);
        return "scopeSet";
    }

    //选择角色范围
    public String selectRole() throws BkmsException {
        String newSelectRoleIds = Tools.filterNull(request.getParameter("newSelectRoleIds"));
        String[] new_ids = Tools.getStringArray(newSelectRoleIds, ",");  //newids
        List roles = (List) session.getAttribute("rem_selectRoles");
        IRoleManageUCC ucc = (IRoleManageUCC) BkmsContext.getBean("pms_roleManageUCC");
        if (roles == null) {
            roles = new ArrayList();
        }
        boolean flag = false;
        for (int i = 0; i < new_ids.length; i++) {
            for (int ii = 0; ii < roles.size(); ii++) {
                String roleId = ((RoleInfoBO) roles.get(ii)).getRoleId();
                if (new_ids[i].equals(roleId)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                flag = false;
                continue;
            }
            RoleInfoBO role = ucc.findRoleInfo(new_ids[i]);
            roles.add(role);
        }
        session.setAttribute("rem_selectRoles", roles);
        //机构范围
        String orgIds = Tools.filterNull(request.getParameter("orgIds"));
        String orgNames = Tools.filterNull(request.getParameter("orgNames"));
        request.setAttribute("rem_selectOrgIds", orgIds);
        request.setAttribute("rem_selectOrgNames", orgNames);
        return "scopeSet";
    }

    //删除
    public String del() throws BkmsException {
        String ids[] = request.getParameterValues("chkblltnId");
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        ucc.deleteRem(ids, user);
        super.showMessageDetail("删除成功！");
        return this.list();
    }

    //更多
    public String more() throws BkmsException {
        IRemUCC ucc = (IRemUCC) BkmsContext.getBean("run_remUCC");
        ArrayList list = ucc.queryRemByUserId(user.getUserId(), user);
        int size = ucc.queryRemBrithDayByUserId(user);
        request.setAttribute("flag", size > 0 ? "1" : "0");
        request.setAttribute("bos", list);
        return "more";
    }

    //设置
    public String set() throws BkmsException {
        return "find";
    }

    //去除人员
    public String delPerson() throws BkmsException {
        String ids[] = request.getParameterValues("selected_person_ids");
        List persons = (List) session.getAttribute("rem_selectPersons");
        List newPersons = new ArrayList();
        if (persons != null) {
            boolean flag = false;
            for (int i = 0; i < persons.size(); i++) {
                String personId = ((Person) persons.get(i)).getPersonId();
                for (int a = 0; a < ids.length; a++) {
                    if (ids[a].equals(personId)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    flag = false;
                    continue;
                }
                newPersons.add(persons.get(i));
            }
        }
        session.setAttribute("rem_selectPersons", newPersons);
        //机构范围
        String orgIds = Tools.filterNull(request.getParameter("orgIds"));
        String orgNames = Tools.filterNull(request.getParameter("orgNames"));
        request.setAttribute("rem_selectOrgIds", orgIds);
        request.setAttribute("rem_selectOrgNames", orgNames);
        return "scopeSet";
    }
}
