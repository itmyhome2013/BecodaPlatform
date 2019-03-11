package com.becoda.bkms.org.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.service.OrgService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-16
 * Time: 15:04:30
 * To change this template use File | Settings | File Templates.
 */
public class OrgTool {

    public static String rootType = "101,111,112,113,114,201,202,204,301,302";
    //    public static String ORG_ADD_PRIVILEGE = "0120";          由于党团工变为单独的模块，该参数废弃
    //    public static String PARTY_ADD_PRIVILEGE = "0121";        由于党团工变为单独的模块，该参数废弃
    //    public static String GROUP_ADD_PRIVILEGE = "0140";         由于党团工变为单独的模块，该参数废弃
    //    public static String LABOUR_ADD_PRIVILEGE = "0150";     由于党团工变为单独的模块，该参数废弃
    public static String ORG_EDIT_PRIVILEGE = "0122";
    public static String PARTY_EDIT_PRIVILEGE = "0123";
    public static String GROUP_EDIT_PRIVILEGE = "0141";
    public static String LABOUR_EDIT_PRIVILEGE = "0151";
    public static String ORG_DISMISS_PRIVILEGE = "0125";
    public static String PARTY_DISMISS_PRIVILEGE = "0124";
    public static String GROUP_DISMISS_PRIVILEGE = "0142";
    public static String LABOUR_DISMISS_PRIVILEGE = "0152";
    public static String ORG_DISMISS_EDIT_PRIVILEGE = "0126";
    public static String PARTY_DISMISS_EDIT_PRIVILEGE = "0127";
    public static String GROUP_DISMISS_EDIT_PRIVILEGE = "0143";
    public static String LABOUR_DISMISS_EDIT_PRIVILEGE = "0153";
    public static String ORG_SORT_PRIVILEGE = "0130";
    public static String PARTY_SORT_PRIVILEGE = "0131";
    public static String GROUP_SORT_PRIVILEGE = "0144";
    public static String LABOUR_SORT_PRIVILEGE = "0154";
    public static String ORG_CHART_PRIVILEGE = "0132";
    public static String PARTY_CHART_PRIVILEGE = "0133";
    public static String GROUP_CHART_PRIVILEGE = "0145";
    public static String LABOUR_CHART_PRIVILEGE = "0155";
    public static String ORG_DOC_PRIVILEGE = "0128";
    public static String PARTY_DOC_PRIVILEGE = "0129";
    public static String GROUP_DOC_PRIVILEGE = "0146";
    public static String LABOUR_DOC_PRIVILEGE = "0156";


    private static Hashtable pkType = new Hashtable();
    private static Hashtable orgCode = new Hashtable();

    static {
        pkType.put("0", "");
        pkType.put("1", "000");
        pkType.put("2", "000000");
        pkType.put("3", "000000000");
        pkType.put("4", "000000000000");

    }

    static {
        orgCode.put("0", "A");
        orgCode.put("1", "B");
        orgCode.put("2", "C");
        orgCode.put("3", "D");
        orgCode.put("4", "E");
        orgCode.put("5", "F");
        orgCode.put("6", "G");
        orgCode.put("7", "H");
        orgCode.put("8", "I");
        orgCode.put("9", "J");

    }

    public static String getPkType(String key) {
        String type = "";
        if (key != null) {
            Object s = (Object) pkType.get(key);
            if (s == null) {
                type = "";
            } else {
                type = (String) s;
            }
        }
        return type;
    }

    public static OrgBO getOrgByDept(String deptId) {
        OrgBO org = null;
        try {
            OrgService orgService = (OrgService) BkmsContext.getBean("org_orgService");
//            org = SysCacheTool.findOrgById(deptId);
            org = orgService.findOrgBO(deptId);
            if (org != null) {
                String type = Tools.filterNull(org.getOrgLevel());
                if (type.length() > 5) {
                    if (!("1".equals(type.substring(4, 5)) || "2".equals(type.substring(4, 5)))) {
//                        return OrgTool.getOrgByDept(org.getSuperId());
                        String orgId = org.getSuperId();
                        if ("-1".equals(orgId)) {
                            return org;
                        } else {
                            return OrgTool.getOrgByDept(org.getSuperId());
                        }
                    }
                }
            }

        } catch (Exception e) {

        }
        return org;


    }

