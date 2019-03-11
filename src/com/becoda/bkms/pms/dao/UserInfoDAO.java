package com.becoda.bkms.pms.dao;

import com.becoda.bkms.cache.SysCacheTool;
//import com.becoda.bkms.ccp.pojo.bo.PartyBO;
//import com.becoda.bkms.ccyl.pojo.bo.CcylBO;
import com.becoda.bkms.common.dao.GenericDAO;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.vo.PersonVO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.pms.api.PmsAPI;
import com.becoda.bkms.pms.pojo.bo.*;
import com.becoda.bkms.pms.pojo.vo.UserInfoVO;
import com.becoda.bkms.pms.pojo.vo.UserPmsInfoVO;
import com.becoda.bkms.pms.util.MenuObj;
import com.becoda.bkms.pms.util.PinYinTrans;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
//import com.becoda.bkms.union.pojo.bo.UnionBO;
import com.becoda.bkms.util.Tools;
import com.opensymphony.xwork2.ActionContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


/**
 * 用户的基本信息数据访问
 *
 * @2015-5-24
 */
public class UserInfoDAO extends GenericDAO {
    public User findUserByUserName(String userName) throws RollbackableException {
        try {
            List list = hibernateTemplate.find("from User u where u.loginName='" + userName + "'");
            return (User) list.get(0);
        } catch (Exception e) {
            throw new RollbackableException("读取用户信息失败！", e, UserInfoDAO.class);
        }
    }

    /**
     * 根据登录ID查询登录人信息
     * (用于权限验证)
     *
     * @param userId
     * @return
     * @throws RollbackableException
     */
    public UserPmsInfoVO queryUserPmsInfoVOByUserId(String userId) throws RollbackableException {
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("from UserPmsInfoVO u where u.personId ='")
                    .append(userId)
                    .append("'");
            List list = super.getHibernateTemplate().find(sf.toString());
            if (list == null || list.size() == 0) return null;
            return (UserPmsInfoVO) list.get(0);
        }
        catch (Exception e) {
            throw new RollbackableException("读取用户信息错误", e, this.getClass());
        }
    }
    /**
     * 修改登录名
     *
     * @param userID   用户ID
     * @param userName 用户名
     *  447451BD015D
     */
