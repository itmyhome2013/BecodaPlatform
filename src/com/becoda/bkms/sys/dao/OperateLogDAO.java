package com.becoda.bkms.sys.dao;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.Tools;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-9
 * Time: 10:31:24
 */
public class OperateLogDAO extends GenericDAO {
    /**
     * @param where sql where statement
     * @return SysLogOperBO--list
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public List queryLogbySqlWhere(String where) throws BkmsException {
        try {
            String strHql = "from OperLogBO slobo where 1=1 ";

            if (where != null && !"".equalsIgnoreCase(where))
                strHql = strHql + " " + where;
            return hibernateTemplate.find(strHql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("查询系统日志出错！", e, this.getClass());
        }
    }

    /**
     * 分页查询
     *
     * @param vo     vo
     * @param strSql strsql
     * @return list
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public List queryLogbySqlWhere(PageVO vo, String strSql) throws BkmsException {
        try {
            String strHql = "from OperLogBO slobo where 1=1 ";
            String strCountHql = "Select count(slobo) from OperLogBO slobo where 1=1 ";

            if (strSql != null && !"".equalsIgnoreCase(strSql)) {
                strHql = strHql + " " + strSql;
                strCountHql = strCountHql + " " + strSql;
            }
            List list = pageQuery(vo, strCountHql, strHql);

            if (list == null) return null;
            List list2 = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                OperLogBO u = new OperLogBO();
                Tools.copyProperties(u, list.get(i));
                list2.add(u);
            }
            return list2;

        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("查询系统日志出错！", e, this.getClass());
        }
    }


    /**
     * @param sqlWhere sqlWhere
     * @return SysLogOperBO /list
     * @throws BkmsException e
     */
    public List queryLogBySql(String sqlWhere) throws BkmsException {

        try {
            List tempLst = queryLogbySqlWhere(sqlWhere);
            if (tempLst == null || tempLst.size() <= 0) {
                return null;
            }
            List tLst = new ArrayList();
            int len = tempLst.size();
            //System.out.println(">>>"+len);
            for (int i = 0; i < len; i++) {
                OperLogBO slobo = (OperLogBO) tempLst.get(i);
                switch (slobo.getOperType().charAt(0)) {
                    case 'A':
                        slobo.setOperType("增加");
                        break;
                    case 'U':
                        slobo.setOperType("修改");
                        break;
                    case 'D':
                        slobo.setOperType("删除");
                        break;
                    default:
                }
                tLst.add(slobo);
            }
            return tLst;
        } catch (Exception e) {
            throw new BkmsException("查询日志信息出错！", e, this.getClass());
        }

    }

    public List queryLogbySql(PageVO vo, String strSql) throws BkmsException {
        try {
            List tempLst = queryLogbySqlWhere(vo, strSql);
            if (tempLst == null || tempLst.size() <= 0) {
                return null;
            }
            List tLst = new ArrayList();
            int len = tempLst.size();
            //System.out.println(">>>"+len);
            for (int i = 0; i < len; i++) {
                OperLogBO slobo = (OperLogBO) tempLst.get(i);
                switch (slobo.getOperType().charAt(0)) {
                    case 'A':
                        slobo.setOperType("增加");
                        break;
                    case 'U':
                        slobo.setOperType("修改");
                        break;
                    case 'D':
                        slobo.setOperType("删除");
                        break;
                    default:
                }
                tLst.add(slobo);
            }
            return tLst;
        } catch (Exception e) {
            throw new BkmsException("查询日志信息出错！", e, this.getClass());
        }
    }

