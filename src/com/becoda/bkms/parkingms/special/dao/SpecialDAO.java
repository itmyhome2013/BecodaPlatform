package com.becoda.bkms.parkingms.special.dao;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;
import com.becoda.bkms.pms.dao.RoleInfoDAO;

public class SpecialDAO extends GenericDAO {

	public List querySpecailList(PageVO vo, ParmsSpecialInfoVo sepVo)throws RollbackableException {
		try {
            StringBuffer sb = new StringBuffer();
            StringBuffer countsb = new StringBuffer();
            sb.append("from ParmsSpecialInfo t where 1=1 ");
            countsb.append("select count(t) from ParmsSpecialInfo t where 1=1 ");
            
            
            if (sepVo.getCheckUserId() != null && !"".equals(sepVo.getCheckUserId())) {
                sb.append(" and t.checkUserId='")
                        .append(sepVo.getCheckUserId()).append("' ");
                countsb.append(" and t.checkUserId='")
                        .append(sepVo.getCheckUserId()).append("' ");
            } 
            //状态
            if (sepVo.getState() != null && !"".equals(sepVo.getState())) {
            	if(sepVo.getState().equals("99")){
            		sb.append("  and t.state not in(0,9,6)  ");
            		countsb.append("  and t.state not in(0,9,6) ");
            	}else if(sepVo.getState().equals("9")){
            		sb.append("  and t.state in(9,11)  ");
            		countsb.append("  and t.state in(9,11) ");
            	}else{
            		sb.append("  and t.state = '").append(sepVo.getState()).append("' ");
            		countsb.append("  and t.state = '").append(sepVo.getState()).append("' ");
            	}
            }
            //检查单位
            if (sepVo.getCheckOrgId() != null && !"".equals(sepVo.getCheckOrgId())) {
            	sb.append("  and t.checkOrgId = '").append(sepVo.getCheckOrgId()).append("' ");
            	countsb.append("  and t.checkOrgId = '").append(sepVo.getCheckOrgId()).append("' ");
            }
            //起始时间
            if (sepVo.getBeginDate() != null && !"".equals(sepVo.getBeginDate())) {
            	sb.append("  and t.checkdate >= '").append(sepVo.getBeginDate()).append("' ");
            	countsb.append("  and t.checkdate >= '").append(sepVo.getBeginDate()).append("' ");
            }
            //截止时间
            if (sepVo.getEndDate() != null && !"".equals(sepVo.getEndDate())) {
            	sb.append("  and t.checkdate <= '").append(sepVo.getEndDate()).append("' ");
            	countsb.append("  and t.checkdate <= '").append(sepVo.getEndDate()).append("' ");
            }
            //整改单位
            if (sepVo.getReceiveOrgId() != null && !"".equals(sepVo.getReceiveOrgId())) {
            	sb.append("  and t.receiveOrgId = '").append(sepVo.getReceiveOrgId()).append("' ");
            	countsb.append("  and t.receiveOrgId = '").append(sepVo.getReceiveOrgId()).append("' ");
            }
            String order = " order by t.createTime  ";
            return pageQuery(vo, countsb.toString(), sb.append(order).toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误!", e, RoleInfoDAO.class);
        }
	}
}
