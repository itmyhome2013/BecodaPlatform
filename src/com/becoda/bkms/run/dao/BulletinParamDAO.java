package com.becoda.bkms.run.dao;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.util.RunTools;
import com.becoda.bkms.util.Tools;

import java.util.List;

public class BulletinParamDAO extends GenericDAO {
    public BulletinParamBO[] queryByCreateOrgId(String topicQry, String dateQry, String orgId, PageVO vo) throws RollbackableException {

        String hq = " from BulletinParamBO bp where bp.blltnId is not null ";
        String countHq = "select count(bp) from BulletinParamBO bp where bp.blltnId is not null";
        if (orgId != null && !orgId.equals("")) {
            hq = hq + " and bp.createOrgId =  '" + orgId + "'";
            countHq = countHq + " and bp.createOrgId =  '" + orgId + "'";
        }
        if (topicQry != null && !topicQry.equals("")) {
            hq = hq + " and bp.blltnTopic like  '%" + topicQry + "%'";
            countHq = countHq + " and bp.blltnTopic like  '%" + topicQry + "%'";
        }
        if (dateQry != null && !dateQry.equals("")) {
            hq = hq + " and bp.submitDate =  '" + dateQry + "'";
            countHq = countHq + " and bp.submitDate =  '" + dateQry + "'";
        }
        hq = hq + " order by bp.submitDate desc  ";
        countHq = countHq + " order by bp.submitDate desc  ";
        List list = pageQuery(vo, countHq, hq);
        try {
            if (list.isEmpty())
                return null;
            BulletinParamBO[] BOs = new BulletinParamBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                BulletinParamBO bo = new BulletinParamBO();
                Tools.copyProperties(bo, list.get(i));
                BOs[i] = bo;
            }

            return BOs;

        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给manager
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }
    }

    /**
     * 功能：根据BulletinID数组和ReaderType查询出公告列表<br>
     * 通常先通过BulletinScopeDAO.findByOrgId()方法查询出BlltnId数组，再使用本方法查询
     * 出某机构有权读到的公告列表。<br>
     *
     * @param strBlltnId 公告Id字符串,用逗号分割，如'001','002','003'，可以直接拼到where语句中<br>
     * @param ReaderType 读者类型(01=全体;02=HR人员)<br>
     * @return List 公告列表
     */
    public BulletinParamBO[] queryByBlltnIdListAndReaderType(String strBlltnId, String ReaderType) throws RollbackableException {
        BulletinParamBO[] BOs = null;
        String strHQL = null;
        String strToday = RunTools.getDate10();
        try {
            if ((strBlltnId != null) && (ReaderType != null)) {
                if (ReaderType.equals("01")) {
                    strHQL = "from BulletinParamBO bo where "
                            + " bo.startDate<=? and bo.endDate>=? "
                            + " and bo.readerType = '01' "
                            + " and bo.blltnId in (" + strBlltnId + ")"
                            + " order by bo.startDate desc";
                } else if (ReaderType.equals("02")) {
                    strHQL = "from BulletinParamBO bo where "
                            + " bo.startDate<=? and bo.endDate>=? "
                            + " and bo.blltnId in (" + strBlltnId + ")"
                            + " order by bo.startDate desc";
                }
                String[] obj = new String[2];
                obj[0] = strToday;
                obj[1] = strToday;
                List list = hibernateTemplate.find(strHQL, obj);

                if (list.isEmpty())
                    return null;
                BOs = new BulletinParamBO[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    BulletinParamBO bo = new BulletinParamBO();
                    Tools.copyProperties(bo, list.get(i));
                    BOs[i] = bo;
                }
            }
        } catch (Exception e) {
            //将DAO中抛出的所有非RollbackableException异常包装成一个RollbackableException异常抛给manager
            throw new RollbackableException("查询数据失败", e, this.getClass());
        }

        return BOs;
    }

}