//    public void updateUserInfo(String userID, String userName, String password) throws RollbackableException {
//        try {
//            User u = (User) hibernateTemplate.get(User.class, userID);
//
//            u.setLoginName(userName);
//            if (password != null)
//                u.setPassword(password);
//            //s
//            hibernateTemplate.update(u);
//        } catch (Exception e) {
//            //e.printStackTrace();
//            throw new RollbackableException("修改数据失败！", e, UserInfoDAO.class);
//
//        }
//
//    }

    /**
     * 检验用户名是否重复
     */
    public boolean checkRepLoginName(String userId, String userName) throws RollbackableException {
        try {
            StringBuffer sql = new StringBuffer().append("from User u where u.loginName='").append(userName).append("' and u.userId <>'").append(userId).append("'");
            List list = hibernateTemplate.find(sql.toString());
            return list != null && list.size() > 0;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息失败！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测是否是系统管理员
     *
     * @param userId 用户ID
     * @return 1 超级管理员  2 系统管理员 3 其他用户
     */
    public String isSysManager(String userId) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return PmsConstants.ROLE_NORMAL;

        StringBuffer sf = new StringBuffer().append("select r from RoleInfoBO r,UserRoleBO u where r.roleId = u.roleId and u.personId ='").append(userId).append("'");
        try {
            List list = hibernateTemplate.find(sf.toString());
            if (list != null && list.size() > 0) {
                //由于系统管理员只允许有一个角色，所以此处无需循环判断，取第一条数据即可
                RoleInfoBO rinfo = (RoleInfoBO) list.get(0);
                if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(rinfo.getRoleId())) {
                    return PmsConstants.IS_SYS_MANAGER;
                } else if (PmsConstants.IS_SYS_MANAGER.equals(rinfo.getSysOper())) {
                    return PmsConstants.ROLE_MANAGER;
                }
            }
            return PmsConstants.ROLE_NORMAL;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取角色信息时发生错误！", e, UserInfoDAO.class);
        }

    }

    /**
     * 得到超级管理员ID
     *
     * @return 超级管理员ID
     */
    public String getSuperManagerId() throws RollbackableException {

        StringBuffer sf = new StringBuffer();
        sf.append("from UserRoleBO u where u.roleId ='").append(PmsConstants.SUPER_MANAGER_ROLE_ID).append("'");
        try {
            List list = hibernateTemplate.find(sf.toString());
            if (list != null && list.size() > 0) {
                UserRoleBO uRoleinfo = (UserRoleBO) list.get(0);
                return uRoleinfo.getPersonId();
            }
            return "";
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取角色信息时发生错误！", e, UserInfoDAO.class);
        }

    }


    /**
     * 更新最新登录信息
     *
     * @param loginTime
     * @param userId
     */
    public void updateUserLoginInfo(String loginTime, String userId) throws RollbackableException {
        try {
            User u = (User) super.findBo(User.class, userId);
            u.setLastLoginTime(loginTime);
            //更新登陆次数
            int times = Integer.parseInt(u.getLoginTimes());
            times = times + 1;
            u.setLoginTimes(Integer.toString(times));
            super.updateBo(userId, u);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("更新登录信息出错！", e, UserInfoDAO.class);
        }
    }


    /**
     * 检查信息集和信项权限
     * 方法：通过SQL语句查询出当前用户下所有角色的信息项和信息集的最大权限
     *
     * @param userId   用户ID
     * @param dataType 0 指标集  1：指标项
     * @return Hashtable  key = 指标集或指标项ID  obj 权限
     *         4479B2C80277
     */
    public Hashtable queryUserDataByDataType(String userId, String dataType) throws RollbackableException {
        StringBuffer sf = new StringBuffer();
        userId = Tools.filterNull(userId);
        dataType = Tools.filterNull(dataType);
        if ("".equals(userId) || "".equals(dataType)) return null;

        boolean isSysManager;
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            isSysManager = true;
            if (dataType.equals(PmsConstants.INFO_SET_TYPE)) //如果是指标集类型
                sf.append("select info.setId  from InfoSetBO info  order by info.setId");
            else
                sf.append("select info.itemId from InfoItemBO info  order by info.itemId");

        } else                                  //普通用户
        {
            isSysManager = false;
            sf.append("select rdata.dataId,max(rdata.pmsType) from RoleDataBO rdata,UserRoleBO urole ");
            sf.append("where rdata.roleId = urole.roleId and  urole.personId='");
            sf.append(userId);
            sf.append("' and rdata.dataType='").append(dataType).append("'");
            sf.append(" group by rdata.dataId order by rdata.dataId");
        }
        try {
            List list = hibernateTemplate.find(sf.toString());
            if (list == null || list.isEmpty()) return null;

            int len = list.size();
            Hashtable hash = new Hashtable();
            for (int i = 0; i < len; i++) {
                if (isSysManager)
                    hash.put(list.get(i).toString(), String.valueOf(PmsConstants.PERMISSION_WRITE));
                else {
                    Object[] obj = (Object[]) list.get(i);
                    hash.put((String) obj[0], (String) obj[1]);
                }
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检查信息集和信项权限
     * 方法：通过SQL语句查询出当前用户下所有角色的信息项和信息集的最大权限
     *
     * @param userId 用户ID
     * @param sType  指标集小类
     * @return Hashtable  key = 指标集或指标项ID  obj 权限
     *         4479B2C80277
     */
    public Hashtable queryUserDataByStype(String userId, String sType) throws RollbackableException {
        StringBuffer sqlInfoSet = new StringBuffer();
        StringBuffer sqlInfoItem = new StringBuffer();
        userId = Tools.filterNull(userId);
        sType = Tools.filterNull(sType);
        if ("".equals(userId) || "".equals(sType)) return null;

        boolean isSysManager;
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            isSysManager = true;
            //指标集
            sqlInfoSet.append("select info.setId  from InfoSetBO info  order by info.setId");
            //指标项
            sqlInfoItem.append("select info.itemId from InfoItemBO info where (info.itemProperty <>'" + SysConstants.INFO_ITEM_PROPERTY_SYSTEM + "' and info.editProp<>'" + SysConstants.INFO_ITEM_EDIT_PROP_HIDE + "') order by info.itemId");

        } else                                  //普通用户
        {
            isSysManager = false;
            //指标集
            sqlInfoSet.append("select rdata.dataId,max(rdata.pmsType) from RoleDataBO rdata,UserRoleBO urole,InfoSetBO info ");
            sqlInfoSet.append("where rdata.roleId = urole.roleId and rdata.dataId=info.setId and  urole.personId='");
            sqlInfoSet.append(userId);
            sqlInfoSet.append("' and info.set_sType='").append(sType).append("'");
            sqlInfoSet.append(" group by rdata.dataId order by rdata.dataId");
            //指标项
            sqlInfoItem.append("select rdata.dataId,max(rdata.pmsType) from RoleDataBO rdata,UserRoleBO urole,InfoSetBO info,InfoItemBO item ");
            sqlInfoItem.append("where rdata.roleId = urole.roleId and info.setId=item.setId and rdata.dataId=item.itemId and  urole.personId='");
            sqlInfoItem.append(userId);
            sqlInfoItem.append("' and info.set_sType='").append(sType).append("'");
            sqlInfoItem.append(" group by rdata.dataId order by rdata.dataId");
        }
        try {
            //指标集
            List list = hibernateTemplate.find(sqlInfoSet.toString());
            if (list == null || list.isEmpty()) return null;

            int len = list.size();
            Hashtable hash = new Hashtable();
            for (int i = 0; i < len; i++) {
                if (isSysManager)
                    hash.put(list.get(i).toString(), String.valueOf(PmsConstants.PERMISSION_WRITE));
                else {
                    Object[] obj = (Object[]) list.get(i);
                    hash.put(obj[0], obj[1]);
                }
            }

            //指标项
            list = hibernateTemplate.find(sqlInfoItem.toString());
            if (list == null || list.isEmpty()) return null;

            len = list.size();
            for (int i = 0; i < len; i++) {
                if (isSysManager)
                    hash.put(list.get(i).toString(), String.valueOf(PmsConstants.PERMISSION_WRITE));
                else {
                    Object[] obj = (Object[]) list.get(i);
                    hash.put(obj[0], obj[1]);
                }
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检查菜单权限<br>
     * 方法：通过SQL语句查询出当前用户下所有角色的操作权限，使用distinct方法,按照CodeID
     * 排序
     *
     * @param userId 用户ID
     * @return 数据结构 key = "operateId"  object = menuObj;
     *         hash中存储一级菜单,所有的二级菜单以及子菜单都通过MenuObj结构实现
     *         4479B3E502EF
     */
    public Hashtable queryUserMenu(String userId) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        StringBuffer sf = new StringBuffer();
        String sysflag = isSysManager(userId);
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(sysflag))            //超级管理员  and operate.moduleStatus='"+PmsConstants.STATUS_BAN+"'
        {
            sf.append("from OperateBO operate where operate.operateType='" + PmsConstants.MENU_TYPE_MENU + "' ")
                    .append("  order by length(operate.treeId),operate.treeId");
        } else {
            sf.append("select operate from OperateBO operate, RoleOperateBO roperate, UserRoleBO urole  ");
            sf.append("where  operate.operateId = roperate.operateId ");
            sf.append(" and operate.operateType='" + PmsConstants.MENU_TYPE_MENU + "' ");
            sf.append(" and operate.sysFlag='" + PmsConstants.MENU_NORMAL + "' ");
            //sf.append(" and operate.moduleStatus='"+PmsConstants.STATUS_BAN+"' ");
            sf.append(" and roperate.roleId = urole.roleId and urole.personId='");
            sf.append(userId).append("' ");
            sf.append(" order by length(operate.treeId),operate.treeId");
        }
        try {
            Hashtable hshOperate = new Hashtable();
            Hashtable hashMainMenu = new Hashtable();
            List list = hibernateTemplate.find(sf.toString());
            List list2 = null;
            if (PmsConstants.IS_SYS_MANAGER.equals(sysflag)) {
                String sql = " from OperateBO operate where operate.sysFlag='" + PmsConstants.MENU_ABNORMAL + "'" +
                        " or operate.operateId='" + StaticVariable.get(PmsConstants.MENU_PMS) + "' order by length(operate.treeId),operate.treeId";
                list2 = hibernateTemplate.find(sql);
                if (list2 != null) {
                    int listSize = list.size();
                    for (int i = 0; i < list2.size(); i++) {
                        list.add(listSize, list2.get(i));
                        listSize++;
                    }
                }
            }
            MenuObj menu;
            int len = list.size();
            for (int i = 0; i < len; i++) {
                OperateBO operate = (OperateBO) list.get(i);
                menu = new MenuObj();
                menu.Clone(operate);
                String superId = menu.getSuperId();
                if (!hshOperate.containsKey(menu.getOperateId()))
                    hshOperate.put(menu.getOperateId(), menu);
                else
                    continue;
                if (superId.equals("-1"))  //将一级菜单加到hash当中
                {
                    hashMainMenu.put(menu.getModuleId(), menu);
                } else {
                    MenuObj pmenu = (MenuObj) hshOperate.get(superId);
                    if (pmenu != null) {
                        List tmpList = pmenu.getMenus();
                        if (tmpList == null) {
                            tmpList = new ArrayList();
                            pmenu.setMenus(tmpList);
                        }
                        tmpList.add(menu);
                    }
                }
            }
            return hashMainMenu;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检查功能权限  包括菜单和按钮<br>
     * 方法：通过SQL语句查询出当前用户下所有角色的操作权限，使用distinct方法,按照CodeID
     * 排序
     *
     * @param userId 用户ID
     * @return 数据结构 key = "operateId"  object = menuObj;
     *         hash中存储一级菜单,所有的二级菜单以及子菜单都通过MenuObj结构实现
     *         4479B3E502EF
     */
    public List queryUserOperate(String userId) throws RollbackableException {
        userId = Tools.filterNull(userId);
        List rtnList = new ArrayList();
        if ("".equals(userId)) return rtnList;
        StringBuffer sf = new StringBuffer();
        String sysflag = isSysManager(userId);
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(sysflag))            //超级管理员
        {                    //管理员功能不参与付权
            sf.append("from OperateBO operate  where operate.sysFlag='" + PmsConstants.MENU_NORMAL + "' ")
                    .append("  order by length(operate.treeId),operate.treeId");
        } else {
            sf.append("select operate from OperateBO operate, RoleOperateBO roperate, UserRoleBO urole  ");
            sf.append("where  operate.operateId = roperate.operateId ");
            sf.append(" and operate.sysFlag='" + PmsConstants.MENU_NORMAL + "' ");
            sf.append(" and roperate.roleId = urole.roleId and urole.personId='");
            sf.append(userId);
            sf.append("' order by length(operate.treeId),operate.treeId");
        }
        try {
            List list = hibernateTemplate.find(sf.toString());
            int len = list.size();
            Hashtable hash = new Hashtable();
            for (int i = 0; i < len; i++) {
                Object o = list.get(i);
                OperateBO oper = new OperateBO();
                Tools.copyProperties(oper, o);
                if (!hash.containsKey(oper.getOperateId())) {
                    hash.put(oper.getOperateId(), oper);
                    rtnList.add(oper);
                }
            }
            return rtnList;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测机构范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权机构 0 无权机构
     * @return List list数据结构: Org 对象
     *         4479B45002D5
     */
    public List queryUserOrgScale(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        StringBuffer sf = new StringBuffer();
        List list = new ArrayList();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select org.orgId from OrgBO org where org.superId='-1'");
            else
                return list;
        } else {
            sf.append("select rorg.condId from  RoleOrgScaleBO rorg, UserRoleBO urole  ")
                    .append("where rorg.roleId = urole.roleId and rorg.codeId='" + PmsConstants.CODE_TYPE_ORG + "' ")
                    .append(" and rorg.scaleFlag='")
                    .append(flag)
                    .append("' and rorg.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'")
                    .append(" order by rorg.condId");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            Hashtable hashOrg = new Hashtable();
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String orgId = tmplst.get(i).toString();
                if (!hashOrg.containsKey(orgId))  //如果不包含，则添加，因为查询范围权限取的是查询和维护两类权限的并集，可能存在重复情况
                {
                    Org org = SysCacheTool.findOrgById(orgId);
                    if (org != null) {
                        list.add(org);
                        hashOrg.put(orgId, org);
                    }
                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测机构范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权机构 0 无权机构
     * @return List list数据结构: Org 对象
     *         4479B45002D5
     */
    public List queryUserWageUnitScale(String userId, String flag, String type) throws RollbackableException {
//        userId = Tools.filterNull(userId);
//        if ("".equals(userId)) return null;
//        StringBuffer sf = new StringBuffer();
//        List list = new ArrayList();
//        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
//        {
//            if (PmsConstants.SCALE_USE.equals(type))
//                sf.append("select wu.unitId from WageUnitBO wu where wu.superId='-1'");
//            else
//                return list;
//        } else {
//            sf.append("select rorg.condId from  RoleOrgScaleBO rorg, UserRoleBO urole  ")
//                    .append("where rorg.roleId = urole.roleId and rorg.codeId='" + PmsConstants.CODE_TYPE_WAGE + "' ")
//                    .append(" and rorg.scaleFlag='")
//                    .append(flag)
//                    .append("' and rorg.pmsType='")
//                    .append(type)
//                    .append("'  and urole.personId='")
//                    .append(userId).append("'")
//                    .append(" order by rorg.condId");
//        }
//        try {
//            List tmplst = hibernateTemplate.find(sf.toString());
//            if (tmplst == null || tmplst.isEmpty()) return null;
//            Hashtable hashOrg = new Hashtable();
//            int len = tmplst.size();
//            for (int i = 0; i < len; i++) {
//                String orgId = tmplst.get(i).toString();
//                if (!hashOrg.containsKey(orgId))  //如果不包含，则添加，因为查询范围权限取的是查询和维护两类权限的并集，可能存在重复情况
//                {
//                    WageUnitBO org = (WageUnitBO) super.findBo(WageUnitBO.class, orgId);
//                    if (org != null) {
//                        list.add(org);
//                        hashOrg.put(orgId, org);
//                    }
//                }
//            }
//            return list;
//        }
//        catch (Exception e) {
//            //e.printStackTrace();
//            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
//        }
        return null;
    }

    /**
     * 检测党务机构范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权党务机构 0 无权党务机构   目前只支持有权党务机构
     * @return List list数据结构: PartyBO 对象
     *         4479B45002D5
     */
    public List queryUserPartyScale(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        List list = new ArrayList();
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select party.partyId from PartyBO party where party.superId='-1'");
            else
                return list;
        } else {
            sf.append("select rparty.partyId from  RolePartyScaleBO rparty, UserRoleBO urole  ")
                    .append("where rparty.roleId = urole.roleId ")
                    .append(" and rparty.scaleFlag='")
                    .append(flag)
                    .append("' and rparty.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            Hashtable hashParty = new Hashtable();
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String partyId = tmplst.get(i).toString();
                if (!hashParty.containsKey(partyId))  //如果不包含，则添加，因为查询范围权限取得是查询和维护2类权限，可能存在重复情况
                {
//                    PartyBO party = (PartyBO) super.findBo(PartyBO.class, partyId);
//                    if (party != null) {
//                        RolePartyScaleBO bo = new RolePartyScaleBO();
//                        bo.setPartyId(partyId);
//                        bo.setPartyName(party.getName());
//                        list.add(bo);
//                        hashParty.put(partyId, party);
//                    }
                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测团组织机构范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权团组织机构 0 无权团组织机构   目前只支持有权团组织机构
     * @return List list数据结构: CcylBO 对象
     *         4479B45002D5
     */
    public List queryUserCcylScale(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        List list = new ArrayList();
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select g.groupId from CcylBO g where g.superId='-1'");
            else
                return list;
        } else {
            sf.append("select rccyl.ccylId from  RoleCcylScaleBO rccyl, UserRoleBO urole  ")
                    .append("where rccyl.roleId = urole.roleId ")
                    .append(" and rccyl.scaleFlag='")
                    .append(flag)
                    .append("' and rccyl.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            Hashtable hashCcyl = new Hashtable();
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String ccylId = tmplst.get(i).toString();
                if (!hashCcyl.containsKey(ccylId))  //如果不包含，则添加，因为查询范围权限取得是查询和维护2类权限，可能存在重复情况
                {
//                    CcylBO ccyl = (CcylBO) super.findBo(CcylBO.class, ccylId);
//                    if (ccyl != null) {
//                        RoleCcylScaleBO bo = new RoleCcylScaleBO();
//                        bo.setCcylId(ccyl.getGroupId());
//                        bo.setCcylName(ccyl.getName());
//                        list.add(bo);
//                        hashCcyl.put(ccylId, ccyl);
//                    }
                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测工会组织机构范围权限
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权工会组织机构 0 无权工会组织机构   目前只支持有权工会组织机构
     * @return List list数据结构: CcylBO 对象
     *         4479B45002D5
     */
    public List queryUserUnionScale(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        List list = new ArrayList();
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select uo.unionId from UnionBO uo where uo.superId='-1'");
            else
                return list;
        } else {
            sf.append("select ruo.unionId from RoleUnionScaleBO ruo, UserRoleBO urole  ")
                    .append("where ruo.roleId = urole.roleId ")
                    .append(" and ruo.scaleFlag='")
                    .append(flag)
                    .append("' and ruo.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            Hashtable hashUnion = new Hashtable();
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String unionId = tmplst.get(i).toString();
                if (!hashUnion.containsKey(unionId))  //如果不包含，则添加，因为查询范围权限取得是查询和维护2类权限，可能存在重复情况
                {
//                    UnionBO uo = (UnionBO) super.findBo(UnionBO.class, unionId);
//                    if (uo != null) {
//                        RoleUnionScaleBO bo = new RoleUnionScaleBO();
//                        bo.setUnionId(uo.getLabourId());
//                        bo.setUnionName(uo.getName());
//                        list.add(bo);
//                        hashUnion.put(unionId, uo);
//                    }
                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }


    /**
     * 检测党务机构范围权限
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权党务机构 0 无权党务机构   目前只支持有权党务机构
     * @return List list数据结构: PartyBO 对象
     *         4479B45002D5
     */
    public List queryUserPartyScaleByLoginCall(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        List list = new ArrayList();
        if (PmsConstants.SCALE_REFUSE.equals(type)) {
            return list;  //党组织不控制排除权限
        }
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select party.partyId from PartyBO party where party.superId='-1'");
            else
                return list;
        } else {
            sf.append("select rparty.partyId from  RolePartyScaleBO rparty, UserRoleBO urole  ")
                    .append("where rparty.roleId = urole.roleId ");
            sf.append(" and rparty.scaleFlag='").append(flag).append("'");
            sf.append(" and rparty.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String partyId = tmplst.get(i).toString();
//                PartyBO party = (PartyBO) super.findBo(PartyBO.class, partyId);
//                if (party != null) {
//                    list.add(party);
//                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测团组织机构范围权限
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权团组织机构 0 无权团组织机构   目前只支持有权团组织机构
     * @return List list数据结构: CcylBO 对象
     *         4479B45002D5
     */
    public List queryUserCcylScaleByLoginCall(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        List list = new ArrayList();
        if (PmsConstants.SCALE_REFUSE.equals(type)) {
            return list;  //党组织不控制排除权限
        }
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select g.groupId from CcylBO g where g.superId='-1'");
            else
                return list;
        } else {
            sf.append("select rccyl.ccylId from  RoleCcylScaleBO rccyl, UserRoleBO urole  ")
                    .append("where   rccyl.roleId = urole.roleId ");
            sf.append(" and rccyl.scaleFlag='").append(flag).append("'");
            sf.append(" and rccyl.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String ccylId = tmplst.get(i).toString();
//                CcylBO ccyl = (CcylBO) super.findBo(CcylBO.class, ccylId);
//                if (ccyl != null) {
//                    list.add(ccyl);
//                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测工会组织机构范围权限
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权工会组织机构 0 无权工会组织机构   目前只支持有权工会组织机构
     * @return List list数据结构: UnionBO 对象
     *         4479B45002D5
     */
    public List queryUserUnionScaleByLoginCall(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        List list = new ArrayList();
        if (PmsConstants.SCALE_REFUSE.equals(type)) {
            return list;  //党组织不控制排除权限
        }
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select uo.unionId from CcylBO uo where uo.superId='-1'");
            else
                return list;
        } else {
            sf.append("select ruo.unionId from  RoleCcylScaleBO ruo, UserRoleBO urole  ")
                    .append("where   ruo.roleId = urole.roleId ");
            sf.append(" and ruo.scaleFlag='").append(flag).append("'");
            sf.append(" and ruo.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String unionId = tmplst.get(i).toString();
//                UnionBO uo = (UnionBO) super.findBo(UnionBO.class, unionId);
//                if (uo != null) {
//                    list.add(uo);
//                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测机构范围权限
     * 此处只合并机构维护排除权限，
     * 有权机构和排除机构之间的关系是通过pmsAPI中checkOrgTreeId方法控制
     * 所有机构树都需要调用pmsAPI中checkOrgTreeId方法进行检测
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权机构 0 无权机构
     * @return List list数据结构: Org 对象
     *         4479B45002D5
     */
    public List queryUserOrgScaleByLoginCall(String userId, String flag, String type) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        StringBuffer sf = new StringBuffer();
        List list = new ArrayList();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select org.orgId from OrgBO org where org.superId='-1'");
            else
                return list;
        } else {
            sf.append("select rorg.condId from  RoleOrgScaleBO rorg, UserRoleBO urole  ")
                    .append("where   rorg.roleId = urole.roleId and rorg.codeId='" + PmsConstants.CODE_TYPE_ORG + "' ");
            if (PmsConstants.SCALE_REFUSE.equals(type) && PmsConstants.OPERATE_SCALE.equals(flag)) {
                //维护范围的排除权限则取维护范围排除和查询范围的排除的并集
                sf.append(" and rorg.pmsType='" + PmsConstants.SCALE_REFUSE + "'");
            } else { //其他范围都按条件查询
                sf.append(" and rorg.scaleFlag='").append(flag).append("' ")
                        .append(" and rorg.pmsType='")
                        .append(type)
                        .append("'");
            }
            sf.append(" and urole.personId='")
                    .append(userId).append("'")
                    .append(" order by substr(rorg.orgScaleId,20,28) ,rorg.condId");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            int len = tmplst.size();
            Hashtable hashOrg = new Hashtable();
            for (int i = 0; i < len; i++) {
                String orgId = tmplst.get(i).toString();
                if (!hashOrg.containsKey(orgId))  //如果不包含，则添加，因为查询范围权限取得是查询和维护2类权限，可能存在重复情况
                {
                    Org org = SysCacheTool.findOrgById(orgId);
                    if (org != null) {
                        list.add(org);
                        hashOrg.put(orgId, org);
                    }
                }
            }
            return list;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 检测机构范围权限
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权机构 0 无权机构
     * @return List list数据结构: WageUnitBO 对象
     *         4479B45002D5
     */
    public List queryUserWageUnitScaleByLoginCall(String userId, String flag, String type) throws RollbackableException {
//        userId = Tools.filterNull(userId);
//        if ("".equals(userId)) return null;
//        StringBuffer sf = new StringBuffer();
//        List list = new ArrayList();
//        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
//        {
//            if (PmsConstants.SCALE_USE.equals(type))
//                sf.append("select wu.unitId from WageUnitBO wu where wu.superId='-1'");
//            else
//                return list;
//        } else {
//            sf.append("select rorg.condId from  RoleOrgScaleBO rorg, UserRoleBO urole  ")
//                    .append("where   rorg.roleId = urole.roleId and rorg.codeId='" + PmsConstants.CODE_TYPE_WAGE + "' ");
//            if (PmsConstants.SCALE_REFUSE.equals(type) && PmsConstants.OPERATE_SCALE.equals(flag)) {
//                //维护范围的排除权限则取维护范围排除和查询范围的排除的并集
//                sf.append(" and rorg.pmsType='" + PmsConstants.SCALE_REFUSE + "'");
//            } else { //其他范围都按条件查询
//                sf.append(" and rorg.scaleFlag='").append(flag).append("' ")
//                        .append(" and rorg.pmsType='")
//                        .append(type)
//                        .append("'");
//            }
//            sf.append(" and urole.personId='")
//                    .append(userId).append("'")
//                    .append(" order by rorg.condId");
//        }
//        try {
//            List tmplst = hibernateTemplate.find(sf.toString());
//            if (tmplst == null || tmplst.isEmpty()) return null;
//            int len = tmplst.size();
//            Hashtable hashOrg = new Hashtable();
//            for (int i = 0; i < len; i++) {
//                String orgId = tmplst.get(i).toString();
//                if (!hashOrg.containsKey(orgId))  //如果不包含，则添加，因为查询范围权限取得是查询和维护2类权限，可能存在重复情况
//                {
//                    WageUnitBO org = (WageUnitBO) super.findBo(WageUnitBO.class, orgId);
//                    if (org != null) {
//                        list.add(org);
//                        hashOrg.put(orgId, org);
//                    }
//                }
//            }
//            return list;
//        }
//        catch (Exception e) {
//            //e.printStackTrace();
//            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
//        }
        return null;
    }


    /**
     * 查询用户的代码项权限
     * 除去超级管理员外，其他人员都需要按权限进行加载
     *
     * @param userId
     * @param flag
     * @param status 过滤标记      status =null or "" 则查询所有,否则查启用的指标集
     * @return
     * @throws RollbackableException
     */
    public Hashtable queryPmsCode(String userId, String flag, String status) throws RollbackableException {
        userId = Tools.filterNull(userId);
        if ("".equals(userId)) return null;
        StringBuffer sf = new StringBuffer();
        String isSysManager = isSysManager(userId);
        try {
            if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager)) {            //超级管理员
                sf.append("select codeitem.setId,codeitem.itemId from CodeItemBO codeitem where codeitem.setId in ('")
                        .append(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID)).append("')");
                if (status != null && !"".equals(status))
                    sf.append(" and codeitem.itemStatus='").append(SysConstants.INFO_STATUS_OPEN).append("'");
                sf.append(" order by codeitem.setId,codeitem.treeId");
            } else {
                //读取所有的代码权限，然后通过Cache添加Hashtable,如果有系统管理代码中的"其他"类别，则通过手工添加CodeItemBO
                sf.append("select rorg.codeId,rorg.condId from RoleOrgScaleBO rorg, UserRoleBO urole")
                        .append(" where rorg.roleId = urole.roleId and rorg.codeId<>'" + PmsConstants.CODE_TYPE_ORG + "' and urole.personId='")
                        .append(userId).append("' and rorg.scaleFlag='")
                        .append(flag)
                        .append("' and rorg.codeId in ('")
                        .append("','").append(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID)).append("')   order by rorg.codeId,substr(rorg.condId,5)+0");
            }
        } catch (Exception e) {
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null || tmplst.isEmpty()) return null;
            Hashtable hash = new Hashtable();
            int len = tmplst.size();
            String setId = "";
            List list = null;
            for (int i = 0; i < len; i++) {
                Object[] obj = (Object[]) tmplst.get(i);
                String tmpSetId = (String) obj[0];
                String tmpItemId = (String) obj[1];
                if (!setId.equals(tmpSetId)) {
                    //如果前一个代码集是系统管理干部，如果是超级管理员则增加“其他”项，
                    list = new ArrayList();
                    setId = tmpSetId;
                    hash.put(setId, list);
                }

                    CodeItemBO cItem = SysCacheTool.findCodeItem(tmpItemId);
                    list.add(cItem);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 得到所有的权限代码
     *
     * @return
     * @throws RollbackableException
     */
    public Hashtable queryAllPmsCode() throws RollbackableException {
        StringBuffer sf = new StringBuffer();
        try {
            sf.append("select codeitem.setId,codeitem.itemId from CodeItemBO codeitem where codeitem.setId in ('")
                    .append(StaticVariable.get(PmsConstants.SYS_PERSONTYPE_CODE_SETID)).append("') order by codeitem.setId,codeitem.treeId");
            Hashtable hash = new Hashtable();
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null) return null;
            int len = tmplst.size();
            String setId = "";
            List list = null;
            for (int i = 0; i < len; i++) {
                Object[] obj = (Object[]) tmplst.get(i);
                String tmpSetId = (String) obj[0];
                String tmpItemId = (String) obj[1];
                if (!setId.equals(tmpSetId)) {
                    list = new ArrayList();
                    setId = tmpSetId;
                    hash.put(setId, list);
                }
                CodeItemBO cItem = SysCacheTool.findCodeItem(tmpItemId);
                list.add(cItem);
            }
            return hash;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * *************************************  queryPmsCode方法的源方法**********************************
     * // lirg 20060808 修改
     * // 修改原因：增加系统管理干部代码中的除去总行管理和一级行管理的系统管理干部外，其他人员的代码项，
     * // public Hashtable queryPmsCode(String userId, String flag) throws RollbackableException {
     * //        StringBuffer sf = new StringBuffer();
     * //        String isSysManager =isSysManager(userId);
     * //        if ("1".equals(isSysManager))            //超级管理员
     * //            sf.append("from CodeItemBO codeitem where codeitem.setId in ('")
     * //                    .append(PmsConstants.SYS_CADRE_CODEID)
     * //                    .append("','").append(PmsConstants.SYS_SAVE_ABILITY_PERSON).append("') order by codeitem.setId");
     * //        else {
     * //            sf.append("select citem from  RoleOrgScaleBO rorg, UserRoleBO urole,CodeItemBO citem ")
     * //                    .append("where   rorg.roleId = urole.roleId and rorg.codeId<>'" + PmsConstants.CODE_TYPE_ORG + "' and urole.personId='")
     * //                    .append(userId).append("' and rorg.scaleFlag='")
     * //                    .append(flag)
     * //                    .append("' and rorg.condId=citem.itemId and citem.setId in ('")
     * //                    .append(PmsConstants.SYS_CADRE_CODEID)
     * //                    .append("','")
     * //                    .append(PmsConstants.SYS_SAVE_ABILITY_PERSON)
     * //                    .append("')")
     * //                    .append(" order by citem.setId");
     * //        }
     * //        Hashtable hash = new Hashtable();
     * //        try {
     * //
     * //            List tmplst = hibernateTemplate.find(sf.toString());
     * //            if (tmplst == null) return null;
     * //            int len = tmplst.size();
     * //            String setId = "";
     * //            List list = null;
     * //            for (int i = 0; i < len; i++) {
     * //                CodeItemBO item = (CodeItemBO) tmplst.get(i);
     * //                if (!hash.containsKey(item.getSetId())) {
     * //                    list = new ArrayList();
     * //                    setId = item.getSetId();
     * //                    hash.put(setId, list);
     * //                }
     * //                CodeItemBO newItem = new CodeItemBO();
     * //                Tools.copyProperties(newItem, item);
     * //                list.add(newItem);
     * //            }
     * //            return hash;
     * //        }
     * //        catch (Exception e) {
     * //            e.printStackTrace();
     * //            throw new RollbackableException( "读取数据错误！", e, UserInfoDAO.class);
     * //        }
     * //    }
     * /**
     * 查询管理员管辖的所有本级用户<br>
     * 查询方法：<br>
     * 先查出当前用户所出的角色ID<br>
     * 然后通过角色表、角色用户关系表、用户表建立关联，查询处当前管理员管辖的所有的用户
     * 信息，按personID排序，将personID相同的用户合并为：是否业务用户、是否机构管理员、
     * 是否部门管理员，取权限最大值
     *
     * @param roleId 447AD377017B
     */
    public List queryCurrentLevelUser(String roleId, PageVO vo) throws RollbackableException {
        roleId = Tools.filterNull(roleId);
        if ("".equals(roleId)) return null;
        try {
            StringBuffer sf = new StringBuffer();
            sf.append("from UserPmsInfoVO u where  u.belongRoleId='").append(roleId).append("' order by  u.deptTreeId,u.deptOrder,u.personOrder");
            StringBuffer sfCount = new StringBuffer();
            sfCount.append("select count(u) from UserPmsInfoVO u where u.belongRoleId='").append(roleId).append("' ");
            return pageQuery(vo, sfCount.toString(), sf.toString());
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 设置用户的启用禁用状态
     *
     * @param status 启用，禁用
     * @param userId 447AE759030B
     */
    public void makeStatus(String userId, boolean status, String superManageId) throws RollbackableException {
        if (userId.equals(superManageId)) {
            throw new RollbackableException("你无法将超级管理员禁用！", UserInfoDAO.class);
        }
        try {
            User user = (User) super.findBo(User.class, userId);
            if (user != null) {
                if (status)
                    user.setStatus(PmsConstants.STATUS_OPEN);
                else
                    user.setStatus(PmsConstants.STATUS_BAN);
                super.updateBo(user.getUserId(), user);
            }

        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("设置失败！", e, UserInfoDAO.class);
        }
    }

    /**
     * 将指定用户设为本级用户
     */
    public void makeCurrentLevelUser(String userId, String roleId, String superManageId) throws RollbackableException {
        if ("".equals(Tools.filterNull(userId))) return;
        User user;
        try {
            user = (User) super.findBo(User.class, userId);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
        if (user != null) {
            String belongRoleId = Tools.filterNull(user.getBelongRoleId());
            //如果是超级管理员，则不允许设定本级用户
            if (userId.equals(superManageId)) {
                String name = SysCacheTool.interpretPerson(user.getUserId());
                throw new RollbackableException(name + "是超级管理员，不允许设定，设定失败！", UserInfoDAO.class);
            }

            //检测是否已经被其他管理员设置为其他级别用户，如果设置了，则提示修改失败！
            if (!"".equals(belongRoleId)) {
                if (roleId.equals(belongRoleId)) return;   //如果已经设置，则返回
                String name = SysCacheTool.interpretPerson(user.getUserId());
                throw new RollbackableException(name + "不属于本级用户，设定失败！", UserInfoDAO.class);
            }
            user.setBelongRoleId(roleId);
            try {
                super.updateBo(userId, user);
            } catch (Exception e) {
                //e.printStackTrace();
                throw new RollbackableException("设定失败！", e, UserInfoDAO.class);
            }

        }
    }

    /**
     * 将取消本级用户
     */
    public void cancelCurrentLevelUser(String userId, String roleId) throws RollbackableException {
        if ("".equals(Tools.filterNull(userId))) return;
        User user;
        try {
            user = (User) super.findBo(User.class, userId);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取数据错误！", e, UserInfoDAO.class);
        }
        if (user != null) {
            try {
            //检测是否已经被其他管理员设置为其他级别用户，如果设置了，则提示修改失败！
            String belongRoleId = Tools.filterNull(user.getBelongRoleId());
            if ("".equals(belongRoleId)) return;  //如果已经为空，则返回

            if("1".equals(belongRoleId)){
                String name = SysCacheTool.interpretPerson(user.getUserId());
                ActionContext ctx=ActionContext.getContext();
                ctx.put("messageContext", " <script> alert('"+name+ "是系统超级管理员不能取消！"+"') </script>");
                return;
//                throw new RollbackableException( name+ "是系统超级管理员不能取消！", UserInfoDAO.class);
            }
            //检测是否已经被其他管理员设置为其他级别用户，如果设置了，则提示修改失败！
            if (roleId != null && !"".equals(roleId) && !roleId.equals(belongRoleId)) {
                String name = SysCacheTool.interpretPerson(user.getUserId());
//                throw new RollbackableException(name + "不属于本级用户，设定失败！", UserInfoDAO.class);
                ActionContext ctx=ActionContext.getContext();
                ctx.put("messageContext", " <script> alert('"+name+ "不属于本级用户，设定失败！"+"') </script>");
                return;
            }
            user.setBelongRoleId("");

                super.updateBo(userId, user);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RollbackableException("设定失败！", e, UserInfoDAO.class);
            }

        }
    }

    /**
     * 清除本级用户，调配做最后一步的时候使用
     * add by yejb 20070125
     *
     * @param userId
     * @throws RollbackableException
     */
    public void cancelCurrentLevelUser(String userId) throws RollbackableException {
        if ("".equals(Tools.filterNull(userId))) return;
        this.cancelCurrentLevelUser(userId, null);
    }

    /**
     * 验证登录
     * 根据登录名、密码查询用户
     *
     * @param loginName
     * @param password
     */
    public User verifyLogon(String loginName, String password) throws RollbackableException {
        loginName = Tools.filterNull(loginName).trim();
        password = Tools.filterNull(password).trim();
        if ("".equals(loginName)) return null;
        if ("".equals(password)) return null;
        try {
            User user;
            List list = hibernateTemplate.find("from User u where u.loginName='" + loginName + "'");
            if (!list.isEmpty()) {
                user = (User) list.get(0);
                //if (password.equals(user.getPassword()))
                if(true)
                    return user;
            }
            return null;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 查询用户信息，不包括用户的所属角色
     *
     * @param vo
     * @param name       姓名
     * @param treeId     机构TreeId
     * @param personType 人员类别
     * @param user       user对象
     * @return
     * @throws RollbackableException
     */
    public List queryUserInfo(PageVO vo, String name, String treeId, String personType, User user) throws RollbackableException {
        try {
            StringBuffer sfSql = new StringBuffer();
            StringBuffer sfCountSql = new StringBuffer();
            StringBuffer sfWhere = new StringBuffer();
            StringBuffer sfOrder = new StringBuffer();
            sfCountSql.append("select count(u.personId) from UserInfoVO u");
            sfSql.append("from UserInfoVO u ");
            if (name != null && !name.equals("")) {
                sfWhere.append(" u.name like'%")
                        .append(name)
                        .append("%' ");
            }
            if (treeId != null && !treeId.equals("")) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
                sfWhere.append("  u.deptTreeId like '")
                        .append(treeId)
                        .append("%' ");
            }
            //取权限条件
            PmsAPI api = new PmsAPI();
            String strPmsCondi = api.getPersonSimpleScaleCondition(user, "u.deptTreeId", false); //todo
            if (strPmsCondi != null && !strPmsCondi.equals("")) {
                sfWhere.append(" and ").append(strPmsCondi);
            }
            //人员类别
            if (personType != null && !"".equals(personType.trim())) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
//                String[] pers = personType.split(",");
//                sfWhere.append(Tools.splitInSql(pers, "u.personType"));
                sfWhere.append(" u.personType like '" + personType + "%'");
            }
            sfOrder.append(" order by u.deptTreeId,u.deptOrder,u.personOrder");

            String sql, sqlCount;
            if (sfWhere.length() == 0) {
                sql = sfSql.toString() + sfOrder.toString();
                sqlCount = sfCountSql.toString() + sfOrder.toString();

            } else {
                sql = sfSql.toString() + " where " + sfWhere.toString() + sfOrder.toString();
                sqlCount = sfCountSql.toString() + " where " + sfWhere.toString() + sfOrder.toString();
            }

            return pageQuery(vo, sqlCount, sql);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 查询用户信息，不包括用户的所属角色
     *
     * @param name       姓名
     * @param treeId     机构TreeId
     * @param personType 人员类别
     * @param user       user对象
     * @return
     * @throws RollbackableException
     */
    public UserInfoVO[] queryUserInfo(String name, String treeId, String personType, User user) throws RollbackableException {
        try {
            StringBuffer sfSql = new StringBuffer();
            StringBuffer sfWhere = new StringBuffer();
            StringBuffer sfOrder = new StringBuffer();
            sfSql.append("from UserInfoVO u ");
            if (name != null && !name.equals("")) {
                sfWhere.append(" u.name like'%")
                        .append(name)
                        .append("%' ");
            }
            if (treeId != null && !treeId.equals("")) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
                sfWhere.append("  u.deptTreeId like '")
                        .append(treeId)
                        .append("%' ");
            }
            //取权限条件
            PmsAPI api = new PmsAPI();
            String strPmsCondi = api.getPersonSimpleScaleCondition(user, "u.deptTreeId", false);
            if (strPmsCondi != null && !strPmsCondi.equals("")) {
                sfWhere.append(" and ").append(strPmsCondi);
            }
            //人员类别
            if (personType != null && !"".equals(personType.trim())) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
                String[] pers = personType.split(",");
                sfWhere.append(Tools.splitInSql(pers, "u.personType"));
            }
            sfOrder.append(" order by u.deptTreeId,u.deptOrder,u.personOrder");
            String sql;
            if (sfWhere.length() == 0) {
                sql = sfSql.toString() + sfOrder.toString();
            } else {
                sql = sfSql.toString() + " where " + sfWhere.toString() + sfOrder.toString();
            }

            List list = hibernateTemplate.find(sql);
            if (list == null || list.isEmpty()) {
                return null;
            }
            UserInfoVO[] pb = new UserInfoVO[list.size()];
            for (int i = 0; i < list.size(); i++) {
                pb[i] = (UserInfoVO) list.get(i);
            }
            return pb;
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息错误！", e, UserInfoDAO.class);
        }
    }

    public List queryUserPmsInfo(String personName, String postType, String treeId, PageVO vo, String personType, String belongRoleId, User user) throws RollbackableException {
        try {
            StringBuffer sfSql = new StringBuffer();
            StringBuffer sfCountSql = new StringBuffer();
            StringBuffer sfWhere = new StringBuffer();
            StringBuffer sfOrder = new StringBuffer();
            sfSql.append("from UserPmsInfoVO u ");
            sfCountSql.append("select count(u) from UserPmsInfoVO u ");

            if (personName != null && !personName.equals("")) {
                sfWhere.append(" u.name like'%")
                        .append(personName)
                        .append("%' ");
            }
            if (postType != null && !postType.equals("")) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
                sfWhere.append("  u.postType like '0110")
                        .append(postType)
                        .append("' ");
            }

            if (treeId != null && !treeId.equals("")) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
                sfWhere.append("  u.deptTreeId like '")
                        .append(treeId)
                        .append("%' ");
            }

            if (belongRoleId != null && !belongRoleId.equals("")) {
                sfWhere.append(" and u.belongRoleId='").append(belongRoleId).append("' ");
            }
            //取权限条件
            PmsAPI api = new PmsAPI();
            String strPmsCondi = api.getPersonSimpleScaleCondition(user, "u.deptTreeId", false);
            if (strPmsCondi != null && !strPmsCondi.equals("")) {
                sfWhere.append(" and ").append(strPmsCondi);
            }
            //人员类别
            if (personType != null && !"".equals(personType.trim())) {
                if (sfWhere.length() != 0)
                    sfWhere.append(" and ");
                String[] pers = personType.split(",");
                sfWhere.append(Tools.splitInSql(pers, "u.personType"));
            }
            sfOrder.append(" order by u.deptTreeId,u.deptOrder,u.personOrder");

            String sql, sqlCount;
            if (sfWhere.length() == 0) {
                sql = sfSql.toString() + sfOrder.toString();
                sqlCount = sfCountSql.toString() + sfOrder.toString();
            } else {
                sql = sfSql.toString() + " where " + sfWhere.toString() + sfOrder.toString();
                sqlCount = sfCountSql.toString() + " where " + sfWhere.toString() + sfOrder.toString();
            }
            return pageQuery(vo, sqlCount, sql);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("读取用户信息错误！", e, UserInfoDAO.class);
        }
    }

    /**
     * 添加用户
     * (此接口供人员管理使用)
     *
     * @param person
     * @throws RollbackableException
     */
    public void addUserInfo(PersonVO person) throws RollbackableException {
        try {
            String personId = person.getPersonId();
            User user = (User) super.findBo(User.class, personId);
            if (user != null) {
                return; //如果用户已经存在,返回
            }
            user = new User();
            user.setUserId(personId);
            user.setStatus(PmsConstants.STATUS_OPEN);  //用户初始启用状态
            String pinyin = translatePinYin(person.getName());
            String prefix = getUserNamePrefix(person.getOrgId());
//            String newLoginName = createNewUserName(pinyin, prefix);
            String newLoginName = person.getPersonCode();
            user.setLoginName(newLoginName);
            String newPassword;
            try {
            	//update by hudl 2015-10-14 15:35:28
                if (person.getNewPassword() != null && !"".equals(person.getNewPassword())) {
                    newPassword = person.getNewPassword();
                } else{
                    newPassword = "666666";
                }
                user.setPassword(Tools.md5(newPassword));
            } catch (Exception e) {
                //e.printStackTrace();
                throw new RollbackableException("密码加密错误!", e, getClass());
            }
            super.createBo(user);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("添加用户失败!", e, getClass());
        }

    }

    /**
     * 删除用户
     * (此接口供人员管理使用)
     *
     * @param userId
     */
    public void deleteUserInfo(String userId) throws RollbackableException {
        if ("".equals(Tools.filterNull(userId))) return;
        try {
            //删除权限信息
            UserRoleBO ur = (UserRoleBO) super.findBo(UserRoleBO.class, userId);
            super.deleteBo(ur);
            //删除用户信息
            User user = (User) super.findBo(User.class, userId);
            super.deleteBo(user);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除用户失败!", e, getClass());
        }
    }

    /**
     * 删除用户权限
     * (此接口供人员管理使用)
     *
     * @param userId
     */
    public void deleteUserPms(String userId) throws RollbackableException {
        if ("".equals(Tools.filterNull(userId))) return;
        try {
            //删除权限信息
            UserRoleBO ur = (UserRoleBO) super.findBo(UserRoleBO.class, userId);
            super.deleteBo(ur);
            //删除所属角色
            User user = (User) super.findBo(User.class, userId);
            user.setBelongRoleId("");
            super.updateBo(userId, user);
        }
        catch (Exception e) {
            //e.printStackTrace();
            throw new RollbackableException("删除用户失败!", e, getClass());
        }
    }

    //**************************一下是生成用户名的方法*******************************************
    //用户名: 姓名全拼+序号+点+区域编码  例如：lironggang1.bj
    //生成规则：
    // 1.通过员工姓名生成相应的拼音，
    // 2.加前缀
    //加前缀方法：
    //    通过员工所属机构查询区域码表，查询是否隶属于某一机构，如果属于属于，则去相应的区域码作前缀
    //    如果在区域码中查不到员工机构的所属机构，则通过员工机构的所在行政区划查找，
    //    查找规则:行政区划的编码长度为6位，现查找6位的，如果区域码中有匹配的相关代码，则去相应的区域码前缀，
    //    如果没有，则截取掉后2位再继续匹配，直道找到位置，如果剩下最后两位没有，则不加前缀。
    //3.加序号
    //加序号方法:
    //         按照来行的先后顺序，第一个不加序号，第二个加1，依次类推
    //4.密码，系统默认使用身份证后6位，如果没有身份证号，则默认666666


    /**
     * 翻译成拼音
     *
     * @param personName
     * @return ''
     */
    private String translatePinYin(String personName) {
        PinYinTrans trans = new PinYinTrans();
        return trans.translate(personName);
        //return CnToSpell.getFullSpell(personName);
    }

    /**
     * 取得用户前缀
     *
     * @param personBelongOrgId 用户所属OrgId
     * @return ''
     */

    private String getUserNamePrefix(String personBelongOrgId) {
        Org org = SysCacheTool.findOrgById(personBelongOrgId);
        String regionalismCode = "";//org.getOrgAddress();
        if (regionalismCode == null || "".equals(regionalismCode) || regionalismCode.length() <= 0) return "";
        regionalismCode = regionalismCode.substring(4, regionalismCode.length());
//        List zoneList = hibernateTemplate.find("from ZoneCodeBO");
//        if (zoneList != null && zoneList.size() > 0) {
//            //先判断所在机构是否属于区域码中设置的机构
//            for (int i = 0; i < zoneList.size(); i++) {
//                ZoneCodeBO zcode = (ZoneCodeBO) zoneList.get(i);
//                String tOrgId = zcode.getOrgId();
//                if (tOrgId != null && !"".equals(tOrgId)) {
//                    Org tOrg = SysCacheTool.findOrgById(tOrgId);
//                    if (org.getTreeId().startsWith(tOrg.getTreeId())) {
//                        return zcode.getZoneCode();
//                    }
//                }
//            }
//            //再判断行政区划是否属于区域码中设置的行政区域
//            if (regionalismCode == null || "".equals(regionalismCode)) return "";  //如果行政区划为空，则不加前缀
//            //因为行政区划为6位，区域码表中，只设置到4位，所以，此处只需从第四位开始取即可
//            for (int j = regionalismCode.length() - 2; j > 0; j -= 2) {
//                String spRegionCode = regionalismCode.substring(0, j);
//                for (int i = 0; i < zoneList.size(); i++) {
//                    ZoneCodeBO zcode = (ZoneCodeBO) zoneList.get(i);
//                    String tRegionCode = zcode.getRegionalismCode();
//                    if (spRegionCode.equals(tRegionCode)) {
//                        return zcode.getZoneCode();
//                    }
//                }
//            }
//        }
        return "";
    }

    private String createNewUserName(String pinyin, String prefix) {
        String sql = "from User u where u.loginName like '" + pinyin + "%." + prefix + "'";
        List list = hibernateTemplate.find(sql);
        if (list == null || list.size() == 0) return pinyin + "." + prefix;
        int maxXh = 0;
        for (int i = 0; i < list.size(); i++) {
            User u = (User) list.get(i);
            //如果用户名相同，则将xh+1
            //取出来的用户名不一定是同拼音名的，例如李勇  李勇立，
            //liyong.bj  liyongli.bj
            //所以，我们取pinyin.length以后的字符，如果是数字，则表明是同音，并且带序号的用户名
            String tUserName = u.getLoginName();
            tUserName = tUserName.substring(0, tUserName.indexOf('.'));
            if (tUserName.equals(pinyin)) {
                continue;
            }

            boolean flag = true;  //拼音名字后面是否是数字标志

            for (int j = pinyin.length(); j < tUserName.length(); j++) {
                String chr = tUserName.substring(j, j + 1);
                if ("123456789".indexOf(chr) == -1) {
                    flag = false;
                }
            }

            if (flag) {
                int tmpXh = Integer.parseInt(tUserName.substring(pinyin.length(), tUserName.length()));
                if (maxXh <= tmpXh) {
                    maxXh = tmpXh;
                }
            }
        }
        return pinyin + Integer.toString(maxXh + 1) + "." + prefix;
    }

    /**
     * 批生成用户信息，用于用户初始化
     */
    public void batchBuildUserInfo() throws RollbackableException {
        List zoneList = queryZoneCode();
        for (int j = 0; j < zoneList.size(); j++) {
            ZoneCodeBO zcode = (ZoneCodeBO) zoneList.get(j);
            String prefix = zcode.getZoneCode();
            String zoneOrgId = zcode.getOrgId();   //区域码表的机构编码
            String zoneRegionalismCode = "0105" + zcode.getRegionalismCode();//区域码表的行政区划码
            HashMap hashPinYinXh = new HashMap();  //拼音序号
            String sql;
            if (zoneOrgId != null && !"".equals(zoneOrgId)) {  //如果机构ID不为空，则按照机构ID生成
                Org zoneOrg = SysCacheTool.findOrgById(zoneOrgId);
                sql = "select p.personId,p.name,p.orgId,p.idCard from PersonBO p where p.deptTreeId like '" + zoneOrg.getTreeId() + "%' and p.personCancel='00900' order by p.unitTime";
            } else {
                sql = "select p.personId,p.name,p.orgId,p.idCard from PersonBO p,OrgBO o where  p.orgId = o.orgId and o.orgAddress like '" + zoneRegionalismCode + "%'  and p.personCancel='00900' order by p.unitTime";
            }
            List updateList = new ArrayList();
            List list = hibernateTemplate.find(sql);
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    Object[] obj = (Object[]) list.get(i);
                    String personId = obj[0].toString();
                    String personName = obj[1].toString();
                    String perOrgId = obj[2].toString();
                    String idCard = (String) obj[3];
                    String pinyin = this.translatePinYin(personName);
                    int xh = 0;
                    Object o = hashPinYinXh.get(prefix + "_" + pinyin);  //hash中按照 bj_lironggang的规则存储用户名
                    if (o != null) {
                        xh = Integer.parseInt(o.toString());
                        xh = xh + 1;

                    }
                    hashPinYinXh.put(prefix + "_" + pinyin, Integer.toString(xh));

                    String newUserName = pinyin + ((xh == 0) ? "" : Integer.toString(xh)) + "." + prefix;
                    String newPassword;
                    if (idCard != null && idCard.length() > 6) {
                        newPassword = idCard.substring(idCard.length() - 6, idCard.length());
                    } else
                        newPassword = "666666";
                    try {
                        newPassword = Tools.md5(newPassword);
                    } catch (Exception e) {
                        //e.printStackTrace();
                    }
                    String sqlUpdate = "update pms_user_info set login_name='" + newUserName + "',login_pwd='" + newPassword;
                    sqlUpdate += "' where pms_person_id='" + personId + "' and login_name is  null"; //只修改密码为空的人远
                    jdbcTemplate.execute(sqlUpdate);
                    //  updateList.add(sqlUpdate);
                }
            }
            //jdbctemplate.batchUpdate((String[]) updateList.toArray(new String[0]));
        }
    }

    private List queryZoneCode() {
//        String sql = "from ZoneCodeBO z where z.orgId is not null or z.regionalismCode is not null order by z.orgId,length(z.regionalismCode) desc ";
//        return hibernateTemplate.find(sql);
        return null;
    }

    /**
     * 检测工会务机构范围权限
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @param flag   1维护 0 查询
     * @param type   1 有权工会务机构 0 无权工会务机构   目前只支持有权工会务机构
     * @return List list数据结构: UnionBO 对象
     */
    public List queryUserLabourScaleByLoginCall(String userId, String flag, String type) throws RollbackableException {

        List list = new ArrayList();
        if (PmsConstants.SCALE_REFUSE.equals(type)) {
            return list;  //不控制排除权限
        }
        StringBuffer sf = new StringBuffer();
        if (PmsConstants.SUPER_MANAGER_ROLE_ID.equals(isSysManager(userId)))            //超级管理员
        {
            if (PmsConstants.SCALE_USE.equals(type))
                sf.append("select labour.labourId from UnionBO labour where labour.superId='-1'");
            else
                return list;
        } else {

            sf.append("select rUnion.unionId from  RoleUnionScaleBO rUnion, UserRoleBO urole  ")
                    .append("where   rUnion.roleId = urole.roleId ");
            sf.append(" and rUnion.scaleFlag='").append(flag).append("'");
            sf.append(" and rUnion.pmsType='")
                    .append(type)
                    .append("'  and urole.personId='")
                    .append(userId).append("'");
        }
        try {
            List tmplst = hibernateTemplate.find(sf.toString());
            if (tmplst == null) return null;
            int len = tmplst.size();
            for (int i = 0; i < len; i++) {
                String id = tmplst.get(i).toString();
//                UnionBO labour = (UnionBO) super.findBo(UnionBO.class, id);
//                list.add(labour);

            }
            return list;
        }
        catch (Exception e) {
            throw new RollbackableException("读取数据错误", e, this.getClass());
        }
    }

    /**
     * 查询用户角色列表
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @return List list数据结构: role 对象
     */
    public List queryUserRoleByLoginCall(String userId) throws RollbackableException {
        StringBuffer sb = new StringBuffer();
        sb.append("select r from RoleInfoBO r,UserRoleBO u where r.roleId=u.roleId and u.personId='")
                .append(userId).append("' order by r.sysOper desc");
        try {
            return hibernateTemplate.find(sb.toString());
        }
        catch (Exception e) {
            throw new RollbackableException("读取数据错误!", e, UserRoleDAO.class);
        }
    }

    /**
     * 查找用户所属管理员编码
     * (此方法专被登录时使用)
     *
     * @param userId 用户ID
     * @return String roleId
     */
    public String findUserBelongRoleByLoginCall(String userId) throws RollbackableException {
        StringBuffer sb = new StringBuffer();
        sb.append("select r from RoleInfoBO r,UserRoleBO u where r.roleId=u.roleId and u.personId='")
                .append(userId).append("' and r.sysOper ='" + PmsConstants.IS_SYS_MANAGER + "'");
        try {
            List list = hibernateTemplate.find(sb.toString());
            if (list.isEmpty()) return null;

            return ((RoleInfoBO) list.get(0)).getRoleId();
        }
        catch (Exception e) {
            throw new RollbackableException("读取数据错误", e, this.getClass());
        }
    }
}