    /**
     * 得到上级机构ID
     *
     * @param list  机构ID列表
     * @param orgId 查询机构ID
     * @return List
     */
    public static List querySuperOrgId(List list, String orgId) {
        if (orgId == null || "".equals(orgId)) return list;
        Org org = SysCacheTool.findOrgById(orgId);
        if (org != null) {
            String superId = org.getSuperId();
            if ("-1".equals(superId)) {
                list.add(org.getOrgId());
                return list;
            } else {
                list.add(org.getOrgId());
                return OrgTool.querySuperOrgId(list, org.getSuperId());
            }
        }
        return list;
    }

    public static String getOrgByDeptName(String deptId) {
        OrgBO org = null;
        String name = "";
        try {
            OrgService orgService = (OrgService) BkmsContext.getBean("org_orgService");
//            org = SysCacheTool.findOrgById(deptId);
            org = orgService.findOrgBO(deptId);
            if (org != null) {
                name = org.getName();
                String type = Tools.filterNull(org.getOrgLevel());
                if (type.length() > 5) {
                    if (!("1".equals(type.substring(4, 5)) || "2".equals(type.substring(4, 5)))) {
                        name = OrgTool.getOrgByDeptName(org.getSuperId()) + "->" + name;
                    }
                }
            }

        } catch (Exception e) {

        }
        return name;


    }


    /**
     * 生成机构隶属码
     *
     * @param orgType   机构类别
     * @param superCode 上级机构
     * @return
     */
    public static String getSubCode(String orgType, String superCode) {
        try {
            if (orgType == null || "".equals(orgType) || orgType.length() < 5)
                return "";
            if ("0891900".equals(orgType) || "0891300".equals(orgType)) {
                return "";
            }
            orgType = orgType.substring(4);
            StringBuffer code = new StringBuffer();
            if (rootType.indexOf(orgType) != -1) {
                return code.append(orgType).append("000000000000").toString();
            }
            String tempCode = superCode.replaceAll("000", "") + orgType;
            String len = String.valueOf((15 - tempCode.length()) / 3);
            return code.append(tempCode).append(OrgTool.getPkType(len)).toString();
        } catch (Exception e) {
            return "";
        }

    }


