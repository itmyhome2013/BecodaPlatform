package com.becoda.bkms.csu.examinfo.web;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.examinfo.pojo.KpQuestion;
import com.becoda.bkms.csu.examinfo.ucc.KpQuestionUCC;
import com.becoda.bkms.util.BkmsContext;

/**
 * Date: 2015-7-28
 * Time: 16:04:02
 */
public class KpQuestionAction extends GenericAction {
		private KpQuestion kpQuestion;
		private JSONObject resutObj;  
		private Map<String, Object> json= new  HashMap<String, Object>();



		






		public Map<String, Object> getJson() {
			return json;
		}



		public void setJson(Map<String, Object> json) {
			this.json = json;
		}



		public JSONObject getResutObj() {
			return resutObj;
		}



		public void setResutObj(JSONObject resutObj) {
			this.resutObj = resutObj;
		}



		//根据信息类型查询信息
	    public String listInformation() throws BkmsException {
	    	KpQuestionUCC ucc = (KpQuestionUCC) BkmsContext.getBean("kp_examinfoUCC");
	    	json = ucc.queryInfoByType(page,rows,kpQuestion);
	        //Map<String, Object> json = new HashMap<String, Object>();     
	       /* json.put("rows", ls);  // rows键 存放每页记录 list 
	        json.put("total", 2);//total键 存放总记录数，必须的 
*/	        //resutObj = JSONObject.fromObject(json);
	        return SUCCESS;
	    }
		
		

		public KpQuestion getKpQuestion() {
			return kpQuestion;
		}

		public void setKpQuestion(KpQuestion kpQuestion) {
			this.kpQuestion = kpQuestion;
		}
}
