package com.becoda.bkms.common.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.platform.ucc.IPlatFormUCC;
import com.becoda.bkms.util.BkmsContext;

public class DictionaryOptionTaget extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String isTextValue;
	private String index; //字段索引名
	private String display;  //显示字典项,格式:[1,2,3]

	@SuppressWarnings("unchecked")
	@Override
	public int doEndTag() throws JspException {
		
		try {
			IPlatFormUCC ucc = (IPlatFormUCC) BkmsContext.getBean("platFormUCC");
			List<Map<String, String>> list = null;
			list = ucc.findTitleForIndeHasSort(index);
			
			JspWriter jspw = this.pageContext.getOut();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Map<String, String> type = (Map<String, String>) iterator.next();
				try {
					if (isTextValue.trim().equals("1")) {
						/*if("0".equals(type.get("ISDEFAULT"))){ //默认选项
							jspw.println("<option value='" + type.get("ENTITYTYPE") + "'  selected='selected'>"
									+ type.get("NAME") + "</option>");
						}else{
							
						}*/
						if(display != null && !"".equals(display)){
							String[] dis = display.replace("[", "").replace("]", "").split(",");
							for(int i=0;i<dis.length;i++){
								if(type.get("ENTITYTYPE").equals(dis[i])){
									jspw.println("<option value='" + type.get("ENTITYTYPE") + "'>"
											+ type.get("NAME") + "</option>");
								}	
							}
						}else{
							jspw.println("<option value='" + type.get("ENTITYTYPE") + "'>"
									+ type.get("NAME") + "</option>");
						}
						
					} else {
						jspw.println("<option value='" + type.get("ABBR")
								+ "'>" + type.get("NAME") + "</option>");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (BkmsException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public int doStartTag() throws JspException {

		return 0;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getIsTextValue() {
		return isTextValue;
	}

	public void setIsTextValue(String isTextValue) {
		this.isTextValue = isTextValue;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}
	
}
