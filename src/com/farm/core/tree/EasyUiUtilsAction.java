package com.farm.core.tree;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.util.BkmsContext;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.tree.ucc.IEasyUiTreeUCC;

/**
 * EasyUI扩展工具类
 * 
 * @author huxuxu
 */
public class EasyUiUtilsAction extends GenericAction{
	private DataQuery query;// 条件查询
	private List<TreeNode> treeNodes = new ArrayList<TreeNode>(); // 返回的JSON数据
	private String id; // 树组件使用的ID
	private String transId; //直接传入树组件ID
	private String nodeState;
	private String text;
	private String isCompany; // 判断是集团还是分公司
	
	private String isTeam; //是否车队
	
	private static IEasyUiTreeUCC ucc;
	public EasyUiUtilsAction(){
		try{
			ucc = (IEasyUiTreeUCC) BkmsContext.getBean("easyUiTreeUCC");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 分公司 - 车队
	 * @return
	 */
	public String treeLoad() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			
			User user = (User) session.getAttribute(Constants.USER_INFO);
			//List l = user.getHaveQueryOrgScale_bus();
			List l = null;
			Org org = (Org)l.get(0);
			
			String superId = org.getSuperId();
			if(!"101".equals(superId) && !"-1".equals(superId) && id == null){
				id = superId;
			}else if(!"101".equals(superId) && !"-1".equals(superId)){
				id = org.getOrgId();
			}
			
			if(id == null){
				id = org.getOrgId();
			}
			
			if("101".equals(id)){
				nodeState = "1";
			}else{
				//分公司
				if(nodeState == null){
					nodeState = "2";
					isCompany = "2";
					isTeam = "1";
				}
				//车队
				else if(!"101".equals(superId) && !"-1".equals(superId)){
					isCompany = "3"; 
				}
				else{
					nodeState = String.valueOf((Integer.parseInt(nodeState) + 1));	
					isTeam = "2";
				}
			}
			
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(100);
			if(transId!=null&&!"".equals(transId)&&"101".equals(id)){
				id = transId;
				nodeState = "2";
				isCompany = "2";
			}
			DataResult result = ucc.treeLoad(query, id,nodeState,isCompany).search();
			List<Map<String, Object>> list = result.getResultList();
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = new TreeNode();
				Map<String, Object> map = list.get(i);
				node.setId((String) map.get("ID"));
				node.setText((String) map.get("NAME"));
				node.setNodeState((String)map.get("NODESTATE"));
				node.setChecked(false);
				// 判断是否有子节点，如果有则closed否则open
				BigDecimal bd = (BigDecimal) map.get("ISCHILDREN");
				int ischildren = bd.intValue();
				
				if (ischildren == 1 && !"2".equals(isTeam)) {
					node.setState("closed");
				} else {
					node.setState("open");
				}
				
				treeNodes.add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return "success";
	}
	
	public String treeLoadNoAuthority(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			
			User user = (User) session.getAttribute(Constants.USER_INFO);
			//List l = user.getHaveQueryOrgScale_bus();
			List l = null;
			Org org = (Org)l.get(0);
			
			if(id == null){
				id = "-1";
			}else if("101".equals(id)){
				
			}else{
				isTeam = "2";
			}
			
			
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(100);
			DataResult result = ucc.treeLoad(query, id,nodeState,isCompany).search();
			List<Map<String, Object>> list = result.getResultList();
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = new TreeNode();
				Map<String, Object> map = list.get(i);
				node.setId((String) map.get("ID"));
				node.setText((String) map.get("NAME"));
				node.setNodeState((String)map.get("NODESTATE"));
				node.setChecked(false);
				// 判断是否有子节点，如果有则closed否则open
				BigDecimal bd = (BigDecimal) map.get("ISCHILDREN");
				int ischildren = bd.intValue();
				
				if (ischildren == 1 && !"2".equals(isTeam)) {
					node.setState("closed");
				} else {
					node.setState("open");
				}
				
				treeNodes.add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return "success";
	}
	
	/**
	 * 分公司 -车队 -线路
	 * @return
	 */
	public String treeLoadLine(){
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			
			User user = (User) session.getAttribute(Constants.USER_INFO);
			//List l = user.getHaveQueryOrgScale_bus();
			List l = null;
			Org org = (Org)l.get(0);
			if(id == null){
				id = org.getOrgId();
			}
			// 集团 展现所有分公司
			if("101".equals(id)){
				nodeState = "1";
			}else{
				//分公司
				if(nodeState == null){
					nodeState = "2";
					isCompany = "2";
				}else{
					nodeState = String.valueOf((Integer.parseInt(nodeState) + 1));	
				}
				
			}
			
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(100);
			DataResult result = ucc.treeLoad(query, id,nodeState,isCompany).search();
			List<Map<String, Object>> list = result.getResultList();
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = new TreeNode();
				Map<String, Object> map = list.get(i);
				node.setId((String) map.get("ID"));
				node.setText((String) map.get("NAME"));
				node.setNodeState((String)map.get("NODESTATE"));
				node.setChecked(false);
				// 判断是否有子节点，如果有则closed否则open
				BigDecimal bd = (BigDecimal) map.get("ISCHILDREN");
				int ischildren = bd.intValue();
				
				if (ischildren == 1) {
					node.setState("closed");
				} else {
					node.setState("open");
				}
				treeNodes.add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return "success";
	}
	
	/**
	 * 分公司 -车队 -线路 -车型
	 * @return
	 */
	public String treeLoadLineModel(){
		
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			
			User user = (User) session.getAttribute(Constants.USER_INFO);
			//List l = user.getHaveQueryOrgScale_bus();
			List l = null;
			Org org = (Org)l.get(0);
			if(id == null){
				id = org.getOrgId();
			}
			// 集团 展现所有分公司
			if("101".equals(id)){
				nodeState = "1";
			}else{
				//分公司
				if(nodeState == null){
					nodeState = "2";
					isCompany = "2";
				}else{
					nodeState = String.valueOf((Integer.parseInt(nodeState) + 1));	
				}
			}
			
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(100);
			DataResult result;
			if("4".equals(nodeState)){
				result = ucc.treeLoadModel(query, id).search();
			}else{
				result = ucc.treeLoad(query, id,nodeState,isCompany).search();
			}
			
			List<Map<String, Object>> list = result.getResultList();
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = new TreeNode();
				Map<String, Object> map = list.get(i);
				node.setId((String) map.get("ID"));
				node.setText((String) map.get("NAME"));
				node.setNodeState(String.valueOf(map.get("NODESTATE")));
				node.setChecked(false);
				// 判断是否有子节点，如果有则closed否则open
				BigDecimal bd = (BigDecimal) map.get("ISCHILDREN");
				int ischildren = bd.intValue();
				
				if("3".equals(nodeState)){
					node.setState("closed");
				}else{
					if (ischildren == 1) {
						node.setState("closed");
					} else {
						node.setState("open");
					}	
				}
				
				
				treeNodes.add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return "success";
	}
	
	public String treeLoadStation(){
		

		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			
			User user = (User) session.getAttribute(Constants.USER_INFO);
			//List l = user.getHaveQueryOrgScale_bus();
			List l = null;
			Org org = (Org)l.get(0);
			
			String superId = org.getSuperId();
			if(!"101".equals(superId) && !"-1".equals(superId) && id == null){
				id = superId;
			}else if(!"101".equals(superId) && !"-1".equals(superId)){
				id = org.getOrgId();
			}
			
			//集团
			if("-1".equals(superId)&& id == null){
				id = "-1";
				nodeState = "1";
			}else if(nodeState == null){ //分公司
				nodeState = "2";
				isCompany = "2";
			}else{
				nodeState = String.valueOf((Integer.parseInt(nodeState) + 1));	
			}
			
			if(id == null){
				id = org.getOrgId();
			}
			
			query = EasyUiUtils.formatGridQuery(request, query);
			query.setPagesize(100);
			
			DataResult result;
			if("3".equals(nodeState)){
				result = ucc.treeLoadStation(query, id).search();
			}else if("2".equals(nodeState)){
				result = ucc.treeLoad2(query, id,nodeState,isCompany).search();	
			}else{
				result = ucc.treeLoad(query, id,nodeState,isCompany).search();	
			}
			List<Map<String, Object>> list = result.getResultList();
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = new TreeNode();
				Map<String, Object> map = list.get(i);
				node.setId((String) map.get("ID"));
				node.setText((String) map.get("NAME"));
				node.setNodeState(String.valueOf(map.get("NODESTATE")));
				node.setChecked(false);
				// 判断是否有子节点，如果有则closed否则open
				String s = String.valueOf(map.get("ISCHILDREN"));
				BigDecimal bd = new BigDecimal(s);
				int ischildren = bd.intValue();
				
				if (ischildren == 1) {
					node.setState("closed");
				} else {
					node.setState("open");
				}
				treeNodes.add(node);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return "success";
	}

	/**
	 * 判断是否有子节点
	 * 
	 * @return
	 */
	public boolean isChildrenNode(String id) {
		Boolean flag = false;
		try {
			flag = ucc.isChildrenNode(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List<TreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<TreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public String getNodeState() {
		return nodeState;
	}

	public void setNodeState(String nodeState) {
		this.nodeState = nodeState;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(String isCompany) {
		this.isCompany = isCompany;
	}

	public String getIsTeam() {
		return isTeam;
	}

	public void setIsTeam(String isTeam) {
		this.isTeam = isTeam;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

}