    public static String getOrgName(String orgId) {
        try {
            Org org = SysCacheTool.findOrgById(orgId);
            if (org != null) {
                return org.getName();
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

    public static String getBackOrgCode(String orgCode) {
        if (orgCode != null) {
            orgCode = orgCode.replaceAll("A", "0");
            orgCode = orgCode.replaceAll("B", "1");
            orgCode = orgCode.replaceAll("C", "2");
            orgCode = orgCode.replaceAll("D", "3");
            orgCode = orgCode.replaceAll("E", "4");
            orgCode = orgCode.replaceAll("F", "5");
            orgCode = orgCode.replaceAll("G", "6");
            orgCode = orgCode.replaceAll("H", "7");
            orgCode = orgCode.replaceAll("I", "8");
            orgCode = orgCode.replaceAll("J", "9");
            return orgCode;
        }
        return "";

    }

    public static boolean checkWageUnit(String orgId) {
//        throw new RuntimeException("方法未实现");
//        WageUnitBO bo = SysCacheTool.findWageUnit(orgId);
        return false;//bo != null;
    }

    public static String getSubCodeBySection(String subCode, String section) {
        if (subCode == null || "".equals(subCode)) {
            return "";
        }
        if (subCode.length() < 15) {
            return "";
        }
        if ("A".equals(section)) {
            subCode = subCode.substring(0, 3);
        } else if ("B".equals(section)) {
            subCode = subCode.substring(3, 6);
        } else if ("C".equals(section)) {
            subCode = subCode.substring(6, 9);
        } else if ("D".equals(section)) {
            subCode = subCode.substring(9, 12);
        } else if ("E".equals(section)) {
            subCode = subCode.substring(12, 15);
        }
        return subCode;
    }

    /**
     * @param orgs         按照层次以及treeId 进行排序的机构数组
     * @param notDrawOrgId 不需要加在的机构
     * @return
     */
    public static OrgChartElement FillOrgChartElement(OrgBO[] orgs, String notDrawOrgId) {
        // orgList = null;
        OrgChartElement rootElement = null;
        Hashtable hashElement = new Hashtable();
//      for(Iterator i = orgList.iterator();i.hasNext();){
//              OrgBO org = (OrgBO) i.next();
        for (int i = 0; i < orgs.length; i++) {
            OrgBO org = orgs[i];
            if (org.getOrgId().equals(notDrawOrgId)) {
                continue;
            }
            OrgChartElement currElement;
            if (hashElement.containsKey(org.getOrgId())) {
                currElement = (OrgChartElement) hashElement.get(org.getOrgId());
            } else {
                currElement = new OrgChartElement();
                currElement.setName(org.getName());
                currElement.setId(org.getOrgId());
                hashElement.put(org.getOrgId(), currElement);
                if (i == 0) {
                    rootElement = currElement;
                }
            }
            //找parent
            if (org.getSuperId() != null && hashElement.containsKey(org.getSuperId())) {
                OrgChartElement parent = (OrgChartElement) hashElement.get(org.getSuperId());
                if (parent != null) {
                    currElement.setParent(parent);
                    //给父结点填字结点
                    List list = parent.getChilds();
                    if (list == null) list = new ArrayList();
                    list.add(currElement);
                    parent.setChilds(list);
                }
            }
            //找next
            if (i < orgs.length - 1) {
                OrgBO nextBo = orgs[i + 1];
                if (Tools.filterNull(nextBo.getSuperId()).equals(org.getSuperId())) {
                    OrgChartElement nextElement = new OrgChartElement();
                    nextElement.setFront(currElement);
                    nextElement.setName(nextBo.getName());
                    nextElement.setId(nextBo.getOrgId());
                    currElement.setNext(nextElement);
                    hashElement.put(nextBo.getOrgId(), nextElement);
                }
            }
        }
        return rootElement;
    }

    /**
     * 画下级机构节点
     *
     * @param htInsideOrg 下级机构
     * @param out
     * @param superId
     * @param flag        是否画撤销机构 null|'' 不画
     * @throws IOException
     */
    public static void drawInsideOrgChart(Hashtable htInsideOrg, JspWriter out, String superId, String flag) throws IOException {
        if (htInsideOrg.containsKey(superId)) {
            List list = (List) htInsideOrg.get(superId);
            if (list != null && list.size() > 0) {
                StringBuffer node = new StringBuffer();
                String oid;
                String pid;
                String name;
                String code;
                for (int i = 0; i < list.size(); i++) {
                    OrgBO obj = (OrgBO) list.get(i);
                    if (obj != null) {
                        if (flag == null || "".equals(flag)) {
                            String cancel = obj.getOrgCancel();
                            if (Constants.YES.equals(cancel))
                                continue;
                        }
                    }
                    oid = obj.getOrgId();
                    pid = superId;

                    if (superId.equals(obj.getSuperId())) {
                        code = obj.getTreeId();
//                    } else if (superId.equals(obj.getSecondSuper())) {
//                        code = obj.getSecondTreeId();
                    } else {
                        continue;
                    }
                    name = obj.getName();

                    int level = code.length() / 3;
                    String open = "false";
                    node.delete(0, node.length());
                    node.append("var node").append(oid).append("=").append("new Node('")
                            .append(oid).append("','")
                            .append(code).append("','")
                            .append(name).append("','(")
                            .append(Tools.filterNullToZero(obj.getFactPersonNum())) //todo
//                            .append(")").append("',")
//                            .append(level).append(",")
                            .append(")").append("',")
                            .append(open).append(");");
                    out.println(node);
                    if (SysCacheTool.findOrgById(pid) != null) {
                        out.println("node" + pid + ".addSubnode(node" + oid + ");");
                    }
                    if (htInsideOrg.containsKey(oid)) {
                        drawInsideOrgChart(htInsideOrg, out, oid, flag);
                    }
                }
            }
        }

    }
}
