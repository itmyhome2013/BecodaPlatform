package com.becoda.bkms.sys.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.api.SysAPI;
import com.becoda.bkms.sys.dao.ActivePageDAO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

import java.lang.reflect.Method;
import java.util.*;


/**
 * Created by IntelliJ IDEA. a
 * User: kangdw
 * Date: 2015-3-11
 * Time: 16:21:44
 * To change this template use File | Settings | File Templates.
 */
public class ActivePageService {
    private ActivePageDAO activePageDAO;
    private SysAPI sysAPI;

    public ActivePageDAO getActivePageDAO() {
        return activePageDAO;
    }

    public void setActivePageDAO(ActivePageDAO activePageDAO) {
        this.activePageDAO = activePageDAO;
    }

    public SysAPI getSysAPI() {
        return sysAPI;
    }

    public void setSysAPI(SysAPI sysAPI) {
        this.sysAPI = sysAPI;
    }

    /**
     * 取得一个指标集的空对象
     *
     * @param setId   指标集ID
     * @param pkValue 主键
     * @param fkValue 外键
     * @return 空的TableVO
     * @throws RollbackableException e
     */
    public TableVO getBlankInfoSetRecord(String setId, String pkValue, String fkValue) throws RollbackableException {
        try {
            TableVO table = new TableVO();
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            List headerList = SysCacheTool.queryInfoItemBySetId(setId);
            InfoItemBO[] header = (InfoItemBO[]) headerList.toArray(new InfoItemBO[headerList.size()]);
            table.setHeader(header);
            table.setInfoSet(set);

            String[] row = new String[header.length];
            int size = header.length;
            for (int i = 0; i < size; i++) {
                row[i] = header[i].getItemDefaultValue() == null ? "" : header[i].getItemDefaultValue();
            }
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType()) && fkValue != null && fkValue.length() > 0)
            {
                row[table.getCol(set.getSetFk())] = fkValue;
            } else
            if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(set.getSet_rsType()) && (pkValue == null || "".equals(pkValue)))
            {
                pkValue = fkValue;
            }
            if (pkValue != null && pkValue.length() > 0) {
                row[table.getCol(set.getSetPk())] = pkValue;
            }
            String[][] rows = new String[1][size];
            rows[0] = row;
            table.setRows(rows);
            return table;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 根据主键查找子集一条记录
     *
     * @param set
     * @param col
     * @param pkValue
     * @return
     * @throws RollbackableException String sql = " from " + set.getSetId() + " where " + set.getSetFk() + "='" + pkValue + "'";
     */
    public String[] findRecord(InfoSetBO set, InfoItemBO[] col, String pkValue) throws RollbackableException {
        return activePageDAO.findRecord(set, col, pkValue);
    }

    /**
     * @param set
     * @param col
     * @param fkValue
     * @return
     * @throws RollbackableException
     */
    public String[][] queryRecord(InfoSetBO set, InfoItemBO[] col, String fkValue) throws RollbackableException {
        String sql = "select * from " + set.getSetId() + " where " + set.getSetFk() + "='" + fkValue + "'";
        String order = set.getSetOrder();
        if (order == null || "".equals(order)) {
            order = set.getSetPk();
        }
        sql += " order by " + order;
        return activePageDAO.queryRecord(col, sql);
    }

    public String[][] queryRecordByCond(InfoSetBO set, InfoItemBO[] col, String where) throws RollbackableException {
        String sql = "select * from " + set.getSetId();
        if (where != null && where.trim().length() > 0) {
            sql += " where " + where;
        }
        return activePageDAO.queryRecord(col, sql);
    }

    public String[][] queryRecordByCond(InfoSetBO set, InfoItemBO[] col, String where, String orderBy) throws RollbackableException {
        String sql = "select * from " + set.getSetId();
        if (where != null && where.trim().length() > 0) {
            sql += " where " + where;
        }
        if (orderBy != null && orderBy.trim().length() > 0) {
            sql += " order by " + orderBy;
        }
        return activePageDAO.queryRecord(col, sql);
    }

    /**
     * 查询当前记录
     *
     * @param set
     * @param col
     * @param fkValue
     * @return
     * @throws RollbackableException
     */
    public String[][] queryCurRecord(InfoSetBO set, InfoItemBO[] col, String[] fkValue) throws RollbackableException {
        boolean isMany = SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType());
        String key = isMany ? set.getSetFk() : set.getSetPk();
        String sql;
        if (isMany) {
            sql = "select * from " + set.getSetId() + " where " + set.getSetId() + "000='" + Constants.YES + "' and " + Tools.splitInSql(fkValue, key);
        } else {
            sql = "select * from " + set.getSetId() + " where " + Tools.splitInSql(fkValue, key);
        }
        return activePageDAO.queryRecord(col, sql);
    }

    /**
     * @param activeQrySql
     * @param showHeader
     * @param page
     * @param isPage
     * @return
     * @throws RollbackableException
     */
    public TableVO queryListBySql(String activeQrySql, InfoItemBO[] showHeader, String righField, PageVO page, boolean isPage) throws RollbackableException {
        try {
            if (activeQrySql == null || activeQrySql.length() == 0 || showHeader == null || showHeader.length == 0)
                return null;
            TableVO table = new TableVO();
            table.setQuerySql(activeQrySql);
            table.setHeader(showHeader);
            if (righField != null && righField.startsWith(",")) righField = righField.substring(1);
            table.setRightItem(righField);
            activePageDAO.queryRecord(table, page, activeQrySql, isPage);
            return table;
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 往子集添加一条记录
     *
     * @param user
     * @param table
     * @param logflag
     * @param linkflag
     * @throws RollbackableException
     */
    public String addRecord(User user, TableVO table, boolean logflag, boolean linkflag, boolean curflag) throws RollbackableException {
        InfoSetBO set = table.getInfoSet();
        InfoItemBO[] header = table.getHeader();
        String[] rs = table.getRows()[0];
        int pkKeyCol = table.getCol(set.getSetPk());
        int fkKeyCol = -1;
        if ((SysConstants.INFO_SET_RS_TYPE_SINGLE).equals(set.getSet_rsType())) {
            fkKeyCol = pkKeyCol;
        } else {
            fkKeyCol = table.getCol(set.getSetFk());
        }
        String rsType = set.getSet_rsType();
        String pkValue = rs[pkKeyCol];
        String fkValue = rs[fkKeyCol];
        try {
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(rsType)) {
                if (fkValue == null || "".equals(fkValue))
                    throw new BkmsException("添加多记录子集记录，外键不能为空", this.getClass());
            }
            //多记录子集或者外键为空的单记录子集
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(rsType) ||
                    (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(rsType) && set.getSetFk() == null)) {
                if (pkValue == null || "".equals(pkValue)) {
                    pkValue = SequenceGenerator.getUUID();
                    rs[pkKeyCol] = pkValue;
                }
            }
            int count = header.length;
            for (int i = 0; i < count; i++) {
                if (SysConstants.INFO_ITEM_DATA_TYPE_COMPUTE.equals(header[i].getItemDataType())) {
                    //处理计算类型数据值
                    String value = this.handleComputeValue(header[i], rs[i], rs, table.getHeaderIndex());
                    rs[i] = value;
                }
            }
            activePageDAO.addRecord(set.getSetId(), header, rs);
            //update time
            String date = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
            String sqltime = "update " + set.getSetId() + " set create_time='" + date + "'," +
                    "last_update_time='" + date + "'," +
                    "last_operator='" + user.getUserId() + "'" +
                    " where " + set.getSetPk() + "='" + pkValue + "'";
            this.executeSql(sqltime);

            //单记录子集 同步增加其他单记录子集 记录
            if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(rsType)) {
                InfoSetBO[] linkSet = sysAPI.queryCascadeInfoSet(set.getSetId());
                if (linkSet != null && linkSet.length > 0) {
                    int size = linkSet.length;
                    List sql = new ArrayList();
                    for (int i = 0; i < size; i++) {
                        if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(linkSet[i].getSet_rsType()))
                        	if(!linkSet[i].getSetId().equals("B000")){
                        		sql.add("insert into " + linkSet[i].getSetId() + " (ID,create_time,last_update_time,last_operator) " +
                                        "values('" + pkValue + "','" + date + "','" + date + "','" + user.getUserId() + "')");
                        	}
                            
                    }
                    if (sql.size() > 0) {
                        activePageDAO.batchExecuteSql((String[]) sql.toArray(new String[0]));
                    }
                }
            }
            if (curflag) {
                this.handleCurStatus(set, new String[]{fkValue});
            }
            if (linkflag) {//处理联动
                if (set.getAddFunction() != null && !"".equals(set.getAddFunction())) {
                    ILinkHandle lh = (ILinkHandle) BkmsContext.getBean(set.getAddFunction());
                    lh.whenAdd(table);
                }
            }
            if (logflag) {
                OperLogBO bo = new OperLogBO();
                bo.setOperatorId(user.getUserId());
                bo.setOperatorName(user.getName());
                bo.setOperatorOrg(user.getDeptId());
                bo.setOperInfosetId(set.getSetId());
                bo.setOperInfosetName(set.getSetName());
                bo.setOperInfoitemId(set.getSetPk());

                if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                    bo.setOperPersonId(fkValue);
                } else {
                    bo.setOperPersonId(pkValue);
                }
                bo.setOperRecordId(pkValue);
                bo.setOperType("add");
                bo.setOperDesc("新增指标集记录");
                HrmsLog.addOperLog(this.getClass(), bo);
            }
            return pkValue;
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 往指定子集添加一条记录
     *
     * @param user
     * @param setId    子集id
     * @param map      由前端页面提交的信息  页面前端提交的信息，map来源于httprequest
     * @param logflag  是否记录日志  true 记录日志 false  不记录
     * @param linkflag 是否联动     true 联动 false 不联动
     * @throws RollbackableException
     */
    public String addRecord(User user, String setId, Map map, boolean logflag, boolean linkflag) throws RollbackableException {
        try {
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            TableVO table = new TableVO();
            table.setInfoSet(set);
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(setId).toArray(new InfoItemBO[0]);
            table.setHeader(header);
            int count = header.length;
            String[][] row = new String[1][count];
            for (int i = 0; i < count; i++) {
                row[0][i] = (String) map.get(header[i].getItemId());
            }
            table.setRows(row);
            return this.addRecord(user, table, logflag, linkflag, true);
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 更新一条指标集信息
     * <p/>
     * tablevo todo 增加tablevo 结构要求
     *
     * @param user
     * @param table
     * @param logflag
     * @param linkflag
     * @throws RollbackableException
     */
    public void updateRecord(User user, TableVO table, boolean logflag, boolean linkflag, boolean curflag) throws RollbackableException {
        try {
            InfoSetBO set = table.getInfoSet();
            boolean isMany = SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType());
            InfoItemBO[] header = table.getHeader();
            String[] values = table.getRows()[0];
            int pkKeyCol = table.getCol(set.getSetPk());
            int fkKeyCol = isMany ? table.getCol(set.getSetFk()) : -1;
            String pkValue = pkKeyCol > -1 ? Tools.filterNull(values[pkKeyCol]) : "";
            String fkValue = fkKeyCol > -1 ? Tools.filterNull(values[fkKeyCol]) : "";
            if (!isMany && pkValue.equals("")) {
                pkValue = fkValue;
            }
            //验证
            if (isMany && "".equals(fkValue) && "".equals(pkValue)) {
                throw new RollbackableException("多记录子集外键值不能为空 ", this.getClass());
            } else if (!isMany && (pkValue == null || "".equals(pkValue))) {
                throw new RollbackableException("单记录子集主键值不能为空值", this.getClass());
            }
            //
            int cols = header.length;
            String[] initValue = null;
            List logList = null;
            HashMap methodHash = null;
            if (logflag || linkflag) {
                if (isMany && "".equals(pkValue)) {
                    initValue = this.findCurrentRecord(set, header, fkValue);
                } else {
                    initValue = this.findRecord(set, header, pkValue);
                }
                logList = new ArrayList();
                methodHash = new HashMap();
            }
            //转换计算类型数据值
            for (int i = 0; i < cols; i++) {
                if (SysConstants.INFO_ITEM_DATA_TYPE_COMPUTE.equals(header[i].getItemDataType())) {
                    values[i] = this.handleComputeValue(header[i], values[i], values, table.getHeaderIndex());
                }
                if (logflag && !Tools.filterNull(initValue[i]).equals(values[i])) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId(set.getSetId());
                    bo.setOperInfosetName(set.getSetName());
                    bo.setOperInfoitemId(header[i].getItemId());
                    bo.setOperInfoitemName(header[i].getItemName());//update todo
                    bo.setOperType("modify");
                    if (isMany)
                        bo.setOperPersonId(fkValue);
                    else
                        bo.setOperPersonId(pkValue);
                    bo.setOperRecordId(pkValue);

                    if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(header[i].getItemDataType())||SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(header[i].getItemDataType())) {
                        if (header[i].getInterpret() == null || "".equals(header[i].getInterpret())) {
                            bo.setOperValuePre(initValue[i]);
                            bo.setOperValueSuf(values[i]);
                            bo.setOperDescPre(initValue[i]);
                            bo.setOperDescSuf(values[i]);
                        } else {
                            Object obj = methodHash.get("class");
                            if (obj == null) {
                                obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                methodHash.put("class", obj);
                            }
                            Method method = (Method) methodHash.get(header[i].getInterpret());
                            if (method == null) {
                                method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", header[i].getInterpret(), new Class[]{String.class});
                                methodHash.put(header[i].getInterpret(), method);
                            }
                            bo.setOperValuePre(method.invoke(obj, new String[]{initValue[i].trim()}).toString());
                            bo.setOperValueSuf(method.invoke(obj, new String[]{values[i].trim()}).toString());
                            bo.setOperDescPre(method.invoke(obj, new String[]{initValue[i].trim()}).toString());
                            bo.setOperDescSuf(method.invoke(obj, new String[]{values[i].trim()}).toString());
                        }
                    } else {
                        bo.setOperValuePre(initValue[i]);
                        bo.setOperValueSuf(values[i]);
                        bo.setOperDescPre(initValue[i]);
                        bo.setOperDescSuf(values[i]);
                    }
                    Org o = SysCacheTool.findOrgById(user.getOrgId());
                    bo.setOperOrgTreeId(o.getTreeId());
                    bo.setOperDesc("更新指标集记录");
                    logList.add(bo);
                }
            }
            //更新
            activePageDAO.updateRecord(user.getUserId(), table);
            if (curflag) {
                this.handleCurStatus(set, new String[]{fkValue});
            }
            if (linkflag) {//处理联动
                if (set.getUpdateFunction() != null && !"".equals(set.getUpdateFunction())) {
                    ILinkHandle lh = null;
                    try {
                        lh = (ILinkHandle) BkmsContext.getBean(set.getUpdateFunction());
                        lh.whenUpdate(table, initValue);
                    } catch (BkmsException e) {
                        throw new RollbackableException(e, this.getClass());
                    }
                }
            }
            if (logflag && logList != null && logList.size() > 0) {//update todo
                HrmsLog.batchAddOperLog(this.getClass(), logList);
            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }

    public String[] findCurrentRecord(InfoSetBO set, InfoItemBO[] header, String fkValue) throws RollbackableException {
        return activePageDAO.findCurrentRecord(set, header, fkValue);
    }

    /**
     * 更新一条指标集信息
     *
     * @param user     操作用户
     * @param setId    指标集
     * @param map      页面前端提交的信息，map来源于httprequest
     * @param logflag
     * @param linkflag
     * @throws RollbackableException
     */
    public void updateRecord(User user, String setId, Map map, boolean logflag, boolean linkflag) throws RollbackableException {
        try {
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            TableVO table = new TableVO();
            table.setInfoSet(set);
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(setId).toArray(new InfoItemBO[0]);
            table.setHeader(header);
            int count = header.length;
            String[][] row = new String[1][count];
            for (int i = 0; i < count; i++) {
                row[0][i] = (String) map.get(header[i].getItemId());
            }
            table.setRows(row);
            this.updateRecord(user, table, logflag, linkflag, true);
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }
    
    public void updatePersonBO(String name, String pk) throws BkmsException{
    	activePageDAO.updatePersonBO(name, pk);
    }

    /**
     * 更新一条指标集信息
     * 只更新Map中有的项
     *
     * @param user     操作用户
     * @param setId    指标集
     * @param map      页面前端提交的信息，map来源于httprequest
     * @param logflag
     * @param linkflag
     * @throws RollbackableException
     */
    public void updateMapRecord(User user, String setId, Map map, boolean logflag, boolean linkflag) throws RollbackableException {
        try {
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            TableVO table = new TableVO();
            table.setInfoSet(set);
            //表头所有项
            List header = SysCacheTool.queryInfoItemBySetId(setId);
            //需要更新的项 --- 存在于 Map 中的项
            List hs = new ArrayList();
            Iterator it = map.keySet().iterator();
            while (it.hasNext()) {
                String o = (String) it.next();
                for (int i = 0; i < header.size(); i++) {
                    InfoItemBO infoItemBO = (InfoItemBO) header.get(i);
                    if (o.equalsIgnoreCase(infoItemBO.getItemId())) {
                        hs.add(infoItemBO);
                        break;
                    }
                }
            }
            //
            table.setHeader((InfoItemBO[]) hs.toArray(new InfoItemBO[hs.size()]));
            //构造值的数组
            int count = hs.size();
            String[][] row = new String[1][count];
            for (int i = 0; i < count; i++) {
                row[0][i] = (String) map.get(((InfoItemBO) hs.get(i)).getItemId());
            }
            table.setRows(row);
            this.updateRecord(user, table, logflag, linkflag, true);
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 删除指定指标集同一外键下多条记录
     *
     * @param set
     * @param pkValue
     * @param fkValue
     * @param logflag
     * @param linkflag
     * @throws RollbackableException
     */
    public void deleteSubRecord(User user, InfoSetBO set, String[] pkValue, String fkValue, boolean logflag, boolean linkflag) throws RollbackableException {
        try {
            if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(set.getSet_rsType())) {
                throw new RollbackableException("单记录子集不使用本方法", this.getClass());
            }
            String pkName = set.getSetPk();
            //删除记录
            activePageDAO.deleteRecord(set.getSetId(), pkName, pkValue);
            // 修改状态位
            this.handleCurStatus(set, new String[]{fkValue});
            //多记录子集删除后联动
            if (linkflag && set.getDelFunction() != null && !"".equals(set.getDelFunction())) {
                ILinkHandle lh = (ILinkHandle) BkmsContext.getBean(set.getDelFunction());
                for (int i = 0; i < pkValue.length; i++) {
                    String pk = pkValue[i];
                    lh.whenDel(set.getSetId(), pk, fkValue);
                }
            }
            if (logflag) {
                List list = new ArrayList();
                for (int i = 0; i < pkValue.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperatorName(user.getName());
                    bo.setOperInfosetId(set.getSetId());
                    bo.setOperInfosetName(set.getSetName());
                    bo.setOperInfoitemId(set.getSetFk());
                    bo.setOperType("delete");
                    bo.setOperPersonId(fkValue);
                    bo.setOperRecordId(pkValue[i]);
                    Org o = SysCacheTool.findOrgById(user.getOrgId());
                    bo.setOperOrgTreeId(o.getTreeId());
                    bo.setOperDesc("批量删除同一外键下多条指标集记录");
                    list.add(bo);
                }
                HrmsLog.batchAddOperLog(this.getClass(), list);

            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 删除一条主集记录  单记录子集
     *
     * @param user
     * @param set
     * @param pkValue
     * @param linkflag
     * @param logflag
     * @throws RollbackableException
     */

    public void deleteMainRecord(User user, InfoSetBO set, String pkValue, boolean linkflag, boolean logflag) throws RollbackableException {
        try {
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                throw new RollbackableException("多记录子集不使用本方法", this.getClass());
            }
            String pkName = set.getSetPk();
            //单记录子集删除后联动
            if (linkflag && set.getDelFunction() != null && !"".equals(set.getDelFunction())) {
                ILinkHandle lh = (ILinkHandle) BkmsContext.getBean(set.getDelFunction());
                lh.whenDel(set.getSetId(), pkValue, null);
            }
            //删除关联表记录
            this.deleteCascadeRecord(set.getSetId(), pkValue);
            //删除主集记录
            activePageDAO.deleteRecord(set.getSetId(), pkName, pkValue);
            if (logflag) {
                OperLogBO bo = new OperLogBO();
                bo.setOperatorId(user.getUserId());
                bo.setOperatorOrg(user.getDeptId());
                bo.setOperatorName(user.getName());
                bo.setOperInfosetId(set.getSetId());
                bo.setOperInfosetName(set.getSetName());
                bo.setOperInfoitemId(set.getSetPk());
                bo.setOperPersonId(pkValue);
                bo.setOperRecordId(pkValue);
                bo.setOperType("delete");
                bo.setOperDesc("删除指标集记录");
                HrmsLog.addOperLog(this.getClass(), bo);
            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 删除关联表的记录
     *
     * @param setId
     * @param keyValue
     * @throws RollbackableException
     */
    private void deleteCascadeRecord(String setId, String keyValue) throws RollbackableException {
        try {
            InfoSetBO[] setList = sysAPI.queryCascadeInfoSet(setId);
            if (setList == null)
                return;
            int count = setList.length;
            String[] sql = new String[count+1];
            for (int i = 0; i < count; i++) {
                String keyName = setList[i].getSetFk();
                sql[i] = "delete  from " + setList[i].getSetId() + " where " + keyName + "='" + keyValue + "'";
            }
            sql[count] = "delete  from PMS_USER_INFO where PMS_PERSON_ID='" + keyValue + "'";
            activePageDAO.batchExecuteSql(sql);
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }


    public void batchAddRecord(User user, String setId, String[] fkValue, Map map, boolean link, boolean log) throws RollbackableException {
        //deal add
        if (fkValue == null || fkValue.length == 0) return;
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        if (set == null) return;
        try {
            InfoItemBO[] col = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(setId).toArray(new InfoItemBO[0]);
            int count = fkValue.length;
            int colnum = col.length;
            TableVO table = new TableVO();
            table.setHeader(col);
            table.setInfoSet(set);
            String[][] rows = new String[1][colnum];
            String fkName = set.getSetFk();
            String pkName = set.getSetPk();
            int pkCol = table.getCol(pkName);
            int fkCol = table.getCol(fkName);
            String[] uuid = SequenceGenerator.getUUID(count);
            for (int i = 0; i < count; i++) {
                String[] rs = new String[colnum];
                for (int j = 0; j < colnum; j++) {
                    rs[j] = (String) map.get(col[j].getItemId());
                }
                rs[pkCol] = uuid[i];
                rs[fkCol] = fkValue[i];
                rows[0] = rs;
                table.setRows(rows);
                this.addRecord(user, table, false, link, false);
            }
            this.handleCurStatus(set, fkValue);
            if (log) {
                List list = new ArrayList();
                for (int i = 0; i < fkValue.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId(set.getSetId());
                    bo.setOperInfosetName(set.getSetName());
                    bo.setOperInfoitemId(set.getSetFk());
                    bo.setOperType("add");
                    bo.setOperPersonId(fkValue[i]);
                    bo.setOperRecordId(uuid[i]);
                    Org o = SysCacheTool.findOrgById(user.getOrgId());
                    bo.setOperOrgTreeId(o.getTreeId());
                    bo.setOperDesc("根据外键批量增加指标集记录");
                    list.add(bo);
                }
                HrmsLog.batchAddOperLog(this.getClass(), list);

            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }

    }

    public void batchUpdateRecord(User user, String setId, String[] items, String[] ids, Map map, boolean link, boolean log) throws RollbackableException {
        //deal update
        if (ids == null || ids.length == 0) return;
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        if (set == null) return;
        try {
            InfoItemBO[] col = new InfoItemBO[items.length + 1];
            //personid
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                col[0] = SysCacheTool.findInfoItem(setId, set.getSetFk());
            } else {
                col[0] = SysCacheTool.findInfoItem(setId, set.getSetPk());
            }
            //列值
            for (int i = 1; i < items.length + 1; i++) {
                col[i] = SysCacheTool.findInfoItem(setId, items[i - 1]);
            }
            int colNums = col.length;
            TableVO table = new TableVO();
            table.setHeader(col);
            table.setInfoSet(set);
            String[][] rows = new String[1][colNums];
            for (int i = 0; i < ids.length; i++) {
                String[] values = new String[colNums];
                values[0] = ids[i];
                for (int j = 1; j < colNums; j++) {
                    values[j] = (String) map.get(col[j].getItemId());
                }
                rows[0] = values;
                table.setRows(rows);
                this.updateRecord(user, table, false, link, false);
            }
            this.handleCurStatus(set, ids);

            if (log) {
                List list = new ArrayList();
                for (int i = 0; i < ids.length; i++) {
                    for (int j = 0; j < items.length; j++) {
                        OperLogBO bo = new OperLogBO();
                        bo.setOperatorId(user.getUserId());
                        bo.setOperatorName(user.getName());
                        bo.setOperatorOrg(user.getDeptId());
                        bo.setOperInfosetId(set.getSetId());
                        bo.setOperInfosetName(set.getSetName());
                        bo.setOperInfoitemId(items[j]);
                        bo.setOperType("modify");
                        bo.setOperPersonId(ids[i]);
                        Org o = SysCacheTool.findOrgById(user.getOrgId());
                        bo.setOperOrgTreeId(o.getTreeId());
                        bo.setOperDesc("根据外键批量更新当前指标集记录");
                        list.add(bo);
                    }
                }
                HrmsLog.batchAddOperLog(this.getClass(), list);

            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 批量删除多记录子集当前记录
     *
     * @param user
     * @param setId
     * @param fkValue
     * @param link
     * @param log
     * @throws RollbackableException
     */
    public void batchDeleteCurRecord(User user, String setId, String[] fkValue, boolean link, boolean log) throws RollbackableException {
        try {
            if (fkValue == null || fkValue.length == 0) return;
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            if (set == null) return;
            int count = fkValue.length;
            String sql = "delete from " + setId + " where " + setId + "000='" + Constants.YES + "'" + Tools.splitInSql(fkValue, set.getSetFk());
            this.executeSql(sql);
            this.handleCurStatus(set, fkValue);
            if (link && set.getDelFunction() != null && !"".equals(set.getDelFunction())) {
                ILinkHandle lh = (ILinkHandle) BkmsContext.getBean(set.getDelFunction());
                for (int i = 0; i < count; i++) {
                    lh.whenDel(set.getSetId(), null, fkValue[i]);
                }
            }
            if (log) {
                List list = new ArrayList();
                for (int i = 0; i < fkValue.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId(set.getSetId());
                    bo.setOperInfosetName(set.getSetName());
                    bo.setOperInfoitemId(set.getSetFk());
                    bo.setOperType("delete");
                    bo.setOperPersonId(fkValue[i]);
                    Org o = SysCacheTool.findOrgById(user.getOrgId());
                    bo.setOperOrgTreeId(o.getTreeId());
                    bo.setOperDesc("根据外键批量删除指标集当前记录");
                    list.add(bo);
                }
                HrmsLog.batchAddOperLog(this.getClass(), list);

            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException("", this.getClass());
        }
    }

    /**
     * 批量删除主集记录
     *
     * @param user
     * @param setId
     * @param pkValue
     * @param link
     * @param log
     * @throws RollbackableException
     */
    public void batchDeleteMainRecord(User user, String setId, String[] pkValue, boolean link, boolean log) throws RollbackableException {
        try {
            if (pkValue == null || pkValue.length == 0) return;
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            if (set == null) return;
            int count = pkValue.length;
            for (int i = 0; i < count; i++) {
                this.deleteMainRecord(user, set, pkValue[i], link, false);
            }
            if (log) {
                List list = new ArrayList();
                for (int i = 0; i < pkValue.length; i++) {
                    OperLogBO bo = new OperLogBO();
                    bo.setOperatorId(user.getUserId());
                    bo.setOperatorName(user.getName());
                    bo.setOperatorOrg(user.getDeptId());
                    bo.setOperInfosetId(set.getSetId());
                    bo.setOperInfosetName(set.getSetName());
                    bo.setOperInfoitemId(set.getSetPk());
                    bo.setOperType("delete");
                    bo.setOperPersonId(pkValue[i]);
                    bo.setOperRecordId(pkValue[i]);
                    Org o = SysCacheTool.findOrgById(user.getOrgId());
                    bo.setOperOrgTreeId(o.getTreeId());
                    bo.setOperDesc("批量删除指标集记录");
                    list.add(bo);
                }
                HrmsLog.batchAddOperLog(this.getClass(), list);
            }
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 公式计算
     *
     * @param cell
     * @param initValue
     * @param rs
     * @param headerIndex
     * @return string
     */
    private String handleComputeValue(InfoItemBO cell, String initValue, String[] rs, Hashtable headerIndex) {
        String re = initValue;
        if (SysConstants.INFO_ITEM_DATA_TYPE_COMPUTE.equals(cell.getItemDataType())
                && cell.getItemFormula() != null
                && !"".equals(cell.getItemFormula())) {
            String formula = cell.getItemFormula();
            if (formula == null) return re;
            String argStr = formula.substring(formula.indexOf("(") + 1, formula.lastIndexOf(")"));
            String method = formula.substring(0, formula.indexOf("("));

            if (argStr == null || "".equals(argStr)) return re;
            String[] args = argStr.split(",");

            if (args == null) return re;
            Class[] argtype = new Class[args.length];
            String[] argvalue = new String[args.length];
            if ("ruleCompute".equals(method)) { //四则运算
                StringTokenizer st = new StringTokenizer(argStr, "+-*/() ");
                String newfm = argStr;
                while (st.hasMoreElements()) {
                    String cellname = ((String) st.nextElement()).trim();
                    String index = (String) headerIndex.get(cellname);
                    String celltmp = rs[Integer.parseInt(index)];
                    if (celltmp != null) {
                        String value = "0.0";
                        try {
                            value = "" + Double.parseDouble(celltmp);
                        } catch (Exception e) {
                            value = "0.0";
                        }
                        newfm = newfm.replaceAll(cellname, value);
                    }
                }
                argtype[0] = String.class;
                argvalue[0] = newfm;
            } else {
                for (int k = 0; k < args.length; k++) {
                    argtype[k] = String.class;
                    int index = Integer.parseInt((String) headerIndex.get(args[k]));
                    argvalue[k] = rs[index];
                }
            }
            re = Tools.methodInvoke("com.becoda.bkms.sys.util.HrmsMath", method, argtype, argvalue).trim();
        }
        return re;
    }

    /**
     * 批量处理当前记录
     *
     * @param set
     * @param fkValue
     * @throws RollbackableException
     */
    public void handleCurStatus(InfoSetBO set, String fkValue[]) throws RollbackableException {
        try {
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(set.getSet_rsType())) {
                String order = set.getSetOrder();
                String orderkey = "subid";
                String sql = " from " + set.getSetId() + " where" + Tools.splitInSql(fkValue, "ID");
                if (order != null && !"".equals(order)) {
                    if (order.toUpperCase().indexOf("SUBID") != 0 || order.toUpperCase().indexOf("ID") != 0) {
                        int pointPosi = order.indexOf(".");
                        if (pointPosi < 0) {
                            orderkey = order.trim().substring(0, 7);
                        } else if (order.length() >= pointPosi + 8) {
                            orderkey = order.trim().substring(pointPosi + 1, pointPosi + 8);
                        }
                    }
                }
                sql = "select max(" + orderkey + ") as " + orderkey + "_max,ID " + sql + " group by ID";
                String update1 = "update " + set.getSetId() + " set " + set.getSetId() + "000='" + Constants.NO + "' where " + Tools.splitInSql(fkValue, "ID");
                this.executeSql(update1);
                String ssql = "select max(subid) from " + set.getSetId() + " a,(" + sql + ") b where a.id=b.id  and a." + orderkey + "=b." + orderkey + "_max group by a.id";
                String update2 = "update " + set.getSetId() + " set " + set.getSetId() + "000='" + Constants.YES + "' where subid in (" + ssql + ")";
                this.executeSql(update2);
            }
        } catch (Exception e) {
            throw new RollbackableException(" 处理当前记录失败 ", e, this.getClass());
        }
    }

    public int queryForInt(String sql) throws RollbackableException {
        return activePageDAO.queryForInt(sql);
    }

    public List queryForList(String sql) throws RollbackableException {
        return activePageDAO.queryForList(sql);
    }

    /**
     * 批量执行sql语句
     *
     * @param sql
     * @throws RollbackableException
     */
    public void batchExecuteSql(String[] sql) throws RollbackableException {
        activePageDAO.batchExecuteSql(sql);
    }

    /**
     * 执行sql语句
     *
     * @param sql
     * @throws RollbackableException
     */
    public void executeSql(String sql) throws RollbackableException {
        activePageDAO.executeSql(sql);
    }
}
