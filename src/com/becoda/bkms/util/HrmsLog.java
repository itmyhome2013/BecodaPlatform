package com.becoda.bkms.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-4-9
 * Time: 12:53:15
 * To change this template use File | Settings | File Templates.
 */
public class HrmsLog {

    public static void addOperLog(Class clazz, OperLogBO log) throws BkmsException {
        Logger log4j = Logger.getLogger(clazz);
        try {
            if (log.getOperatorId() != null && !"".equals(log.getOperatorId())) {
                Person per = SysCacheTool.findPersonById(log.getOperatorId());
                log.setOperatorName(per.getName());
                log.setOperatorDept(per.getDeptId());
                log.setOperatorOrg(per.getOrgId());
            }
            if (log.getOperatorOrg() != null && !"".equals(log.getOperatorOrg())) {
                Org o = SysCacheTool.findOrgById(log.getOperatorOrg());
                log.setOperOrgTreeId(o.getTreeId()); //被操作机构treeid
            }
            if (log.getOperDatetime() == null) {
                Timestamp opertime = new Timestamp(System.currentTimeMillis());
                log.setOperDatetime(opertime);

            }
//            log.setOperId(SequenceGenerator.getUUID());
            ((GenericDAO) BkmsContext.getBean("genericDAO")).createBo(log);
        } catch (Exception e) {
            throw new RollbackableException(e, HrmsLog.class);
        }
    }

    public static void batchAddOperLog(Class clazz, List log) throws BkmsException {
        Logger log4j = Logger.getLogger(clazz);
        try {
            int count = 0;
            if (log != null) {
                count = log.size();
//                String[] seq = SequenceGenerator.getUUID(count);
                Timestamp opertime = new Timestamp(System.currentTimeMillis());
                for (int i = 0; i < count; i++) {
                    OperLogBO bo = (OperLogBO) log.get(i);
//                    bo.setOperId(seq[i]);
                    if (bo.getOperDatetime() == null) {
                        bo.setOperDatetime(opertime);
                    }
                }
            }
            ((GenericDAO) BkmsContext.getBean("genericDAO")).getHibernateTemplate().saveOrUpdateAll(log);
        } catch (Exception e) {
            throw new RollbackableException(e, HrmsLog.class);
        }
    }
//    public static void batchAddOperLog(Class clazz, List log) throws BkmsException {
//        Logger log4j = Logger.getLogger(clazz);
//        try {
//            int count = 0;
//            if (log != null) {
//                count = log.size();
//                Timestamp opertime = new Timestamp(System.currentTimeMillis());
//                for (int i = 0; i < count; i++) {
//                    OperLogBO bo = (OperLogBO) log.get(i);
//                    if (bo.getOperDatetime() == null) {
//                        bo.setOperDatetime(opertime);
//                    }
//                    ((GenericDAO) BkmsContext.getBean("genericDAO")).createBo(bo);
//                }
//            }
//        } catch (Exception e) {
//            throw new RollbackableException(e, HrmsLog.class);
//        }
//    }

    public static void addOperLog(Class clazz, String userId, String moduleName, String operDesc) throws RollbackableException {
        Logger log4j = Logger.getLogger(clazz);
        try {
            OperLogBO log = new OperLogBO();
            log.setModuleName(moduleName);
            log.setOperDesc(operDesc);

            log.setOperatorId(userId);
            Person per = SysCacheTool.findPersonById(log.getOperatorId());
            log.setOperatorName(per.getName());
            log.setOperatorDept(per.getDeptId());
            log.setOperatorOrg(per.getOrgId());

            Org o = SysCacheTool.findOrgById(per.getOrgId());
            log.setOperOrgTreeId(o.getTreeId());
            Timestamp opertime = new Timestamp(System.currentTimeMillis());
            log.setOperDatetime(opertime);

//            log.setOperId(SequenceGenerator.getUUID());
            ((GenericDAO) BkmsContext.getBean("genericDAO")).createBo(log);
        } catch (Exception e) {
            throw new RollbackableException(e, HrmsLog.class);
        }
    }

    public static void logInfo(String msg) {
        Logger log4j = Logger.getLogger("info");
        log4j.info(msg);
    }
}
