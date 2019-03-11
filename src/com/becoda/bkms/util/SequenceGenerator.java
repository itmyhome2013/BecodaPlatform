package com.becoda.bkms.util;

import com.becoda.bkms.common.exception.BkmsException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.id.UUIDHexGenerator;
import org.safehaus.uuid.EthernetAddress;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-02-22
 */

public class SequenceGenerator {
    /**
     * 生成UUID
     *
     * @return UUID
     */
    public static String getUUID() {
//        UUID uuid = UUIDGenerator.getInstance().generateTimeBasedUUID(new EthernetAddress("00:16:36:14:0e:d5"));
//        String key = uuid.toString();
//        String[] tmp = key.split("-");
//        key = tmp[0] + tmp[4] + tmp[1] + tmp[2] + tmp[3];
        UUIDHexGenerator generator = new UUIDHexGenerator();
        return  generator.generate(null, null).toString();
    }

    /**
     * 生成指定个数UUID
     *
     * @param num
     * @return 多个 UUID
     */
    public static String[] getUUID(int num) {
//        UUIDGenerator generator = UUIDGenerator.getInstance();
//        String[] key = new String[num];
//        for (int i = 0; i < num; i++) {
//            UUID uuid = generator.generateTimeBasedUUID(new EthernetAddress("00:16:36:14:0e:d5"));
//            String keyuuid = uuid.toString();
//            String[] tmp = keyuuid.split("-");
//            key[i] = tmp[0] + tmp[4] + tmp[1] + tmp[2] + tmp[3];
//        }
//        return key;
        UUIDHexGenerator generator = new UUIDHexGenerator();
        String[] key = new String[num];
        for (int i = 0; i < num; i++) {
            key[i]=generator.generate(null, null).toString();
        }
        return key;
    }

    /**
     * 从本系统sys_sequence表中取得sequence值同时更新sequence表
     *
     * @param tableName sequence名字
     * @return 返回主键
     */
    public static synchronized String getKeyId(String tableName) throws BkmsException {
        String key = null;
        Connection conn = null;
        Session s = null;
        try {
            SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
            s = sf.openSession();
            conn = s.connection();
            conn.setAutoCommit(false);
            Statement stat = conn.createStatement();
            String sql = "select SEQ_NAME,SEQ_LENGTH,SEQ_PREFIX,INIT_VALUE,MAX_VALUE,SEQ_STEP,CUR_VALUE,SEQ_SUFFIX from SYS_SEQUENCE where SEQ_NAME='" + tableName + "' for update";
            ResultSet rs = stat.executeQuery(sql);
            int length = 0;
            String prefix = null;
            int init = 100;
            int step = 1;
            int cur = 0;
            String suffix = null;
            boolean isExist = false;
            while (rs.next()) {
                isExist = true;
                length = rs.getInt("SEQ_LENGTH");
                prefix = rs.getString("SEQ_PREFIX");
                suffix = rs.getString("SEQ_SUFFIX");
                init = rs.getInt("INIT_VALUE");
                cur = rs.getInt("CUR_VALUE");
                step = rs.getInt("SEQ_STEP");
            }
            if (!isExist) {
                sql = "insert into SYS_SEQUENCE (SEQ_NAME,INIT_VALUE,SEQ_STEP) values('" + tableName + "'," + init + "," + 1 + ")";
                stat.execute(sql);
            }
            if (cur == 0) {
                cur = init;
            }
            if (step == 0) {
                step = 1;
            }
            if (prefix == null) {
                prefix = "";
            }
            if (suffix == null) {
                suffix = "";
            }
            cur = cur + step;
            key = buildValue(prefix, suffix, cur, length);
            rs.close();
            sql = "update SYS_SEQUENCE SET CUR_VALUE=" + cur + " where SEQ_NAME='" + tableName + "'";
            stat.executeUpdate(sql);

            stat.close();
            conn.commit();

            s.close();
            return key;
        } catch (Exception exc) {
            try {
                if (conn != null) {
                    conn.rollback();
                    s.close();
                }
            } catch (Exception se) {
                se.printStackTrace();
            }
            throw new BkmsException("生成序列号错误", exc, SequenceGenerator.class);
        }
    }

