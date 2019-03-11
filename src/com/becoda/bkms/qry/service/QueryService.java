package com.becoda.bkms.qry.service;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.qry.QryConstants;
import com.becoda.bkms.qry.dao.QueryDAO;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.bo.QueryConditionBO;
import com.becoda.bkms.qry.pojo.bo.QueryItemBO;
import com.becoda.bkms.qry.pojo.bo.QueryStaticBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticVO;
import com.becoda.bkms.util.CodeUtil;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Hashtable;
import java.util.List;

public class QueryService {
    private StaticService staticservice;
    private ConditionItemService conditionitemservice;
    private QueryItemService queryitemservice;
    private QueryDAO querydao;
    private JdbcTemplate jdbctemplate;

    /**
     * 获取一个hashtable里面保存sql语句
     *
     * @param qryId
     * @return
     * @throws BkmsException
     */
    public Hashtable findSQL(String qryId) throws BkmsException {
        Hashtable ret = null;
        try {
            QueryBO bo = (QueryBO) querydao.findBoById(QueryBO.class, qryId);
            if (bo != null) {
                ret = new Hashtable();
                ret.put(QryConstants.QRYSQL_showField, bo.getSelectStr());
                ret.put(QryConstants.QRYSQL_from, bo.getFromStr());
                ret.put(QryConstants.QRYSQL_where, StringUtils.trimToEmpty(bo.getWhereStr()));
                ret.put(QryConstants.QRYSQL_order, StringUtils.trimToEmpty(bo.getOrderStr()));
                String all = "select " + bo.getSelectStr() + " from " + bo.getFromStr()
                        + (null == bo.getWhereStr() ? "" : " where " + bo.getWhereStr())
                        + (null == bo.getOrderStr() ? "" : " order by " + bo.getOrderStr());
                ret.put(QryConstants.QRYSQL_qrySql, all);
            }
        } catch (Exception e) {
            throw new RollbackableException("获取sql语句出错", e, getClass());
        }
        return ret;
    }

    /**
     * 根据qryId查询vo.vo中需要包含各个bo,需要调用其他service方法查询
     *
     * @param qryId
     * @return QueryVO
     */
    public QueryVO findQueryVO(String qryId) throws BkmsException {
        if (qryId == null || "".equals(qryId))
            return null;
        try {
            QueryVO vo = new QueryVO();
            QueryBO bo = (QueryBO) querydao.findBoById(QueryBO.class, qryId);
            if (bo == null)
                throw new RollbackableException("查找不到QueryBO", null, getClass());
            Tools.copyProperties(vo, bo);
            QueryItemBO[] ibos = queryitemservice.queryQueryItem(qryId);
            if (ibos == null)
                throw new RollbackableException("", null, getClass());
            vo.setItem(ibos);
            StaticVO[] svos = staticservice.queryStaticVO(qryId);
            vo.setStatics(svos);
            return vo;
        } catch (Exception e) {
            throw new RollbackableException("", e, getClass());
        }
    }

    public QueryVO findQryWageVO(String qryId) throws BkmsException {
        if (qryId == null || "".equals(qryId))
            return null;
        try {
            QueryVO vo = new QueryVO();
            QueryBO bo = (QueryBO) querydao.findBoById(QueryBO.class, qryId);
            if (bo == null)
                throw new RollbackableException("", null, getClass());
            Tools.copyProperties(vo, bo);
            StaticVO[] svos = staticservice.queryStaticVO(qryId);
            vo.setStatics(svos);
            return vo;
        } catch (RollbackableException e) {
            throw e;
        } catch (Exception e) {
            throw new RollbackableException("", e, getClass());
        }
    }

    /**
     * 根据classId查询一组querybo.
     *
     * @param classId
     * @return QueryBO[]
     */
    public QueryBO[] queryQuery(String classId) throws BkmsException {
        return querydao.queryQueryByClassId(classId);
    }

    /**
     * 根据qryId删除一个查询.需要调用其他bo的删除方法.删除其他bo的数据
     *
     * @param qryId
     */
    public void deleteQuery(String qryId) throws BkmsException {
        try {
            if (qryId == null)
                return;
            QueryStaticBO[] sbos = staticservice.queryStatic(qryId);
            if (sbos != null && sbos.length > 0) {
                for (int i = 0; i < sbos.length; i++) {
                    conditionitemservice.deleteCondition(sbos[i].getStaticId());
                }
                staticservice.deleteStatic(qryId);
            }
            queryitemservice.deleteQueryItem(qryId);
            querydao.deleteBo(querydao.findBoById(QueryBO.class, qryId));
            /******** add by wanglijun 2015-7-26 ***********/
        } catch (Exception e) {
            throw new RollbackableException("无法删除查询定义", e, getClass());
        }
    }

