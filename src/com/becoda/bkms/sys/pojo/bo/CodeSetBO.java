package com.becoda.bkms.sys.pojo.bo;

import java.io.Serializable;


/**
 * po_代码集
 * author:lirg
 * 2015-5-24
 */
public class CodeSetBO implements Serializable {
    /**
     * 代码集ID
     */
    private String setId;

    /**
     * 代码集名称
     */
    private String setName;

    /**
     * 描述
     */
    private String setDesc;

    /**
     * 是否到最低层
     * 1 到最低层
     * 0 不到
     */
    private String setLayer;

    /**
     * 状态
     * 1 启用
     * 0 禁用
     */
    private String setStatus;

    /**
     * 层级编码长度
     */
    private String layerLength;

    /**
     * 指标项ID范围名称   国标  用户  系统
     */
    private String setScaleName;
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

    public String getSetLayer() {
        return setLayer;
    }

    public void setSetLayer(String setLayer) {
        this.setLayer = setLayer;
    }

    public String getSetStatus() {
        //if(setStatus==null) setStatus="1";
        return setStatus;
    }

    public void setSetStatus(String setStatus) {
        this.setStatus = setStatus;
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


    public String getLayerLength() {
        return layerLength;
    }

    public void setLayerLength(String layerLength) {
        this.layerLength = layerLength;
    }
}
