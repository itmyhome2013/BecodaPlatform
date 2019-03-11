package com.becoda.bkms.org.dao;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.OrgConstants;
import com.becoda.bkms.org.pojo.bo.OrgBO;
//import com.becoda.bkms.post.pojo.bo.PostBO;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 */
public class OrgDAO extends GenericDAO {

    /**
     * 查询系统内的全部结构(排好序的)
     *
     * @return
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *          e
     */
    public List queryOrg() throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from OrgBO o order by o.orgSort");
            if (list == null) {
                return null;
            } else {
                ArrayList rs = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    hibernateTemplate.evict(list.get(i));
                    rs.add(list.get(i));
                }
                return rs;
            }
        } catch (Exception e) {
            throw new RollbackableException("", e, OrgDAO.class);
        }
    }

    /**
     * 查询机构的下一级机构
     *
     * @param superId
     * @return
     * @throws RollbackableException
     */
    public List queryOrgBySuper(String superId) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from OrgBO o where o.superId='" + superId + "' order by o.orgSort");
            if (list == null) {
                return null;
            } else {
                ArrayList rs = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    rs.add(list.get(i));
                }
                return rs;
            }
        } catch (Exception e) {
            throw new RollbackableException("", e, OrgDAO.class);
        }
    }

    /**
     * 查询一个机构
     *
     * @param orgId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO findOrg(String orgId) throws RollbackableException {
        try {
            return (com.becoda.bkms.org.pojo.bo.OrgBO) super.findBoById(com.becoda.bkms.org.pojo.bo.OrgBO.class, orgId);
        } catch (Exception e) {
            throw new RollbackableException("", e, OrgDAO.class);
        }
    }

    /**
     * 查询机构下是否还有正常的机构
     *
     * @param treeId
     * @return
     * @throws RollbackableException
     */
    public int queryCountBySubValidOrg(String treeId) throws RollbackableException {
        try {
            List lcount = hibernateTemplate.find("select count(o) from OrgBO o where substr(o.treeId,1," + treeId.length() + ") = '" + treeId + "' and o.orgCancel ='" + Constants.NO + "' and o.treeId <> '" + treeId + "'");
            if (lcount != null) {
                return Integer.parseInt(lcount.get(0).toString());
            }
            return 0;

        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询机构下是否还有机构
     *
     * @param treeId
     * @return
     * @throws RollbackableException
     */
    public int queryCountBySubOrg(String treeId) throws RollbackableException {
        try {
            List lcount = hibernateTemplate.find("select count(o) from OrgBO o where substr(o.treeId,1," + treeId.length() + ") = '" + treeId + "' and o.treeId <> '" + treeId + "'");
            if (lcount != null) {
                return Integer.parseInt(lcount.get(0).toString());
            }
            return 0;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    public int queryCountPerByOrg(String treeId) throws RollbackableException {
        try {
//            return jdbcTemplate.queryForInt("select count(ID) from A001 where substr(A001738,1," + treeId.length() + ") = '" + treeId + "'"); update by huangh in 091009 
            return jdbcTemplate.queryForInt("select count(ID) from A001 where substr(A001738,1," + treeId.length() + ") like '" + treeId + "'");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询机构下的岗位数
     *
     * @param orgId
     * @return
     * @throws RollbackableException
     */
    public int queryCountPostByOrg(String orgId) throws RollbackableException {
        try {
            return jdbcTemplate.queryForInt("select count(*) from C001 where C001010 = '" + orgId + "'");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询某个机构下的所有的下级机构，不包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO[] queryAllOrgBySuper(String superTreeId, String cancel) throws RollbackableException {
        try {
            String sql = "";
            if (!"".equals(cancel)) {
                sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' and o.orgCancel = '" + cancel + "' and o.treeId <> '" + superTreeId + "' order by o.orgSort";
            } else {
                sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' and o.treeId <> '" + superTreeId + "' order by o.orgSort";
            }
            List list = hibernateTemplate.find(sql);
            if (list == null) {
                return null;
            }
            OrgBO[] pb = new OrgBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (com.becoda.bkms.org.pojo.bo.OrgBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }


    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO[] queryAllOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        try {
            String sql = "";
            if (!"".equals(cancel)) {
                sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' and o.orgCancel = '" + cancel + "' order by length(o.treeId),o.orgSort";
            } else {
                sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' order by length(o.treeId),o.orgSort";
            }
            List list = hibernateTemplate.find(sql);
            if (list == null) {
                return null;
            }
            OrgBO[] pb = new OrgBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (OrgBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO[] queryAllOrgByNotSelf(String superTreeId) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from OrgBO o where o.treeId  like '" + superTreeId + "%' and o.treeId <> '" + superTreeId + "'");
            if (list == null) {
                return null;
            }
            OrgBO[] pb = new OrgBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (OrgBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询机构代码是否重复
     *
     * @param orgCode
     * @return
     * @throws RollbackableException
     */
    public int queryOrgCodeCount(String orgCode, String orgId) throws RollbackableException {
        try {
            return jdbcTemplate.queryForInt("SELECT COUNT(B001010) FROM B001 WHERE B001010 = '" + orgCode + "' AND ID <> '" + orgId + "'");
        } catch (Exception e) {
            throw new RollbackableException("查询机构代码失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询同一父类机构下是否有机构名重复
     *
     * @param superId
     * @param newOrgName
     * @return
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
    public List queryOrgName(String superId, String newOrgName) throws RollbackableException {
        try {
            String sql = "from OrgBO o where  o.name='" + newOrgName + "' and o.superId='" + superId + "'";
            return hibernateTemplate.find(sql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    /**
     * 查询某个机构下的岗位
     *
     * @param orgId
     * @return
     * @throws RollbackableException
     */
//    public PostBO[] queryPostByOrg(String orgId) throws RollbackableException {
//        try {
//            List list = hibernateTemplate.find("from PostBO p where p.orgId = '" + orgId + "'");
//            if (list == null) {
//                return null;
//            }
//            PostBO[] pb = new PostBO[list.size()];
//            for (int i = 0; i < list.size(); i++) {
//                pb[i] = (PostBO) list.get(i);
//            }
//            return pb;
//        } catch (Exception e) {
//            throw new RollbackableException("查询失败", e, OrgDAO.class);
//        }
//    }


    /**
     * 根据TreeId查询机构
     *
     * @param treeId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO findOrgByTreeId(String treeId) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from OrgBO p where p.treeId = '" + treeId + "'");
            if (list == null || list.size() == 0) {
                return null;
            }
            return (OrgBO) list.get(0);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 根据TreeId的长度得到总行本部和一级分行
     */
    public List findOrgByType() throws RollbackableException {
        try {
            return hibernateTemplate.find("from OrgBO b where length(b.treeId)=6  order by b.orgSort");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    public com.becoda.bkms.org.pojo.bo.OrgBO[] queryNextLeveOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        try {

            String sql = "";
            if (!"".equals(cancel)) {
                sql = "from OrgBO o where   (o.treeId = '" + superTreeId + "' or o.treeId like '" + superTreeId + "___') and o.orgCancel = '" + cancel + "' order by o.orgSort";
            } else {
                sql = "from OrgBO o where  (o.treeId = '" + superTreeId + "' or o.treeId like '" + superTreeId + "___') order by o.orgSort";
            }
            List list = hibernateTemplate.find(sql);
            if (list == null) {
                return null;
            }
            OrgBO[] pb = new OrgBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (OrgBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询某个机构下的所有的下级外设机构(包括虚拟机构)，包括本身
     * lrg update 2015-7-6
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO[] queryAllOutsideOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        try {

            String sql = "";
            if (!"".equals(cancel)) {
                sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' and o.orgCancel = '" + cancel + "' order by o.orgSort,o.treeId";
            } else {
                sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' order by o.orgSort,o.treeId";
            }
            List list = hibernateTemplate.find(sql);
            if (list == null) {
                return null;
            }
            OrgBO[] pb = new OrgBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (OrgBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询某个机构下的所有的下级内设机构
     * lrg update 2015-7-6
     *
     * @param superTreeId
     * @return 将同一个superId的机构放到同一个List当中，
     *         然后以superId作Key,list作object，存储到 hashtable当中
     * @throws RollbackableException
     */
    public Hashtable queryAllInsideOrgBySuperSelf(String superTreeId, String cancel) throws RollbackableException {
        try {

            String sql = "";
            if (!"".equals(cancel)) {
                sql = "from OrgBO o where (o.treeId like '" + superTreeId + "%') and o.orgCancel = '" + cancel
                        // 不能按treeId排序，因为存在排序号
                        // 因为存在某个内设机构可能在不同的外设机构挂接的情况，
                        // 所以，他只能存在一个orgSort，而在另一个机构下无法找到orgSort。
                        // 按照内设机构的后三位进行排序，如果一个机构存在多个机构挂接的情况，
                        // 在另一个机构下显示的顺序就默认取第一个机构下的orgSort.
                        + "' order by substr(o.orgSort,length(o.orgSort)-2,3)";
            } else {
                sql = "from OrgBO o where (o.treeId like '" + superTreeId + "%') " +
                        "order by substr(o.orgSort,length(o.orgSort)-2,3)";
            }
            List list = hibernateTemplate.find(sql);
            if (list == null) {
                return null;
            }
            //OrgBO[] pb = new OrgBO[list.size()];
            Hashtable ht = new Hashtable();
            for (int i = 0; i < list.size(); i++) {
                com.becoda.bkms.org.pojo.bo.OrgBO org = (com.becoda.bkms.org.pojo.bo.OrgBO) list.get(i);
                List tmpList;
                //第一主管机构
                if (ht.containsKey(org.getSuperId())) {
                    tmpList = (List) ht.get(org.getSuperId());
                } else {
                    tmpList = new ArrayList();
                    ht.put(org.getSuperId(), tmpList);
                }
                tmpList.add(org);
            }
            return ht;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询某个机构下第一层内设机构
     * lrg update 2015-7-6
     *
     * @param superId
     */
    public List queryInsideOrgBySuperId(String superId) throws RollbackableException {
        try {

            String sql = "from OrgBO o where (o.superId= '" + superId + "') " +
                    "order by substr(o.orgSort,length(o.orgSort)-2,3)";
            return hibernateTemplate.find(sql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    /**
     * 查询某个机构下的所有的下级外设机构(包括虚拟机构)，包括本身
     * lrg update 2015-7-6
     *
     * @param superTreeId
     * @return
     * @throws RollbackableException
     */
    public com.becoda.bkms.org.pojo.bo.OrgBO[] queryAllOutsideOrgBySuperSelfOrderByLayer(String superTreeId, int layer) throws RollbackableException {
        try {

            String sql = "";
            int maxTreeIdLength = (layer - 1) * 3 + superTreeId.length();
            sql = "from OrgBO o where o.treeId like '" + superTreeId + "%' and o.orgCancel = '" + Constants.NO + "' and length(o.treeId)<='" + maxTreeIdLength + "' order by length(o.treeId), o.treeId";

            List list = hibernateTemplate.find(sql);
            if (list == null) {
                return null;
            }
            OrgBO[] pb = new OrgBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (OrgBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    public List queryAllStockOrgBySuperAndLayer(String stockTreeId, int layer) throws RollbackableException {
        try {
            if (stockTreeId == null) return null;
            List lst;
            String hql;
            int maxTreeIdLength = (layer - 1) * 3 + stockTreeId.length();
            hql = "from OrgBO t where t.stockTreeId  like '" + stockTreeId + "%' and t.orgCancel= '" + Constants.NO + "'" +
                    " and length(t.stockTreeId)<='" + maxTreeIdLength + "'"
                    + " order by length(t.stockTreeId),substr(t.stockTreeId,1,length(t.stockTreeId)-3) ,t.stockKind," +
                    " substr(t.orgSort,length(t.orgSort)-2,3) "; //同一层内的机构按照orgSort排序
            lst = hibernateTemplate.find(hql);
            return lst;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    /**
     * 按股权关系查询某个机构下的所有的下级内设机构
     * lrg update 2015-7-6
     *
     * @param stockTreeId f
     * @param cancel
     * @return 将同一个superId的机构放到同一个List当中，
     *         然后以superId作Key,list作object，存储到 hashtable当中
     * @throws RollbackableException
     */
    public Hashtable queryAllInsideOrgBySuperStockSelf(String stockTreeId, String cancel) throws RollbackableException {
        try {

            String sql = "";
            List orgList = this.queryAllStockOrgBySuper(stockTreeId, cancel);
            Hashtable ht = new Hashtable();
            for (int j = 0; j < orgList.size(); j++) {
                com.becoda.bkms.org.pojo.bo.OrgBO org = (com.becoda.bkms.org.pojo.bo.OrgBO) orgList.get(j);
                if (!"".equals(cancel)) {
                    sql = "from OrgBO o where  (o.treeId like '"
                            + org.getTreeId() + "%' or o.secondTreeId like '" + org.getTreeId() + "%') and o.orgCancel = '" + cancel
                            // 不能按treeId排序，因为存在排序号
                            // 因为存在某个内设机构可能在不同的外设机构挂接的情况，
                            // 所以，他只能存在一个orgSort，而在另一个机构下无法找到orgSort。
                            // 按照内设机构的后三位进行排序，如果一个机构存在多个机构挂接的情况，
                            // 在另一个机构下显示的顺序就默认取第一个机构下的orgSort.
                            + "' order by substr(o.orgSort,length(o.orgSort)-2,3)";
                } else {
                    sql = "from OrgBO o where  (o.treeId like '"
                            + org.getTreeId() + "%' or o.secondTreeId like '" + org.getTreeId() + "%') " +
                            "order by substr(o.orgSort,length(o.orgSort)-2,3)";
                }
                List list = hibernateTemplate.find(sql);
                if (list == null) {
                    return null;
                }
                List tmpList = new ArrayList();
                ht.put(org.getOrgId(), tmpList);
                for (int i = 0; i < list.size(); i++) {
                    com.becoda.bkms.org.pojo.bo.OrgBO innerorg = (com.becoda.bkms.org.pojo.bo.OrgBO) list.get(i);
                    tmpList.add(innerorg);
                }
            }
            return ht;
        } catch (Exception
                e) {
            throw new RollbackableException("查询失败", e, OrgDAO.class);
        }
    }

    public List queryAllStockOrgBySuper(String stockTreeId, String cancel) throws RollbackableException {
        try {
            if (stockTreeId == null) {
                return null;
            }
            String hql;
            if (!"".equals(cancel)) {
                hql = "from OrgBO t where t.stockTreeId  like '" + stockTreeId + "%' and t.orgCancel= '" + cancel + "'" +
                        //  " and t.orgLevel <>'" + Constants.ORG_TYPE_INSIDE
                        " order by length(t.stockTreeId),substr(t.stockTreeId,1,length(t.stockTreeId)-3) ,t.stockKind," +
                        " substr(t.orgSort,length(t.orgSort)-2,3) "; //同一层内的机构按照orgSort排序
            } else {
                hql = "from OrgBO t where t.stockTreeId  like '" + stockTreeId + "%'" +
                        " order by length(t.stockTreeId)," +
                        "t.stockTreeId";// +
                //" substr(t.orgSort,length(t.orgSort)-2,3) ";//同一层内的机构按照orgSort排序

            }
            return hibernateTemplate.find(hql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    /**
     * @param orgTreeId
     * @param cancel    ""或者null 过虑逻辑删除机构
     * @return
     * @throws RollbackableException
     */
    public List queryOrgPostNum(String orgTreeId, String cancel) throws RollbackableException {
        try {
            if (orgTreeId == null || "".equals(orgTreeId)) return null;
            //机构编码、机构编码、岗位编码、岗位编制、超编人数、缺编人数、实有人数（计算）、实有人数（岗位子集）、人员姓名
            String hql = "select id,A001701,A001705,A001715,C001735,C001209,C001210,num,C001200,A001001 from " +
                    "(select id,A001001,A001701,A001705,A001715,count(id) as num from A001 where (A001725='014511' or A001725='014515') and A001728 like '" + orgTreeId + "%' " +
                    "or A001738 like '" + orgTreeId + "%' group by A001701,A001705,A001715,A001001,id) aa left join " +
                    "(select id,B001730,B001715,postid,C001005,C001735,C001209,C001210,C001200 from B001 " +
                    "inner join C001 on B001.id = C001.C001010 where B001003 like '" + orgTreeId + "%') bb " +
                    "on aa.a001715 = bb.postid where (bb.id = aa.a001701 or bb.id = aa.a001705)";
            if ("".equals(cancel)) {
                hql = " and bb.B001730 <> '" + Constants.YES + "' ";
            }
            hql = hql + " order by bb.B001715";
            return jdbcTemplate.queryForList(hql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }
    /**
     * 根据父id分页查询
     * @param vo
     * @param superid
     * @return
     */
    public  List queryOrgBysuperId(PageVO vo,String superid)throws RollbackableException{
    	try {
			StringBuffer sb = new StringBuffer();
			StringBuffer countsb = new StringBuffer();
			sb.append("from OrgBO t where 1=1 and t.superId='" + superid + "'");
			countsb
					.append("select count(t) from OrgBO t where 1=1 and t.superId='"
							+ superid + "'");
			return pageQuery(vo, countsb.toString(), sb.toString());
		} catch (Exception e) {
			throw new RollbackableException("查询失败", e, this.getClass());
		}
    }
}
