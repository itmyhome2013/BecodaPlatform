package com.becoda.bkms.tls.webmgr.ucc;

import com.becoda.bkms.tls.webmgr.pojo.vo.InformationVO;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:44:28
 * To change this template use File | Settings | File Templates.
 */
public interface IInformationUCC {
    /**
     * 保存信息
     *
     * @param bo
     * @return
     * @throws RollbackableException
     */
    public String saveInfomation(InformationBO bo) throws RollbackableException;

    /**
     * 保存信息
     *
     * @param bo
     * @throws RollbackableException
     */
    public void updateInfomation(InformationBO bo) throws RollbackableException;

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
     * 根据id获得信息
     *
     * @param id
     * @return
     * @throws RollbackableException
     */
    public InformationBO findInfoById(String id) throws RollbackableException;

    /**
     * 根据信息类型获得信息
     *
     * @param type
     * @return
     * @throws RollbackableException
     */
    public List queryInfoByType(PageVO page,String orgId,String type) throws RollbackableException;

    /**
     * 查询所有信息
     *
     * @return
     * @throws RollbackableException
     */
    public List queryInfo() throws RollbackableException;

    /**
     * 根据机构查询信息
     *
     * @return
     * @throws RollbackableException
     */
    public List queryInfoByOrg(PageVO page,String orgId, String type) throws RollbackableException;

    /**
     * 判断同一类别下的公告是否重名
     *
     * @param name
     * @param type
     * @return
     * @throws RollbackableException
     */
    public boolean checkSameName(String name, String type, String id) throws RollbackableException;

    /**
     * 批量修改信息状态
     *
     * @param ids
     * @param col 字段
     * @param value 值
     * @throws RollbackableException
     */
    public void updateAllStatus(String[] ids, String col ,String value) throws RollbackableException;
    //根据主键获得信息集合
    public List queryInfo(String[] ids) throws RollbackableException;
    /**
     * 生成html
     *
     * @param templateName  html的模板名称
     * @param path     html模板的绝对路径
     * @throws RollbackableException
     */
    public String buildHtml(InformationBO vo, String path, String templateName, String baseHttpPath) throws RollbackableException;
    /**
     * 批量生成html，并更新informationBO的html路径
     *
     * @param templateName html的模板名称
     * @param path         html模板的绝对路径
     * @param list         InformationBO集合
     * @throws RollbackableException
     */
    public void saveBuildHtml(List list, String path, String templateName, String baseHttpPath) throws RollbackableException;
}
