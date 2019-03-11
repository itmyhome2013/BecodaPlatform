package com.farm.core.dictionary.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.util.BkmsContext;
import com.farm.core.dictionary.bo.DictionaryTypeBO;
import com.farm.core.dictionary.ucc.IDictionaryTypeUCC;
import com.farm.core.page.CommitType;
import com.farm.core.page.PageSet;
import com.farm.core.page.PageSets;
import com.farm.core.page.PageType;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.util.ParameterUtils;

/**
 * 字典类型
 * @author huxuxu
 *
 */
public class DictionaryTypeAction {
	private DataResult result;// 结果集合
	private Map<String, Object> jsonResult;// 结果集合
	private DictionaryTypeBO entity;// 实体封装
	private DataQuery query;// 条件查询
	private PageSet pageset;// 请求状态
	private String ids;// 主键集合

	/* 页面参数 */
	private String dicId;// 用于关联字典类型
	private String id;// 异步树传入的id
	private String parentId; // 父ID
	private String parentName;// 父组织机构名称
	private String type; // 类型

	private String treeCode;
	private boolean isHasDefault;
	
	/**
	 * 查询结果集合
	 * 
	 * @return
	 */
	public String queryall() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			query = EasyUiUtils.formatGridQuery(request, query);
			query = DataQuery.init(query, "dictionary_type a LEFT JOIN dictionary_type b ON a.parentid = b.id",
					"a.id AS ID,a.sort AS SORT,a.name AS NAME,a.entitytype AS ENTITYTYPE,a.state AS STATE,b.name AS PNAME,a.isdefault as isdefault,a.parentid as parentid");
			query.addUserWhere(" and (a.state = '0' or a.state = '1') ORDER BY cast(a.sort as int) asc");// 查询非删除的组织机构
			//query.addDefaultSort(new DBSort("a.sort", "asc"));
			if(ids!=null&&ids.trim().length()>0){
				query.addRule(new DBRule("a.entity", ids, "="));
			}
			result = query.search();
			result.runDictionary("1:可用,0:禁用", "STATE");
			result.runDictionary("1:否,0:是", "ISDEFAULT");
			jsonResult = EasyUiUtils.formatGridData(result);
		} catch (Exception e) {
			e.printStackTrace();
			result = DataResult.getInstance(
					new ArrayList<Map<String, Object>>(), 0, 1, 10);
			result.setMessage(e + e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 检查是否已存在默认值 
	 */
	public String checkIsHasDefault(){
		try {
			IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
			isHasDefault = ucc.checkIsHasDefault(entity.getEntity());	
		} catch (BkmsException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 提交新增数据
	 * 
	 * @return
	 */
	public String addSubmit() {
		try {
			IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
			if(entity.getId()!=null && !"".equals(entity.getId())){
				ucc.updateDictionaryType(entity);
			}else{
				ucc.saveDictionaryType(entity);	
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
			IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
			for (String id : ParameterUtils.expandDomainPara(ids)) {
				ucc.deleteDictionaryType(id);
			}
			pageset = new PageSet(PageType.DEL, CommitType.TRUE);
		} catch (BkmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "success";
	}
	
	public String view() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				
				if (dicId == null || dicId.equals("")) {
					throw new RuntimeException("找不到关联的数据字典！");
				}

				entity = new DictionaryTypeBO();
				entity.setEntity(dicId);
				return "success";
			}
			case (0): {// 展示
				IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
				entity = ucc.getDictionaryType(ids);
				return "success";
			}
			case (2): {// 修改
				IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
				entity = ucc.getDictionaryType(ids);
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
	
	/**
	 * (树组织结构)显示详细信息
	 * 
	 * @return
	 */
	public String viewTree() {
		try {
			switch (pageset.getPageType()) {
			case (1): {// 新增
				if(dicId==null||dicId.equals("")){
					throw new RuntimeException("找不到关联的数据字典！");
				}
				IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
				entity = new DictionaryTypeBO();
				entity.setEntity(dicId);
				if (parentId != null && !parentId.equals("")) {
					DictionaryTypeBO pEntity = ucc.getDictionaryType(parentId);
					if (pEntity.getState().equals("1")) {
						parentName = pEntity.getName();// 回显父组织机构名称
						entity.setParentid(parentId);
					}
				}else{
					entity.setParentid("NONE");
				}
				
			}
			case (0): {// 展示
				return "success";
			}
			case (2): {// 修改
				IDictionaryTypeUCC ucc = (IDictionaryTypeUCC) BkmsContext.getBean("dic_dictionaryTypeUCC");
				entity = ucc.getDictionaryType(ids);
				if (parentId != null && !parentId.equals("")) {
					DictionaryTypeBO pEntity = ucc.getDictionaryType(parentId);
					if (pEntity.getState().equals("1")) {
						parentName = pEntity.getName();// 回显父组织机构名称
						entity.setParentid(parentId);
					}
				}else{
					entity.setParentid("NONE");
				}
				return "success";
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return "success";
	}

	public DataResult getResult() {
		return result;
	}

	public void setResult(DataResult result) {
		this.result = result;
	}

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public DictionaryTypeBO getEntity() {
		return entity;
	}

	public void setEntity(DictionaryTypeBO entity) {
		this.entity = entity;
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

	public String getDicId() {
		return dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public boolean isHasDefault() {
		return isHasDefault;
	}

	public void setHasDefault(boolean isHasDefault) {
		this.isHasDefault = isHasDefault;
	}

}
