package com.becoda.bkms.tls.webmgr.web;

import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.tls.TlsConstants;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;
import com.becoda.bkms.tls.webmgr.pojo.vo.InformationVO;
import com.becoda.bkms.tls.webmgr.ucc.IInformationUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.FileUtil;
import com.becoda.bkms.util.Tools;

/**
 * Date: 2015-7-28
 * Time: 16:04:02
 */
public class InformanageAction extends GenericPageAction {
    private  InformationVO infvo;

    public InformationVO getInfvo() {
        return infvo;
    }

    public void setInfvo(InformationVO infvo) {
        this.infvo = infvo;
    }
    
    private List<String> filePars;
    
    public List<String> getFilePars() {
		return filePars;
	}

	public void setFilePars(List<String> filePars) {
		this.filePars = filePars;
	}

	//根据信息类型查询信息
    public String listInformation() throws BkmsException {
        String type = request.getParameter("type");
        if((type==null||type.equals(""))&&infvo!=null){
            type=infvo.getType();
        }


//        String type = request.getParameter("type");
        IInformationUCC ucc = (IInformationUCC) BkmsContext.getBean("tls_informationUCC");
        String qry_orgId = request.getParameter("qry_orgId");
        if (qry_orgId == null ||qry_orgId .trim().length() == 0) {
            qry_orgId = user.getOrgId();
        }
        List ls = ucc.queryInfoByType(vo,qry_orgId,type);
        request.setAttribute("infoList", ls);
        request.setAttribute("ty", type);
        return "list";
    }
    
    //保存或更新信息
    public String saveInformation() throws BkmsException {
        IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
//        String id = request.getParameter("id");
        String id=infvo.getId();
        InformationBO bo = ucc.findInfoById(id);
        String isPublish = request.getParameter("isPublish");
        if (ucc.checkSameName(infvo.getTitle(), infvo.getType(), id)) {//判断是否存在重名
            showMessageDetail("标题名称已经存在，请重新输入标题！");
            return "edit";
        }
        
        if (bo == null) { //新增
            bo = new InformationBO();
            Tools.copyProperties(bo, infvo);
            if(filePars!=null&&filePars.size()>0){
            	String par [] = filePars.get(0).split(",");
            	bo.setAttachFileURL(par[1]);
            }
            bo.setCreator(user.getUserId());
            bo.setCreateOrgId(user.getOrgId());
            bo.setCreateTime(Tools.getSysDate(null));
            bo.setUpdateTime(Tools.getSysDate(null));
            bo.setStatus(TlsConstants.INFO_STATUS_NO);
            bo.setIsTop(TlsConstants.IS_NOT_TOP);
            bo.setInfoIsBanner(TlsConstants.IS_NOT_BANNER);
            this.publishInfo(bo, hrequest, isPublish, ucc);
            ucc.saveInfomation(bo);
        } else {       //保存
        	if(filePars!=null&&filePars.size()>0){
            	String par [] = filePars.get(0).split(",");
            	bo.setAttachFileURL(par[1]);
            }
            bo.setTitle(infvo.getTitle());
            bo.setInfoSynopsis(infvo.getInfoSynopsis());
            bo.setContent(infvo.getContent());
            bo.setIsTop(TlsConstants.IS_NOT_TOP);
            bo.setUpdateTime(Tools.getSysDate(null));
            bo.setCreator(user.getUserId());
            bo.setCreateOrgId(user.getOrgId());
//            if (!"".equals(imgURL)) {
//                bo.setAttachFileURL(imgURL);
//            }
            this.publishInfo(bo, hrequest, isPublish, ucc);
            ucc.updateInfomation(bo);
        }
        showMessage("保存成功！");
        return listInformation();
    }

    private InformationBO publishInfo(InformationBO bo, BkmsHttpRequest request, String isPublish, IInformationUCC ucc) throws RollbackableException {
        //是否发布
        if (isPublish != null && "isPublish".equals(isPublish)) {
            String url = ucc.buildHtml(bo, request.getRealPath("/"), "information.templete", request.getContextPath());
            bo.setStatus(TlsConstants.INFO_STATUS_YES);
            bo.setStaticFileURL(url);
        }
        return bo;
    }

    //获得某一信息进行编辑
    public String findInfo() throws BkmsException {
        IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
        String id = request.getParameter("id");
        if (id != null && !"".equals(id)) {
            InformationBO bo = ucc.findInfoById(id);
            infvo=new InformationVO();
            Tools.copyProperties(infvo, bo);
        }
        String ty=request.getParameter("type");
         request.setAttribute("ty",ty);
        return "edit";
    }

    //跳转至编辑页面
    public String gotoEdit() throws BkmsException {
        String ty=request.getParameter("type");
        String id=request.getParameter("id");
        request.setAttribute("ty",ty);
        infvo=new InformationVO();
        infvo.setType(ty);
        infvo.setId(id);
//        InformationBO bo= new InformationBO();
//        bo.setType(ty);
//
//        Tools.copyProperties(infvo, bo);
        return "edit";
    }

    //删除信息
    public String deleteInformation() throws BkmsException {
        String id = request.getParameter("id");
        IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
        InformationBO bo = ucc.findInfoById(id);
        ucc.deleteInfomation(id);
        if (bo != null) {
            String path = bo.getStaticFileURL();
            if (path != null && path.trim().length() > 0) {
                path = request.getSession().getServletContext().getRealPath("/") + path;
                FileUtil.delFile(path);
            }
        }
        super.showMessage("删除成功！");
        return listInformation();
    }

    //批量修改信息状态
    public String updateInfoStatus() throws BkmsException {
        String[] ids = request.getParameterValues("chk");
        String status = request.getParameter("status");
        IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
        if (TlsConstants.INFO_STATUS_YES.equals(status)) {
            super.showMessage("发布成功！");
            List list = ucc.queryInfo(ids);
            //模板名称：information.templete
            ucc.saveBuildHtml(list, request.getRealPath("/"), "information.templete", request.getContextPath());
        } else {
            ucc.updateAllStatus(ids, "info_status", status);
            super.showMessage("取消发布成功！");
        }
        return listInformation();
    }

    //批量修改信息是否置顶
    public String updateInfoIsTop() throws BkmsException {
        String[] ids = request.getParameterValues("chk");
        String status = Tools.filterNull(request.getParameter("status"));
        IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
        ucc.updateAllStatus(ids, "INFO_IS_TOP", status);
        if (TlsConstants.IS_TOP.equals(status)) {
            super.showMessage("置顶成功！");
        } else {
            super.showMessage("取消成功！");
        }
        return listInformation();
    }
    
  //批量修改信息是否banner图
    public String updateInfoIsBnner() throws BkmsException {
        String[] ids = request.getParameterValues("chk");
        String status = Tools.filterNull(request.getParameter("status"));
        IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
        ucc.updateAllStatus(ids, "INFO_IS_BANNER", status);
        if (TlsConstants.IS_BANNER.equals(status)) {
            super.showMessage("设置banner图成功！");
        } else {
            super.showMessage("取消banner图成功！");
        }
        return listInformation();
    }
}
