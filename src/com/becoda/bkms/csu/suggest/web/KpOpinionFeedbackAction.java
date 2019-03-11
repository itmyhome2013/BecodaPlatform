package com.becoda.bkms.csu.suggest.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;
import com.becoda.bkms.csu.suggest.ucc.IKpOpinionFeedbackUCC;
import com.becoda.bkms.csu.suggest.vo.KpOpinionFeedbackForm;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.util.Tools;

/**
 * 意见反馈Action User: yinhui Date: 2015-4-9 Time: 11:08:19
 */
@SuppressWarnings("serial")
public class KpOpinionFeedbackAction extends GenericAction {

	private String opinionId;  //意见主键ID
	
	private String foreignId;  //意见外键ID
	
	private String type;       //意见反馈类型
	
	private String content;    //意见反馈内容
	
	private KpOpinionFeedbackForm form;
	
	/**
	 * 保存意见反馈信息
	 * 
	 * @return
	 * @throws BkmsException
	 */
	public String saveKpOpinionFeedback() throws BkmsException {
		KpOpinionFeedback pojo = null;
		KpOpinionFeedbackForm kaf = (KpOpinionFeedbackForm) form;
		String flag = Tools.filterNull(kaf.getOpinionId());
		try {
			IKpOpinionFeedbackUCC ucc = (IKpOpinionFeedbackUCC) getBean("suggest_kpOpinionFeedbackUCC");
			if ("".equals(flag)) { // 添加
				pojo = new KpOpinionFeedback();
				Tools.copyProperties(pojo, form);
			// attachement.setCreateTime(Tools.getSysDate("yyyy-MM-dd"));
				pojo.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				pojo.setForeignId(foreignId);
				ucc.addKpOpinionFeedback(pojo, user);
			} else {// 修改
				pojo = ucc.findKpOpinionFeedback(form.getOpinionId(), user);
				pojo.setType(form.getType());
				pojo.setContent(form.getContent());
				pojo.setUpdateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				ucc.updateKpOpinionFeedback(pojo, user);
			}
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("text/html;charset=UTF-8");
			try {
				httpResponse.getWriter().print("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.showMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer()
					.append(getClass().getName()).append("错误:").toString(), e,
					this.getClass());
			this.addActionError(he.getFlag() + he.getCause().getMessage());
		}
		return null;
	}

	/**
	 * 获取意见反馈信息
	 * @return
	 * @throws BkmsException
	 */
	public String getKpOpinionFeedback() throws BkmsException{
		KpOpinionFeedback temp = null;
		try {
			IKpOpinionFeedbackUCC ucc = (IKpOpinionFeedbackUCC) getBean("suggest_kpOpinionFeedbackUCC");
			temp = ucc.findKpOpinionFeedback(opinionId, user);
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e,this.getClass());
			this.addActionError(he.getFlag() + he.getCause().getMessage());
		}
		JSONObject jo = JSONObject.fromObject(temp);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 跳到列表页面
	 * 
	 * @return
	 * @throws BkmsException
	 */
	public String toList() throws BkmsException {
		return "toList";
	}

	/**
	 * 查询意见反馈信息
	 * @return
	 * @throws BkmsException
	 */
	public String queryList() throws BkmsException {
		KpOpinionFeedback pojo = new KpOpinionFeedback();
		pojo.setType(type);
		pojo.setContent(content);
		IKpOpinionFeedbackUCC ucc = (IKpOpinionFeedbackUCC) getBean("suggest_kpOpinionFeedbackUCC");
//		Tools.copyProperties(pojo, form);
//		KpOpinionFeedbackForm kaf = (KpOpinionFeedbackForm) form;
		Map map = ucc.queryList(pojo, page, rows, user);
		// JSONArray jsonArray = JSONArray.fromObject(isProblemList);
		JSONObject jo = JSONObject.fromObject(map);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除意见反馈信息
	 * @throws BkmsException
	 */
	public String deleteEntity() throws BkmsException {
		String re = "sucess";
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			IKpOpinionFeedbackUCC ucc = (IKpOpinionFeedbackUCC) getBean("suggest_kpOpinionFeedbackUCC");
			if(!"".equals(ids)&&null!=ids) {
				ucc.deleteEntitys(ids);
			}
		} catch (Exception e) {
			re = "error";
			e.printStackTrace();
		}
		try {
			httpResponse.getWriter().print(re);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getOpinionId() {
		return opinionId;
	}

	public void setOpinionId(String opinionId) {
		this.opinionId = opinionId;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public KpOpinionFeedbackForm getForm() {
		return form;
	}

	public void setForm(KpOpinionFeedbackForm form) {
		this.form = form;
	}

}
