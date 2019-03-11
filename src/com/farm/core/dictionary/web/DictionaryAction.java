package com.farm.core.dictionary.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import com.farm.core.dictionary.bo.DictionaryBO;
import com.farm.core.dictionary.ucc.IDictionaryUCC;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.tree.EasyUiTreeNode;
import com.farm.core.util.ParameterUtils;

/**
 * 数据字典
 * @author huxuxu
 */
public class DictionaryAction {
	private DataResult result;// 结果集合
	private Map<String, Object> jsonResult;// 结果集合
	private DictionaryBO entity;// 实体封装
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合
	private String type; //类型
	
	private String id;
	private String index;
	private List<EasyUiTreeNode> treeNodes;// 异步树的封装
	
	private IDictionaryUCC ucc;
	public DictionaryAction(){
		try {
			ucc = (IDictionaryUCC) BkmsContext.getBean("dic_dictionaryUCC");
		} catch (BkmsException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			query = EasyUiUtils.formatGridQuery(request, query);
			query.addDefaultSort(new DBSort("utime", "desc nulls last"));
			DataResult result = ucc.queryAll(query).search();
			result.runDictionary("1:序列,0:树", "TYPE");
			
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}
	
	
	public String loadTree() {
		if(id==null){
			id="NONE";
		}
		treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(EasyUiTreeNode
				.queryTreeNodeOne(id, "SORT", " (SELECT a.id AS ID,a.treecode AS TREECODE,a.entitytype AS ENTITYTYPE,b.ENTITYINDEX as ENTITYINDEX,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM dictionary_type a LEFT JOIN dictionary_entity b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and ENTITYINDEX='"+index+"' and a.state!=2").getResultList(), EasyUiTreeNode
				.queryTreeNodeTow(id, "SORT", " (SELECT a.id AS ID,a.treecode AS TREECODE,a.entitytype AS ENTITYTYPE,b.ENTITYINDEX as ENTITYINDEX,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM dictionary_type a LEFT JOIN dictionary_entity b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and a.ENTITYINDEX='"+index+"' and a.state!=2").getResultList(), "PARENTID", "ID",
				"NAME","CTIME");
		return "success";
	}
	
	/**
	 * 加载树节点
	 * 
	 * @return
	 */
	public String loadTreeNode() {
		
		treeNodes = EasyUiTreeNode.formatAsyncAjaxTree(EasyUiTreeNode
				.queryTreeNodeOne(id, "SORT", " (SELECT a.id AS ID,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM DICTIONARY_TYPE a LEFT JOIN DICTIONARY_ENTITY b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and a.ENTITY='"+ids+"' and a.state!=2").getResultList(), EasyUiTreeNode
				.queryTreeNodeTow(id, "SORT", " (SELECT a.id AS ID,a.parentid AS PARENTID,a.name AS NAME,a.ctime AS CTIME,a.sort AS SORT,a.entity AS ENTITY,a.state AS STATE FROM DICTIONARY_TYPE a LEFT JOIN DICTIONARY_ENTITY b ON a.ENTITY = b.id  WHERE b.type = 0) ", "ID",
						"PARENTID", "NAME","CTIME"," and a.ENTITY='"+ids+"' and a.state!=2").getResultList(), "PARENTID", "ID",
				"NAME","CTIME");
		return "success";
	}
	
	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			if(entity.getId()!=null && !"".equals(entity.getId())){
				ucc.updateDictionary(entity);
			}else{
				ucc.saveDictionary(entity);	
			}
			pageset = new PageSet(PageType.ADD, CommitType.TRUE);
		} catch (Exception e) {
			e.printStackTrace();
			pageset = new PageSet(PageType.ADD, CommitType.FALSE, e
					.getMessage());
		}
		return "success";
	}
	
	/**
	 * 删除数据
	 * @return
	 */
	public String delSubmit(){
		try {
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				ucc.deleteDictionary(id);
			}
			pageset = new PageSet(PageType.DEL, CommitType.TRUE);
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				return "success";
			}
			case (0): {// 展示
				entity = ucc.getDictionary(ids);
				return "success";
			}
			case (2): {// 修改
				entity = ucc.getDictionary(ids);
				return "success";
			}
			default:
				break;
			}
		} catch (Exception e) {
			pageset = PageSets.initPageSet(pageset, PageType.OTHER,
					CommitType.FALSE, e);
		}
		return "success";
	}
	
	public String forSend() {
		if("1".equals(type)){
			return "sequence";
		}else if("0".equals(type)){
			return "tree";
		}else{
			return "success";
		}
	}

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public PageSet getPageset() {
		return pageset;
	}

	public void setPageset(PageSet pageset) {
		this.pageset = pageset;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public DictionaryBO getEntity() {
		return entity;
	}

	public void setEntity(DictionaryBO entity) {
		this.entity = entity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<EasyUiTreeNode> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<EasyUiTreeNode> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
}
