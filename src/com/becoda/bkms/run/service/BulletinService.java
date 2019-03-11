package com.becoda.bkms.run.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.run.dao.BulletinContentDAO;
import com.becoda.bkms.run.dao.BulletinParamDAO;
import com.becoda.bkms.run.dao.BulletinScopeDAO;
import com.becoda.bkms.run.pojo.bo.BulletinContentBO;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.pojo.bo.BulletinScopeBO;
import com.becoda.bkms.run.pojo.vo.BulletinForm;
import com.becoda.bkms.run.util.RunTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-3-12
 * Time: 15:15:58
 * To change this template use File | Settings | File Templates.
 */
public class BulletinService {
    private BulletinContentDAO bulletinContentDAO;
    private BulletinParamDAO bulletinParamDAO;
    private BulletinScopeDAO bulletinScopeDAO;

    /**
     * 功能：根据公告ID查询公告信息和正文内容<br>
     * 调用BulletintParamDAO.findByBlltnId()方法和
     * BulletinContentDAO.findByBlltnId()方法<br>
     *
     * @param BulletinId 公告ID<br>
     * @return HashMap 公告信息和正文内容<br>
     *         HashMap包含topic,content,authorId,,submitDate,startDate,endDate<br>
     */
    public HashMap queryBulletinParamAndContent(String BulletinId) throws RollbackableException {
        try {
            HashMap map = new HashMap();
            BulletinParamBO parambo = null;
            BulletinContentBO contentbo = null;
            if (bulletinParamDAO.findBoById(BulletinParamBO.class, BulletinId) != null) {
                parambo = (BulletinParamBO) bulletinParamDAO.findBoById(BulletinParamBO.class, BulletinId);
                map.put("topic", parambo.getBlltnTopic());
                map.put("endDate", parambo.getEndDate());
                map.put("startDate", parambo.getStartDate());
                map.put("submitDate", parambo.getSubmitDate());
                map.put("authorId", parambo.getAuthorId());
                map.put("createOrgId", parambo.getCreateOrgId());
            }
            if (bulletinContentDAO.findBoById(BulletinContentBO.class, BulletinId) != null) {
                contentbo = (BulletinContentBO) bulletinContentDAO.findBoById(BulletinContentBO.class, BulletinId);
                map.put("content", contentbo.getBlltnContent());
            }
            return map;
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("查询公告信息和正文内容失败", e, this.getClass());
        }
    }

    /**
     * 功能：根据User对象查询公告列表<br>
     * 解析user对象中的用户机构和用户类型，
     * 调用this.queryBulletinParam()得到公告列表。
     */
    public BulletinParamBO[] queryBulletinParam(User user) throws RollbackableException {
        try {
            //OrgId = user.getOrgId();//注：getOrgId换成get所在部门
            String UserId = user.getUserId();
            Person pbo = SysCacheTool.findPersonById(UserId);
            String OrgId = pbo.getDeptId();
            String ReaderType;
            if (user.isBusinessUser() || user.isSysOper()) {
                ReaderType = "02";
            } else {
                ReaderType = "01";
            }
            String OrgIds = null;
            if (OrgId != null) {
                //下面是循环取其上级机构的orgid并加到OrgIds串里
                while (!OrgId.equals("-1")) {
                    if (OrgIds == null) {
                        OrgIds = "'" + OrgId + "'";
                    } else {
                        OrgIds = OrgIds + ",'" + OrgId + "'";
                    }
                    OrgId = SysCacheTool.findOrgById(OrgId).getSuperId();
                }
            }
            BulletinScopeBO[] scopelist = bulletinScopeDAO.findByOrgIds(OrgIds);
            String strblltnIdList = null;
            if (scopelist != null) {
                BulletinScopeBO bo = null;
                for (int i = 0; i < scopelist.length; i++) {
                    bo = scopelist[i];
                    if (strblltnIdList == null) {
                        strblltnIdList = "'" + bo.getBlltnId() + "'";
                    } else {
                        strblltnIdList = strblltnIdList + ",'" + bo.getBlltnId() + "'";
                    }
                }
            }
            if (strblltnIdList != null) {
                return bulletinParamDAO.queryByBlltnIdListAndReaderType(strblltnIdList, ReaderType);
            }
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("查询公告列表失败", e, this.getClass());
        }
        return null;
    }

