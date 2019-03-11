package com.becoda.bkms.sys.dao;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import com.becoda.bkms.util.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-11
 * Time: 16:21:00
 * To change this template use File | Settings | File Templates.
 */
public class ActivePageDAO extends GenericDAO {
    /**
     * 增加子集的一条记录
     *
     * @param setId 子集编号
     * @param cells 包含字段和值
     */
    public void addRecord(String setId, InfoItemBO[] cells, String[] cellValue) throws RollbackableException {
        if (setId == null || cells == null)
            return;
        if (cells.length != cellValue.length) return;
        try {
            //--start 拼接插入语句
            StringBuffer sql = new StringBuffer("insert into ");
            sql.append(setId).append("( ");
            int count = cells.length;
            StringBuffer field = new StringBuffer();  //存放字段名
            StringBuffer value = new StringBuffer();  //存放字段的值
            String tmp;
            for (int i = 0; i < count; i++) {
                if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(cells[i].getItemDataType()))
                    continue;
                field.append(cells[i].getItemId()).append(",");
                tmp = cellValue[i] == null || "".equals(cellValue[i]) ? "''" : "'" + cellValue[i] + "'";
                value.append(tmp).append(",");
            }
            field.deleteCharAt(field.length() - 1);
            value.deleteCharAt(value.length() - 1);
            sql.append(field).append(") values ( ").append(value).append(" ) ");

            jdbcTemplate.execute(sql.toString());
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 修改一个子集的记录
     *
     * @param operatorId 操作人ID
     * @param table      TableVO
     * @throws RollbackableException e
     */
    public void updateRecord(String operatorId, TableVO table) throws RollbackableException {
        if (table == null || table.getRows() == null || table.getRows().length == 0) {
            return;
        }
        InfoSetBO set = table.getInfoSet();
        String setId = set.getSetId();     //得到子集的编号,"A001,B002";
        String pkName = set.getSetPk();    //得到子集的主键编号，"ID,ORGUID,POSTID,.."
        String fkName = set.getSetFk();    //得到子集的外键编号，"ID,ORGUID,POSTID,.."
        //主键列与外键列位置
        int pkKeyCol = table.getCol(pkName);
        int fkKeyCol = fkName == null ? -1 : table.getCol(fkName);

        String pkValue, fkValue;
        String otherValue;

        String[][] rows = table.getRows();
        InfoItemBO[] header = table.getHeader();
        ArrayList list = new ArrayList();
        for (int r = 0; r < rows.length; r++) {
            String[] row = rows[r];
            if (row == null || row.length == 0) continue;
            pkValue = pkKeyCol > -1 ? row[pkKeyCol] : "";
            fkValue = fkKeyCol > -1 ? row[fkKeyCol] : "";

            //--start 拼接更新语句
            StringBuffer sql = new StringBuffer("update ");
            sql.append(setId).append(" set ");
            StringBuffer field = new StringBuffer();
            for (int i = 0; i < header.length; i++) {
                InfoItemBO head = header[i];
                if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(head.getItemDataType()) || head.getItemId().equals(pkName) || head.getItemId().equals(fkName))
                    continue;
                field.append(header[i].getItemId()).append(" = ");
                otherValue = row[i] == null || "".equals(row[i]) ? "''" : "'" + row[i] + "'";
                field.append(otherValue).append(",");
            }

            //
            //update time and operator
            String date = Tools.getSysDate("yyyy-MM-dd HH:mm:ss");
            field.append("last_update_time='").append(date).append("',");
            field.append("last_operator='").append(operatorId).append("',");
            //

            field.deleteCharAt(field.length() - 1);
            sql.append(field);
            if (!"".equals(pkValue)) {
                sql.append(" where ").append(pkName).append("='").append(pkValue).append("'");
                list.add(sql.toString());
            } else if (!"".equals(fkValue)) {
                sql.append(" where ").append(fkName).append("='").append(fkValue).append("' and ").append(setId).append("000 = '" + Constants.YES + "'");
                list.add(sql.toString());
            }
            //--end 拼接更新语句
            if (r % 1000 == 0) {
                jdbcTemplate.batchUpdate((String[]) list.toArray(new String[list.size()]));
                list.clear();
            }
        }
    }

