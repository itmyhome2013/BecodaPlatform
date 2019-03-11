package com.becoda.bkms.tls.webmgr.web;

import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.tls.TlsConstants;
import com.becoda.bkms.tls.webmgr.pojo.bo.QuestionnaireBO;
import com.becoda.bkms.tls.webmgr.pojo.vo.QuestionnaireVO;

import com.becoda.bkms.tls.webmgr.ucc.IQuestionnaireUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;


import javax.servlet.http.HttpSession;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class QuestionnaireAction extends GenericPageAction {
	private QuestionnaireVO qvo;
	

	public QuestionnaireVO getQvo() {
		return qvo;
	}

	public void setQvo(QuestionnaireVO qvo) {
		this.qvo = qvo;
	}

	// 查询问卷表
	public String queryQuestionnaire() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");	
		try {
			String sur_start_date = request.getParameter("sur_start_date");
			// sur_start_date=sur_start_date.substring(0, 4);
			// System.out.println(sur_start_date);
			List ls = ucc.queryQues(vo, sur_start_date);
			Map map = ucc.queryOption(sur_start_date);
			request.setAttribute("list", ls);
			request.setAttribute("map", map);
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		//return mapping.getInputForward();
		return "queryQuestionnaire";

	}

	// 新增修改调查问卷
	public String editQuestionnaire() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		String surid = request.getParameter("sur_id");
		// QuestionnaireBO quesbo = null;
		if (surid.equals("") || surid == null) {
			qvo=new QuestionnaireVO();
			qvo.setSur_start_date("");
			qvo.setSur_type("1");
			qvo.setSur_jointype("1");
		} else {
			
			qvo=new QuestionnaireVO();
			qvo.setSur_id(surid);
			QuestionnaireBO quesbo = ucc.queryQuesBySurID(surid);
			Tools.copyProperties(qvo, quesbo);
		}
		return "editQuestionnaire";
	}

	// 保存调查问卷
	public String saveQuestionnaire() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {

			if(qvo.getSur_type().equals("1"))
				qvo.setSur_canselect_num("");

			String surid = (String) request.getParameter("sur_id");
			// if (ucc.checkSameName(vo.getSur_name())) {// 判断是否存在重名
			// super.showMessageDetail("问卷名称已经存在，请重新输入标题！");
			// return mapping.findForward("editQuestionnaire");
			// }
			QuestionnaireBO bo = new QuestionnaireBO();
			Tools.copyProperties(bo, qvo);
			if (qvo.getSur_id().equals("") || qvo.getSur_id() == null) {
				bo.setSur_stats(TlsConstants.QU_STATUS_NO);
				bo.setCreator(user.getUserId());
				bo.setCreate_orgid(user.getOrgId());
				bo.setCreate_date(Tools.getSysDate("yyyy-MM-dd HH:mm:ss"));
					
				surid = ucc.saveQuestionnaire(bo);
			} else {
//				bo = ucc.queryQuesBySurID(surid);
//				bo.setSur_name(vo.getSur_name());
//				bo.setSur_start_date(vo.getSur_start_date());
//				bo.setSur_end_date(vo.getSur_end_date());
//				bo.setSur_type(vo.getSur_type());
//				bo.setSur_select_num(vo.getSur_select_num());
//				bo.setSur_canselect_num(vo.getSur_canselect_num());
//				bo.setSur_view_other(vo.getSur_view_other());
//				bo.setSur_jointype(vo.getSur_jointype());
//
//				vo.setSur_id(bo.getSur_id());
//				vo.setCreator(bo.getCreator());
//				vo.setCreate_orgid(bo.getCreate_orgid());
//				vo.setCreate_date(bo.getCreate_date());
				
				ucc.updateQuestionnaire(bo);
			}
			request.setAttribute("surid", surid);
			showMessageDetail("保存成功！");

			// this.listInformation(user, mapping, form, request, response,
			// session);
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		
		return this.listQuestionnaire();
	}

	public String backQuestionnaire() throws BkmsException {
		return listQuestionnaire();

		//return mapping.findForward("queryQuestionnaire");
	}

	public String listQuestionnaire() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {
			
			String sur_start_date = null;
			List ls = ucc.queryQues(vo, sur_start_date);
			Map map = ucc.queryOption(sur_start_date);
			request.setAttribute("list", ls);
			request.setAttribute("map", map);
			//List ls = null;
			ls = ucc.queryQues();
			request.setAttribute("list", ls);
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "queryQuestionnaire";
	}

	// 删除调查问卷信息
	public String deleteInformation() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {

			String[] ids = request.getParameterValues("chk");
			ucc.deleteQuestionnaires(ids);

			super.showMessageDetail("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		listQuestionnaire();
		return "queryQuestionnaire";
	}

	// 批量修改调查问卷状态
	public String updateQuesStatus() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {
			String[] ids = request.getParameterValues("chk");
			String status = request.getParameter("status");
			if(ucc.checkIsNullOption(ids)){
				super.showMessageDetail("问卷选项没有内容！");
				
			}else{
			ucc.updateAllStatus(ids, status);
			super.showMessageDetail("发布成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		listQuestionnaire();
		return "queryQuestionnaire";
	}

	// 修改增加调查选项跳转
	public String editOptions() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		String surid = hrequest.getParameter("surid");
		QuestionnaireBO quesbo = ucc.queryQuesBySurID(surid);
		List optionsList = ucc.queryOptionsBySur(surid);
		if (optionsList.size() != 0) {
			Iterator it = optionsList.iterator();
			String[] options = new String[optionsList.size()];
			for (int i = 0; it.hasNext(); i++) {
				Map optionMap = (Map) it.next();
				if(((String) optionMap.get("OPT_TYPE")).equals("2")){
					request.setAttribute("special", (String) optionMap.get("OPT_NAME"));
					
					break;
				}
				options[i] = (String) optionMap.get("OPT_NAME");
				
			}
			request.setAttribute("options", options);
		}
		request.setAttribute("quesbo", quesbo);
		return "editOptions";

	}

	// 保存调查问卷选项
	public String saveQuesoptions() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		request.setAttribute("js", "window.returnValue='true';self.close();");
		String[] contents = request.getParameterValues("content");
		String special = request.getParameter("special");
		String surid = request.getParameter("surid");
		if (ucc.queryOptionsBySurID(surid) != 0) {
			ucc.deleteOptions(new String[] { surid });
		}
		ucc.saveOptions(surid, contents, special);
		super.showMessageDetail("保存选项成功");
		request.setAttribute("message", "保存选项成功");
		return "closeOptions";
	}

	// 调查范围的跳转
	public String editQuesRight() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		//QuestionnaireVO vo = (QuestionnaireVO) form;
		QuestionnaireVO qvo = null;
		String name = request.getParameter("perOrgNames");
		String surid = request.getParameter("sur_id");
		String type = request.getParameter("type");
		 request.setAttribute("type", type);
		 qvo.setSur_id(surid);
		
		List list = ucc.queryQuesRightByName(vo, surid, name,type);
		
		if (list != null)
			request.setAttribute("list", list);
		return "editright";
	}

	// 增加调查部门/人员
	public String saveQuesRight() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		 String id = request.getParameter("perOrgIds");
		String surid = request.getParameter("sur_id");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		if (!id.equals("")) {
			String[] per_org_id =id.split(",");
		
			ucc.saveQuesRight(new String[] { surid }, per_org_id, type);

			super.showMessageDetail("增加成功");
		}
		String name = null;
		List list = ucc.queryQuesRightByName(vo, surid, name,type);

		if (list != null)
			request.setAttribute("list", list);
		return "editright";
	}

	// 删除调查人员
	public String deleteQuesRights() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {

			String[] ids = request.getParameterValues("chk");

			//ucc.deleteQuesRights(ids);
			super.showMessageDetail("删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		String surid = request.getParameter("sur_id");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		String name = null;
		List list = ucc.queryQuesRightByName(vo, surid, name,type);
		if (list != null)
			request.setAttribute("list", list);
		return "editright";
	}

	// 培训班导入
	public String serachPlan()
			throws BkmsException {
//		QuestionnaireVO vo = (QuestionnaireVO) form;
//		vo.setSur_id(request.getParameter("sur_id"));
//		
//		TlsActualizeManageUCC ucc = (TlsActualizeManageUCC) getBean("tls_ActualizeManageUCC");
//		try {
//			String planYear = request.getParameter("pxyear");
//			//System.out.println(planYear);
//			if (Tools.filterNull(planYear).equals("")) {
//				planYear = Tools.getSysDate("yyyy");
//			}
//			TlsPlanManageBO[] planManageBO = ucc.queryPlanList(planYear, user
//					.getOrgId());
//			request.setAttribute("planManageBO", planManageBO);
//			request.setAttribute("planYear", planYear);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
//            this.addActionError(he.getFlag()+he.getCause().getMessage());
//		}

		return "import";
	}

	

	// 调查结果
	public String analysisResult() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {
			String surid = request.getParameter("sur_id");
			String surname = request.getParameter("sur_name");
			request.setAttribute("surname", surname);
			//QuestionnaireVO vo = (QuestionnaireVO) form;
			QuestionnaireVO vo = new QuestionnaireVO();
			vo.setSur_name(surname);
			vo.setSur_id(surid);
			List optionsList = ucc.queryOptionsBySur(surid);
			if (optionsList.size() != 0) {
				Iterator it = optionsList.iterator();
				String[] optionName = new String[optionsList.size()];
				for (int i = 0; it.hasNext(); i++) {
					Map optionMap = (Map) it.next();
					optionName[i] = (String) optionMap.get("OPT_NAME");
				}
				request.setAttribute("optionsName", optionName);
			}
			List numList = ucc.queryResult(surid);
			if (numList.size() != 0) {
				Iterator it = numList.iterator();
				String[] num = new String[numList.size()];
				String[] no = new String[numList.size()];
				for (int i = 0; it.hasNext(); i++) {
					Map numMap = (Map) it.next();
					num[i] = String.valueOf(numMap.get("num"));
					no[i] = String.valueOf(numMap.get("no"));
				}
				request.setAttribute("num", num);
				request.setAttribute("no", no);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}

		return "analysis";
	}
	
	
	
	// 查看其他调查结果
	public String viewOtherResult() throws BkmsException {
		IQuestionnaireUCC ucc = (IQuestionnaireUCC) BkmsContext.getBean("tls_questionnaireUCC");
		try {
			String surid = request.getParameter("sur_id");
			List otherResults = ucc.queryResultOther(surid);
			if (otherResults.size() != 0) {
				Iterator it = otherResults.iterator();
				String[] others = new String[otherResults.size()];
				for (int i = 0; it.hasNext(); i++) {
					Map optionMap = (Map) it.next();
					others[i] = (String) optionMap.get("OTHER_INFO");
				}
				request.setAttribute("otherResults", others);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}

		return "viewother";
	}

}