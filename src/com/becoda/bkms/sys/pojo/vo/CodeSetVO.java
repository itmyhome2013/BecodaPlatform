package com.becoda.bkms.sys.pojo.vo;

import com.becoda.bkms.sys.SysConstants;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-12
 * Time: 14:36:45
 * To change this template use File | Settings | File Templates.
 */

public class CodeSetVO {
    private String setId;// 代码集ID
    private String setName;//代码集名称
    private String setDesc;// 国标代码
    private String setLayer;//是否到最底层   1：到最底层 0：不到
    private String setStatus = SysConstants.INFO_STATUS_OPEN;// 状态     1：启用     0：未启用
    private String layerLength;//层级编码长度
    private String setScaleName;// 指标项ID范围名称   国标  用户  系统
    /**
     * 标识指项目 默认值为 1 
     */
    private String state;


    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSetScaleName() {
        return setScaleName;
    }

    public void setSetScaleName(String setScaleName) {
        this.setScaleName = setScaleName;
    }

    public String getSetId() {
        return setId;
    }

    public void setSetId(String setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetDesc() {
        return setDesc;
    }

    public void setSetDesc(String setDesc) {
        this.setDesc = setDesc;
    }

    public String getSetLayer() {
        return setLayer;
    }

    public void setSetLayer(String setLayer) {
        this.setLayer = setLayer;
    }

    public String getSetStatus() {
        //if(setStatus==null) setStatus=1;
        return setStatus;
    }

    public void setSetStatus(String setStatus) {
        this.setStatus = setStatus;
    }

    public String getLayerLength() {
        return layerLength;
    }

    public void setLayerLength(String layerLength) {
        this.layerLength = layerLength;
    }


}