    /**
     * 从本系统sys_sequence表中取得sequence值同时更新sequence表
     *
     * @param tableName sequence名字
     * @param len       sequence长度
     * @return 返回主键
     */
    public static synchronized String getKeyIdByLen(String tableName, int len) throws BkmsException {
        String key = null;
        Connection conn = null;
        Session s = null;
        try {
            SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
            s = sf.openSession();
            conn = s.connection();
            Statement stat = conn.createStatement();
            String sql = "select SEQ_NAME,SEQ_LENGTH,SEQ_PREFIX,INIT_VALUE,MAX_VALUE,SEQ_STEP,CUR_VALUE,SEQ_SUFFIX from SYS_SEQUENCE where SEQ_NAME='" + tableName + "' for update";
            ResultSet rs = stat.executeQuery(sql);
            int length = len;
            String prefix = null;
            int init = 0;
            int step = 1;
            int cur = 0;
            String suffix = null;
            boolean isExist = false;
            while (rs.next()) {
                isExist = true;
                //  length = rs.getInt("SEQ_LENGTH");
                prefix = rs.getString("SEQ_PREFIX");
                suffix = rs.getString("SEQ_SUFFIX");
                init = rs.getInt("INIT_VALUE");
                cur = rs.getInt("CUR_VALUE");
                step = rs.getInt("SEQ_STEP");
            }
            if (!isExist) {
                sql = "insert into SYS_SEQUENCE (SEQ_NAME,INIT_VALUE,SEQ_STEP) values('" + tableName + "'," + init + "," + 1 + ")";
                stat.execute(sql);
            }
            if (cur == 0) {
                cur = init;
            }
            if (step == 0) {
                step = 1;
            }
            if (prefix == null) {
                prefix = "";
            }
            if (suffix == null) {
                suffix = "";
            }
            cur = cur + step;
            key = buildValue(prefix, suffix, cur, length);
            rs.close();
            sql = "update SYS_SEQUENCE SET CUR_VALUE=" + cur + " where SEQ_NAME='" + tableName + "'";
            stat.executeUpdate(sql);

            stat.close();
            conn.commit();

            s.close();
            return key;
        } catch (Exception exc) {
            try {
                if (conn != null) {
                    conn.rollback();
                    s.close();
                }
            } catch (Exception se) {
                se.printStackTrace();
            }
            throw new BkmsException("生成序列号错误", exc, SequenceGenerator.class);
        }
    }

