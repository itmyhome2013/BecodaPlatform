package com.becoda.bkms.east.jhzb.dao;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-4
 * Time: 11:23:37
 * To change this template use File | Settings | File Templates.
 */
public class InfoSetDAO extends GenericDAO {


    /**
     * 新建指标集
     *
     * @param infoSet
     * @throws RollbackableException
     */
    public void createInfoSet(InfoSetBO infoSet) throws RollbackableException {
        try {
            hibernateTemplate.save(infoSet);
            StringBuffer sql = new StringBuffer();
            String type = infoSet.getSet_rsType();
            //创建数据库表
            sql.append(" create table " + infoSet.getSetId() + " ( ");
            sql.append("create_time VARCHAR2(20),");
            sql.append("last_update_time VARCHAR2(20),");
            sql.append("last_operator VARCHAR2(100),");
            sql.append(infoSet.getSetPk() + " VARCHAR2(100), ");
            if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(type)) {
                sql.append(infoSet.getSetFk() + " VARCHAR2(100),");   //外键
                sql.append(infoSet.getSetId() + "000 VARCHAR2(100),");  //status
            }
            sql.append("constraint PK_")       //pk
                    .append(infoSet.getSetId())
                    .append(" primary key (")
                    .append(infoSet.getSetPk())
                    .append("))");
            jdbcTemplate.execute(sql.toString());
            //增加指标项记录
            sql.delete(0, sql.length());
            if (SysConstants.INFO_SET_RS_TYPE_SINGLE.equals(type)) {
                sql.append("INSERT INTO SYS_INFO_ITEM ( ITEM_ID, SET_ID,ITEM_PROPERTY,edit_Property,ITEM_TYPE,ITEM_LENGTH,ITEM_STATUS ) VALUES ( ");
                sql.append("'").append(infoSet.getSetPk()).append("',");
                sql.append("'").append(infoSet.getSetId()).append("', ");
                sql.append("'" + SysConstants.INFO_ITEM_PROPERTY_SYSTEM + "',");
                sql.append("'" + SysConstants.INFO_ITEM_EDIT_PROP_HIDE + "',");
                sql.append("'" + SysConstants.INFO_ITEM_DATA_TYPE_STRING + "',");
                sql.append("'100',");
                sql.append("'" + SysConstants.INFO_STATUS_OPEN + "') ");
                jdbcTemplate.execute(sql.toString());

                //如果是单记录，将主集ID 插入到数据库
                sql.delete(0, sql.length());
                sql.append("insert into ").append(infoSet.getSetId()).append("(").append(infoSet.getSetFk()).append(")")
                        .append(" select ").append(infoSet.getSetFk()).append(" from ").append(infoSet.getSet_bType()).append("001");
                jdbcTemplate.execute(sql.toString());

            } else if (SysConstants.INFO_SET_RS_TYPE_MANY.equals(type)) {//处理1：n指标集
                //增加指标项记录
                //主键
                sql.delete(0, sql.length());
                sql.append("INSERT INTO SYS_INFO_ITEM ( ITEM_ID, SET_ID,ITEM_PROPERTY,edit_Property,ITEM_TYPE,ITEM_LENGTH,ITEM_STATUS ) VALUES ( ");
                sql.append("'").append(infoSet.getSetPk()).append("',");
                sql.append("'").append(infoSet.getSetId()).append("',");
                sql.append("'" + SysConstants.INFO_ITEM_PROPERTY_SYSTEM + "',");
                sql.append("'" + SysConstants.INFO_ITEM_EDIT_PROP_HIDE + "',");
                sql.append("'" + SysConstants.INFO_ITEM_DATA_TYPE_STRING + "',");
                sql.append("'100',");
                sql.append("'" + SysConstants.INFO_STATUS_OPEN + "') ");
                jdbcTemplate.execute(sql.toString());
                //外键
                sql.delete(0, sql.length());
                sql.append("INSERT INTO SYS_INFO_ITEM ( ITEM_ID, SET_ID,ITEM_PROPERTY,edit_Property,ITEM_TYPE,ITEM_LENGTH,ITEM_STATUS ) VALUES ( ");
                sql.append("'").append(infoSet.getSetFk()).append("',");
                sql.append("'").append(infoSet.getSetId()).append("',");
                sql.append("'" + SysConstants.INFO_ITEM_PROPERTY_SYSTEM + "',");
                sql.append("'" + SysConstants.INFO_ITEM_EDIT_PROP_HIDE + "',");
                sql.append("'" + SysConstants.INFO_ITEM_DATA_TYPE_STRING + "',");
                sql.append("'100',");
                sql.append("'" + SysConstants.INFO_STATUS_OPEN + "') ");
                jdbcTemplate.execute(sql.toString());
                //状态位
                sql.delete(0, sql.length());
                sql.append("INSERT INTO SYS_INFO_ITEM (ITEM_ID, SET_ID,ITEM_PROPERTY,edit_Property,ITEM_TYPE,CODE_SET_ID,ITEM_STATUS ) VALUES ( ");
                sql.append("'").append(infoSet.getSetId()).append("000',");
                sql.append("'").append(infoSet.getSetId()).append("',");
                sql.append("'" + SysConstants.INFO_ITEM_PROPERTY_SYSTEM + "',");
                sql.append("'" + SysConstants.INFO_ITEM_EDIT_PROP_HIDE + "',");
                sql.append("'" + SysConstants.INFO_ITEM_DATA_TYPE_CODE + "',");
                sql.append("'0090',"); // todo
                sql.append("'" + SysConstants.INFO_STATUS_OPEN + "') ");
                jdbcTemplate.execute(sql.toString());
            }
        } catch (Exception e) {
            throw new RollbackableException("添加失败", e, this.getClass());
        }

    }

    /**
     * 删除信息集
     *
     * @param setId 信息集ID
     */
    public void deleteInfoSet(String setId) throws RollbackableException {
        try {
            //删除数据表
            StringBuffer sf = new StringBuffer();
            sf.append("drop table ").append(setId);
            jdbcTemplate.execute(sf.toString());

            //删除指标项
            sf.delete(0, sf.length());
            sf.append("delete from sys_info_item  where set_id='");
            sf.append(setId);
            sf.append("'");
            jdbcTemplate.execute(sf.toString());

            //删除表记录
            InfoSetBO iSet = (InfoSetBO) super.findBoById(InfoSetBO.class, setId);
            super.deleteBo(iSet);
        } catch (Exception e) {
            throw new RollbackableException("删除失败", e, InfoSetDAO.class);
        }
    }


    /**
     * 得到某类指标集列表          setId = "A001" "B001" "C001" "D001"
     */
    public InfoSetBO[] queryCascadeInfoSet(String setId) throws RollbackableException {
        try {
            List list;
            InfoSetBO set = SysCacheTool.findInfoSet(setId);
            if (set != null) {
                String pkName = set.getSetPk();
                String sql = "from InfoSetBO s where s.set_bType='" + set.getSet_bType() + "' and s.setFk='" + pkName + "' order by s.setSequence+0";
                list = hibernateTemplate.find(sql);
                if (null == list || list.size() == 0)
                    return null;
                else {
                    return (InfoSetBO[]) list.toArray(new InfoSetBO[0]);
                }
            }
            return null;
        } catch (Exception e) {
            throw new RollbackableException("检索查询类别失败", e, this.getClass());
        }
    }

    /**
     * 得到制定小类的指标集列表
     *
     * @param sType  信息集小类      如果sType =null or "" 则查询所有
     * @param status 过滤标记      status =null or "" 则查询所有,否则查启用的指标集
     * @return cn.ccb.hrdc.sys.pojo.bo.InfoSetBO[]
     */
    public InfoSetBO[] queryInfoSets(String sType, String status) throws RollbackableException {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("from InfoSetBO iset ");
            if (sType != null && !"".equals(sType.trim())) {
                sb.append(" where iset.set_sType='").append(sType).append("'");
            }
            if (status != null && !"".equals(status.trim())) {
                if (sb.indexOf("where") == -1)
                    sb.append(" where iset.setStatus ='").append(SysConstants.INFO_STATUS_OPEN).append("'");
                else
                    sb.append(" and iset.setStatus ='").append(SysConstants.INFO_STATUS_OPEN).append("'");
            }
            sb.append(" order by iset.setSequence+0");
            List list = hibernateTemplate.find(sb.toString());
            return (InfoSetBO[]) list.toArray(new InfoSetBO[0]);
        } catch (Exception e) {
            throw new RollbackableException("读取数据错误！", e, InfoSetDAO.class);
        }
    }

    /**
     * 康澍增加 根据大类查指标集
     *
     * @param type
     * @return
     * @throws RollbackableException
     */
    public InfoSetBO[] queryInfoSetByBigType(String type) throws RollbackableException {
        if (type == null)
            return null;
        try {
            List list = hibernateTemplate.find("from InfoSetBO i where i.set_bType = ?  order by i.setSequence+0", type);
            if (list.isEmpty()) return null;
            return (InfoSetBO[]) list.toArray(new InfoSetBO[0]);
        } catch (Exception e) {
            throw new RollbackableException("", e, getClass());
        }
    }

    public InfoSetBO[] queryInfoSetByBigType(String type, String rsType) throws RollbackableException {
        if (type == null)
            return null;
        if (rsType == null || rsType.trim().equals("") || rsType.trim().equalsIgnoreCase("null")) {
            return queryInfoSetByBigType(type);
        }
        try {
            List list = hibernateTemplate.find("from InfoSetBO i where i.set_bType = '" + type + "' and  i.set_rsType= '" + rsType + "' order by i.setSequence+0");
            if (list.isEmpty()) return null;
            return (InfoSetBO[]) list.toArray(new InfoSetBO[list.size()]);
        } catch (Exception e) {
            throw new RollbackableException("", e, getClass());
        }
    }

    /**
     * 得到一个新的指标集名称
     *
     * @param bType 大类  A B C D
     * @return 指标集名称
     */
    public String getNewSetId(String bType, String setProperty) throws RollbackableException {
        try {

            int ini = 200;
            int end = 699;
            if (SysConstants.INFO_SET_PROPERTY_SYS.equals(setProperty) || SysConstants.INFO_SET_PROPERTY_HIDE.equals(setProperty)) {
                ini = 700;
                end = 999;
            } else if (SysConstants.INFO_SET_PROPERTY_GB.equals(setProperty)) {
                ini = 0;
                end = 999;
            }
            String newSetId = "";
            List list = hibernateTemplate.find("select b.setId from InfoSetBO b where b.setId like'" + bType + "%'");
            if (list != null && list.size() > 0) {
                for (int i = ini; i < end; i++) {
                    newSetId = "000" + Integer.toString(i);
                    newSetId = bType + newSetId.substring(newSetId.length() - 3);
                    if (list.indexOf(newSetId) == -1) {
                        break;
                    }
                }
                //达到最大数
                return newSetId;
            } else {
                newSetId = "000" + Integer.toString(ini);
                newSetId = bType + newSetId.substring(newSetId.length() - 3);
                return newSetId;
            }
        } catch (Exception e) {
            throw new RollbackableException("读取指标集数据时出错！", e, getClass());
        }
    }
}
