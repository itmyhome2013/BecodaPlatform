package com.becoda.bkms.tls.webmgr.pojo.vo;


//import org.apache.struts.upload.FormFile;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:46:11
 * To change this template use File | Settings | File Templates.
 */
public class InformationVO  {
    private String id;
    private String title;
    private String type;
    private String content;      //内容
//    private String creator;      //创建者
//    private String createOrgId; //创建机构id
//    private String createTime;
    private String updator;      //最后更新者
    private String updateOrgId; //最后更新单位
    private String updateTime;  //最后更新时间
    private String isTop;        //是否置顶      置顶：1，未置顶：0
    private String staticFileURL;     //静态调用文件地址
    private String attachFileURL;     //附件文件地址
    private String status;       //信息状态   未发布：0，已发布：1，取消发布：2
    private String infoSynopsis; //简介
    private String infoIsBanner; //是否 banner图
//    private FormFile uploadFile; //图片

//    public FormFile getUploadFile() {
//        return uploadFile;
//    }
//
//    public void setUploadFile(FormFile uploadFile) {
//        this.uploadFile = uploadFile;
//    }

    public String getInfoIsBanner() {
		return infoIsBanner;
	}

	public void setInfoIsBanner(String infoIsBanner) {
		this.infoIsBanner = infoIsBanner;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public String getUpdateOrgId() {
        return updateOrgId;
    }

    public void setUpdateOrgId(String updateOrgId) {
        this.updateOrgId = updateOrgId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTop() {
        return isTop;
    }

    public void setTop(String top) {
        isTop = top;
    }

    public String getStaticFileURL() {
        return staticFileURL;
    }

    public void setStaticFileURL(String staticFileURL) {
        this.staticFileURL = staticFileURL;
    }

    public String getAttachFileURL() {
        return attachFileURL;
    }

    public void setAttachFileURL(String attachFileURL) {
        this.attachFileURL = attachFileURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public String getInfoSynopsis() {
		return infoSynopsis;
	}

	public void setInfoSynopsis(String infoSynopsis) {
		this.infoSynopsis = infoSynopsis;
	}
    
}