    /**
     * 从本系统sys_sequence表中取得sequence值，指定sequence 初始化值 同时更新sequence表
     *
     * @param tableName sequence 名字
     * @param initValue sequence初始值
     * @return
     * @throws BkmsException
     */
    public static synchronized String getKeyIdByInit(String tableName, int initValue) throws BkmsException {
        String key = null;
        Connection conn = null;
        Session s = null;
        try {
            SessionFactory sf = (SessionFactory) BkmsContext.getBean("sessionFactory");
            s = sf.openSession();
            conn = s.connection();
            Statement stat = conn.createStatement();
            String sql = "select SEQ_NAME,SEQ_LENGTH,SEQ_PREFIX,INIT_VALUE,MAX_VALUE,SEQ_STEP,CUR_VALUE,SEQ_SUFFIX from SYS_SEQUENCE where SEQ_NAME='" + tableName + "' for update";
            ResultSet rs = stat.executeQuery(sql);
            int length = 0;
            String prefix = null;
            int init = initValue;
            int step = 1;
            int cur = 0;
            String suffix = null;
            boolean isExist = false;
            while (rs.next()) {
                isExist = true;
                length = rs.getInt("SEQ_LENGTH");
                prefix = rs.getString("SEQ_PREFIX");
                suffix = rs.getString("SEQ_SUFFIX");
                init = rs.getInt("INIT_VALUE");
                cur = rs.getInt("CUR_VALUE");
                step = rs.getInt("SEQ_STEP");
            }
            if (!isExist) {
                sql = "insert into SYS_SEQUENCE (SEQ_NAME,INIT_VALUE,SEQ_STEP) values('" + tableName + "'," + init + "," + 1 + ")";
                stat.execute(sql);
            }
            if (cur == 0) {
                cur = init;
            }
            if (step == 0) {
                step = 1;
            }
            if (prefix == null) {
                prefix = "";
            }
            if (suffix == null) {
                suffix = "";
            }
            cur = cur + step;
            key = buildValue(prefix, suffix, cur, length);
            rs.close();
            sql = "update SYS_SEQUENCE SET CUR_VALUE=" + cur + " where SEQ_NAME='" + tableName + "'";
            stat.executeUpdate(sql);

            stat.close();
            conn.commit();

            s.close();
            return key;
        } catch (Exception exc) {
            try {
                if (conn != null) {
                    conn.rollback();
                    s.close();
                }
            } catch (Exception se) {
                se.printStackTrace();
            }
            throw new BkmsException("生成序列号错误", exc, SequenceGenerator.class);
        }
    }

    /**
     * 从系统获取一组sequence值
     *
     * @param seqName sequence名字，
     * @param num     sequence数量
     * @return
     * @throws BkmsException
     */
    public static synchronized String[] getKeyId(String seqName, int num) throws BkmsException {
        Connection conn = null;
        String[] keyvalue = new String[num];
        Session s = null;
        try {
            s = ((SessionFactory) BkmsContext.getBean("sessionFactory")).openSession();
            conn = s.connection();
            conn.setAutoCommit(false);
            Statement stat = conn.createStatement();
            String sql = "select SEQ_NAME,SEQ_LENGTH,SEQ_PREFIX,INIT_VALUE,MAX_VALUE,SEQ_STEP,CUR_VALUE,SEQ_SUFFIX from SYS_SEQUENCE where SEQ_NAME='" + seqName + "' for update";
            ResultSet rs = stat.executeQuery(sql);
            int length = 0;
            String prefix = null;
            int init = 100;
            int step = 1;
            int cur = 0;
            String suffix = null;
            boolean isExist = false;
            while (rs.next()) {
                isExist = true;
                length = rs.getInt("SEQ_LENGTH");
                prefix = rs.getString("SEQ_PREFIX");
                suffix = rs.getString("SEQ_SUFFIX");
                init = rs.getInt("INIT_VALUE");
                cur = rs.getInt("CUR_VALUE");
                step = rs.getInt("SEQ_STEP");
            }
            if (!isExist) {
                sql = "insert into SYS_SEQUENCE (SEQ_NAME,INIT_VALUE,SEQ_STEP) values('" + seqName + "'," + init + "," + 1 + ")";
                stat.execute(sql);
            }
            if (cur == 0) {
                cur = init;
            }
            if (step == 0) {
                step = 1;
            }
            if (prefix == null) {
                prefix = "";
            }
            if (suffix == null) {
                suffix = "";
            }
            for (int i = 0; i < num; i++) {
                cur = cur + step;
                keyvalue[i] = buildValue(prefix, suffix, cur, length);
            }
            rs.close();
            sql = "update SYS_SEQUENCE SET CUR_VALUE=" + cur + " where SEQ_NAME='" + seqName + "'";
            stat.executeUpdate(sql);
            stat.close();
            conn.commit();
            s.close();
            return keyvalue;
        } catch (Exception exc) {
            try {
                if (conn != null) {
                    conn.rollback();
                    s.close();
                }
            } catch (Exception se) {
                se.printStackTrace();
            }
            throw new BkmsException("生成序列号错误", exc, SequenceGenerator.class);
        }
    }

