package com.becoda.bkms.org.ucc;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.org.pojo.bo.OrgBO;
//import com.becoda.bkms.org.pojo.vo.OrgChangeVO;
import com.becoda.bkms.org.pojo.vo.OrgSetVO;
import com.becoda.bkms.org.pojo.vo.OrgVO;
//import com.becoda.bkms.post.pojo.bo.PostBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface IOrgUCC {
    public OrgBO findOrgBO(String orgId) throws BkmsException;

    /**
     * 检查机构下是否还有下级正常机构
     *
     * @param ids
     * @throws BkmsException
     */
    public void checkOrgSubValidOrg(String[] ids) throws BkmsException;

    /**
     * 检查机构下是否还有下级机构
     *
     * @param ids
     * @throws BkmsException
     */
    public void checkOrgSubOrg(String[] ids) throws BkmsException;

    /**
     * 检查机构下是否还有人
     *
     * @param ids
     * @throws BkmsException
     */
    public void checkPerByOrg(String[] ids) throws BkmsException;

    /**
     * 检查机构下是否还有岗位
     *
     * @param ids
     * @throws BkmsException
     */
    public void checkPostByOrg(String[] ids) throws BkmsException;


    /**
     * 查询某个机构下的所有的下级机构，不包括本身
     *
     * @param superTreeId
     * @return
     * @throws BkmsException
     */
    public OrgBO[] queryAllOrgBySuper(String superTreeId) throws BkmsException;

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws BkmsException
     */
    public OrgBO[] queryAllOrgBySuperSelf(String superTreeId) throws BkmsException;


    /**
     * 查询某个机构下的所有的下级机构，不包括本身
     *
     * @param superTreeId
     * @return
     * @throws BkmsException
     */
    public OrgBO[] queryAllOrgBySuper(String superTreeId, String cancel) throws BkmsException;

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws BkmsException
     */
    public OrgBO[] queryAllOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException;

    /**
     * 查询机构代码是否重复
     *
     * @param orgCode
     * @return
     * @throws BkmsException
     */
    public int queryOrgCodeCount(String orgCode, String orgId) throws BkmsException;

    /**
     * 查询同一父类机构下是否有机构名重复
     *
     * @param superId
     * @param newOrgName
     * @return
     * @throws BkmsException
     */
    public List queryOrgName(String superId, String newOrgName) throws BkmsException;


    /**
     * 查询某个机构下的岗位
     *
     * @param orgId
     * @return
     * @throws BkmsException
     */
//    public PostBO[] queryPostByOrg(String orgId) throws BkmsException;

    /**
     * 根据TreeId查询机构
     *
     * @param treeId
     * @return
     * @throws BkmsException
     */
    public OrgBO findOrgByTreeId(String treeId) throws BkmsException;

//    public String createOrg(OrgVO orgvo, User user) throws BkmsException;

    //public String createOrg(User user,String setId, Map map) throws BkmsException;

    public String createOrg(User user, String setId, OrgVO orgVO) throws BkmsException;

    /**
     * add by sunmh 2015-09-06
     */
    public TableVO queryOrgList(User user, PageVO page, String orgName, String superId, String orgLevel, String cancel, String from) throws BkmsException;

    public TableVO queryOrgList(User user, PageVO page, String orgName, String superId, String orgType, String orgLevel, String cancel, String forom) throws BkmsException;

    //孙明辉修改,支持传入queryID
    public String queryOrgList(TableVO table, String orgName, String superId, String orgType, int pageNum, int rowNum, String cancel, User user, String flag, String queryId) throws BkmsException;

    /**
     * s
     * 机构撤销
     *
     * @param orgsetvo
     * @param ids
     * @throws BkmsException
     */
    public void updateDismissOrg(OrgSetVO orgsetvo, String[] ids, User user) throws BkmsException;

    /**
     * 机构恢复
     *
     * @param ids
     * @throws BkmsException
     */
    public void updateBackOrg(String[] ids, User user) throws BkmsException;

    /**
     * 机构删除
     *
     * @param ids
     * @param user
     * @throws BkmsException
     */
    public void deleteOrg(String[] ids, User user) throws BkmsException;

    /**
     * 主管机构变更
     *
     * @param changeOrg
     * @param user
     * @param superOrg
     * @param orgchangevo
     * @param ids
     * @throws BkmsException
     */
//    public void updateOrgChange(String changeOrg, User user, String superOrg, OrgChangeVO orgchangevo, String[] ids) throws BkmsException;
//
//    //机构更名，机构变动地址 flag判断是更名还是变址
//    public void updateOrgChange(String changeOrg, User user, String newStatus, OrgChangeVO orgchangevo, String flag) throws BkmsException;

    /**
     * 机构合并
     *
     * @param uniteOrgId
     * @param user
     * @param orgId
     * @param orgchangevo
     * @param ids
     * @throws RollbackableException
     */
//    public String updateOrgUnite(String uniteOrgId, User user, String orgId, OrgChangeVO orgchangevo, String[] ids) throws RollbackableException;

    /**
     * 机构排序
     *
     * @param orgs
     * @throws BkmsException
     */
    public void updateOrgSort(OrgBO[] orgs, String superId, User user) throws BkmsException;

//    public OrgBO findUnitOrgIncludeFunDep(String orgId) throws BkmsException;

    /**
     * 查询总行和区县支行
     *
     * @return
     * @throws BkmsException
     */
    public List findOrgByType() throws BkmsException;

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws BkmsException
     */
    public OrgBO[] queryAllOutsideOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException;

    public OrgBO[] queryNextLeveOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException;

    public Hashtable queryAllInsideOrgBySuperSelf(String superTreeId, String cancel) throws BkmsException;

    /**
     * 查询某个机构下第一层内设机构
     * lrg update 2015-7-6
     *
     * @param superId
     */
    public List queryInsideOrgBySuperId(String superId) throws BkmsException;

    /**
     * 查询某个机构下的所有的下级机构，包括本身
     *
     * @param superTreeId
     * @return
     * @throws BkmsException
     */
    public OrgBO[] queryAllOutsideOrgBySuperSelfOrderByLayer(String superTreeId, int layer) throws BkmsException;

    public List queryAllStockOrgBySuperAndLayer(String stockTreeId, int layer) throws BkmsException;

    public List queryAllStockOrgBySuper(String stockTreeId, String cancel) throws BkmsException;

//    public Hashtable queryAllInsideOrgBySuperStockSelf(String superStockTreeId, String cancel) throws BkmsException;

//    public List queryOrgPostNum(String orgTreeId, String cancel) throws BkmsException;

    TableVO findOrgInfoSetRecord(User user, String setId, String pk) throws BkmsException;

    TableVO queryOrgInfoSetRecordList(User user, String setId, String fk) throws BkmsException;

    TableVO getBlankOrgInfoSetRecord(User user, String setId, String fk) throws BkmsException;

    void deleteOrgInfoSetRecord(User user, String setId, String[] pk, String fk) throws BkmsException;

    void updateOrgInfoSetRecord(User user, String setId, Map map, String pk) throws BkmsException;

    void addOrgInfoSetRecord(User user, String setId, Map parameterMap) throws BkmsException;

    public String findOrgByDeptName(String deptId) throws BkmsException;

    public OrgBO findOrgByDept(String deptId) throws BkmsException;
    /**
	 * 根据父id查询（分页查询）
	 * @param vo
	 * @param specsocre
	 * @return
	 */
    public  List queryOrgBysuperId(PageVO vo,String superid)throws BkmsException;
}