    public void updatePersonBO(String name, String pk) throws BkmsException{
    	String sql = "update A001 set A001001 = '"+name+"' where id = '"+pk+"'";
    	jdbcTemplate.update(sql.toString());
    }
    /**
     * 删除指定的记录
     *
     * @param setId   子集编号
     * @param pkName  主键名
     * @param pkValue 主键的值
     * @throws RollbackableException
     */
    public void deleteRecord(String setId, String pkName, String pkValue) throws RollbackableException {
        try {
            //--strat 拼接删除语句
            StringBuffer sql = new StringBuffer("delete from ");
            sql.append(setId).append(" where ");
            sql.append(pkName).append("='").append(pkValue).append("'");
            //--end 拼接删除语句
            jdbcTemplate.execute(sql.toString());
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    /**
     * 删除指标集合的多条记录
     *
     * @param setId
     * @param pkname
     * @param pkValue
     * @throws RollbackableException
     */
    public void deleteRecord(String setId, String pkname, String[] pkValue) throws RollbackableException {
        try {
            StringBuffer sql = new StringBuffer("delete from ");
            sql.append(setId).append(" where ");
            sql.append(Tools.splitInSql(pkValue, pkname));
            jdbcTemplate.execute(sql.toString());
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public void executeSql(String sql) throws RollbackableException {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public void batchExecuteSql(String[] sql) throws RollbackableException {
        try {
            int count = sql.length;
            if (count < 1000) {
                jdbcTemplate.batchUpdate(sql);
            } else {
                ArrayList list = new ArrayList();
                for (int i = 0; i < count; i++) {
                    list.add(sql[i]);
                    if (i % 1000 == 0) {
                        jdbcTemplate.batchUpdate((String[]) list.toArray(new String[0]));
                        list.clear();
                    }
                }
                if (list.size() > 0)
                    jdbcTemplate.batchUpdate((String[]) list.toArray(new String[0]));
            }
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public String[] findRecord(InfoSetBO set, InfoItemBO[] col, String pkValue) throws RollbackableException {
        try {
            String sql = " from " + set.getSetId() + " where " + set.getSetPk() + "='" + pkValue + "'";
            int count = col.length;
            StringBuffer field = new StringBuffer();
            for (int i = 0; i < count; i++) {
                if (!SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(col[i].getItemDataType()))
                    field.append(col[i].getItemId()).append(",");
            }

            sql = "select " + field.substring(0, field.length() - 1) + sql;
            String[] data = new String[count];
            List rs = jdbcTemplate.queryForList(sql);
            if (!rs.isEmpty()) {
                for (int i = 0; i < rs.size(); i++) {
                    Map line = (Map) rs.get(i);
                    for (int j = 0; j < count; j++) {
                        if (!SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(col[i].getItemDataType())) {
                            data[j] = Tools.filterNull(line.get(col[j].getItemId()));
                        }
                    }
                }
                return data;
            } else return null;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public String[][] queryRecord(InfoItemBO[] col, String sql) throws RollbackableException {
        try {
            int count = col.length;
            List rs = jdbcTemplate.queryForList(sql);
            if (rs.isEmpty()) return null;
            int size = rs.size();
            String[][] data = new String[size][count];
            for (int r = 0; r < size; r++) {
                Map line = (Map) rs.get(r);
                for (int i = 0; i < count; i++) {
                    if (!SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(col[i].getItemDataType())) {
                        data[r][i] = Tools.filterNull(line.get(col[i].getItemId()));
                    }
                }
            }
            return data;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public int queryForInt(String sql) throws RollbackableException {
        try {
            return jdbcTemplate.queryForInt(sql);
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public List queryForList(String sql) throws RollbackableException {
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }

    public void queryRecord(TableVO table, PageVO pageVO, String sql, boolean page) throws RollbackableException {
        InfoItemBO[] header = table.getHeader();
        int count = header.length;
        try {
            if (page) {
                //查询记录总数
                String distinct = "*";
                int lineCount = pageVO.getTotalRecord();
                if (lineCount <= 0) {
                    String sql1 = "SELECT COUNT(" + distinct + ") FROM (" + sql + ")";
                    lineCount = this.queryForInt(sql1);
                    pageVO.setTotalRecord(lineCount);
                }
                int runPageSize = pageVO.getPageSize();
                int pageCount = lineCount / runPageSize;
                if (lineCount % runPageSize > 0)
                    pageCount++;
                if (pageVO.getCurrentPage() > pageCount && pageCount != 0)
                    pageVO.setCurrentPage(pageCount);
                pageVO.setTotalPage(pageCount);

                //动态查询
                int rownummax = pageVO.getCurrentPage() * runPageSize;
                int rownumin = (pageVO.getCurrentPage() - 1) * runPageSize;
                StringBuffer stmp = new StringBuffer();
                stmp.append("SELECT * FROM " +
                        "(" +
                        " SELECT A.*, rownum r" +
                        " FROM" +
                        " (" + sql + ") A" +
                        " WHERE rownum< =" + rownummax +
                        " ) B" +
                        " WHERE r > " + rownumin);
                sql = stmp.toString();
            }
            List rs = jdbcTemplate.queryForList(sql);
            if (!rs.isEmpty() && rs.size() > 0) {
                int rsCount = rs.size();
                String[][] rows = new String[rs.size()][count];
                String[][] rightData = null;
                boolean checkRight = false;
                int rightCol = 0;
                String[] rightItem = null;
                if (table.getRightItem() != null) {
                    rightItem = table.getRightItem();
                    rightCol = rightItem.length;
                    rightData = new String[rsCount][rightCol];
                    checkRight = true;
                }
                int j = 0;
                for (int r = 0; r < rsCount; r++) {
                    String[] line = new String[count];
                    String[] right = new String[rightCol];
                    Map rsLine = (Map) rs.get(r);
                    for (int i = 0; i < count + rightCol; i++) {
                        if (i < count) {
                            if (!SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[i].getItemDataType())) {
                                try {
                                    line[i] = Tools.filterNull(rsLine.get(header[i].getItemId()));
                                } catch (Exception e) {
                                }
                            }
                        } else if (checkRight) {
                            right[i - count] = Tools.filterNull(rsLine.get(rightItem[i - count]));
                        }
                    }
                    rows[j] = line;
                    if (checkRight) rightData[j] = right;
                    j++;
                }
                table.setRows(rows);
                table.setRightData(rightData);
            } else {
                table.setRows(null);
                table.setRightData(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
    }

    public String[] findCurrentRecord(InfoSetBO set, InfoItemBO[] col, String fkValue) throws RollbackableException {
        try {
            String sql = " from " + set.getSetId() + " where ID='" + fkValue + "' and " + set.getSetId() + "000 = '" + Constants.YES + "'";
            int count = col.length;
            StringBuffer field = new StringBuffer();
            for (int i = 0; i < count; i++) {
                if (!SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(col[i].getItemDataType()))
                    field.append(col[i].getItemId()).append(",");
            }

            sql = "select " + field.substring(0, field.length() - 1) + sql;
            String[] data = new String[count];
            List rs = jdbcTemplate.queryForList(sql);
            if (!rs.isEmpty()) {
                for (int i = 0; i < rs.size(); i++) {
                    Map line = (Map) rs.get(i);
                    for (int j = 0; j < count; j++) {
                        if (!SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(col[i].getItemDataType())) {
                            data[j] = Tools.filterNull(line.get(col[j].getItemId()));
                        }
                    }
                }
                return data;
            } else return null;
        } catch (Exception e) {
            throw new RollbackableException(e, this.getClass());
        }
    }
}
