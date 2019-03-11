package com.becoda.bkms.emp.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-15
 * Time: 14:48:25
 * To change this template use File | Settings | File Templates.
 */
public interface IEmpInfoManageUCC {
    /**
     * 设置条件查询
     *
     * @param user
     * @param name
     * @param empType
     * @param orgTreeId
     * @param page
     * @return
     * @throws BkmsException
     */
//    public TableVO queryEmpListByCond(User user, String name, String empType, String orgTreeId, PageVO page) throws BkmsException;

    /**
     * 查询人员
     *
     * @param user
     * @param name
     * @param personCode    员工号
     * @param orgTreeId
     * @param sqlHash   从高级查询获得sql组合
     * @param page
     * @return
     * @throws BkmsException
     */
    public TableVO queryEmpListByCond(User user, String name, String personCode, String orgTreeId, Hashtable sqlHash, PageVO page) throws BkmsException;

    /**
     * 分页查询
     *
     * @param user
     * @param activeQrySql
     * @param activeQryShowItem
     * @param page
     * @return
     * @throws BkmsException
     */
    public TableVO queryEmpList(User user, String activeQrySql, String activeQryShowItem, PageVO page) throws BkmsException;

    /**
     * @param user
     * @param setId
     * @param
     * @return
     * @throws BkmsException
     */
    public TableVO createBlankEmpInfoSetRecord(User user, String setId, String fk) throws BkmsException;

    /**
     * 查询人员指标集信息
     *
     * @param user
     * @param setId
     * @param key
     * @return
     * @throws BkmsException
     */
    public TableVO queryEmpInfoSetRecordList(User user, String setId, String key) throws BkmsException;

    /**
     * 查询人员指定指标集信息
     *
     * @param user
     * @param setId
     * @param pk
     * @return
     * @throws BkmsException
     */
    public TableVO findEmpInfoSetRecord(User user, String setId, String pk) throws BkmsException;

    /**
     * 添加人员指标集记录
     *
     * @param user
     * @param setId
     * @param map
     * @return
     * @throws BkmsException
     */
    public String addEmpInfoSetRecord(User user, String setId, Map map) throws BkmsException;

    /**
     * 更新人员指标集记录
     *
     * @param user
     * @param setId
     * @param map
     * @throws BkmsException
     */
    public void updateEmpInfoSetRecord(User user, String setId, Map map, String pk) throws BkmsException;
    
    public void updatePersonBO(String name, String pk) throws BkmsException;

    /**
     * 删除人员指标集记录
     *
     * @param user
     * @param setId
     * @param pk
     * @param fk
     * @return
     * @throws BkmsException
     */
    public void deleteEmpInfoSetRecord(User user, String setId, String[] pk, String fk) throws BkmsException;

//    void updateEmpInfoSetRecord(User user, String setId, Map map, String pk, byte[] photo) throws BkmsException;
}
