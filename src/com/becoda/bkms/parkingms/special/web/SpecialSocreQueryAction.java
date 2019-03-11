package com.becoda.bkms.parkingms.special.web;

import java.util.ArrayList;
import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocre;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialSocreRec;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialSocreVo;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreRecUCC;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
/**
 * 街道小区分值记录Action
 * @author lhq
 *
 */
public class SpecialSocreQueryAction extends GenericPageAction {
	private ParmsSpecialSocreVo socre;

	public ParmsSpecialSocreVo getSocre() {
		return socre;
	}

	public void setSocre(ParmsSpecialSocreVo socre) {
		this.socre = socre;
	}

	//按街道小区分值查询（分页查询）
	public String querySocreList() {
		try {
			ISpecialSocreUCC socreucc = (ISpecialSocreUCC) BkmsContext.getBean("specialsocreUCC");
			ISpecialSocreRecUCC socrerecucc = (ISpecialSocreRecUCC) BkmsContext.getBean("specialsocrerecUCC");
			ParmsSpecialSocreVo socreVo=new ParmsSpecialSocreVo();
			String socretime=Tools.filterNull(request.getParameter("socretime"));//获取页面查询时间
			if("".equals(socretime)){
				socretime=Tools.getSysDate("yyyyMMdd");
			}if(!"".equals(socretime)){
				if(socretime.length()==7){
					socretime =	socretime.substring(0,6)+"0"+socretime.substring(6,socretime.length());
				}
			}
			//request.setAttribute("socretime", socretime);
			socreVo.setSocreTime(socretime);
			//vo.setPageSize(5);
			List socreList=socreucc.querySocreList(vo,socreVo);
			List list=new ArrayList();
			ParmsSpecialSocreRec socrerec=new ParmsSpecialSocreRec();
			for(int i=0;i<socreList.size();i++){
				ParmsSpecialSocre socre=(ParmsSpecialSocre)socreList.get(i);
				socrerec.setRecId(socre.getRecId());
				socrerec.setSocreId(socre.getSocreId());
				int yearsocre = socreucc.getYearSocre(socre, socreVo.getSocreTime().substring(0, 4));//年扣分
				int monthsocre = socreucc.getMonthSocre(socre, socreVo.getSocreTime().substring(0, 6));//月扣分
				int weksocre = socrerecucc.getWekSocre(socrerec, socreVo.getSocreTime().substring(0, 8));//周扣分
				socre.setYearsoc(yearsocre+"");
				socre.setMonthsoc(monthsocre+"");
				socre.setWeksoc(weksocre+"");
				list.add(socre);
			}
			request.setAttribute("socreList", list);
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
			System.out.println(he.getFlag() + he.getCause().getMessage());
			this.addActionError(he.getFlag() + he.getCause().getMessage());
		}
		return "querySocreList";
	}
	//按城区分值查询(分页查询)
	public String queryCqSocreList(){
		try {
			List queryCqSocreList=new ArrayList();
			String socretime=Tools.filterNull(request.getParameter("socretime"));//获取页面查询时间
			if("".equals(socretime)){
				socretime=Tools.getSysDate("yyyyMMdd");
			}if(!"".equals(socretime)){
				if(socretime.length()==7){
					socretime =	socretime.substring(0,6)+"0"+socretime.substring(6,socretime.length());
				}
			}
			IOrgUCC orgucc=(IOrgUCC) BkmsContext.getBean("org_orgUCC");
			ISpecialSocreUCC socreucc = (ISpecialSocreUCC) BkmsContext.getBean("specialsocreUCC");
			ISpecialSocreRecUCC socrerecucc = (ISpecialSocreRecUCC) BkmsContext.getBean("specialsocrerecUCC");
			List list = orgucc.queryOrgBysuperId(vo, "291f00f4001636140ed5961911deb193");//查询所有城区
			ParmsSpecialSocreRec socrerec=new ParmsSpecialSocreRec();
			ParmsSpecialSocre socre=new ParmsSpecialSocre();
			for(int i=0;i<list.size();i++){
				OrgBO orgbo=(OrgBO)list.get(i);
				int yearsocre=0;
				int monthsocre=0;
				int weksocre=0;
				//查询年月扣分条件
				socre.setCtcid(orgbo.getOrgId());
				socre.setSocreTime(socretime);
				//查询周扣分条件（查询扣分记录表）
				socrerec.setCtcid(orgbo.getOrgId());
				socrerec.setSocreTime(socretime);
				yearsocre = socreucc.getYearSocre(socre, socretime.substring(0, 4));//年扣分
				monthsocre = socreucc.getMonthSocre(socre, socretime.substring(0, 6));//月扣分
				weksocre = weksocre+socrerecucc.getWekSocre(socrerec, socretime.substring(0, 8));//周扣分
				orgbo.setYearsoc(yearsocre+"");
				orgbo.setMonthsoc(monthsocre+"");
				orgbo.setWeksoc(weksocre+"");
				queryCqSocreList.add(orgbo);
			}
			request.setAttribute("queryCqSocreList", queryCqSocreList);
			
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
			this.addActionError(he.getFlag() + he.getCause().getMessage());
		}
		return "queryCqSocreList";
	}
	//按城区扣分查询，生成生成图使用
	public String queryCqSocreListCreateT(){
		try {
			List queryCqSocreList=new ArrayList();
			String socretime=Tools.filterNull(request.getParameter("socretime"));//获取页面查询时间
			if("".equals(socretime)){
				socretime=Tools.getSysDate("yyyyMMdd");
			}if(!"".equals(socretime)){
				if(socretime.length()==7){
					socretime =	socretime.substring(0,6)+"0"+socretime.substring(6,socretime.length());
				}
			}
			IOrgUCC orgucc=(IOrgUCC) BkmsContext.getBean("org_orgUCC");
			ISpecialSocreUCC socreucc = (ISpecialSocreUCC) BkmsContext.getBean("specialsocreUCC");
			ISpecialSocreRecUCC socrerecucc = (ISpecialSocreRecUCC) BkmsContext.getBean("specialsocrerecUCC");
			List list = orgucc.queryInsideOrgBySuperId("291f00f4001636140ed5961911deb193");//查询所有城区
			ParmsSpecialSocreRec socrerec=new ParmsSpecialSocreRec();
			ParmsSpecialSocre socre=new ParmsSpecialSocre();
			for(int i=0;i<list.size();i++){
				OrgBO orgbo=(OrgBO)list.get(i);
				int yearsocre=0;
				int monthsocre=0;
				int weksocre=0;
				//查询年月扣分条件
				socre.setCtcid(orgbo.getOrgId());
				socre.setSocreTime(socretime);
				//查询周扣分条件（查询扣分记录表）
				socrerec.setCtcid(orgbo.getOrgId());
				socrerec.setSocreTime(socretime);
				yearsocre = socreucc.getYearSocre(socre, socretime.substring(0, 4));//年扣分
				monthsocre = socreucc.getMonthSocre(socre, socretime.substring(0, 6));//月扣分
				weksocre = weksocre+socrerecucc.getWekSocre(socrerec, socretime.substring(0, 8));//周扣分
				orgbo.setYearsoc(yearsocre+"");
				orgbo.setMonthsoc(monthsocre+"");
				orgbo.setWeksoc(weksocre+"");
				queryCqSocreList.add(orgbo);
			}
			request.setAttribute("queryCqSocreList", queryCqSocreList);
			
		} catch (Exception e) {
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
			this.addActionError(he.getFlag() + he.getCause().getMessage());
		}
//		String types= Tools.filterNull(request.getParameter("types"));
//		if(types.equals("column")){
//			request.setAttribute("titletext", "各城区扣分图");
//			return "queryCqSocreListcolumn";//柱状图
//		}
	//	request.setAttribute("titletext", "各城区月扣分百分比图");
		return "queryCqSocreListpie";//饼图
	}

}