    public void updateBulletin(BulletinForm vo, String userId, String orgId) throws RollbackableException {
        try {
            BulletinParamBO parambo = new BulletinParamBO();
            BulletinContentBO contentbo = new BulletinContentBO();
            String scopeOrgIds = null;
            String[] BulletinScope = null;

            parambo.setBlltnId(vo.getBulletinId());
            parambo.setBlltnTopic(vo.getTopic());
            parambo.setStartDate(vo.getStartDate());
            parambo.setEndDate(vo.getEndDate());
            parambo.setReaderType(vo.getReaderType());
            parambo.setAuthorId(userId);
            parambo.setCreateOrgId(orgId);
            parambo.setSubmitDate(RunTools.getDate10());

            contentbo.setBlltnId(vo.getBulletinId());
            contentbo.setBlltnContent(vo.getContent());

            scopeOrgIds = vo.getScopeOrgIds();
            if (scopeOrgIds != null && scopeOrgIds.length() > 0) {
                BulletinScope = scopeOrgIds.split("\\,");
            }

            bulletinParamDAO.updateBo(parambo.getBlltnId(), parambo);
            bulletinContentDAO.updateBo(contentbo.getBlltnId(), contentbo);
            String bulletinId = vo.getBulletinId();
            if (bulletinId != null && bulletinId.length() > 0) {
                BulletinScopeBO[] bolist = bulletinScopeDAO.findById(bulletinId);
                if (bolist != null) {
                    for (int j = 0; j < bolist.length; j++) {
                        BulletinScopeBO scopebo = bolist[j];
                        BulletinScopeBO bo = (BulletinScopeBO) bulletinScopeDAO.findBo(scopebo.getClass(), scopebo.getSerialId());
                        bulletinScopeDAO.deleteBo(bo);
                    }
                }
                for (int i = 0; i < BulletinScope.length; i++) {
                    BulletinScopeBO scopebo = new BulletinScopeBO();
                    scopebo.setBlltnId(bulletinId);
                    scopebo.setOrgId(BulletinScope[i]);
                    bulletinScopeDAO.createBo(scopebo);  //新增
                }
            }

        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("修改公告失败", e, this.getClass());
        }

    }

    /**
     * 功能：创建公告<br>
     *
     * @param vo BulletinEditVO
     */
    public void createBulletin(BulletinForm vo, String userId, String orgId) throws RollbackableException {
        try {
            BulletinParamBO parambo = new BulletinParamBO();
            String BulletinContent = null;
            String[] BulletinScopeIds = null;
            parambo.setBlltnTopic(vo.getTopic());
            parambo.setStartDate(vo.getStartDate());
            parambo.setEndDate(vo.getEndDate());
            parambo.setReaderType(vo.getReaderType());
            parambo.setAuthorId(userId);
            parambo.setCreateOrgId(orgId);
            parambo.setSubmitDate(RunTools.getDate10());
            BulletinContent = vo.getContent();

            if (vo.getScopeOrgIds() != null) {
                BulletinScopeIds = vo.getScopeOrgIds().split("\\,");
            }
            String BulletinId = bulletinParamDAO.createBo(parambo);  //新增

            if (BulletinId != null && BulletinId.length() > 0) {
                BulletinContentBO contentbo = new BulletinContentBO();
                contentbo.setBlltnId(BulletinId);
                contentbo.setBlltnContent(BulletinContent);

                bulletinContentDAO.createBo(contentbo);  //新增
                for (int i = 0; i < BulletinScopeIds.length; i++) {
                    BulletinScopeBO scopebo = new BulletinScopeBO();
                    scopebo.setBlltnId(BulletinId);
                    scopebo.setOrgId(BulletinScopeIds[i]);
                    bulletinScopeDAO.createBo(scopebo);  //新增
                }
            }

        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("创建公告失败", e, this.getClass());
        }
    }

    /**
     * 功能：根据公告ID数组删除一批公告记录<br>
     * 调用BulletinContentDAO.deleteByBlltnId()方法；
     * 调用BulletinScopeDAO.deleteByBlltnId()方法；
     * 调用BulletinParamDAO.delete()方法；<br>
     *
     * @param bulletinIdArray [] 公告ID数组<br>
     * @throws RollbackableException
     */
    public void deleteBulletinByBulletinIdArray(String bulletinIdArray[]) throws RollbackableException {
        try {
            String id = null;
            for (int i = 0; i < bulletinIdArray.length; i++) {
                id = bulletinIdArray[i].trim();
                bulletinContentDAO.deleteBo(BulletinContentBO.class, id);
                bulletinContentDAO.deleteBo(BulletinParamBO.class, id);
                //下面是删除BulletinScope表的内容
                BulletinScopeBO[] bolist = bulletinScopeDAO.findById(id);
                if (bolist != null) {
                    for (int j = 0; j < bolist.length; j++) {
                        BulletinScopeBO scopebo = bolist[j];
                        bulletinContentDAO.deleteBo(BulletinScopeBO.class, scopebo.getSerialId());
                    }
                }
            }

        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("删除公告失败", e, this.getClass());
        }
    }

