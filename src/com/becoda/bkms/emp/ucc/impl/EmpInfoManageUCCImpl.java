package com.becoda.bkms.emp.ucc.impl;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCache;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.ucc.IEmpInfoManageUCC;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.sys.service.ActivePageService;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Hashtable;
import java.util.Map;


public class EmpInfoManageUCCImpl implements IEmpInfoManageUCC {
    private ActivePageService activePageService;


    public ActivePageService getActivePageService() {
        return activePageService;
    }

    public void setActivePageService(ActivePageService activePageService) {
        this.activePageService = activePageService;
    }

    /**
     * 查询人员
     *
     * @param user
     * @param name
     * @param personCode   员工号     
     * @param orgTreeId
     * @param sqlHash   从高级查询获得sql组合
     * @param page
     * @return
     * @throws BkmsException
     */
    public TableVO queryEmpListByCond(User user, String name, String personCode, String orgTreeId, Hashtable sqlHash, PageVO page) throws BkmsException {
        try {
            //调用高级查询的接口，获得拼好的sql语句各块
            String select = (String) sqlHash.get(QryConstants.QRYSQL_showField);
            String from = (String) sqlHash.get(QryConstants.QRYSQL_from);
            String where = (String) sqlHash.get(QryConstants.QRYSQL_where);
            String order = (String) sqlHash.get(QryConstants.QRYSQL_order);
            InfoItemBO[] header = getHeader(select);
            String sql = "select " + select + " from " + from
                    + " where 1=1 "
                    + ("".equals(where) ? "" : " and " + where);

            //拼接自己的条件
            if (name != null && name.length() > 0) {
                sql += " and  A001.A001001 LIKE  '" + name + "%'";
            }
            if (personCode != null && personCode.length() > 0) {
                //String es[] = empType.split(",");
                sql += " and  A001.A001735 ='"+personCode+"'";
            }
            if (orgTreeId != null && orgTreeId.length() > 0) {
                sql += " and  A001.A001738 like '" + orgTreeId + "%'";
            }
            sql += ("".equals(order) ? "" : " order by " + order);

            //转换显示项
            String showItem = "";
            for (int i = 0; i < header.length; i++) {
                showItem += "," + header[i].getSetId() + "." + header[i].getItemId();
            }
            //拼接权限字段
            String righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_PERSON));
            sql = addSqlSelect(sql, righField);

            page.setQrySql(sql);
            page.setShowField(showItem);

            TableVO table = activePageService.queryListBySql(sql, header, righField, page, true);
            table.setRightItem(righField);

