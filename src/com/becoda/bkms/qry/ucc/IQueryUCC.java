package com.becoda.bkms.qry.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.qry.pojo.bo.QueryBO;
import com.becoda.bkms.qry.pojo.vo.QueryVO;
import com.becoda.bkms.qry.pojo.vo.StaticResultVO;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import java.util.Hashtable;
import java.util.List;

public interface IQueryUCC {


    public void createTmpId(String[] ids) throws BkmsException;

    /**
     * 根据传入的qryId数组删除多个查询 <BR>
     * 需要删除staticbo querybo queryitembo conditionbo
     *
     * @param qryIds
     */
    public void deleteQuery(String[] qryIds) throws BkmsException;

    /**
     * 根据vo创建一个查询 vo中包含querybo staticbo queryitembo,conditionbo,<BR>
     * 创建前需要判断是否有qryid,若有调用deleteQuery方法.
     *
     * @param vo
     * @return qryid
     */
    public QueryVO createQuery(QueryVO vo) throws BkmsException;

    public QueryVO createStatic(QueryVO vo) throws BkmsException;

    /**
     * 根据qryId从数据库中查询各个bo 组装成queryvo
     *
     * @param qryId 查询定义id
     * @return QueryVO 返回组装了各个bo的vo对象
     */
    public QueryVO findQueryVO(String qryId) throws BkmsException;

    public QueryVO findQryWageVO(String qryId) throws BkmsException;

    /**
     * 根据vo拼出查询sql.本方法内部需要根据类别得到不同(ABCD)类的拼sql的实现类.
     *
     * @param vo 包含各个bo的vo对象.未保存到数据库中的vo也可以进行查询
     * @return String
     */
    public Hashtable buildQuerySqlHash(User user, QueryVO vo) throws BkmsException;

    /**
     * 据vo拼出整个sql 包括select部分 from部分 where部分 orderby部分
     * 返回hash 包含 各部分sql和全部的sql
     * 招聘模块使用,仅拼写E类的指标,并不使用通用的权限接口
     */
    public Hashtable buildQuerySqlForRecr(User user, QueryVO vo) throws BkmsException;

    public QueryBO[] queryQuery(String classId) throws BkmsException;

    public QueryBO[] queryQuery(String classId, PageVO vo) throws BkmsException;

    public QueryVO findDefaultQueryVO(String setType, String qsType, String classId) throws BkmsException;

    public QueryVO findDefaultQryWageVO
            (String
                    setType, String
                    qsType, String
                    classId) throws BkmsException;

    public Hashtable findSQL(String qryId) throws BkmsException;

    public TableVO executeQuery(Hashtable sqlHash, PageVO pvo) throws BkmsException;

    public TableVO executeQuery(User user, Hashtable sqlHash, PageVO pvo, String pmsType) throws BkmsException;

    /**
     * 根据系统标志查询
     *
     * @param sysFlag
     * @return
     * @throws BkmsException
     */
    public List queryQueryBySysFlag(String sysFlag) throws BkmsException;

    public StaticResultVO[] executeStatic(User user, QueryVO vo) throws BkmsException;
}
