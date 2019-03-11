package com.becoda.bkms.cache;

import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.bo.PersonCode;
import com.becoda.bkms.org.OrgConstants;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.dao.ActivePageDAO;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.CodeSetBO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoSetBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User: kangdw
 * Date: 2015-5-13
 * Time: 14:18:04
 */
public class SysCacheTool {

    /**
     * 翻译代码  kangdw 090826
     *
     * @param code
     * @return 翻译后的中文,若没找到 返回""
     */
    public static String interpretCode(String code) {
        CodeItemBO obj = findCodeItem(code);
        if (obj != null) return Tools.filterNull(obj.getItemName());
        else return Tools.filterNull(code);
    }

    public static String interpretCodes(String codes) {
        String codeName = "";
        if (codes != null) {
            String[] ids = codes.split(",");
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    if (i == 0) {
                        codeName += interpretCode(ids[i]);
                    } else {
                        codeName += "," + interpretCode(ids[i]);
                    }
                }
            }
        }
        return codeName;
    }

    /**
     * 翻译代码   kangdw 090826
     *
     * @param setId
     * @param code
     * @return 翻译后的中文,若没找到 返回""
     * @deprecated
     */
    public static String interpretCode(String setId, String code) {
        return interpretCode(code);
    }

    /**
     * 翻译代码集名称  kangdw 090826
     *
     * @param setId
     * @return 翻译后的中文,若没找到 返回""
     */
    public static String interpretCodeSet(String setId) {
        CodeSetBO set = findCodeSet(setId);
        if (set != null) return Tools.filterNull(set.getSetName());
        else return setId;
    }

    /**
     * 翻译信息集名称   kangdw 090826
     *
     * @param code
     * @return 翻译后的中文,若没找到 返回""
     */
    public static String interpretInfoSet(String code) {
        InfoSetBO obj = findInfoSet(code);
        if (obj != null) return Tools.filterNull(obj.getSetName());
        else return code;
    }

    public static String interpretInfoItem(String setId, String code) {
        InfoItemBO obj = findInfoItem(setId, code);
        if (obj != null) return Tools.filterNull(obj.getItemName());
        else return code;
    }

    public static String interpretOrg(String orgId) {
        Org obj = (Org) findObject(CacheConstants.OBJ_ORG, null, orgId);
        if (obj != null && OrgConstants.LEVEL_ZX.equals(obj.getOrgLevel())) { //如果是二级部门，显示机构全称
            return Tools.filterNull(obj.getOrgHeader());
        } else if (obj != null) {
            return Tools.filterNull(obj.getName());
        } else return Tools.filterNull(orgId);
    }

    /**
     * 得到机构全称
     *
     * @param orgId
     * @return
     */
    public static String interpretOrgAllName(String orgId) {
        Org obj = (Org) findObject(CacheConstants.OBJ_ORG, null, orgId);
        if (obj != null && OrgConstants.LEVEL_ZX.equals(obj.getOrgLevel())) { //如果是二级部门，显示机构全称
            return Tools.filterNull(obj.getOrgHeader());
        } else if (obj != null) {
            return Tools.filterNull(obj.getOrgHeader());
        } else return Tools.filterNull(orgId);
    }

    /**
     * @param orgId 逗号“，”分隔的多个机构编号
     * @return
     */
    public static String interpretOrgs(String orgId) {
        String orgName = "";
        if (orgId != null) {
            String[] ids = orgId.split(",");
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    if (i == 0) {
                        orgName += interpretOrg(ids[i]);
                    } else {
                        orgName += "," + interpretOrg(ids[i]);
                    }
                }
            }
        }
        return orgName;
    }


    public static String interpretPerson(String personId) {
        Person obj = (Person) findObject(CacheConstants.OBJ_PERSON, null, personId);
        if (obj != null) return Tools.filterNull(obj.getName());
        else return Tools.filterNull(personId);
    }

    public static String interpretPost(String postId) {
        if (postId == null || postId.length() == 0) return "";
//        PostBO obj = (PostBO) findObject(CacheConstants.OBJ_POST, null, postId);
//        if(obj!=null)
//        return Tools.filterNull(obj.getName());
//        return "";
        return interpretCode(postId) ;
    }

