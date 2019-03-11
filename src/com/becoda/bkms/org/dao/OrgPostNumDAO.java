package com.becoda.bkms.org.dao;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;

import java.util.List;


public class OrgPostNumDAO extends GenericDAO {
   

    /**
     * @param orgTreeId
     * @param cancel    ""或者null 过虑逻辑删除机构
     * @return
     * @throws RollbackableException
     */
    public List queryOrgPostNum(String orgTreeId, String cancel) throws RollbackableException {
        try {
            if (orgTreeId == null || "".equals(orgTreeId)) return null;
            //机构编码、机构编码、岗位编码、岗位编制、超编人数、缺编人数、实有人数（计算）、实有人数（岗位子集）、人员姓名
            String hql = "select ID,A001701,A001705,A001715,C001735,C001209,C001210,NUM,C001200,A001001 from " +
                    "(select id,A001001,A001701,A001705,A001715,count(id) as num from A001 where (A001725='014511' or A001725='014515') and A001728 like '" + orgTreeId + "%' " +
                    "or A001738 like '" + orgTreeId + "%' group by A001701,A001705,A001715,A001001,id) aa left join " +
                    "(select b01.id orguid,B001730,B001715,c01.id postid,C001005,C001735,C001209,C001210,C001200 from B001 b01 " +
                    "inner join C001 c01 on b01.id = c01.C001010 where B001003 like '" + orgTreeId + "%') bb " +
                    "on aa.a001715 = bb.postid where (bb.orguid = aa.a001701 or bb.orguid = aa.a001705)";
            if ("".equals(cancel)) {
                hql = " and bb.B001730 <> '" + Constants.YES + "' ";
            }
            hql = hql + " order by bb.B001715";
            return jdbcTemplate.queryForList(hql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    /**
     * 查询人员的能力素质情况
     *
     * @param postId
     * @param year
     * @return
     * @throws RollbackableException e
     */
    public List queryPerAbility(String postId, String year) throws RollbackableException {
        String sql = "select A001ID as id,a.id as C017ID,A293203,C017203,C017203-A293203 as differ from " +
                "(select ID," +
                " sum(case when C017203=10701 then 1 when C017203=10702 then 1.5 when C017203=10703 then 2 when C017203=10704 then 2.5 " +
                " when C017203=10705 then 3 when C017203=10706 then 3.5 when C017203=10707 then 4 when C017203=10708 then 4.5 " +
                " when C017203=10709 then 5 end) C017203 " +
                " from C017 where ID = '" + postId + "' group by ID) a, " +
                "(select c.A001ID,c.C017ID," +
                "sum(case when c.A293203=10701 then 1 when c.A293203=10702 then 1.5 when c.A293203=10703 then 2 when c.A293203=10704 then 2.5 " +
                "when c.A293203=10705 then 3 when c.A293203=10706 then 3.5 when c.A293203=10707 then 4 when c.A293203=10708 then 4.5 " +
                "when c.A293203=10709 then 5 end) A293203 " +
                "from " +
                "  (select A001.ID as A001ID,C017.ID as C017ID," +
                "    (case when A293203>=C017203 then C017203 when A293203<C017203 then A293203 end) A293203 " +
                "    from A293,A001,C017 " +
                "    where A293200='" + year + "' and C017.ID = '" + postId + "' and A001.ID = A293.ID and A293201=C017202)c " +
                "group by c.A001ID,c.C017ID) b " +
                "where a.id=b.C017ID order by A293203 desc,differ";

//        String sql = "select id,a.postid,A293203,C017203,C017203-A293203 as differ from" +
//                " (select POSTID,sum(case when C017203=10701 then 1 when C017203=10702 then 1.5 when C017203=10703 then 2 when C017203=10704 then 2.5" +
//                " when C017203=10705 then 3 when C017203=10706 then 3.5 when C017203=10707 then 4 when C017203=10708 then 4.5" +
//                " when C017203=10709 then 5 end) C017203" +
//                " from C017 where POSTID = '" + postId + "' group by POSTID) a," +
//                " (select c.ID,c.POSTID,sum(case when c.A293203=10701 then 1 when c.A293203=10702 then 1.5 when c.A293203=10703 then 2 when c.A293203=10704 then 2.5" +
//                " when c.A293203=10705 then 3 when c.A293203=10706 then 3.5 when c.A293203=10707 then 4 when c.A293203=10708 then 4.5" +
//                " when c.A293203=10709 then 5 end) A293203" +
//                " from (select A001.ID,POSTID,case when A293203>=C017203 then C017203 when A293203<C017203 then A293203 end A293203" +
//                " from A293,A001,C017 where A293200='" + year + "' and POSTID = '" + postId + "'" +
//                " and A001.ID = A293.ID and A293201=C017202)c group by c.ID,c.POSTID) b" +
//                " where a.POSTID=b.POSTID order by A293203 desc,differ";

//        String sql = "SELECT ID,ROUND(SUM(score),1) score " +
//                "FROM (" +
//                "SELECT ID,CASE WHEN C017200='3105400300' THEN 0.6 WHEN C017200='3105400301' THEN 0.25 WHEN C017200='3105400302' THEN 0.15 END * AVG(score) score FROM (" +
//                "SELECT ID,C017202,C017200 , CASE WHEN A293203>=C017203 THEN 5 WHEN A293203<C017203 THEN 2+(C017203-2)*A293203/C017203 ELSE 0 END score FROM (" +
//                "SELECT A293.ID,C017202,C017200," +
//                "CASE WHEN A293203=10701 THEN 1 WHEN A293203=10702 THEN 1.5 WHEN A293203=10703 THEN 2 WHEN A293203=10704 THEN 2.5 WHEN A293203=10705 THEN 3 WHEN A293203=10706 THEN 3.5 WHEN A293203=10707 THEN 4 WHEN  A293203=10708 THEN 4.5 WHEN  A293203=10709 THEN 5 END A293203," +
//                "CASE WHEN C017203=10701 THEN 1 WHEN C017203=10702 THEN 1.5 WHEN C017203=10703 THEN 2 WHEN C017203=10704 THEN 2.5 WHEN C017203=10705 THEN 3 WHEN C017203=10706 THEN 3.5 WHEN C017203=10707 THEN 4 WHEN  C017203=10708 THEN 4.5 WHEN  C017203=10709 THEN 5 END C017203 " +
//                "FROM C017 LEFT JOIN A293 ON  A293201=C017202 WHERE A293000 ='00901' AND  C017.postId = '" + postId + "' --AND ID IN (SELECT ID FROM A001 WHERE A001728 LIKE '001000%')" +
//                ") subTable)subTable2 GROUP BY ID,C017200  ) subTable3 GROUP BY ID ORDER BY score DESC";
        return jdbcTemplate.queryForList(sql);
//        List list = new ArrayList();
//        Statement stat = null;
//        ResultSet rs = null;
//        try {
//            Connection conn = s.connection();
//            stat = conn.createStatement();
//            rs = stat.executeQuery(sql);
//            while (rs.next()) {
//                Hashtable ht = new Hashtable();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    ht.put(rs.getMetaData().getColumnName(i), rs.getString(i));
//                }
//                list.add(ht);
//            }
//            rs.close();
//            stat.close();
//            return list;
//        } catch (Exception e) {
//            throw new RollbackableException("查询失败", e, this.getClass());
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (stat != null) stat.close();
//            } catch (SQLException e) {
//                throw new RollbackableException("Statement关闭错误", e, this.getClass());
//            }
//        }
    }

    /**
     * 查询人员的能力素质情况
     *
     * @param postId
     * @param year
     * @return
     * @throws RollbackableException
     */
    public List queryPerAbilityFromZP(String postId, String year) throws RollbackableException {

        String sql = "SELECT  ID,ROUND(SUM(score),1)+0 score " +
                "FROM (" +
                "SELECT ID,CASE WHEN C017200='3105400300' THEN 0.6 WHEN C017200='3105400301' THEN 0.25 WHEN C017200='3105400302' THEN 0.15 END * AVG(score) score FROM (" +
                "SELECT ID,C017202,C017200 , CASE WHEN A293203>=C017203 THEN 5 WHEN A293203<C017203 THEN 2+(C017203-2)*A293203/C017203 ELSE 0 END score FROM (" +
                "SELECT Z293.ID,C017202,C017200," +
                "CASE WHEN A293203=10701 THEN 1 WHEN A293203=10702 THEN 1.5 WHEN A293203=10703 THEN 2 WHEN A293203=10704 THEN 2.5 WHEN A293203=10705 THEN 3 WHEN A293203=10706 THEN 3.5 WHEN A293203=10707 THEN 4 WHEN  A293203=10708 THEN 4.5 WHEN  A293203=10709 THEN 5 END A293203," +
                "CASE WHEN C017203=10701 THEN 1 WHEN C017203=10702 THEN 1.5 WHEN C017203=10703 THEN 2 WHEN C017203=10704 THEN 2.5 WHEN C017203=10705 THEN 3 WHEN C017203=10706 THEN 3.5 WHEN C017203=10707 THEN 4 WHEN  C017203=10708 THEN 4.5 WHEN  C017203=10709 THEN 5 END C017203 " +
                "FROM C017 LEFT JOIN Z293 ON  A293201=C017202 WHERE A293000 ='00901' AND  C017.postId = '" + postId + "' --AND ID IN (SELECT ID FROM A001 WHERE A001728 LIKE '001000%')" +
                ") subTable)subTable2 GROUP BY ID,C017200  ) subTable3 GROUP BY ID ORDER BY score DESC ";
        return jdbcTemplate.queryForList(sql);
//        List list = new ArrayList();
//        Statement stat = null;
//        ResultSet rs = null;
//        try {
//            Connection conn = s.connection();
//            stat = conn.createStatement();
//            rs = stat.executeQuery(sql);
//            while (rs.next()) {
//                Hashtable ht = new Hashtable();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    ht.put(rs.getMetaData().getColumnName(i), rs.getString(i));
//                }
//                list.add(ht);
//            }
//            rs.close();
//            stat.close();
//            return list;
//        } catch (Exception e) {
//            throw new RollbackableException("查询失败", e, this.getClass());
//        } finally {
//            try {
//                if (rs != null) rs.close();
//                if (stat != null) stat.close();
//            } catch (SQLException e) {
//                throw new RollbackableException("Statement关闭错误", e, this.getClass());
//            }
//        }
    }

    /**
     * 查询人员情况
     *
     * @param pId
     * @return
     * @throws RollbackableException
     */
    public List findPersonInfoFromZP(String pId, String setId) throws RollbackableException {

        String sql = "select * from " + setId + " where id='" + pId + "'";
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }
}
