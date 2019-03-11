package com.becoda.bkms.emp.dao;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
//import com.becoda.bkms.emp.pojo.bo.LabourBotherBO;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.util.Tools;

import java.util.List;


public class PersonDAO extends GenericDAO {

    /**
     * 根据人员id查询人员对象
     *
     * @param perId per id
     * @return PersonBO
     * @throws RollbackableException e
     */
    public PersonBO findPerson(String perId) throws RollbackableException {
        try {
            return (PersonBO) hibernateTemplate.get(PersonBO.class, perId);
        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, PersonDAO.class);
        }
    }

    //根据人员姓名和身份证来确定一个人
    public List findPerson(String personName, String idCard) throws RollbackableException {
        try {
            List list;
            if (idCard.length() == 18) {
                String idCard_15 = idCard.substring(0, 6) + idCard.substring(8, 17);//转化为15位
                list = hibernateTemplate.find(" from PersonBO p  where p.name like '" + personName + "' and (p.idCard like '" + idCard + "' or p.idCard like '" + idCard_15 + "')");
            } else {
                list = hibernateTemplate.find(" from PersonBO p  where p.name like '" + personName + "' and p.idCard like '" + idCard + "'");
            }
            if (null == list || list.size() == 0)
                return null;
            else
                return list;
        } catch (Exception e) {
            throw new RollbackableException("检索失败错误", e, this.getClass());
        }
    }

    /**
     * 根据人员编号查询人员对象
     *
     * @param perCode 人员编号
     * @return PersonBO
     * @throws RollbackableException e
     */
    public PersonBO findPersonByCode(String perCode) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("select p from PersonBO p where p.personCode='" + perCode + "'");
            if (list == null || list.size() == 0) {
                return null;
            } else {
                hibernateTemplate.evict(list.get(0));
                return (PersonBO) list.get(0);
            }

        } catch (Exception e) {
            throw new RollbackableException("查询人员失败", e, PersonDAO.class);
        }
    }


    /**
     * 根据查询条件查询人员
     *
     * @param addSql addsql(where)
     * @return 人员对象数组
     * @throws RollbackableException e
     */
    public PersonBO[] queryPerson(String addSql) throws RollbackableException {
        try {
            List lcount = hibernateTemplate.find("select count(p) from PersonBO p where 1=1 " + addSql);
            if (lcount != null) {
                /*    Integer count = (Integer) lcount.get(0);
                if (count.intValue() > 500) {
                    throw new RollbackableException("记录数超过500,请重新设置条件!", this.getClass());
                }*/
                List list = hibernateTemplate.find("from PersonBO p where 1=1 " + addSql);
                if (list == null) {
                    return null;
                }
                PersonBO[] pb = new PersonBO[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    pb[i] = (PersonBO) list.get(i);
                }
                return pb;
            }
            return null;
        } catch (Exception e) {
            throw new RollbackableException(e.getMessage(), e, PersonDAO.class);
        }

    }


    public int queryResultCount(String addSql) throws RollbackableException {
        try {
            return queryForIntByHql("select count(p) from PersonBO p where 1=1 " + addSql);
        } catch (Exception e) {
            throw new RollbackableException(e.getMessage(), e, PersonDAO.class);
        }
    }

    public PersonBO[] queryAllPerson(String sql) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from PersonBO p where 1=1 " + sql);
            if (list == null) {
                return null;
            }
            PersonBO[] pb = new PersonBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                hibernateTemplate.evict(list.get(i));
                pb[i] = (PersonBO) list.get(i);
            }
            return pb;
        }
        catch (Exception e) {
            throw new RollbackableException("查询失败", e, PersonDAO.class);
        }

    }


    /**
     * 根据条件查询人数
     *
     * @param addSql addsql(where)
     * @return num
     * @throws RollbackableException e
     */
    public int queryPersonCount(String addSql) throws RollbackableException {
        try {
            String hql = "select count(p) from PersonBO p where 1=1 " + addSql;
            return queryForIntByHql(hql);
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, PersonDAO.class);
        }
    }

    /**
     * 查询某个机构下的人员
     *
     * @param superId   dept or orgid
     * @param cacelFlag cacelFlag
     * @return PersonBO[]
     * @throws RollbackableException e
     */
    public PersonBO[] queryPersonBySuper(String superId, String cacelFlag) throws RollbackableException {
        try {
            // List list = super.find("from PersonBO p where p.deptId ='" + superId + "' and p.personCancel = '" + cacelFlag + "' and p.partyCancel = '00900' order by p.sort");
            List list = hibernateTemplate.find("from PersonBO p where p.deptId ='" + superId + "' and p.personCancel = '" + cacelFlag + "'  order by p.sort");
            if (list == null) {
                return null;
            }
            PersonBO[] pb = new PersonBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                hibernateTemplate.evict(list.get(i));
                pb[i] = (PersonBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, PersonDAO.class);
        }
    }

    /**
     * 查询岗位下的人员
     *
     * @param postId     岗位ID
     * @param cancelFlag cancelFlag
     * @return PersonBO[]
     * @throws RollbackableException e
     */
    public PersonBO[] queryPersonByPost(String postId, String cancelFlag) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from PersonBO p where p.postId ='" + postId + "' and p.personCancel = '" + cancelFlag + "' order by p.sort+0");
            if (list == null) {
                return null;
            }
            PersonBO[] pb = new PersonBO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (PersonBO) list.get(i);
            }
            return pb;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, PersonDAO.class);
        }
    }

    //根据拼音姓名查询
    public List queryPersonByNameSpell(String spell) throws RollbackableException {
        try {
            return hibernateTemplate.find("from PersonBO p where p.nameSpell ='" + spell + "' order by p.sort+0");
        }
        catch (Exception e) {
            throw new RollbackableException("查询失败", e, PersonDAO.class);
        }
    }

    public List queryAllPersonByOrg(String orgId) throws RollbackableException {
        try {
            Org bo = SysCacheTool.findOrgById(orgId);
            if (bo != null) {
                return hibernateTemplate.find("from PersonBO p where p.personCancel = '" + Constants.NO + "' and p.retireCancel = '" + Constants.NO + "' and p.deptTreeId like '" + bo.getTreeId() + "%'");
            } else {
                return null;
            }
        }
        catch (Exception e) {
            throw new RollbackableException("查询失败", e, PersonDAO.class);
        }

    }

    /**
     * 查询机构下所有中层人员
     *
     * @param orgId orgId
     * @return List<PersonBO>
     * @throws RollbackableException e
     */
    public List queryMiddleLevelPersons(String orgId) throws RollbackableException {
        Org bo = SysCacheTool.findOrgById(orgId);
        if (bo != null) {
            StringBuffer hql = new StringBuffer(" from PersonBO p ");
            hql.append(" where p.personCancel ='").append(Constants.NO);
            hql.append("' and p.retireCancel ='").append(Constants.NO);
            hql.append("' and p.deptTreeId like '").append(bo.getTreeId()).append("%'");
            hql.append(" and(p.postLevel='016508' or p.postLevel='016509' or p.postLevel='016510')");
            return hibernateTemplate.find(hql.toString());
        } else {
            return null;
        }
    }


    /**
     * 查询机构或部门下的合同工的人数
     *
     * @param orgId 机构
     * @return person num
     * @throws RollbackableException e
     */
    public int queryContractPerson(String orgId) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from OrgBO b where b.orgId='" + orgId + "'");
            if (list != null && list.size() > 0) {
                OrgBO b = (OrgBO) list.get(0);
                String treeId = b.getTreeId();
                String hql = "select count(p) from PersonBO p where p.personType='013511' and p.deptTreeId like '" + treeId + "%'";
                List list1 = hibernateTemplate.find(hql);
                return Tools.parseInt(list1.get(0));
            }
            return 0;
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    // -----------------劳动纠纷-------------------------
//    public void addLabourBother(LabourBotherBO bo) throws RollbackableException {
//        try {
//            this.createBo(bo);
//        } catch (Exception e) {
//            throw new RollbackableException("添加失败", e, this.getClass());
//        }
//    }


    public List queryLabourBother(PageVO vo, String year, String name, String botherType) throws RollbackableException {
        try {
            StringBuffer countHql = new StringBuffer("Select count(l) from LabourBotherBO l  where 1=1");
            StringBuffer queryHql = new StringBuffer("select l from LabourBotherBO l  where 1=1");
//            String countHql = "Select count(l) from LabourBotherBO l  where 1=1";
//            String queryHql = "select l from LabourBotherBO  where 1=1";
            if (year != null && !"".equals(year)) {
                countHql.append(" and l.year='").append(year).append("'");
                queryHql.append(" and l.year='").append(year).append("'");
            }
            if (name != null && !"".equals(name)) {
                countHql.append(" and l.people like '%").append(name).append("%'");
                queryHql.append(" and l.people like '%").append(name).append("%'");
            }
            if (botherType != null && !"".equals(botherType)) {
                countHql.append(" and l.botherType = '").append(botherType).append("'");
                queryHql.append(" and l.botherType = '").append(botherType).append("'");
            }
            countHql.append(" order by l.startTime");
            queryHql.append(" order by l.startTime");
            return this.pageQuery(vo, countHql.toString(), queryHql.toString());
//          return  this.find("select l from LeaderMatterBO l,PersonBO p where l.year='"+year+"' and l.perId=p.personId order by p.deptSort+0,p.sort+0");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    //******************************************************end  劳动纠纷*******************************************/

    /**
     * 根据机构的treeId 查询机构下(包括机构下的所有机构和部门)的所有党员和预备党员
     *
     * @param orgTreeId 机构的treeId
     * @return List<PersonBO>
     * @throws RollbackableException e
     */

    public List queryPartyMembersByOrgTreeId(String orgTreeId) throws RollbackableException {
        try {
            return hibernateTemplate.find("from  PersonBO p where p.deptTreeId like '" + orgTreeId + "%' and (p.partyFigure='012001' or p.partyFigure='012002' or p.partyFigure2='012001' or p.partyFigure2='012002')");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    /**
     * 根据机构的treeId 查询机构下(包括机构下的所有机构和部门)的所有团员
     *
     * @param orgTreeId 机构的treeId
     * @return List<PersonBO>
     * @throws RollbackableException e
     */
    public List queryGroupMembersByOrgTreeId(String orgTreeId) throws RollbackableException {
        try {
            return hibernateTemplate.find("from PersonBO p where p.deptTreeId like '" + orgTreeId + "%' and (p.partyFigure='012003' or p.partyFigure2='012003')");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    /**
     * 根据机构的treeId 查询机构下(包括机构下的所有机构和部门)的所有团员
     *
     * @param orgTreeId 机构的treeId
     * @return List<PersonBO>
     * @throws RollbackableException e
     */

    public List queryLabourMembersByOrgTreeId(String orgTreeId) throws RollbackableException {
        try {
            return hibernateTemplate.find("from PersonBO p where p.deptTreeId like '" + orgTreeId + "%' and p.labourMember='" + Constants.YES + "'");
        } catch (Exception e) {
            throw new RollbackableException("查询失败", e, this.getClass());
        }
    }

    //每天定时计算年龄
    public void computeAge() throws RollbackableException {
        try {
            String sql = "update A001 set A001205=floor(months_between(sysdate,to_date(A001011,'YYYY-MM-DD'))/12) ";
//            where  (A001725<>0145000281 or A001725<>014515)
            jdbcTemplate.execute(sql);
        }
        catch (Exception e) {
            throw new RollbackableException("更新失败", e, this.getClass());
        }
    }

    //每天定时计算工龄                                                        n
    public void computeWorkYears() throws RollbackableException {
        try {
            // String sql = "update A701 b set b.A701705=(select floor(months_between(sysdate,to_date(a.A001041,'YYYY-MM'))/12) from A001 a  where a.ID=b.ID and a.A001725<>0145000281 and a.A001725<>014515) " +
            //          " where  exists  (select  1  from  A001  c  where   c.ID=b.ID and c.A001725<>0145000281 and c.A001725<>014515) ";
            String sql = " update A701 b set b.A701705 = (select  to_char(sysdate, 'yyyy')- to_char(to_date(A001041,'yyyy-mm'), 'yyyy')+1 from A001 a where a.ID = b.ID  and a.A001725 <>'0145000281'  and a.A001725 <> '014515'  and a.A001041 is not null )" +
                    "  where  exists  (select  1  from  A001  c  where   c.ID=b.ID and c.A001725<>'0145000281' and c.A001725<>'014515' and c.A001041 is not null)";
            jdbcTemplate.execute(sql);
            String sql2 = "update A701 set A701.A701705=(A701705-A701710)  where A701705+0>=A701710+0";
            jdbcTemplate.execute(sql2);
        }
        catch (Exception e) {
            throw new RollbackableException("更新失败", e, this.getClass());
        }
    }


    //通过工龄计算工龄区间段
    public void computeWorkYearsSect() throws RollbackableException {
        try {
            String[] sql = new String[20];
            //工龄区间（5年制）
            sql[0] = "update A202  set A202.A202211='306111'   where  (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=5";
            sql[1] = "update A202  set A202.A202211='306112'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>5  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=10)";
            sql[2] = "update A202  set A202.A202211='306113'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>10  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=15)";
            sql[3] = "update A202  set A202.A202211='306114'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>15  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=20)";
            sql[4] = "update A202  set A202.A202211='306115'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>20  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=25)";
            sql[5] = "update A202  set A202.A202211='306116'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>25  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=30)";
            sql[6] = "update A202  set A202.A202211='306117'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>30  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<=35)";
            sql[7] = "update A202  set A202.A202211='306118'   where  (select  A701.A701705   from  A701  where  A202.id=A701.id)+0>35";
            //工龄区间（10年制）
            sql[8] = "update A202  set A202.A202212='3060000486'   where  (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<10";
            sql[9] = "update A202  set A202.A202212='3060000487'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=10 and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<20)";
            sql[10] = "update A202  set A202.A202212='3060000488'   where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=20 and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<30)";
            sql[11] = "update A202  set A202.A202212='3060000489'   where  (select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=30";
            //工龄区间(内退)
            sql[12] = "update A202  set A202.A202214='3087000820' where  (select  A701.A701705  from  A701  where  A202.id=A701.id)+0<5";
            sql[13] = "update A202  set A202.A202214='3087000821'  where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=5  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<10)";
            sql[14] = "update A202  set A202.A202214='3087000822'  where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=10  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<15)";
            sql[15] = "update A202  set A202.A202214='3087000823'  where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=15  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<20)";
            sql[16] = "update A202  set A202.A202214='3087000824'  where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=20  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<25)";
            sql[17] = "update A202  set A202.A202214='3087000825'  where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=25  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<30)";
            sql[18] = "update A202  set A202.A202214='3087000826'  where  ((select  A701.A701705   from  A701  where  A202.id=A701.id)+0>=30  and (select  A701.A701705   from  A701  where  A202.id=A701.id)+0<35)";
            sql[19] = "update A202  set A202.A202214='3087000827' where  (select  A701.A701705  from  A701  where  A202.id=A701.id)+0>=35";
            jdbcTemplate.batchUpdate(sql);
        }
        catch (Exception e) {
            throw new RollbackableException("更新失败", e, this.getClass());
        }
    }

    

    

    

    //每天定时计算岗位的实有人数,超编,缺编等
    public void computerPerInPost() throws RollbackableException {
        try {
            String sql = "update C001 c set C001200=( select count(a.id) from A001 a where a.A001715=c.postid) ";
            jdbcTemplate.execute(sql);
            String sql2 = "update C001 c set c.C001209=(C001200-C001735)  where C001200+0>=C001735+0";
            jdbcTemplate.execute(sql2);
            String sql3 = "update C001 c set c.C001210=(C001735-C001200)  where C001735+0>=C001200+0";
            jdbcTemplate.execute(sql3);
        }
        catch (Exception e) {
            throw new RollbackableException("更新失败", e, this.getClass());
        }

    }




    public void deleteBo(Class clazz, String pk) throws RollbackableException {
        Object o = hibernateTemplate.get(clazz, pk);
        if (o != null) {
            hibernateTemplate.delete(o);
        }
    }

    public void updateBo(Object bo) {
        hibernateTemplate.update(bo);
    }

    private int queryForIntByHql(String hql) {
        List list = hibernateTemplate.find(hql);
        if (list != null && list.size() > 0) {
            return Integer.parseInt(list.get(0).toString());
        }
        return 0;
    }

}
