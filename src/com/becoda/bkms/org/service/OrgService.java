package com.becoda.bkms.org.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.OrgConstants;
import com.becoda.bkms.org.dao.OrgDAO;
//import com.becoda.bkms.org.pojo.bo.BakTimeBO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
//import com.becoda.bkms.org.pojo.vo.BakTimeVO;
//import com.becoda.bkms.org.pojo.vo.OrgChangeVO;
import com.becoda.bkms.org.pojo.vo.OrgSetVO;
import com.becoda.bkms.org.pojo.vo.OrgVO;
//import com.becoda.bkms.post.pojo.bo.PostBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-17
 * Time: 16:33:26
 * To change this template use File | Settings | File Templates.
 */
public class OrgService {
    private OrgDAO orgDAO;

    public OrgDAO getOrgDAO() {
        return orgDAO;
    }

    public void setOrgDAO(OrgDAO orgDAO) {
        this.orgDAO = orgDAO;
    }

    public List queryOrg() throws RollbackableException {
        Org org = new Org();
        return orgDAO.findByExample(org, "orgSort");
    }

    public Org findOrg(String orgId) throws RollbackableException {
        return (Org) orgDAO.findBoById(Org.class, orgId);
    }

    public OrgBO findOrgBO(String orgId) throws RollbackableException {
        return (OrgBO) orgDAO.findBoById(OrgBO.class, orgId);
    }

    public OrgBO findOrgBOByCode(String code) throws RollbackableException {
        OrgBO bo = new OrgBO();
        bo.setOrgCode(code);
        return (OrgBO) orgDAO.findByExample(bo).get(0);
    }

    public List queryOrgBySuper(String superId) throws RollbackableException {
        Org org = new Org();
        org.setSuperId(superId);
        return orgDAO.findByExample(org, "orgSort");
    }

    /**
     * 检查机构下是否还有下级正常机构
     *
     * @param ids
     * @throws RollbackableException
     */
    public void checkOrgSubValidOrg(String[] ids) throws RollbackableException {
        for (int i = 0; i < ids.length; i++) {
            Org o = SysCacheTool.findOrgById(ids[i]);
            if (o != null) {
                int count = orgDAO.queryCountBySubValidOrg(o.getTreeId());
                if (count > 0) {
                    throw new RollbackableException(o.getName() + "机构下还有正常机构,不能撤销", this.getClass());
                }
            }
        }

    }

    /**
     * 检查机构下是否还有下级机构
     *
     * @param ids
     * @throws RollbackableException
     */
    public void checkOrgSubOrg(String[] ids) throws RollbackableException {
        for (int i = 0; i < ids.length; i++) {
            Org o = SysCacheTool.findOrgById(ids[i]);
            if (o != null) {
                int count = orgDAO.queryCountBySubOrg(o.getTreeId());
                if (count > 0) {
                    throw new RollbackableException(o.getName() + "下还有机构,不能合并或删除", this.getClass());
                }
            }
        }
    }

    /**
     * 检查机构下是否还有人
     *
     * @param ids
     * @throws RollbackableException
     */
    public void checkPerByOrg(String[] ids) throws RollbackableException {
        for (int i = 0; i < ids.length; i++) {
            Org o = SysCacheTool.findOrgById(ids[i]);
            if (o != null) {
                int count = orgDAO.queryCountPerByOrg(o.getTreeId());
                if (count > 0) {
                    throw new RollbackableException("机构下还有人员,不能撤销", this.getClass());
                }
            }
        }
    }

    /**
     * 检查机构下是否还有岗位
     *
     * @param ids
     * @throws RollbackableException
     */
    public void checkPostByOrg(String[] ids) throws RollbackableException {
        for (int i = 0; i < ids.length; i++) {
            int count = orgDAO.queryCountPostByOrg(ids[i]);
            if (count > 0) {
                throw new RollbackableException("机构下还有岗位或者岗位模板,不能撤销", this.getClass());
            }
        }
    }