//    public static String interpretWageUnit(String unitId) {
//        return interpretOrg(unitId);
//        //  kangdw 090826  return SysCacheTool.interpretOrg(unitId);
//    }


    /**
     * 查找数据对象
     * kangdw 090826  将静态hash废除,放入二级缓存
     *
     * @param operObj  数据对象类型
     * @param dict_num 如果数据对象是指标项或者代码项则为指标集或者代码集的ID，否则为""或者为null
     * @param code     唯一Id
     * @return 数据对象
     */
    private static Object findObject(int operObj, String dict_num, String code) {
        Object obj = null;
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            if (code != null && !"".equals(code.trim())) {
                switch (operObj) {
                    case CacheConstants.OBJ_CODESET:
                        obj = ht.get(CodeSetBO.class, code);
                        break;
                    case CacheConstants.OBJ_CODEITEM:
                        //todo 代码是否是全局唯一的 ,需要调整这部分程序,目前是按照代码全局唯一设计的
                        if (true) {//todo  这里可以做成开关 代码是否全局唯一
                            obj = ht.get(CodeItemBO.class, code);
                        } else {
                            List l = ht.find("from CodeItemBO c where c.setId = ? and c.itemId =?", new Object[]{dict_num, code});
                            if (!l.isEmpty()) {
                                obj = l.get(0);
                            }
                        }
                        break;
                    case CacheConstants.OBJ_INFOSET:
                        obj = ht.get(InfoSetBO.class, code);
                        break;
                    case CacheConstants.OBJ_INFOITEM:
                        if ((dict_num == null || "".equals(dict_num)) && code.length() == 7)
                            dict_num = code.substring(0, 4);

                        List l = ht.find("from InfoItemBO c where c.setId = ? and c.itemId =?", new Object[]{dict_num, code});
                        if (!l.isEmpty()) {
                            obj = l.get(0);
                        }
                        break;
                    case CacheConstants.OBJ_ORG:
                        obj = ht.get(Org.class, code); //使用org 而不是用orgbo 因为org内字段少,减轻cache压力
                        break;
//                    case CacheConstants.OBJ_POST:
//                        obj = ht.get(PostBO.class, code);
//                        break;
                    case CacheConstants.OBJ_PERSON:
                        obj = ht.get(Person.class, code);
//                        List lp = ht.find("from Person c where c.personId =?", code);
//                        if (lp != null && lp.size() > 0) {
//                            obj = lp.get(0);
//                        }
                        break;
                }
            }
            Object detatchPo = null;
            if (obj != null) {
                detatchPo = obj.getClass().newInstance();
                Tools.copyProperties(detatchPo, obj);
            }
            return detatchPo;
        } catch (Exception e) {
            new BkmsException(e, SysCacheTool.class);
            return null;
        }
    }

    /**
     * 查询下级数据对象
     *
     * @param operObj  数据对象类型。主要是机构、代码项、指标项 、党务机构、发薪单位
     * @param dict_num 如果数据对象是指标项或者代码项,则为指标集或者代码集的ID，否则为""或者为null
     * @param superId  如果数据对象是指标项, superId为null
     * @return 下级数据对象
     * @deprecated
     */
     public static ArrayList querySubObject(int operObj, String dict_num, String superId) {
        List list = new ArrayList();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);

            if (CacheConstants.OBJ_CODEITEM == operObj) {

                if (superId == null || "".equals(superId))
                    return (ArrayList) list;
                else if (dict_num == null || "".equals(dict_num))
                    dict_num = superId.substring(0, 4);
            }
            switch (operObj) {
                case CacheConstants.OBJ_CODEITEM:
                    if(superId.equals(dict_num)){
                        list = ht.find("from CodeItemBO c where c.setId=? order by c.treeId", new Object[]{dict_num});
                    }else{
                        list = ht.find("from CodeItemBO c where c.itemSuper = ? and c.setId=? order by c.treeId", new Object[]{superId, dict_num});
                    }
                    break;
                case CacheConstants.OBJ_INFOITEM:
                    list = ht.find("from InfoItemBO i where i.setId =? order by i.itemSequence+0", dict_num);
                    break;
                case CacheConstants.OBJ_ORG:
                    list = ht.find("from Org o where o.superId = ? order by o.orgSort", superId);
                    break;
            }
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
            return (ArrayList) list;
        } catch (Exception e) {
            new BkmsException(e, SysCacheTool.class);
        }

        return new ArrayList();
    }

    public static HashMap querySubOrgNumByUnit(String unitId) {
        String sql = "select a.id," +
                "       nvl((select count(id) as orgcount " +
                "       from b001 " +
                "       where b001.b001002 = a.id " +
                "       group by b001002),  0) as sub_num " +
                "  from b001 a  where b001002 = '" + unitId + "' ";
        try {
            ActivePageDAO activePageDAO = (ActivePageDAO) BkmsContext.getBean("sys_activePageDAO");
            List list = activePageDAO.queryForList(sql);
            HashMap hash = new HashMap();
            if (list != null) {
                int count = list.size();
                for (int i = 0; i < count; i++) {
                    Map m = (Map) list.get(i);
                    hash.put(m.get("id"), m.get("sub_num"));
                }
            }
            return hash;
        } catch (Exception e) {
            e.printStackTrace();
            new BkmsException("数据！", SysCacheTool.class);
        }
        return null;
    }


    public static InfoSetBO findInfoSet(String setId) {
        return (InfoSetBO) findObject(CacheConstants.OBJ_INFOSET, "", setId);
    }

    public static InfoItemBO findInfoItem(String setId, String itemId) {
        return (InfoItemBO) findObject(CacheConstants.OBJ_INFOITEM, setId, itemId.trim());
    }

    public static ArrayList queryInfoItemBySetId(String setId) {
//        ArrayList list = new ArrayList();
        return querySubObject(CacheConstants.OBJ_INFOITEM, setId, null);
    }

    public static CodeSetBO findCodeSet(String setId) {
        return (CodeSetBO) findObject(CacheConstants.OBJ_CODESET, "", setId);
    }

    public static CodeItemBO findCodeItem(String itemId) {
        return (CodeItemBO) findObject(CacheConstants.OBJ_CODEITEM, null, itemId);
    }

    /**
     * @param setId
     * @param itemId
     * @return
     * @deprecated
     */
    public static CodeItemBO findCodeItem(String setId, String itemId) {
        return (CodeItemBO) findObject(CacheConstants.OBJ_CODEITEM, null, itemId);
    }

    public static ArrayList queryCodeItemBySetId(String setId) {
        ArrayList list = new ArrayList();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);

            list = (ArrayList) ht.find("from CodeItemBO c where c.setId = ? order by c.treeId", setId);
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            new BkmsException("数据转换错误！", SysCacheTool.class);
        }
        return list;
    }

    /**
     * by sunmh  2015-09-12
     * 根据superid查询下级代码，包括super本身
     *
     * @param setId
     * @param superId
     * @return list of codeitembo
     */
    public static ArrayList queryCodeItemBySetIdAndSuperId(String setId, String superId) {
        ArrayList list = new ArrayList();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            list = (ArrayList) ht.find("from CodeItemBO c where c.setId = ? and c.itemSuper = ? and c.itemStatus='1' and c.itemId <> '3010401116' order by c.treeId", new Object[]{setId, superId});
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            new BkmsException("数据转换错误！", SysCacheTool.class);
        }

        return list;
    }
    /**
     * by sunmh  2015-09-12
     * 根据superid查询下级代码，包括super本身
     *
     * @param superId
     * @return list of infosetBO
     */
    public static ArrayList queryInfoSetBySuperId(String superId) {
        ArrayList list = new ArrayList();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            list = (ArrayList) ht.find("from InfoSetBO c where c.setStatus = '1' and c.set_sType = ?   order by c.setSequence", new Object[]{superId});
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            new BkmsException("数据转换错误！", SysCacheTool.class);
        }

        return list;
    }
    /**
     * by sunmh  2015-09-12
     * 根据superid查询下级代码，包括super本身
     *
     * @param superId
     * @return list of InfoItemBO
     */
    public static ArrayList queryInfoItemBySuperId(String superId) {
        ArrayList list = new ArrayList();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            String fieldName=superId+"201";
            list = (ArrayList) ht.find("from InfoItemBO c where c.itemStatus='1' and c.setId = ?   order by case  when item_id='CREATE_TIME' then 1 when item_id='LAST_UPDATE_TIME' then 2  when item_id='LAST_OPERATOR' then 3  when item_id='SUBID' then 4  when item_id='ID' then 5  end,c.itemSequence+0,c.itemId", new Object[]{superId});
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            new BkmsException("数据转换错误！", SysCacheTool.class);
        }

        return list;
    }
    /**
     * by zhu_lw  2018-03-16
     * 根据setId查询下级代码，包括super本身
     * 计划指标导入专用
     * @param superId
     * @return list of InfoItemBO
     */
    public static ArrayList queryInfoItemBySetID(String setId) {
        ArrayList list = new ArrayList();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            String fieldName=setId+"000";
            list = (ArrayList) ht.find("from InfoItemBO c where c.itemStatus='1' and c.setId = ?   order by case  when item_id='CREATE_TIME' then 1 when item_id='LAST_UPDATE_TIME' then 2  when item_id='LAST_OPERATOR' then 3  when item_id='SUBID' then 4  when item_id='ID' then 5 when item_id=? then 6  end,c.itemSequence+0,c.itemId", new Object[]{setId,fieldName});
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            new BkmsException("数据转换错误！", SysCacheTool.class);
        }

        return list;
    }