    /**
     * 根据vo保存一个查询 ,需要调用其他service方法，将vo分解为多个bo并申请主键保存
     *
     * @param vo
     * @return String
     */
    public QueryVO createQuery(QueryVO vo) throws BkmsException {
        if (vo == null)
            return null;
        try {
            QueryBO querybo = new QueryBO();
            Tools.copyProperties(querybo, vo);
            String qryId = querybo.getQryId();
            if (querybo.getClassId() == null || "".equals(querybo.getClassId()))
                throw new RollbackableException("没有类别id无法保存查询", null, getClass());

            if ("".equals(Tools.filterNull(querybo.getQryId()))) {
                qryId = SequenceGenerator.getKeyId("qry_query");
                querybo.setQryId(qryId);
                querydao.createBo(querybo);
            } else {
                querydao.getHibernateTemplate().merge(querybo);
            }
            vo.setQryId(querybo.getQryId());
            qryId = querybo.getQryId();
            //保存显示项目
            QueryItemBO[] itembos = vo.getItem();
            if (itembos == null || itembos.length <= 0)
                throw new RollbackableException("显示项目为空", null, getClass());

            queryitemservice.createQueryItem(itembos, qryId);
            //保存统计项目
            StaticVO[] svo = vo.getStatics();
            if (svo != null)
                staticservice.createStatic(svo, qryId);
            Hashtable sqlHash = this.getQuerySqlHash(null, vo);
//            sqlHash.get(QryConstants.SQL_SCALE_PART);
//            sqlHash.get(QryConstants.SQL_FULL);
            querybo.setSelectStr((String) sqlHash.get(QryConstants.SQL_SELECT_PART));
            querybo.setFromStr((String) sqlHash.get(QryConstants.SQL_FROM_PART));
            querybo.setWhereStr((String) sqlHash.get(QryConstants.SQL_WHERE_PART));
            querybo.setOrderStr((String) sqlHash.get(QryConstants.SQL_ORDER_PART));
            querydao.getHibernateTemplate().merge(querybo);
        } catch (Exception e) {
            throw new RollbackableException("保存查询条件错误", e, getClass());
        }
        return vo;

    }

    public QueryVO createStatic(QueryVO vo) throws BkmsException {
        if (vo == null)
            return null;
        try {
            QueryBO querybo = new QueryBO();
            Tools.copyProperties(querybo, vo);
            String qryId = querybo.getQryId();
            if (querybo.getClassId() == null || "".equals(querybo.getClassId()))
                throw new RollbackableException("没有类别id无法保存查询", null, getClass());

            if ("".equals(Tools.filterNull(querybo.getQryId()))) {
                qryId = SequenceGenerator.getKeyId("qry_query");
                querybo.setQryId(qryId);
                querydao.createBo(querybo);
            } else {
                querydao.getHibernateTemplate().merge(querybo);
            }
            vo.setQryId(querybo.getQryId());
            qryId = querybo.getQryId();
            //保存显示项目
            QueryItemBO[] itembos = vo.getItem();
            if (itembos == null || itembos.length <= 0)
                throw new RollbackableException("显示项目为空", null, getClass());

            queryitemservice.createQueryItem(itembos, qryId);
            //保存统计项目
            StaticVO[] svo = vo.getStatics();
            if (svo != null)
                staticservice.createStatic(svo, qryId);
        } catch (Exception e) {
            throw new RollbackableException("保存查询条件错误", e, getClass());
        }
        return vo;

    }

