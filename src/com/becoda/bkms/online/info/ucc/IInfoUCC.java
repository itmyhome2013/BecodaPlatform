package com.becoda.bkms.online.info.ucc;

import java.util.List;

import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.online.info.pojo.bo.InfoBo;
import com.becoda.bkms.online.info.pojo.vo.InfoVo;

public interface IInfoUCC {

	/**
     * 根据id获得信息
     *
     * @param id
     * @return
     * @throws RollbackableException
     */
    public InfoBo findInfoById(String id) throws RollbackableException;
    
    /**
     * 判断是否重名
     *
     * @param name
     * @param type
     * @return
     * @throws RollbackableException
     */
    public boolean checkSameName(String name, String id) throws RollbackableException;
    
    /**
     * 保存信息
     *
     * @param bo
     * @return
     * @throws RollbackableException
     */
    public String saveInfomation(InfoBo bo) throws RollbackableException;

    /**
     * 保存信息
     *
     * @param bo
     * @throws RollbackableException
     */
    public void updateInfomation(InfoBo bo) throws RollbackableException;

    /**
     * 删除信息
     *
     * @param id
     * @throws RollbackableException
     */
    public void deleteInfomation(String id) throws RollbackableException;

    /**
     * 删除多条信息
     *
     * @param id
     * @throws RollbackableException
     */
    public void deleteInfomation(String[] id) throws RollbackableException;
    
    /**
     * 根据信息类型获得信息
     *
     * @param type
     * @return
     * @throws RollbackableException
     */
    public List queryInfoByType(PageVO page,InfoVo info) throws RollbackableException;
    
    //根据主键获得信息集合
    public List queryInfo(String[] ids) throws RollbackableException;
    
    /**
     * 批量修改信息状态
     *
     * @param ids
     * @param col 字段
     * @param value 值
     * @throws RollbackableException
     */
    public void updateAllStatus(String[] ids, String col ,String value) throws RollbackableException;
}
