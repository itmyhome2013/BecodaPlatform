package com.becoda.bkms.parkingms.special.web;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocreRec;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreRecUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
public class SpecialSocreRecQueryAction extends GenericPageAction{
	//根据街道小区id和日期查询扣分记录
	public String querySocreRecList(){
		try {
			String recid = request.getParameter("recid");
			request.setAttribute("recid", recid);
			String socretime = Tools.filterNull(request.getParameter("socretime"));
			if (socretime.equals("")) {
				socretime = Tools.getSysDate("yyyyMMdd");
			}
			if(!"".equals(socretime)){
				if(socretime.length()==7){
					socretime =	socretime.substring(0,6)+"0"+socretime.substring(6,socretime.length());
				}
			}
			request.setAttribute("socretime", socretime);
			ISpecialSocreRecUCC specialsocrerecUCC = (ISpecialSocreRecUCC) BkmsContext.getBean("specialsocrerecUCC");
			ParmsSpecialSocreRec socrerec = new ParmsSpecialSocreRec();
			socrerec.setRecId(recid);
			socrerec.setSocreTime(socretime);
			List socrereclist = specialsocrerecUCC.queryListPage(vo, socrerec);
			request.setAttribute("socrereclist", socrereclist);
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
			this.addActionError(he.getFlag() + he.getCause().getMessage());
		}
		return "querySocreRecList";
	}

}