//    public static PostBO findPost(String postId) {
//        return (PostBO) findObject(CacheConstants.OBJ_POST, null, postId);
//    }

    public static Person findPersonById(String personId) {
        return (Person) findObject(CacheConstants.OBJ_PERSON, "", personId);
    }

    /**
     * 根据人员代码编号（非系统编号）查找人员
     * <p/>
     * //     * @param code
     *
     * @return 人员对象
     */

    public static Person findPersonByCode(String code) {
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            PersonCode pc = (PersonCode) ht.get(PersonCode.class, code);
            Person p = new Person();
            if (pc != null) {
                Object po = ht.get(Person.class, pc.getPersonId());
                Tools.copyProperties(p, po);
                return p;
            }
        } catch (Exception e) {
            new BkmsException("用户读取错误", e, SysCacheTool.class);
            return null;
        }
        return null;
    }

    public static String findPersonIdByCode(String code) {
        Person p = findPersonByCode(code);
        if (p != null)
            return p.getPersonId();

        return "";
    }

    public static Org findOrgById(String orgId) {
        return (Org) findObject(CacheConstants.OBJ_ORG, "", orgId);
    }

    public static Org findOrgByCode(String code) {
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);

            List l = ht.find("from Org o where o.orgCode = ?", code);
            if (l.isEmpty())
                return null;

            Org o = new Org();
            Tools.copyProperties(o, l.get(0));
            return o;
        } catch (Exception e) {
            new BkmsException("按机构编号查找错误", e, SysCacheTool.class.getClass());
        }
        return null;
    }

    public static String findOrgIdByCode(String code) {
        Org o = findOrgByCode(code);
        if (o != null)
            return o.getOrgId();
        return null;
    }

    public static List queryAllInfoSet() {
        return queryAllInfoSet(true);
    }