    /**
     * 查询某个机构下的所有的下级机构，不包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO[] queryAllOrgBySuper(String superTreeId) throws RollbackableException {
        return orgDAO.queryAllOrgBySuper(superTreeId, "");
    }

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO[] queryAllOrgBySuperSelf(String superTreeId) throws RollbackableException {
        return orgDAO.queryAllOrgBySuperSelf(superTreeId, "");
    }


    /**
     * 查询某个机构下的所有的下级机构，不包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO[] queryAllOrgBySuper(String superTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryAllOrgBySuper(superTreeId, cancel);
    }

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO[] queryAllOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryAllOrgBySuperSelf(superTreeId, cancel);
    }

    /**
     * 查询机构代码是否重复
     *
     * @param orgCode
     * @return
     * @throws RollbackableException
     */
    public int queryOrgCodeCount(String orgCode, String orgId) throws RollbackableException {
        return orgDAO.queryOrgCodeCount(orgCode, orgId);
    }

    /**
     * 查询同一父类机构下是否有机构名重复
     *
     * @param superId
     * @param newOrgName
     * @return
     * @throws RollbackableException
     */
    public List queryOrgName(String superId, String newOrgName) throws RollbackableException {
        return orgDAO.queryOrgName(superId, newOrgName);
    }


   

    /**
     * 根据TreeId查询机构
     *
     * @param treeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO findOrgByTreeId(String treeId) throws RollbackableException {
        return orgDAO.findOrgByTreeId(treeId);
    }

    public String createOrg(OrgVO orgvo, User user) throws RollbackableException {
        throw new RollbackableException("方法未实现", this.getClass());

    }

    /**
     * add by sunmh 2015-09-06
     */
    public String queryOrgList(TableVO table, String orgName, String superId, String orgType, int pageNum, int rowNum, String cancel, User user, String flag) throws RollbackableException {
        return queryOrgList(table, orgName, superId, orgType, pageNum, rowNum, cancel, user, flag, OrgConstants.DEFAULT_QUERY_ORG);
    }

    //孙明辉修改,支持传入queryID
    public String queryOrgList(TableVO table, String orgName, String superId, String orgType, int pageNum, int rowNum, String cancel, User user, String flag, String queryId) throws RollbackableException {
        throw new RollbackableException("方法未实现", this.getClass());

    }

    /**
     * 机构撤销
     *
     * @param orgsetvo
     * @param ids
     * @throws RollbackableException
     */
    public void updateDismissOrg(OrgSetVO orgsetvo, String[] ids) throws BkmsException {
        try {
            ActivePageService apm = (ActivePageService) BkmsContext.getBean("sys_activePageService");
            List list = new ArrayList();

            for (int i = 0; i < ids.length; i++) {
                list.add("update B001 set B001730 = '" + Constants.YES + "',B001715='' where ID='" + ids[i] + "'");
                list.add("update B004 set B004045 ='" + Tools.filterNull(orgsetvo.getCancelTime()) + "',B004205 ='" + Tools.filterNull(orgsetvo.getCancelType()) + "',"
                        + "B004210='" + Tools.filterNull(orgsetvo.getMemo()) + "' where  ID='" + ids[i] + "'");
                //清除报表目录记录
//                list.add("delete from rpt_class_set where cur_org in (select id from B001 start with id='" + ids[i] + "' connect by prior id=B001002)");
            }
            if (list.size() > 0) {
                apm.batchExecuteSql((String[]) list.toArray(new String[list.size()]));
            }
        } catch (Exception e) {
            throw new RollbackableException("机构撤销失败", e, this.getClass());
        }
    }