            PmsAPI pms = new PmsAPI();
            pms.checkPersonRecord(user, table);
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public TableVO queryEmpList(User user, String activeQrySql, String activeQryShowItem, PageVO page) throws BkmsException {
        try {
            if (activeQryShowItem.startsWith(",")) activeQryShowItem = activeQryShowItem.substring(1);
            String[] items = activeQryShowItem.split(",");
            int len = items.length;
            InfoItemBO[] header = new InfoItemBO[len];
            for (int i = 0; i < len; i++) {
                String[] item = items[i].split("\\.");
                header[i] = SysCacheTool.findInfoItem(item[0], item[1]);
            }
            String righField = Tools.filterNull(StaticVariable.get(PmsConstants.COTROL_ITEMS_PERSON));
            if (righField != null && righField.length() > 0) {
                String[] fields = righField.split(",");
                String requiredFieldString = null;
                int fromIndex = activeQrySql.toLowerCase().indexOf("from");
                String select = activeQrySql.substring(0, fromIndex);
                String upperSelect = select.toUpperCase();
                for (int i = 0; i < fields.length; i++) {
                    String field = fields[i];
                    if (upperSelect.indexOf(field.toUpperCase()) < 0) {
                        if (requiredFieldString == null) {
                            requiredFieldString = field;
                        } else {
                            requiredFieldString += "," + field;
                        }
                    }
                }
                if (requiredFieldString != null) {
                    String from = activeQrySql.substring(fromIndex);
                    activeQrySql = select + "," + requiredFieldString + " " + from;
                }
//                if (activeQrySql.indexOf(righField) < 0) {
//                    int fromIndex = activeQrySql.toLowerCase().indexOf("from");
//                    String select = activeQrySql.substring(0, fromIndex);
//                    String from = activeQrySql.substring(fromIndex);
//                    if (righField.startsWith(",")) righField = righField.substring(1);
//                    if (righField.endsWith(",")) righField = righField.substring(0, righField.lastIndexOf(",") - 1);
//                    activeQrySql = select + "," + righField + " " + from;
//                }
            }
            TableVO table = activePageService.queryListBySql(activeQrySql, header, righField, page, true);
            PmsAPI pms = new PmsAPI();
            pms.checkPersonRecord(user, table);
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public TableVO createBlankEmpInfoSetRecord(User user, String setId, String fk) throws BkmsException {
        try {
            TableVO table = activePageService.getBlankInfoSetRecord(setId, null, fk);
            PmsAPI pms = new PmsAPI();
            table.setColRight(pms.checkHeader(user, table.getHeader()));
//            table.setTableRight(pms.checkSpecialTable(user, setId,fk));
            return table;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }


    public TableVO findEmpInfoSetRecord(User user, String setId, String pk) throws BkmsException {
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        try {
            TableVO table = new TableVO();
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(set.getSetId()).toArray(new InfoItemBO[0]);
            String[][] rows = null;
            if (pk == null) {
                rows = new String[1][header.length];
                String[] blank = new String[header.length];
                for (int i = 0; i < header.length; i++) {
                    blank[i] = header[i].getItemDefaultValue();
                }
                rows[0] = blank;
            } else {
                String row[] = activePageService.findRecord(set, header, pk);
                rows = new String[1][header.length];
                rows[0] = row;
            }
            table.setInfoSet(set);
            table.setHeader(header);
            table.setRows(rows);

            PmsAPI pms = new PmsAPI();
            pms.checkPersonRecord(user, table);
//            table.setTableRight(pms.checkSpecialTable(user, setId,pk));//add by yxm on 2015-3-25
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public TableVO queryEmpInfoSetRecordList(User user, String setId, String fk) throws BkmsException {
        InfoSetBO set = SysCacheTool.findInfoSet(setId);
        try {
            TableVO table = new TableVO();
            InfoItemBO[] header = (InfoItemBO[]) SysCacheTool.queryInfoItemBySetId(set.getSetId()).toArray(new InfoItemBO[0]);
            String rows[][] = activePageService.queryRecord(set, header, fk);
            table.setInfoSet(set);
            table.setHeader(header);
            table.setRows(rows);
            PmsAPI pms = new PmsAPI();
            pms.checkPersonRecord(user, table);
//            table.setTableRight(pms.checkTable(user, setId));
            table.setTableRight(pms.checkSpecialTable(user, setId,fk));
            return table;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public String addEmpInfoSetRecord(User user, String setId, Map map) throws BkmsException {
        try {
            String re = activePageService.addRecord(user, setId, map, true, true);
            if (setId.equalsIgnoreCase("A001")) {
                SysCache.setMap(new String[]{re}, CacheConstants.OPER_ADD, CacheConstants.OBJ_PERSON);
            }
            return re;
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

    public void updateEmpInfoSetRecord(User user, String setId, Map map, String pk) throws BkmsException {
        try {
            activePageService.updateRecord(user, setId, map, true, true);
            if (setId.equalsIgnoreCase("A001")) {
                SysCache.setMap(new String[]{pk}, CacheConstants.OPER_UPDATE, CacheConstants.OBJ_PERSON);
            }
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }
    
    public void updatePersonBO(String name, String pk) throws BkmsException{
    	activePageService.updatePersonBO(name, pk);
    }

    public void deleteEmpInfoSetRecord(User user, String setId, String[] pk, String fk) throws BkmsException {
        try {
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            activePageService.deleteSubRecord(user, set, pk, fk, true, true);
            if (setId.equalsIgnoreCase("A001")) {
                SysCache.setMap(pk, CacheConstants.OPER_DELETE, CacheConstants.OBJ_PERSON);
            }
        } catch (RollbackableException re) {
            throw re;
        } catch (Exception e) {
            throw new BkmsException("", e, this.getClass());
        }
    }

//    public void (User user, String setId, Map map, String pk, byte[] photo) throws BkmsException {
//        if (photo != null && photo.length > 0) {
//            AttachmentService attachmentService = (AttachmentService) BkmsContext.getBean("cont_attachmentService");
//            String photoId = attachmentService.createAttachment(photo, null, null, 0, "person", "images", user);
//            map.put("A001225", photoId);
//        }
//        updateEmpInfoSetRecord(user, setId, map, pk);
//    }

    public static InfoItemBO[] getHeader(String SelectSql) {
        if (SelectSql == null) return null;
        String[] select = StringUtils.split(SelectSql, ",");
        InfoItemBO[] vos = new InfoItemBO[select.length];
        String set[] = StringUtils.split(select[0], ".");    //A001.ID
        InfoItemBO b = SysCacheTool.findInfoItem(set[0], set[1]);
        InfoItemBO v = new InfoItemBO();
        Tools.copyProperties(v, b);
        vos[0] = v;
        int j = 1;
        for (int i = 1; i < select.length; i++) {
            String s1 = select[i];
            String sets[] = StringUtils.split(select[i].trim(), ".");    //A001.ID
            InfoItemBO bo;
            if (sets != null && sets.length == 2)
                bo = SysCacheTool.findInfoItem(sets[0], sets[1]);
            else
                bo = SysCacheTool.findInfoItem(null, s1.trim());
            if (bo != null) {
                v = new InfoItemBO();
                Tools.copyProperties(v, bo);
                v.setShowId(true);
                vos[j] = v;
                j++;
            }
        }
        return (InfoItemBO[]) ArrayUtils.subarray(vos, 0, j);
    }

    /**
     * 拼加权限字段(为了防止sql语句的select项里字段重复)
     *
     * @param sql        拼好的sql语句
     * @param rightField 权限需要的字段
     * @return
     */
    public static String addSqlSelect(String sql, String rightField) {
        if (sql == null) return "";
        if (rightField == null || rightField.length() == 0) return sql;
        if (rightField.length() > 0) {
            if (sql.indexOf(rightField) < 0) {
                if (rightField.startsWith(",")) rightField = rightField.substring(1);
                if (rightField.endsWith(",")) rightField = rightField.substring(0, rightField.lastIndexOf(",") - 1);
                int fromIndex = sql.toLowerCase().indexOf(" from ");
                String select1 = sql.substring(0, fromIndex);
                String from1 = sql.substring(fromIndex);
                String[] fs = rightField.split(",");
                sql = select1;
                for (int i = 0; i < fs.length; i++) {
                    if ((select1.toUpperCase().indexOf(fs[i].trim().toUpperCase())) < 0)
                        sql += "," + fs[i];

                }
                sql += " " + from1;
            }
        }
        return sql;
    }
}
