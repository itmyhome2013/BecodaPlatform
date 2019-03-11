package com.becoda.bkms.csu.common.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.csu.common.pojo.KpAttachement;
import com.becoda.bkms.csu.common.ucc.IKpAttachementUCC;
import com.becoda.bkms.csu.common.vo.KpAttachementForm;
import com.becoda.bkms.csu.suggest.pojo.KpOpinionFeedback;
import com.becoda.bkms.csu.suggest.ucc.IKpOpinionFeedbackUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.util.FileUtil;
import com.becoda.bkms.util.Tools;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-9
 * Time: 11:08:19
 */
public class KpAttachementAction extends GenericAction {
	
	private static final long serialVersionUID = 8060456981726568542L;
	private String fileId;         //文件主键
	private String foreignId;      //关联外键
	private String createTime;       //创建时间
	private String createUserId;   //创建人ID
	private String createUserName; //创建人姓名
	private String updateTime;       //修改时间
	private String updateUserId;   //修改人ID
	private String updateUserName; //修改人姓名
	private String fileName;    //文件名称
	private String serialName;  //编号形式的文件名称
	private String fileUrl;     //文件的原路径  如：视频的原路径，图片的原路径，文件的原路径
	private String fileHdUrl;   //视频文件的高清路径   
	private String fileSdUrl;   //视频文件 的普清路径
	private String fileImgUrl;  //视频文件的截图文件路径
	private String fileCode;    //文件编码
	private String fileType;    //文件类型 1：视频   2：图片 3：文件 4：其他
	
	private KpAttachementForm form;
	
	/**
	 * 获取附件基本信息
	 * @return
	 * @throws BkmsException
	 */
	public String getEntity() throws BkmsException {
		KpAttachement attachement = null;
        try {
        	IKpAttachementUCC ucc = (IKpAttachementUCC) getBean("comm_kpAttachementUCC");
        	attachement = ucc.findEntity(fileId, user);
            Tools.copyProperties(attachement, form);
        } catch (Exception e) {
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        JSONObject jo = JSONObject.fromObject(attachement);
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
	 * 保存附件信息
	 * @return
	 * @throws BkmsException
	 */
	public String saveKpAttachement() throws BkmsException {
		String re = "success";
		String mes = "";
		KpAttachement attachement = new KpAttachement();
        Tools.copyProperties(attachement, form);
        KpAttachementForm kaf = (KpAttachementForm) form;
        String flag = Tools.filterNull(kaf.getFileId());
        try {
        	IKpAttachementUCC ucc = (IKpAttachementUCC) getBean("comm_kpAttachementUCC");
            if ("".equals(flag)) { //添加
            	ucc.addEntity(attachement, user);
            	mes = "保存成功！";
            } else {//修改
            	ucc.updateEntity(attachement, user);
            	mes = "保存成功！";
            }
        } catch (Exception e) {
        	re = "fail";
        	e.printStackTrace();
            BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
        }
        
        httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(re);
			this.showMessageDetail(mes);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
	
	/**
	 * 删除附件
	 * @return
	 */
	public String deleteFile(){
		String re = "success";
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			IKpAttachementUCC attachUCC = (IKpAttachementUCC) getBean("comm_kpAttachementUCC");
			if(null!=ids&&ids.length>0) {
				for(int i=0; i<ids.length; i++) {
					
					KpAttachement attachement = attachUCC.findEntity(ids[i], user);
					//文件类型 1：视频   2：图片 3：文件 4：其他
					if("1".equals(attachement.getFileType())) {
						FileUtil.delFile(attachement.getFileUrl());
						FileUtil.delFile(attachement.getFileHdUrl());
						FileUtil.delFile(attachement.getFileSdUrl());
					} else if("2".equals(attachement.getFileType()) 
							|| "3".equals(attachement.getFileType())
							|| "4".equals(attachement.getFileType())) {
						FileUtil.delFile(attachement.getFileUrl());
					} 
				}
				attachUCC.deleteEntitys(ids, user);
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
	 * 查询附件列表
	 * @return
	 * @throws BkmsException
	 */
	public String queryList() throws BkmsException {
        this.showMessage("保存成功！");
        KpAttachement pojo = new KpAttachement();
        pojo.setFileId(fileId);         //文件主键
    	pojo.setForeignId(foreignId);      //关联外键
    	pojo.setCreateTime(createTime);       //创建时间
    	pojo.setCreateUserId(createUserId);   //创建人ID
    	pojo.setCreateUserName(createUserName); //创建人姓名
    	pojo.setUpdateTime(updateTime);       //修改时间
    	pojo.setUpdateUserId(updateUserId);   //修改人ID
    	pojo.setUpdateUserName(updateUserName); //修改人姓名
    	pojo.setFileName(fileName);    //文件名称
    	pojo.setFileType(fileType);    //文件类型 1：视频   2：图片 3：文件 4：其他
    	IKpAttachementUCC ucc = (IKpAttachementUCC) getBean("comm_kpAttachementUCC");
//    		Tools.copyProperties(pojo, form);
//    		KpOpinionFeedbackForm kaf = (KpOpinionFeedbackForm) form;
		Map map = ucc.queryList(pojo, page, rows, user);
		// JSONArray jsonArray = JSONArray.fromObject(isProblemList);
		JSONObject jo = JSONObject.fromObject(map);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jo);
		} catch (IOException e) {
			e.printStackTrace();
			BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
            this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return null;
    }
	
	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getForeignId() {
		return this.foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	
	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	
	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public String getFileHdUrl() {
		return fileHdUrl;
	}

	public void setFileHdUrl(String fileHdUrl) {
		this.fileHdUrl = fileHdUrl;
	}

	public String getFileSdUrl() {
		return fileSdUrl;
	}

	public void setFileSdUrl(String fileSdUrl) {
		this.fileSdUrl = fileSdUrl;
	}

	public String getFileImgUrl() {
		return fileImgUrl;
	}

	public void setFileImgUrl(String fileImgUrl) {
		this.fileImgUrl = fileImgUrl;
	}

	public String getFileCode() {
		return this.fileCode;
	}

	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public KpAttachementForm getForm() {
		return form;
	}

	public void setForm(KpAttachementForm form) {
		this.form = form;
	}
	
}