    /**
     * 根据机构id获得其下所有机构的id
     *
     * @param ids
     * @throws RollbackableException
     */
    public String[] querySubOrg(String[] ids) throws RollbackableException {
        try {
            ActivePageService apm = (ActivePageService) BkmsContext.getBean("sys_activePageService");
            Map map = new HashMap();
            for (int i = 0; i < ids.length; i++) {
                String treeId = SysCacheTool.findOrgById(ids[i]).getTreeId();
                List list = apm.queryForList("select id from B001 where B001003 like '" + treeId + "%'");
                for (int j = 0; j < list.size(); j++) {
                    Map idMap = (Map) list.get(j);
                    String id = (String) idMap.get("ID");
                    map.put(id, id);
                }
            }
            String[] idsNew = new String[map.size()];
            Set set = map.keySet();
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                String id = (String) it.next();
                idsNew[i] = id;
                i += 1;
            }
            return idsNew;
        } catch (Exception e) {
            throw new RollbackableException("机构恢复出错", e, this.getClass());
        }
    }

    /**
     * 机构恢复
     *
     * @param ids
     * @throws RollbackableException
     */
    public void updateBackOrg(String[] ids) throws RollbackableException {
        try {
            ActivePageService apm = (ActivePageService) BkmsContext.getBean("sys_activePageService");
            String[] sql = new String[ids.length * 2];
            for (int i = 0; i < ids.length; i++) {
                sql[i] = "update B001 set B001730 = '" + Constants.NO + "' where ID='" + ids[i] + "'";
                sql[ids.length * 2 - i - 1] = "update B004 set B004045 ='',B004205 ='',"
                        + "B004210='' where  ID='" + ids[i] + "'";
            }
            apm.batchExecuteSql(sql);
        } catch (Exception e) {
            throw new RollbackableException("机构恢复出错", e, this.getClass());
        }

    }

    /**
     * 机构删除
     *
     * @param ids
     * @param user
     * @throws RollbackableException
     */
    public void deleteOrg(String[] ids, User user) throws RollbackableException {
//        throw new RollbackableException("方法未实现", this.getClass());
        try {
            ActivePageService apm = (ActivePageService) BkmsContext.getBean("sys_activePageService");
//            TableVO table = apm.findNewPageInfo("B001", null);
//            apm.removePageInfo(table, ids, user);
            apm.batchDeleteMainRecord(user, "B001", ids, true, true);
        } catch (Exception e) {
            throw new RollbackableException("机构删除失败", e, this.getClass());
        }
    }

  
    /**
     * 机构排序
     *
     * @param orgs
     * @throws RollbackableException
     */
    public void updateOrgSort(OrgBO[] orgs, String superId) throws BkmsException {
        try {
            ActivePageService apm = (ActivePageService) BkmsContext.getBean("sys_activePageService");
            if (orgs != null) {
                String[] sql = new String[orgs.length];
                for (int i = 0; i < orgs.length; i++) {
                    //修改新排序的机构排序号
                    sql[i] = "UPDATE B001 SET B001715 = '" + Tools.filterNull(orgs[i].getOrgSort()) + "' WHERE ID='" + orgs[i].getOrgId() + "'";
                }
                apm.batchExecuteSql(sql);


                List list = new ArrayList();
                for (int i = 0; i < orgs.length; i++) {
                    int len = orgs[i].getOrgSort().length() + 1;
//修改新排序的机构的子机构的排序号
                    String sql1 = "update b001 b1 set b1.b001715 = concat('" + orgs[i].getOrgSort() + "', substr(b1.b001715," + len + ", length(b1.b001715))) where b1.b001003 like '" + orgs[i].getTreeId() + "%'".toUpperCase();
                    list.add(sql1);
                }
                //联动人员的所属部门排序号
                Org b = SysCacheTool.findOrgById(superId);
                if (b != null) {
                    String sql1 = "update a001 a1 set a1.a001743 = (select b1.b001715 from b001 b1 where b1.id = a1.a001705 and b1.b001003 like '" + b.getTreeId() + "%') where exists (select b1.b001715 from b001 b1 where b1.id = a1.a001705 and b1.b001003 like '" + b.getTreeId() + "%')".toUpperCase();
                    list.add(sql1);
                }
                apm.batchExecuteSql((String[]) list.toArray(new String[list.size()]));
            }
        } catch (Exception e) {
            throw new RollbackableException("机构排序失败", e, this.getClass());
        }
    }

    public OrgBO findUnitOrgIncludeFunDep(String orgId) throws RollbackableException {
        OrgBO org = orgDAO.findOrg(orgId);
        if (org != null) {
            if (!"-1".equals(org.getSuperId()))
                org = this.findUnitOrgIncludeFunDep(org.getSuperId());
        }
        return org;
    }

    /**
     * 查询总行和区县支行
     *
     * @return
     * @throws RollbackableException
     */
    public List findOrgByType() throws RollbackableException {
        return orgDAO.findOrgByType();
    }

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO[] queryAllOutsideOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryAllOutsideOrgBySuperSelf(superTreeId, cancel);
    }

    public OrgBO[] queryNextLeveOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryNextLeveOrgBySuperSelf(superTreeId, cancel);
    }

    public Hashtable queryAllInsideOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryAllInsideOrgBySuperSelf(superTreeId, cancel);
    }

    /**
     * 查询某个机构下第一层内设机构
     * lrg update 2015-7-6
     *
     * @param superId
     */
    public List queryInsideOrgBySuperId(String superId) throws RollbackableException {
        return orgDAO.queryInsideOrgBySuperId(superId);
    }

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public OrgBO[] queryAllOutsideOrgBySuperSelfOrderByLayer(String superTreeId, int layer) throws RollbackableException {
        return orgDAO.queryAllOutsideOrgBySuperSelfOrderByLayer(superTreeId, layer);
    }

    public List queryAllStockOrgBySuperAndLayer(String stockTreeId, int layer) throws RollbackableException {
        return orgDAO.queryAllStockOrgBySuperAndLayer(stockTreeId, layer);
    }

    public List queryAllStockOrgBySuper(String stockTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryAllStockOrgBySuper(stockTreeId, cancel);
    }

    public Hashtable queryAllInsideOrgBySuperStockSelf(String superStockTreeId, String cancel) throws RollbackableException {
        return orgDAO.queryAllInsideOrgBySuperStockSelf(superStockTreeId, cancel);
    }

    public List queryOrgPostNum(String orgTreeId, String cancel) throws RollbackableException {
        List list = orgDAO.queryOrgPostNum(orgTreeId, cancel);
        if (list == null || list.isEmpty()) return null;

        List retList = new ArrayList();
        String oldPost = "";
        String sumNum = "";
        String names = "";
        String ids = "";
        Map sumMap = null;
        for (int i = 0; i < list.size(); i++) {
            Map map = (HashMap) list.get(i);
            String postId = Tools.filterNull((String) map.get("a001715"));
            String num = Tools.filterNull((String) map.get("num"));
            String name = Tools.filterNull((String) map.get("a001001"));
            String id = Tools.filterNull((String) map.get("id"));
            if ("".equals(oldPost)) {
                sumMap = new HashMap(map);
            } else if (postId.equals(oldPost) && sumMap != null) {
                sumNum = String.valueOf(Integer.parseInt(num) + Integer.parseInt((String) sumMap.get("num")));
                names = name + " " + Tools.filterNull((String) sumMap.get("a001001"));
                ids = id + " " + Tools.filterNull((String) sumMap.get("id"));
                sumMap.put("num", sumNum);
                sumMap.put("a001001", names);
                sumMap.put("id", ids);
            } else {
                retList.add(sumMap);
                sumMap = new HashMap(map);
            }
            oldPost = postId;
            if (i == list.size() - 1)
                retList.add(sumMap);
        }
        return retList;
    }

    public String findOrgByDeptName(String deptId) throws RollbackableException {
        OrgBO org = null;
        String name = "";
//            OrgService orgService = (OrgService) BkmsContext.getBean("org_orgService");
//            org = SysCacheTool.findOrgById(deptId);
        org = this.findOrgBO(deptId);
        if (org != null) {
            name = org.getName();
            String type = Tools.filterNull(org.getOrgLevel());
            if (type.length() > 5) {
                if (!("1".equals(type.substring(4, 5)) || "2".equals(type.substring(4, 5)))) {
                    name = this.findOrgByDeptName(org.getSuperId()) + "->" + name;
                }
            }
        }
        return name;
    }

    public OrgBO findOrgByDept(String deptId) throws RollbackableException {
        OrgBO org = this.findOrgBO(deptId);
        return org;
    }

    public void checkWageUnitByOrg(String[] ids) throws RollbackableException {
        String idstr = getInSql(ids);
        String sql = "select count(*) from wage_unit where unit_id in (" + idstr + ")";
        int n = orgDAO.getJdbcTemplate().queryForInt(sql);
        if (n > 0) {
            throw new RollbackableException("所选机构包含发薪机构，不能删除", this.getClass());
        }
    }

    private static String getInSql(String[] sarray) {
        if (sarray == null || sarray.length < 1) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sarray.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("'").append(sarray[i]).append("'");
        }
        return sb.toString();
    }
	/**
	 * 根据父id查询（分页查询）
	 * @param vo
	 * @param specsocre
	 * @return
	 */
    public  List queryOrgBysuperId(PageVO vo,String superid)throws RollbackableException{
    	return orgDAO.queryOrgBysuperId(vo, superid);
    }
}
