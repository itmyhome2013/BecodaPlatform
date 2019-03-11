package com.becoda.bkms.qry.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: ye
 * Date: 2015-4-23
 * Time: 15:56:19
 * To change this template use File | Settings | File Templates.
 */
public class QryTools {
    public static String ser(Object obj) throws BkmsException {
        ObjectOutputStream out = null;
        String ret = "";
        ByteArrayOutputStream hsSqlByte = null;
        try {
            hsSqlByte = new ByteArrayOutputStream();
            out = new ObjectOutputStream(hsSqlByte);
            out.writeObject(obj);
            ret = Endecode.base64EncodeFoArray(hsSqlByte.toByteArray());
        } catch (IOException e) {
            throw new BkmsException("序列化查询对象错误", e, e.getClass());
        } finally {
            if (hsSqlByte != null) try {
                hsSqlByte.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ret;
    }

    public static Hashtable deSer(String str) throws BkmsException {
        Hashtable ret = null;
        ObjectInputStream in = null;
        ByteArrayInputStream hsSqlByte = null;
        try {
            hsSqlByte = new ByteArrayInputStream(Endecode.base64DecodeToArray(str));
            in = new ObjectInputStream(hsSqlByte);
            ret = (Hashtable) in.readObject();
        } catch (Exception e) {
            throw new BkmsException("反序列化查询对象错误", e, e.getClass());
        } finally {
            if (hsSqlByte != null)
                try {
                    hsSqlByte.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
        }
        return ret;
    }

    public static Object deSer1(String str) throws BkmsException {
        Object ret = null;
        ObjectInputStream in = null;
        ByteArrayInputStream hsSqlByte = null;
        try {
            hsSqlByte = new ByteArrayInputStream(Endecode.base64DecodeToArray(str));
            in = new ObjectInputStream(hsSqlByte);
            ret = in.readObject();
        } catch (Exception e) {
            throw new BkmsException("反序列化查询对象错误", e, e.getClass());
        } finally {
            if (hsSqlByte != null) try {
                hsSqlByte.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            if (in != null) try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return ret;
    }

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
                int fromIndex = sql.toLowerCase().indexOf("from");
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
    public static Hashtable getOperatorByStr() throws BkmsException {
        Hashtable hash = new Hashtable();
        String[] str = {"equal", "notequal", "morethan", "lessthan", "moreequal",
                "lessequal", "in", "notin", "like", "notlike", "exist", "notexist",
                "isnull", "notisnull", "equalsysdate", "morethansysdate", "lessthansysdate", "likesysdateMD", "likesysdateM", "likesysdateY"};
        String[] operator = {"=", "不等于", ">", "<", ">=", "<=",
                "在列表中", "不在列表中", "匹配", "不匹配", "存在", "不存在", "为空",
                "不为空", "等于系统日期", "大于系统日期+(天)", "小于系统日期+(天)", "匹配系统月日+(天)", "匹配系统月+(月)", "匹配系统年"};
        int count = str.length;
        for (int i = 0; i < count; i++) {
            hash.put(str[i], operator[i]);
        }
        return hash;
    }
}