    /**
     * 查询操作日志
     *
     * @param startdate
     * @param enddate
     * @pdOid 8a152244-aadf-4ab1-aa72-60e0da5ac751
     */
    public List queryOperLog(Timestamp startdate, Timestamp enddate,
                             String operatorName, String userName,
                             String operType, String orgId, PageVO vo) throws RollbackableException {
        try {
            String strHql = "from OperLogBO l where 1=1 ";
            String strCountHql = "Select count(l) from OperLogBO l where 1=1 ";
            List list;
            List vector = new ArrayList();
            StringBuffer sql = new StringBuffer();
            StringBuffer where = new StringBuffer();
            where.append(startdate == null || "".equals(startdate) ? "" :
                    " and to_char(l.operDatetime,'yyyy-MM-dd HH:mm:ss.q')>'" +
                            startdate + "'");
            where.append(enddate == null || "".equals(enddate) ? "" :
                    " and to_char(l.operDatetime,'yyyy-MM-dd HH:mm:ss.q')<'" +
                            enddate + "'");
            where.append(operatorName == null || "".equals(operatorName) ? "" :
                    " and l.operatorName like'" + operatorName + "%'");
            where.append(userName == null || "".equals(userName) ? "" :
                    " and l.operUsername like '" + userName + "%'");
            where.append(operType == null || "".equalsIgnoreCase(operType) ? "" :
                    " and l.operType='" + operType + "'");
            if (orgId != null && !orgId.equals("")) {
                Org org = SysCacheTool.findOrgById(orgId);
                if (org != null) {
                    String treeId = org.getTreeId();
                    where.append(" and l.operOrgTreeId like '").append(treeId).append("%'");
                }
            }
            /* where.append(orgId == null || "".equalsIgnoreCase(orgId) ? "" :
    " and l.operatorOrg like '" + orgId + "'");*/
            if (where.length() > 0) {
                sql.append(where);
            }
            sql.append(" order by l.operDatetime desc");

            if (sql.toString() != null && !sql.toString().equals("")) {
                strHql = strHql + " " + sql.toString();
                strCountHql = strCountHql + " " + sql.toString();
            }
            list = pageQuery(vo, strCountHql, strHql);
            if (null == list || list.size() == 0)
                return null;
            else {
                int length = list.size();
                for (int i = 0; i < length; i++) {
                    vector.add(list.get(i));
                }
                return vector;
            }
        }
        catch (Exception e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    /**
     * 查询操作日志,不分页 , setIds、itemIds 如果都不为空，则是或的关系
     *
     * @param startdate
     * @param enddate
     * @param operatorName
     * @param userName
     * @param operType
     * @param orgId
     * @param setIds
     * @param itemIds
     * @return
     * @throws RollbackableException
     */
    public List queryOperLog(Timestamp startdate, Timestamp enddate,
                             String operatorName, String userName,
                             String operType, String orgId, String[] setIds, String[] itemIds) throws RollbackableException {
        try {
            String strHql = "from OperLogBO l where 1=1 ";
            StringBuffer sql = new StringBuffer();
            StringBuffer where = new StringBuffer();
            where.append(startdate == null || "".equals(startdate) ? "" :
                    " and to_char(l.operDatetime,'yyyy-MM-dd HH:mm:ss.q')>='" +
                            startdate + "'");
            where.append(enddate == null || "".equals(enddate) ? "" :
                    " and to_char(l.operDatetime,'yyyy-MM-dd HH:mm:ss.q')<='" +
                            enddate + "'");
            where.append(operatorName == null || "".equals(operatorName) ? "" :
                    " and l.operatorName like'" + operatorName + "%'");
            where.append(userName == null || "".equals(userName) ? "" :
                    " and l.operUsername like '" + userName + "%'");
            where.append(operType == null || "".equalsIgnoreCase(operType) ? "" :
                    " and l.operType='" + operType + "'");
            if (orgId != null && !orgId.equals("")) {
                Org org = SysCacheTool.findOrgById(orgId);
                if (org != null) {
                    String treeId = org.getTreeId();
                    where.append(" and l.operOrgTreeId like '" + treeId + "%'");
                }
            }
            if (setIds != null && setIds.length > 0 && itemIds != null && itemIds.length > 0) {
                where.append(" and (").append(Tools.splitInSql(setIds, "l.operInfosetId"));
                where.append(" or ").append(Tools.splitInSql(itemIds, "l.operInfoitemId")).append(")");
            } else if (setIds != null && setIds.length > 0) {
                where.append(" and ").append(Tools.splitInSql(setIds, "l.operInfosetId"));
            } else if (itemIds != null && itemIds.length > 0) {
                where.append(" and ").append(Tools.splitInSql(itemIds, "l.operInfoitemId"));
            }
            /* where.append(orgId == null || "".equalsIgnoreCase(orgId) ? "" :
    " and l.operatorOrg like '" + orgId + "'");*/
            if (where.length() > 0) {
                sql.append(where);
            }
            sql.append(" order by l.operDatetime desc");

            if (sql.toString() != null && !sql.toString().equals("")) {
                strHql = strHql + " " + sql.toString();
            }
            return hibernateTemplate.find(strHql);
        }
        catch (Exception e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    public OperLogBO[] queryOperLog(String[] operId) throws RollbackableException {
        try {
            if (operId == null || operId.length == 0) {
                List list = null;
                String sql = "from OperLogBO l ";
                list = hibernateTemplate.find(sql);
                if (null == list || list.size() == 0) {
                    return null;
                } else {
                    int length = list.size();
                    OperLogBO[] logLog = new OperLogBO[length];
                    for (int i = 0; i < length; i++) {
                        logLog[i] = (OperLogBO) list.get(i);
                    }
                    return logLog;
                }
            } else {
                int count = operId.length;
                OperLogBO[] logLog = new OperLogBO[count];
                for (int i = 0; i < count; i++) {
                    logLog[i] = (OperLogBO) this.findBo(OperLogBO.class, operId[i]);
                }
                return logLog;
            }

        }
        catch (RollbackableException e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    /**
     * added by qinyan
     *
     * @param optLogLog SysLogOperBO
     * @return SysLogOperBO[]
     * @throws RollbackableException
     */
    public OperLogBO[] query(OperLogBO optLogLog) throws RollbackableException {
        try {
            List list = null;
            String sql = "from OperLogBO log where 1=1 ";
            if (optLogLog.getOperPersonId() != null &&
                    !optLogLog.getOperPersonId().equals(""))
                sql = sql + " and log.operPersonId='" + optLogLog.getOperPersonId() + "'";

            if (optLogLog.getOperInfosetId() != null && !optLogLog.getOperInfosetId().equals(""))
                sql = sql + " and log.operInfosetId='" + optLogLog.getOperInfosetId() + "'";

            sql = sql + "order by log.operDatetime desc";
            list = hibernateTemplate.find(sql);
            if (null == list || list.size() == 0) {
                return null;
            } else {
                int length = list.size();
                OperLogBO[] logLog = new OperLogBO[length];
                for (int i = 0; i < length; i++) {
                    logLog[i] = (OperLogBO) list.get(i);
                }
                return logLog;
            }


        }
        catch (Exception e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    /**
     * added by qinyan
     *
     * @param personId String
     * @return SysLogOperBO[]
     * @throws RollbackableException
     */
    public OperLogBO[] queryOperLogByuser(String personId) throws RollbackableException {
        try {
            List list = null;
            StringBuffer sql = new StringBuffer();
            sql.append("from OperLogBO l where l=1");
            sql.append("order by l.operDatetime desc");
            list = hibernateTemplate.find(sql.toString());
            if (null == list || list.size() == 0) {
                return null;
            } else {
                int length = list.size();
                OperLogBO[] logLog = new OperLogBO[length];
                for (int i = 0; i < length; i++) {
                    logLog[i] = (OperLogBO) list.get(i);
                }
                return logLog;
            }

        }
        catch (Exception e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    /**
     * 删除数据库操作日志
     *
     * @pdOid 6e350f9e-1a6f-4bac-a115-ef4b35e39c58
     */
    public void removeLog(OperLogBO[] logLog) throws RollbackableException {
        try {
            int count = logLog.length;
            for (int i = 0; i < count; i++) {
                deleteBo(logLog[i]);
            }
        }
        catch (Exception e) {
            throw new RollbackableException("删除失败.", e, this.getClass());
        }
    }

}
