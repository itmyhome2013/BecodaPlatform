package com.becoda.bkms.pcs.ucc.impl;

import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.pcs.service.ActCountService;
import com.becoda.bkms.pcs.service.ActExTaskusercfgService;
import com.becoda.bkms.pcs.ucc.ActExTaskusercfgUCC;
import com.becoda.bkms.pcs.ucc.IActCountUCC;
import com.becoda.bkms.util.Tools;

/**
 * @描述：TODO(我的流程、归档流程、进行中流程、经办流程 配置UCC实现层)
 * @author kris
 * @date:2016-1-22
 */
public class ActExCountUCCImpl implements IActCountUCC{
	private ActCountService actCountService;
    /**
     *  获取我经办过的流程列表信息
     * @param vo
	 * @param user
	 * @return
	 * @throws BkmsException
     */
	public List<Map<String, String>> queryMyPcsPageList(PageVO vo,User user) throws BkmsException {
		StringBuffer querySQL= new StringBuffer(" select ");
				querySQL.append(" p.processid,p.processname,p.createdate ,hp.end_act_id_ ,hp.end_time_");
				querySQL.append(" from act_ex_process p left join act_hi_procinst hp on p.processid=hp.business_key_ ");
				querySQL.append(" where  hp.proc_inst_id_ in  (select  distinct(ht.proc_inst_id_)  from  act_hi_taskinst ht    where ht.assignee_='"+user.getUserId()+"' )");
					  
		StringBuffer countSQL= new StringBuffer(" select ");
				countSQL.append(" count(*) ");
				countSQL.append(" from act_ex_process p left join act_hi_procinst hp on p.processid=hp.business_key_ ");
				countSQL.append(" where  hp.proc_inst_id_ in  (select  distinct(ht.proc_inst_id_)  from  act_hi_taskinst ht    where ht.assignee_='"+user.getUserId()+"' )");
						  
		  
		if(!Tools.stringIsNull(Tools.filterNull(user.getUserId()))){
	    	//条件
	    //	querySQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	    //	countSQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	     }else{
	    	 throw new RuntimeException("用户ID为空");
	     }
	        //排序
    	    querySQL.append("order by hp.end_time_ desc");
		 return this.actCountService.queryPageListBySql(vo, countSQL.toString(), querySQL.toString());
	}

	/**
	 *  获取已归档的流程信息
	 * @param vo
	 * @param user
	 * @return
	 * @throws BkmsException
	 */ 
	public List<Map<String, String>> queryEndPcsPageList(PageVO vo,User user) throws BkmsException {
		StringBuffer querySQL= new StringBuffer(" select ");
				querySQL.append("  p.processid  as processid  ,p.processname as processname ,p.createdate as createdate ,hp.end_act_id_ as end_act_id_ ,hp.end_time_  as end_time_ ,p.CREATEUSERNAME as CREATEUSERNAME,p.URGENCY as URGENCY  ,p.COMMENTS as COMMENTS");
				querySQL.append(" from act_ex_process p  left join act_hi_procinst hp on p.processid=hp.business_key_ ");
				querySQL.append(" where hp.end_act_id_='End'");
 
		StringBuffer countSQL= new StringBuffer(" select ");
				countSQL.append(" count(*) ");
				countSQL.append(" from  act_ex_process p  left join act_hi_procinst hp on p.processid=hp.business_key_ ");
				countSQL.append(" where hp.end_act_id_='End'");
		  
		if(!Tools.stringIsNull(Tools.filterNull(user.getUserId()))){
	    	//条件
	    //	querySQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	    //	countSQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	     }else{
	    	 throw new RuntimeException("用户ID为空");
	     }
	        //排序
    	    querySQL.append("  order by hp.end_time_ desc");
		 return this.actCountService.queryPageListBySql(vo, countSQL.toString(), querySQL.toString());
	}

	/**
	 * 获取全部流程信息
	 * @param vo
	 * @param user
	 * @return
	 * @throws BkmsException
	 */
	public List<Map<String, String>> queryAllPcsPageList(PageVO vo,User user) throws BkmsException {
		StringBuffer querySQL= new StringBuffer(" select ");
				querySQL.append("  p.processid ,p.processname as PROCESSNAME ,p.createdate ,hp.end_act_id_ ,hp.end_time_,p.treecode");
				querySQL.append(" from act_ex_process p left join act_hi_procinst hp on p.processid=hp.business_key_ ");
			    querySQL.append(" where 1=1 ");
				//querySQL.append("  and   p.treecode is null");
		StringBuffer countSQL= new StringBuffer(" select ");
				countSQL.append("  p.processid,p.processname,p.createdate ,hp.end_act_id_ ,hp.end_time_,p.treecode");
				countSQL.append(" from act_ex_process p left join act_hi_procinst hp on p.processid=hp.business_key_ ");
				countSQL.append(" where 1=1 ");
				//countSQL.append("  and   p.treecode is null");	  
		  
		if(!Tools.stringIsNull(Tools.filterNull(user.getUserId()))){
	    //条件
	    //	querySQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	    //	countSQL.append(" and B.ASSIGNEE_ = '"+user.getUserId()+"' ");
	     }else{
	    	 throw new RuntimeException("用户ID为空");
	     }
	        //排序
    	    querySQL.append(" order by hp.end_time_ desc");
		 return this.actCountService.queryPageListBySql(vo, countSQL.toString(), querySQL.toString());
	}

	
	
	public ActCountService getActCountService() {
		return actCountService;
	}

	public void setActCountService(ActCountService actCountService) {
		this.actCountService = actCountService;
	}
	 

	
	
	

}