    /**
     * 创建一个默认queryvo 用于新建查询或统计时用。
     *
     * @param setType 指标集类别 A|B|C|D 对应 人员|机构|岗位|组织关系
     * @param qsType  Q|S 对应 查询|统计
     * @param classId 类别id 查询统计左侧的类别树的id
     * @return
     * @throws BkmsException
     */
    public QueryVO findDefaultQueryVO(String setType, String qsType, String classId) throws BkmsException {
        if (setType == null || qsType == null)
            throw new BkmsException("SetType或QsType为空,不能继续", null, getClass());
        QueryVO vo = new QueryVO();
        vo.setQsType(qsType);
        vo.setSetType(setType);
        vo.setClassId(classId);
        vo.setCount("checked");
        StaticVO svo = new StaticVO();
        QueryStaticBO sbo = new QueryStaticBO();
        sbo.setStaticId(String.valueOf(System.currentTimeMillis()));
        svo.setStatics(sbo);
        //设置默认查询条件 分别机构/人员/岗位/党组织设置
        QueryConditionBO[] conditions = null;
//        if ("A".equals(setType)) {
//            sbo.setGroup(" A1 ");
//            sbo.setGroupShow("<SPAN id=groupSpan onmouseover=spanOver(this) style=\"WIDTH: 10px\" onclick=groupChange(this); onmouseout=spanOut(this) spanType=\"left_bracket\">&nbsp;</SPAN> <SPAN style=\"WIDTH: 20px\">A1&nbsp;</SPAN><SPAN id=groupSpan onmouseover=spanOver(this) style=\"WIDTH: 10px\" onclick=groupChange(this); onmouseout=spanOut(this) spanType=\"right_bracket\">&nbsp;</SPAN> ");
//            conditions = new QueryConditionBO[1];
//            conditions[0] = new QueryConditionBO();
//            conditions[0].setStaticId(sbo.getStaticId());
//            conditions[0].setGroupId("A1");
//            conditions[0].setItemId("A001054");
//            conditions[0].setSetId("A001");
//            conditions[0].setOperator("in");
//            conditions[0].setText(CodeUtil.interpretCode(null, "013511"));
//            conditions[0].setValue("013511");
//        }
        svo.setCondi(conditions);
        vo.setStatics(new StaticVO[]{svo});
        QueryItemBO[] showItems = queryitemservice.queryDefaultItem(setType);
        vo.setItem(showItems);
        return vo;
    }

    public QueryVO findDefaultQryWageVO(String setType, String qsType, String classId) throws BkmsException {
        if (setType == null || qsType == null)
            throw new BkmsException("SetType或QsType为空,不能继续", null, getClass());
        QueryVO vo = new QueryVO();
        vo.setQsType(qsType);
        vo.setSetType(setType);
        vo.setClassId(classId);
        vo.setCount("checked");
        StaticVO svo = new StaticVO();
        QueryStaticBO sbo = new QueryStaticBO();
        sbo.setStaticId(String.valueOf(System.currentTimeMillis()));
        svo.setStatics(sbo);
        vo.setStatics(new StaticVO[]{svo});
        return vo;
    }

    public QueryBO[] queryQuery(String classId, PageVO vo) throws BkmsException {
        return querydao.queryQueryByClassId(classId, vo);
    }
//*********************** 拼sql部分 **********************************

    /**
     * 根据vo拼出整个sql 包括select部分 from部分 where部分 orderby部分
     * 返回hash 包含 各部分sql和全部的sql
     *
     * @param vo
     * @return Hashtable
     */
    public Hashtable getQuerySqlHash(User user, QueryVO vo) throws BkmsException {
        if (vo == null || vo.getItem() == null)
            return null;
        QueryUtil qu = new QueryUtil();
        QueryBO bo = new QueryBO();
        Tools.copyProperties(bo, vo);

        return qu.buildQuerySql(user, vo.getItem(), vo.getStatics()[0], bo, vo.isNoAddDefaultCondFlag());
    }

    public Hashtable getQryWageSqlHash(User user, QueryVO vo) throws BkmsException {

        return null;

    }

    /**
     * 据vo拼出整个sql 包括select部分 from部分 where部分 orderby部分
     * 返回hash 包含 各部分sql和全部的sql
     * 招聘模块使用,仅拼写E类的指标,并不使用通用的权限接口
     */
    public Hashtable buildQuerySqlForRecr(User user, QueryVO vo) throws BkmsException {

        return null;

    }

    public String getQuerySql1(User user, QueryVO vo) throws BkmsException {
        return null;
    }

    /**
     * 根据系统标志查询
     *
     * @param sysFlag
     * @return
     * @throws BkmsException
     */
    public List queryQueryBySysFlag(String sysFlag) throws BkmsException {
        return querydao.queryQueryBySysFlag(sysFlag);
    }


    public JdbcTemplate getJdbctemplate() {
        return jdbctemplate;
    }

    public void setJdbctemplate(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }


    public QueryDAO getQuerydao() {
        return querydao;
    }

    public void setQuerydao(QueryDAO querydao) {
        this.querydao = querydao;
    }

    public StaticService getStaticservice() {
        return staticservice;
    }

    public void setStaticservice(StaticService staticservice) {
        this.staticservice = staticservice;
    }

    public ConditionItemService getConditionitemservice() {
        return conditionitemservice;
    }

    public void setConditionitemservice(ConditionItemService conditionitemservice) {
        this.conditionitemservice = conditionitemservice;
    }

    public QueryItemService getQueryitemservice() {
        return queryitemservice;
    }

    public void setQueryitemservice(QueryItemService queryitemservice) {
        this.queryitemservice = queryitemservice;
    }
}