    private static String buildValue(String prefix, String suffix, int vuale, int length) {
        String str = "";
        String tmpkey = vuale + "";
        String mask = "0000000000" + tmpkey;
        if (length > tmpkey.length()) {
            mask = mask.substring(mask.length() - length, mask.length());
        } else {
            mask = tmpkey;
        }
        str = prefix + mask + suffix;
        return str;
    }

    /**
     * 生成树型id值 。如 0101，010102
     *
     * @param table       数据库表名
     * @param treeField   treeid字段名
     * @param superTreeId 父层treeid值 。若无上级节点值，可以输入null值或者空值。
     * @param levelLength 每一层长度。
     * @param step        步长
     * @return 返回treeId
     */
    public static synchronized String getTreeId(String table, String treeField, String superTreeId, int levelLength, int step) throws BkmsException {
        return getTreeId(table, treeField, superTreeId, levelLength, step, null);
    }

    /**
     * 生成树型id值 。如 0101，010102
     *
     * @param table       数据库表名
     * @param treeField   treeid字段名
     * @param superTreeId 父层treeid值 。若无上级节点值，可以输入null值或者空值。
     * @param levelLength 每一层长度。
     * @param step        步长
     * @param where       查询where条件
     * @return 返回treeId
     */
    public static synchronized String getTreeId(String table, String treeField, String superTreeId, int levelLength, int step, String where) throws BkmsException {
        String key;
        Connection conn;
        Session s = null;
        try {
            s = ((SessionFactory) BkmsContext.getBean("sessionFactory")).openSession();
            conn = s.connection();
            Statement stat = conn.createStatement();
            String ulimit = "9999999999";
            ulimit = ulimit.substring(0, levelLength);
            StringBuffer sql = new StringBuffer();
            sql.append("select ").append(treeField).append(" from ").append(table);
            if (superTreeId == null || "".equals(superTreeId) || "-1".equals(superTreeId)) {
                superTreeId = "";
                sql.append(" where ")
                        .append(treeField)
                        .append(" between ")
                        .append(0).append(" and ").append(ulimit);
            } else {
                String sre = "+0";
                try {
                    Long.parseLong(superTreeId);
                } catch (Exception e) {
                    sre = "";
                }
                sql.append(" where ");
                sql.append(treeField);
                sql.append(" like '");
                sql.append(superTreeId);
                sql.append("%' and  ");
                sql.append(treeField);
                sql.append(" between '");
                sql.append(superTreeId);
                sql.append("'").append(sre).append(" and '");
                sql.append(superTreeId).append(ulimit);
                sql.append("'").append(sre).append("");
            }
            if (where != null && !"".equals(where)) {
                sql.append(" and ").append(where);
            }
            sql.append(" order by ").append(treeField).append(" desc");
            sql.append(" for update");
            ResultSet rs = stat.executeQuery(sql.toString());
            String cur = "0";
//            while (rs.next()) {
            if (rs.next()) {
                cur = rs.getString(1);
            }
            if (cur == null) {
                cur = "0";
            }
            if ((superTreeId + ulimit).equals(cur)) {
                throw new BkmsException("生成treeid序列号错误,超出有效范围", SequenceGenerator.class);
            }
            if ("0".equals(cur)) {
                String mask = "0000000000" + step;
                cur = superTreeId + mask.substring(mask.length() - levelLength, mask.length());
            } else {
                int size = cur.length();
                String mask = "0000000000" + (Integer.parseInt(cur.substring(size - levelLength, size)) + step);
                String tmp = mask.substring(mask.length() - levelLength, mask.length());
                cur = superTreeId + tmp;
            }
            key = cur;
            rs.close();
            stat.close();
            conn.commit();
            s.close();
            return key;
        } catch (Exception exc) {
            try {
                s.close();
            } catch (Exception se) {
                se.printStackTrace();
            }
            throw new BkmsException("生成treeid序列号错误", exc, SequenceGenerator.class);
        }
    }
}
