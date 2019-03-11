package com.becoda.bkms.sys.pojo.vo;

import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;

import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-3-11
 * Time: 16:21:20
 * To change this template use File | Settings | File Templates.
 */
public class TableVO {
    private InfoSetBO infoSet;
    //表格权限
    private int tableRight;
    //行值
    private String[][] rows;
    private String[][] rightData;
    //指标项 （列）的信息
    private InfoItemBO[] header;
    //行权限
    private int[] rowRight;
    //列权限
    private int[] colRight;

    private int[][] cellRight; //存放每个单元格的权限

    private String querySql;

    private String[] rightItem;

    private Hashtable headerIndex;

    public TableVO() {
        headerIndex = new Hashtable();
    }

    /**
     * 根据指标项ID查找指标所在列号
     *
     * @param infoItemId
     * @return 列号，如果为-1 则不在列中
     */
    public int getCol(String infoItemId) {
        int col = -1;
        String colindex = (String) headerIndex.get(infoItemId);
        if (colindex == null) {
            int length = this.header.length;
            for (int i = 0; i < length; i++) {
                InfoItemBO item = header[i];
                if (infoItemId.equalsIgnoreCase(item.getItemId())) {
                    col = i;
                    headerIndex.put(infoItemId, String.valueOf(i));
                    break;
                }
            }
        } else {
            col = Integer.parseInt(colindex);
        }
        return col;
    }

    /**
     * 按列号 获取列头信息
     *
     * @param col 列号
     * @return 获取指标信息
     */
    public InfoItemBO getInfoItem(int col) {
        return this.header[col];
    }

    /**
     * 根据列号获取列权限
     *
     * @param col
     * @return 列权限
     */
    public int getColRight(int col) {
        if (this.colRight != null)
            return this.colRight[col];
        else {
            return PmsConstants.PERMISSION_READ;
        }
    }

    /**
     * 按指标项Id获取列权限
     *
     * @param infoItemId
     * @return 返回列的读写权限
     */
    public int getColRight(String infoItemId) {
        int colnum = this.getCol(infoItemId);
        return this.colRight[colnum];
    }


    /**
     * 按行号获取行权限
     *
     * @param row
     * @return 行权限
     */
    public int getRowRight(int row) {
        return this.rowRight[row];
    }

    /**
     * 完全清除行数据
     */
    public void clearRows() {
        this.rows = null;
    }

    /**
     * 获取表头信息
     */
    public InfoItemBO[] getHeader() {
        return header;
    }

    public void setHeader(InfoItemBO[] header) {
        this.header = header;
        int len = header.length;
        for (int i = 0; i < len; i++) {
            if(header[i]!=null){
                headerIndex.put(header[i].getItemId(), String.valueOf(i));
            }
        }
    }

    public int getTableRight() {
        return tableRight;
    }

    public void setTableRight(int tableRight) {
        this.tableRight = tableRight;
    }

    public String[][] getRows() {
        return rows;
    }

    public void setRows(String[][] rows) {
        this.rows = rows;
    }

    public int[] getRowRight() {
        return rowRight;
    }

    public void setRowRight(int[] rowRight) {
        this.rowRight = rowRight;
    }

    public int[] getColRight() {
        return colRight;
    }

    public void setColRight(int[] colRight) {
        this.colRight = colRight;
    }

    public Hashtable getHeaderIndex() {
        return headerIndex;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public InfoSetBO getInfoSet() {
        return infoSet;
    }

    public void setInfoSet(InfoSetBO infoSet) {
        this.infoSet = infoSet;
    }

    public String[] getRightItem() {
        return rightItem;
    }

    public void setRightItem(String[] rightItem) {
        this.rightItem = rightItem;
    }

    public void setRightItem(String rightItem) {
        if (rightItem != null) {
            this.rightItem = rightItem.split(",");
        }
    }

    public String[][] getRightData() {
        return rightData;
    }

    public void setRightData(String[][] rightData) {
        this.rightData = rightData;
    }

    public int[][] getCellRight() {
        return cellRight;
    }

    public int getCellRight(int row, int col) {
        return this.cellRight[row][col];
    }

    public int getCellRight(int row, String itemId) {
        int col = this.getCol(itemId);
        return this.cellRight[row][col];
    }

    public void setCellRight(int[][] cellRight) {
        this.cellRight = cellRight;
    }

    public void setCellRight(int row, int col, int right) {
        this.cellRight[row][col] = right;
    }

    public void setCellRight(int row, String itemId, int right) {
        int col = this.getCol(itemId);
        this.cellRight[row][col] = right;
    }

    public void reset() {
        this.rows = null;
        this.rowRight = null;
        this.colRight = null;
        this.rows = null;
        this.rightItem = null;
        this.rightData = null;
    }

    public String getRowItem(int row, String itemId) {
        if (rows == null || rows.length < row + 1) {
            return null;
        }
        int colItem = getCol(itemId);
        if (colItem < 0) {
            return null;
        }
        return rows[row][colItem];
    }
}
