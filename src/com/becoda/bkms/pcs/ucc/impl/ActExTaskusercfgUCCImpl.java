package com.becoda.bkms.pcs.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.pcs.service.ActExTaskusercfgService;
import com.becoda.bkms.pcs.ucc.ActExTaskusercfgUCC;

/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：ActExTaskusercfgUCCImpl
 * @描述：TODO(工作流人员配置UCC实现层)
 * @创建人： 张晓亮
 * @创建时间：2016年1月21日 上午10:00:55
 * @修改人：张晓亮
 * @修改时间：2016年1月21日 上午10:00:55
 * @修改备注：
 */
public class ActExTaskusercfgUCCImpl implements ActExTaskusercfgUCC{
	private ActExTaskusercfgService actExTaskusercfgService;
	
	public List<Map<String, String>> queryActExTaskurgBytaskKey(String taskKey)throws Exception {
		StringBuffer querySQL= new StringBuffer(" select ");
        querySQL.append(" b.userid ");
        querySQL.append(" from act_ex_taskusercfg a, act_ex_groupuser b  where a.selectivetype = '1' and a.taskkey ='"+taskKey+"' and a.userorgroupid = b.groupid   ");
        querySQL.append(" union ");
        querySQL.append(" select a.USERORGROUPID as  userid  from act_ex_taskusercfg a where a.selectivetype = '0' and a.taskkey ='"+taskKey+"' ");
        List<Map<String, String>> userlist = this.actExTaskusercfgService.queryListBySql(querySQL.toString());
        return userlist;
	}
	
    //get and set
	public ActExTaskusercfgService getActExTaskusercfgService() {
		return actExTaskusercfgService;
	}

	public void setActExTaskusercfgService(
			ActExTaskusercfgService actExTaskusercfgService) {
		this.actExTaskusercfgService = actExTaskusercfgService;
	}

	
	
	

}
