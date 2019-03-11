package com.becoda.bkms.emp.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
import com.becoda.bkms.emp.pojo.vo.PersonChangeVO;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
//import com.becoda.bkms.emp.pojo.vo.ZPResumeInfoVO;
import com.becoda.bkms.emp.pojo.vo.RecoverVO;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import java.util.List;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-4-1
 * Time: 16:19:17
 */
public interface IPersonUCC {
    /**
     * 检查用户对象是否已经有效
     *
     * @param person PersonVO
     * @return errMsg
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    public String checkNewPerson(PersonVO person) throws BkmsException;

    /**
     * 创建用户
     *
     * @param person person
     * @param user   user
     * @param photo  photo
     * @return person id
     * @throws BkmsException e
     */
    public String createPerson(PersonVO person, User user, byte[] photo)  throws RollbackableException;

    /**
     * 查询人员基本信息
     *
     * @return
     * @throws RollbackableException
     */
//    public ZPResumeInfoVO queryPersonInfo(String personId) throws BkmsException;

    /**
     * 查询人员信息插入产品系统
     */
//    public String createZPToPerson(User user, String personId, String personCode, String deptId, String orgId, BkmsHttpRequest request) throws BkmsException;

    /**
     * 查询待入职人员简历
     *
     * @throws com.becoda.bkms.common.exception.RollbackableException
     *
     */
//    public ZPResumeInfoVO queryZPResumeInfo(String personId) throws BkmsException;

    //查询员工是否已经入产品库
    public String checkZPToPerson(String personCode, String IdNum) throws BkmsException;

    // 查询待入职人员信息
    public TableVO queryZpToPersonList(User user, String name, String cartnum, PageVO page, String qryId) throws BkmsException;

    /**
     * 查询人员列表
     *
     * @param user       user
     * @param name       username
     * @param personType personType
     * @param superId    orgid
     * @param page       PageVO
     * @param cancelFlag cancelFlag
     * @return TableVO
     * @throws BkmsException e
     */
    public TableVO queryPersonList(User user, String unit, String name, String personType, String superId, PageVO page, String cancelFlag) throws BkmsException;

    public TableVO queryPersonList(User user, PageVO page) throws BkmsException;

    //old
    public TableVO queryPersonList(User user, String unit, String name, String personType, String superId, PageVO page, String cancelFlag, String qryId, boolean right) throws BkmsException;

    //new
    public TableVO queryPersonList(User user, String name, String superId, PageVO page, String cancelFlag, String qryId, String personCode) throws BkmsException;

    public TableVO queryPersonBySuper(User user, String superId, PageVO page, String cancelFlag, String qryId) throws BkmsException;

    /**
     * 查询人员
     *
     * @param user       operator
     * @param superId    orgid
     * @param page       PageVO
     * @param cancelFlag cancelFlag
     * @return TableVO
     * @throws BkmsException e
     */
    public TableVO queryPersonBySuper(User user, String superId, PageVO page, String cancelFlag) throws BkmsException;

    public TableVO queryPersonBySuperRight(User user, String superId, PageVO page, String cancelFlag, boolean right) throws BkmsException;

    /**
     * 人员减员
     *
     * @param user           operator
     * @param personchangevo PersonChangeVO
     * @param ids            person ids
     * @throws BkmsException e
     */
    void updateDismissPerson(User user, PersonChangeVO personchangevo, String[] ids) throws BkmsException;

    public PersonBO findPerson(String id) throws BkmsException;

    public String findDissmissType(String personId) throws RollbackableException;

    /**
     * 撤销人员减员
     *
     * @param user 操作人
     * @param ids  String[id]
     * @throws BkmsException e
     */
    void updateBackPerson(User user, String[] ids) throws BkmsException;

//    public void updateBackPersonForCzb(User user, String perId, String orgId, String deptId, String time, String postType,String personType) throws BkmsException;
    public void updateBackPersonForCzb(User user, RecoverVO vo) throws BkmsException;

    /**
     * 删除人员
     *
     * @param ids  person id array
     * @param user operator
     * @throws BkmsException e
     */
    void deletePerson(String[] ids, User user) throws BkmsException;

    /**
     * 离退休人员减员
     *
     * @param user           操作人
     * @param personchangevo personchangevo
     * @param ids            ids
     * @throws BkmsException e
     */
    void updateRetireDismissPerson(User user, PersonChangeVO personchangevo, String[] ids) throws BkmsException;

    /**
     * 查询已减员人员
     *
     * @param user         operator
     * @param name         username
     * @param dismissType  减员类型
     * @param disstartDate 开始时间
     * @param disendDate   结束时间
     * @param superId      orgid
     * @param page         PageVO
     * @return TableVO
     * @throws BkmsException e
     */
    TableVO queryDismissPer(User user, String name, String dismissType, String disstartDate, String disendDate, String superId, PageVO page) throws BkmsException;

    /**
     * 查询当前记录
     *
     * @param user  operator
     * @param setId set id
     * @param fks   外键数组
     * @return TableVO
     * @throws BkmsException e
     */
    public TableVO findCurRecord(User user, String setId, String[] fks) throws BkmsException;

    /**
     * 查询记录总数
     *
     * @param whereSql 带有 add 的 HQL where 子句
     * @return count
     * @throws BkmsException e
     */
    int queryResultCount(String whereSql) throws BkmsException;

    /**
     * 查询人员
     *
     * @param whereSql 带有 add 的 HQL where 子句
     * @return PersonBO[]
     * @throws BkmsException e
     */
    PersonBO[] queryPerson(String whereSql) throws BkmsException;

    /**
     * 查询人员
     *
     * @param superId    orgid
     * @param cancelFlag cancelFlag
     * @return PersonBO[]
     * @throws BkmsException e
     */
    PersonBO[] queryPersonBySuper(String superId, String cancelFlag) throws BkmsException;

    /**
     * 更新其人员排序
     *
     * @param user    operator
     * @param persons persons
     * @throws BkmsException e
     */
    void updatePersonSort(User user, PersonBO[] persons) throws BkmsException;

    /**
     * 查询多个用户的id与name
     *
     * @param idString id string, join by ','
     * @return List<String(personId | name)>
     * @throws BkmsException e
     */
    public List queryMultiPerson(String idString) throws BkmsException;

    /**
     * 查询所有用户
     *
     * @param whereSql 带有 add 的 HQL where 子句
     * @return PersonBO[]
     * @throws com.becoda.bkms.common.exception.BkmsException
     *          e
     */
    PersonBO[] queryAllPerson(String whereSql) throws BkmsException;

    /**
     * 根据工号查询所有满足的工号
     * @param code
     * @return
     * @throws BkmsException
     */
    public List queryPersonCodeByCode(String code) throws BkmsException ;
}