//    public static WageUnitBO findWageUnit(String wageUnitId) {
////        return (WageUnitBO) findObject(CacheConstants.OBJ_WAGEUNIT, "", wageUnitId);
//        return (WageUnitBO) findObject(9, "", wageUnitId);
//    }

    /**
     * 查询所有指标集
     *
     * @param includeBan true,包含禁用的；false：不包含禁用的
     * @return list<InfoSetBO>
     */
    public static List queryAllInfoSet(boolean includeBan) {
        ArrayList list;

        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            String hq = "from InfoSetBO b";
            if (!includeBan)
                hq += " where b.setStatus <> '" + SysConstants.INFO_STATUS_BAN + "'";

            list = (ArrayList) ht.find(hq);
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
            return list;
        } catch (Exception e) {
            new BkmsException("查询所有指标集错误", e, SysCacheTool.class);
            return new ArrayList();
        }
    }


    /**
     * add by huangh in 091009
     * 查询下级机构，不包括撤销机构
     *
     * @return 下级数据对象
     * @deprecated
     */
    public static ArrayList querySubOrg(String superId) {
        List list;
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            list = ht.find("from Org o where o.superId = ? and o.orgCancel =" + Constants.NO + " order by o.orgSort", superId);
            for (int i = 0; i < list.size(); i++) {
                Object po = list.get(i);
                Object detatchPo = po.getClass().newInstance();
                Tools.copyProperties(detatchPo, po);
                list.set(i, detatchPo);
            }
            return (ArrayList) list;
        } catch (Exception e) {
            new BkmsException(e, SysCacheTool.class);
        }
        return new ArrayList();
    }
    public static String interpretPersons(String personIds) {
        String pName = "";
        if (personIds != null) {
            String[] ids = personIds.split(",");
            if (ids != null) {
                for (int i = 0; i < ids.length; i++) {
                    if (i == 0) {
                        pName += interpretPerson(ids[i]);
                    } else {
                        pName += "," + interpretPerson(ids[i]);
                    }
                }
            }
        }
        return pName;
    }
}