    public List queryBulletinParamAndScopeByCreateOrgId(String topicQry, String dateQry, String orgId, PageVO vo) throws RollbackableException {
        List list_ret = new ArrayList();
        try {
            String scopeOrgIds = null;
            String scopeOrgNames = null;
            BulletinParamBO parambo = null;
            BulletinScopeBO scopebo = new BulletinScopeBO();
            HashMap map = null;

            String blltnId = null;
            String authorId = null;
            String submitDate = null;
            String blltnTopic = null;
            String startDate = null;
            String endDate = null;
            String organScope = null;
            String readerType = null;
            String createOrgId = null;

            BulletinParamBO[] parambolist = bulletinParamDAO.queryByCreateOrgId(topicQry, dateQry, orgId, vo);
            if (parambolist != null && parambolist.length > 0) {
                for (int i = 0; i < parambolist.length; i++) {
                    parambo = parambolist[i];
                    blltnId = parambo.getBlltnId();
                    authorId = parambo.getAuthorId();
                    blltnTopic = parambo.getBlltnTopic();
                    submitDate = parambo.getSubmitDate();
                    startDate = parambo.getStartDate();
                    endDate = parambo.getEndDate();
                    readerType = parambo.getReaderType();

                    map = new HashMap();
                    map.put("blltnId", blltnId);
                    map.put("authorId", authorId);
                    map.put("blltnTopic", blltnTopic);
                    map.put("submitDate", submitDate);
                    map.put("startDate", startDate);
                    map.put("endDate", endDate);

                    BulletinScopeBO[] scopebolist = bulletinScopeDAO.findById(blltnId);
                    if (scopebolist != null && scopebolist.length > 0) {
                        scopeOrgIds = "";
                        scopeOrgNames = "";
                        Org org = null;
                        String tmpOrgName = "";
                        for (int j = 0; j < scopebolist.length; j++) {
                            scopebo = scopebolist[j];
                            scopeOrgIds = scopebo.getOrgId();
                            org = SysCacheTool.findOrgById(scopeOrgIds);
                            if (org != null) {
                                tmpOrgName = org.getName();
                            }
                            scopeOrgNames = scopeOrgNames + tmpOrgName + ",";
                        }
//                        scopeOrgNames = "北京行;上海行;";
//                           scopeOrgNames=scopeOrgIds; //此句正式使用时需删除
                        if (readerType.equals("01"))
                            readerType = "全体人员";
                        else if (readerType.equals("02"))
                            readerType = "HR人员";
                        organScope = scopeOrgNames + "(" + readerType + ")";
                    }
                    map.put("organScope", organScope);
                    list_ret.add(map);
                }
            }
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("查询失败", e, this.getClass());
        }
        return list_ret;
    }

    //
    public String findBulletinContent(String BulletinId) throws RollbackableException {
        try {
            BulletinContentBO bo =
                    (BulletinContentBO) bulletinContentDAO.findBoById(BulletinContentBO.class, BulletinId);

            return bo.getBlltnContent();
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("获取公告内容失败", e, this.getClass());
        }
    }

    public BulletinParamBO findBulletinParamByBulletinId(String BulletinId) throws RollbackableException {
        try {
            return
                    (BulletinParamBO) bulletinParamDAO.findBoById(BulletinParamBO.class, BulletinId);
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("获取公告内容失败", e, this.getClass());
        }
    }

    public String[] findBulletinScopeByBulletinId(String BulletinId) throws RollbackableException {
        String[] ret = null;
        try {
            BulletinScopeBO bo;
            BulletinScopeBO[] listbo = bulletinScopeDAO.findById(BulletinId);
            if (listbo != null) {
                ret = new String[listbo.length];
                for (int i = 0; i < listbo.length; i++) {
                    bo = (BulletinScopeBO) listbo[i];
                    ret[i] = bo.getOrgId();
                }
            }
        } catch (Exception e) {
            //将manager中抛出的所有非RollbackableException、非DAOException异常包装成一个RollbackableException异常抛给action
            throw new RollbackableException("查询公告机构范围失败", e, this.getClass());
        }
        return ret;
    }

    public BulletinContentDAO getBulletinContentDAO() {
        return bulletinContentDAO;
    }

    public void setBulletinContentDAO(BulletinContentDAO bulletinContentDAO) {
        this.bulletinContentDAO = bulletinContentDAO;
    }

    public BulletinParamDAO getBulletinParamDAO() {
        return bulletinParamDAO;
    }

    public void setBulletinParamDAO(BulletinParamDAO bulletinParamDAO) {
        this.bulletinParamDAO = bulletinParamDAO;
    }

    public BulletinScopeDAO getBulletinScopeDAO() {
        return bulletinScopeDAO;
    }

    public void setBulletinScopeDAO(BulletinScopeDAO bulletinScopeDAO) {
        this.bulletinScopeDAO = bulletinScopeDAO;
    }
}
