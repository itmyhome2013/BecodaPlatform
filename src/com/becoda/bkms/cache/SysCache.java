package com.becoda.bkms.cache;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.bo.PersonCode;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.util.BkmsContext;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * User: kangdw
 * Date: 2015-5-13
 * Time: 10:59:14
 */
public class SysCache {
    private static Logger log = Logger.getLogger(SysCache.class);
//
//    //存放代码集对象 key：setId; value：codeSet object
//    static final HashMap codeSetMap = new HashMap();
//
//    //存放代码项对象 key：setId; value：code HashMap
//    static final HashMap codeItemMap = new HashMap();
//    //存放代码项对象 key：superId; value：String
//    static final HashMap codeItemSubStrMap = new HashMap();
//
//    //存放指标集合对象 key：setId ; value：infoSet object
//    static final LinkedHashMap infoSetMap = new LinkedHashMap();
//
//    //存放指标项对象 key：itemId; value：infoItem HashMap
//    static final HashMap infoItemMap = new HashMap();
//    static final HashMap infoItemSubStrMap = new HashMap();
//
//    //存放组织机构对象 key：orgid ; value：org object
//    static final HashMap orgMap = new HashMap();
//    //存放组织机构子机构信息
//    static final HashMap orgSubStrMap = new HashMap();
//    //存放组织机构编号和组织机构id key:orgcode; value:orgId;
//    static final HashMap orgCodeMap = new HashMap();
//
//    //存放人员对象 key：personId; value：person object
//    static final HashMap personMap = new HashMap();
//    //存放人员的编号 key:code; value:personId
//    static final HashMap personCodeMap = new HashMap();
//
//    //存放岗位对象 key：postID; value：post object
//    static final HashMap postMap = new HashMap();


    /**
     * 一次加载全部指标集合
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     * @deprecated
     */
    public static void loadInfoSetMap() throws BkmsException {
//        try {
//            infoSetMap.clear();
//            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_INFOSET);
//            InfoSetBO[] list = service.queryAllInfoSet();
//            if (list == null || list.length == 0) return;
//            int count = list.length;
//            for (int i = 0; i < count; i++) {
//                InfoSetBO element = list[i];
//                InfoSetBO newElement = new InfoSetBO();
//                Tools.copyProperties(newElement, element);
//                infoSetMap.put(element.getSetId(), newElement);
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载指标集失败", e, SysCache.class);
//        }
    }

    /**
     * 按照指标集加载指标项
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     * @deprecated
     */
    public static void loadInfoItemMap() throws BkmsException {
//        try {
//            infoItemMap.clear();
//            infoItemSubStrMap.clear();
//
//            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_INFOITEM);
//            InfoItemBO[] list = service.queryAllInfoItem();
//            if (list == null || list.length == 0) return;
//            int count = list.length;
//            for (int i = 0; i < count; i++) {
//                InfoItemBO element = list[i];
//                InfoItemBO newElement = new InfoItemBO();
//                Tools.copyProperties(newElement, element);
//
//                HashMap tmp = (HashMap) infoItemMap.get(newElement.getSetId());
//                if (tmp == null) {
//                    tmp = new HashMap();
//                }
//                tmp.put(newElement.getItemId(), newElement);
//                infoItemMap.put(newElement.getSetId(), tmp);
//
//                String str = Tools.filterNull((String) infoItemSubStrMap.get(newElement.getSetId()));
//                str += newElement.getItemId() + ",";
//                infoItemSubStrMap.put(newElement.getSetId(), str);
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载指标项失败", e, SysCache.class);
//        }
    }

    /**
     * 全部加载代码集
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     * @deprecated
     */
    public static void loadCodeSetMap() throws BkmsException {
//        try {
//            codeSetMap.clear();
//            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_CODESET);
//            CodeSetBO[] list = service.queryAllCodeSet();
//            if (list == null || list.length == 0) return;
//            int count = list.length;
//            for (int i = 0; i < count; i++) {
//                CodeSetBO element = list[i];
//                CodeSetBO newElement = new CodeSetBO();
//                Tools.copyProperties(newElement, element);
//                codeSetMap.put(newElement.getSetId(), newElement);
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载代码集失败", e, SysCache.class);
//        }
    }

    /**
     * 按照代码集加载对应的代码项
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     * @deprecated
     */
    public static void loadCodeItemMap() throws BkmsException {
//        try {
//            codeItemMap.clear();
//            codeItemSubStrMap.clear();
//
//            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_CODESET);
//            CodeItemBO[] list = service.queryAllCodeItem();
//            if (list == null || list.length == 0) return;
//            int count = list.length;
//            for (int i = 0; i < count; i++) {
//                CodeItemBO element = list[i];
//                CodeItemBO newElement = new CodeItemBO();
//                Tools.copyProperties(newElement, element);
//                HashMap tmp = (HashMap) codeItemMap.get(newElement.getSetId());
//                if (tmp == null) {
//                    tmp = new HashMap();
//                }
//                tmp.put(newElement.getItemId(), newElement);
//                codeItemMap.put(newElement.getSetId(), tmp);
//                String superId = newElement.getItemSuper();
//                if (superId == null || "".equals(superId) || "-1".equals(superId)) {
//                    superId = newElement.getSetId();
//                }
//                String str = (String) codeItemSubStrMap.get(superId);
//                if (str == null) {
//                    str = "";
//                }
//                str += newElement.getItemId() + ",";
//                codeItemSubStrMap.put(superId, str);
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载代码项失败", e, SysCache.class);
//        }
    }

    /**
     * 一次加载全部组织机构
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     */
    public static void loadOrgMap() throws BkmsException {
        //todo
        HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
        ht.getSessionFactory().evict(Org.class);
//        try {
//            orgMap.clear();
//            orgSubStrMap.clear();
//            orgCodeMap.clear();
//
//            OrgService service = (OrgService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_ORG);
//            List list = service.queryOrg();
//            if (list == null || list.size() == 0) return;
//            int count = list.size();
//            for (int i = 0; i < count; i++) {
//                Org element = (Org) list.get(i);
//                Org newElement = new Org();
//                Tools.copyProperties(newElement, element);
//
//                orgMap.put(newElement.getOrgId(), newElement);
//                String superId = newElement.getSuperId();
//                String str = (String) orgSubStrMap.get(superId);
//                if (str == null) {
//                    str = "";
//                }
//                str += newElement.getOrgId() + ",";
//                orgSubStrMap.put(superId, str);
//                if (!"".equals(Tools.filterNull(newElement.getOrgCode()))) {
//                    orgCodeMap.put(newElement.getOrgCode(), newElement.getOrgId());
//                }
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载组织机构失败", e, SysCache.class);
//        }
    }

    /**
     * 一次加载全部岗位
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     */
    public static void loadPostMap() throws BkmsException {
        //todo
        HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
//        ht.getSessionFactory().evict(PostBO.class);
//        try {
//            postMap.clear();
//            PostService service = (PostService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_POST);
//            List list = service.queryAllPost();
//            if (list == null || list.size() == 0) return;
//            int count = list.size();
//            for (int i = 0; i < count; i++) {
//                PostBO element = (PostBO) list.get(i);
//                PostBO newElement = new PostBO();
//                Tools.copyProperties(newElement, element);
//                postMap.put(newElement.getPostId(), newElement);
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载岗位失败", e, SysCache.class);
//        }
    }

    /**
     * @deprecated
     */
    public static void loadPersonMap() throws BkmsException {
//        try {
//            personMap.clear();
//            EmpService service = (EmpService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_PERSON);
//
//            List list = service.queryAllPerson();
//            if (list == null || list.size() == 0) return;
//            int count = list.size();
//            for (int i = 0; i < count; i++) {
//                Person element = (Person) list.get(i);
//                Person newElement = new Person();
//                Tools.copyProperties(newElement, element);
//                personMap.put(newElement.getPersonId(), newElement);
//            }
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载人员信息失败", e, SysCache.class);
//        }
    }

    /**
     * 释放全部人员
     *
     * @deprecated
     */

    public static void releasePerson() throws BkmsException {
//        try {
//            personMap.clear();
//        } catch (Exception e) {
//            throw new BkmsException("缓存加载人员信息失败", e, SysCache.class);
//        }
    }


    /**
     * 系统缓存初始化管理
     *
     * @throws com.becoda.bkms.common.exception.BkmsException
     *
     * @deprecated kangdw modify cache都放入二级缓存中.不需要在启动的时候进行加载了
     */
    public static void loadSysCache() throws BkmsException {
//        try {
        //log.info("==开始加载缓存");
        //SysCache.loadCodeSetMap();
        //SysCache.loadCodeItemMap();       codeitemmap包括两个 一个是键到对象 另一个是键到下级列表的键,第一个使用二级cache即可缓存,第二个可以使用querycache进行缓存
        //SysCache.loadInfoSetMap();
        //SysCache.loadInfoItemMap();       键到对象,setid到逗号分隔的itemId
        //SysCache.loadOrgMap();            键到org对象,orgid到逗号分隔的下级orgid,orgcode到orgid 三个
        //SysCache.loadPostMap();           postid 到对象
        //SysCache.loadPersonMap();         personId到对象
        //log.info("==缓存加载完毕");
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BkmsException(e.getMessage(), e, SysCache.class);
//        }
    }

    public static void setPerson(String personId, int operType) throws BkmsException {
        if (CacheConstants.OPER_ADD == operType)
            return;

        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            ht.setCacheQueries(true);
            Person p = (Person) ht.get(Person.class, personId);
            //人员删除和修改后都需要把对应的person 从二级cache中去除
            if (p != null) {
                ht.getSessionFactory().evict(Person.class, p.getPersonId());
                if (p.getPersonCode() != null)
                    ht.getSessionFactory().evict(PersonCode.class, p.getPersonCode());
            }

//            switch (operType) {
//                case CacheConstants.OPER_ADD:
//                case CacheConstants.OPER_UPDATE:
//                    EmpService service = (EmpService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_PERSON);
//                    Person p = service.findPerson(personId);
//                    if (p != null) {
//                        Person newP = new Person();
//                        Tools.copyProperties(newP, p);
//                        personMap.put(personId, newP);
//                        personCodeMap.put(newP.getPersonCode(), newP.getPersonId());
//                    }
//                    break;
//                case CacheConstants.OPER_DELETE:
//                    personMap.remove(personId);
//                    break;
//            }
        } catch (BkmsException e) {
            throw new BkmsException("缓存加载人员信息失败", e, SysCache.class);
        }
    }

    /**
     * @param personCode
     * @param operType
     * @throws BkmsException
     * @deprecated 方法废除 康澍 090826
     */
    public static void setPersonByCode(String personCode, int operType) throws BkmsException {
        try {
            if (CacheConstants.OPER_ADD == operType)
                return;

            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
            PersonCode pc = (PersonCode) ht.get(PersonCode.class, personCode);
            if (pc != null) {
                ht.getSessionFactory().evict(PersonCode.class, personCode);
                ht.getSessionFactory().evict(Person.class, pc.getPersonId());
            }

//            switch (operType) {
//                case CacheConstants.OPER_ADD:
//                case CacheConstants.OPER_UPDATE:
//                    EmpService service = (EmpService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_PERSON);
//                    Person p = service.findPersonByCode(personCode);
//                    if (p != null) {
//                        Person newP = new Person();
//                        Tools.copyProperties(newP, p);
//                        personMap.put(p.getPersonId(), p);
//                        personCodeMap.put(p.getPersonCode(), p.getPersonId());
//                    }
//                    break;
//                case CacheConstants.OPER_DELETE:
//                    personMap.remove(personCode);
//                    break;
//            }
        } catch (Exception e) {
            throw new BkmsException("缓存加载人员信息失败", e, SysCache.class);
        }
    }

    /**
     * @param id
     * @param operType
     * @param operObj
     * @throws BkmsException
     * @deprecated 方法废弃 ,内部实现中关于指标代码等持久化对象刷新的代码被注释,因为其进行更新的都是hibernate操作,二级cache应该被自动更新,不需要再手工处理
     */
    public static void setMap(String[] id, int operType, int operObj) throws BkmsException {
        if (id == null || id.length == 0 || CacheConstants.OPER_ADD == operType) return;

        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");


            for (int i = 0; i < id.length; i++) {
                switch (operObj) {
                    case CacheConstants.OBJ_ORG:
                        ht.getSessionFactory().evict(Org.class, id[i]);
                        break;
                    case CacheConstants.OBJ_PERSON:
                        Person p = (Person) ht.get(Person.class, id[i]);
                        if (p != null) {
                            ht.getSessionFactory().evict(Person.class, id[i]);
                            if (p.getPersonCode() != null)
                                ht.getSessionFactory().evict(PersonCode.class, p.getPersonCode());
                        }
                        break;
                }

            }
        } catch (Exception e) {
            throw new BkmsException("刷新缓存错误", e, SysCache.class);
        }

////        switch (operType) {
////            case CacheConstants.OPER_DELETE:
//                for (int i = 0; i < count; i++) {
//                    //若为机构、代码项、党务、发薪单位处理下级代码字符串
//                    if (operObj == CacheConstants.OBJ_CODEITEM) {
//                        String setId = id[i].substring(0, 4);
//                        HashMap map = (HashMap) codeItemMap.get(setId);
//                        if (map != null) {
//                            CodeItemBO item = (CodeItemBO) map.get(id[i]);
//                            delSubStr(operObj, codeItemSubStrMap, item.getItemId(), item.getItemSuper());
//                            map.remove(id[i]);
//                            codeItemMap.put(setId, map);
//                        }
//                    } else if (operObj == CacheConstants.OBJ_CODESET) { //删除对应的代码项目对象
//                        HashMap itemMap = (HashMap) codeItemMap.get(id[i]);
//                        if (itemMap != null && itemMap.size() > 0) {
//                            Object[] key = itemMap.keySet().toArray();
//                            int size = key.length;
//                            for (int k = 0; k < size; k++) {
//                                codeItemSubStrMap.remove(key.toString());
//                            }
//                        }
//                        codeItemMap.remove(id[i]);
//                        codeSetMap.remove(id[i]);
//                    } else if (operObj == CacheConstants.OBJ_INFOITEM) { //删除对应的指标项对象
//                        if ("ID".equals(id[i]) || "SUBID".equals(id[i]))
//                            continue;
//                        String setId = id[i].substring(0, 4);
//                        HashMap map = (HashMap) infoItemMap.get(setId);
//                        if (map != null) {
//                            map.remove(id[i]);
//                            infoItemMap.put(setId, map);
//
//                            String str = Tools.filterNull((String) SysCache.infoItemSubStrMap.get(setId));
//                            str = str.replaceAll(id[i] + ",", "");
//                            if (str.trim().length() > 0) {
//                                infoItemSubStrMap.put(setId, str);
//                            } else {
//                                infoItemSubStrMap.remove(setId);
//                            }
//                        }
//                    } else if (operObj == CacheConstants.OBJ_INFOSET) { //删除对应的指标对象
//                        infoItemMap.remove(id[i]);
//                        infoItemSubStrMap.remove(id[i]);
//                        infoSetMap.remove(id[i]);
//                    } else if (operObj == CacheConstants.OBJ_ORG) {
//                        Org org = (Org) orgMap.get(id[i]);
//                        delSubStr(operObj, orgSubStrMap, org.getOrgId(), org.getSuperId());
//                        orgCodeMap.remove(id[i]);
//                        orgMap.remove(id[i]);
//                    } else if (operObj == CacheConstants.OBJ_PERSON) {
//                        personCodeMap.remove(id[i]);
//                        personMap.remove(id[i]);
//                    }
//                }
//                break;
//            case CacheConstants.OPER_ADD:
//            case CacheConstants.OPER_UPDATE:
//                try {
//                    for (int i = 0; i < count; i++) {
//                        if (id[i] == null || "".equals(id[i])) continue;
//
//                        if (operObj == CacheConstants.OBJ_CODESET) {
//                            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_CODESET);
//                            CodeSetBO element = service.findCodeSet(id[i]);
//                            if (element == null) continue;
//                            codeSetMap.put(element.getSetId(), element);
//                        } else if (operObj == CacheConstants.OBJ_CODEITEM) {
//                            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_CODEITEM);
//                            CodeItemBO element = service.findCodeItem(id[i]);
//                            if (element == null) continue;
//                            HashMap tmp = (HashMap) codeItemMap.get(element.getSetId());
//                            if (tmp == null) {
//                                tmp = new HashMap();
//                            }
//                            tmp.put(element.getItemId(), element);
//                            codeItemMap.put(element.getSetId(), tmp);
//                        } else if (operObj == CacheConstants.OBJ_INFOSET) {
//                            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_INFOSET);
//                            InfoSetBO element = service.findInfoSet(id[i]);
//                            if (element == null) continue;
//                            infoSetMap.put(element.getSetId(), element);
//
//                        } else if (operObj == CacheConstants.OBJ_INFOITEM) {
//                            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_INFOITEM);
//                            String setId = id[i].substring(0, 4);
//                            InfoItemBO element = service.findInfoItem(setId, id[i]);
//                            if (element == null) continue;
//                            HashMap tmp = (HashMap) infoItemMap.get(element.getSetId());
//                            if (tmp == null) {
//                                tmp = new HashMap();
//                            }
//                            tmp.put(element.getItemId(), element);
//                            infoItemMap.put(element.getSetId(), element);
//                        } else if (operObj == CacheConstants.OBJ_ORG) {
//                            OrgService service = (OrgService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_ORG);
//                            Org element = service.findOrg(id[i]);
//                            if (element == null) continue;
//                            orgMap.put(element.getOrgId(), element);
//                            orgCodeMap.put(element.getOrgCode(), element.getOrgId());
//                        } else if (operObj == CacheConstants.OBJ_PERSON) {
//                            EmpService service = (EmpService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_PERSON);
//                            Person element = service.findPerson(id[i]);
//                            if (element == null) continue;
//                            personMap.put(element.getPersonId(), element);
//                            personCodeMap.put(element.getPersonCode(), element.getPersonId());
//                        } else if (operObj == CacheConstants.OBJ_POST) {
//                            PostService service = (PostService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_POST);
//                            PostBO element = service.findPost(id[i]);
//                            if (element == null) continue;
//                            postMap.put(element.getPostId(), element);
//                        }
//                    }
//                    break;
//                } catch (Exception e) {
//                    throw new BkmsException("缓存维护信息失败", e, SysCache.class);
//                }
    }


    /**
     * @param list
     * @param operType
     * @param operObj
     * @throws BkmsException
     * @deprecated 方法废弃 ,内部实现全部被注释,因为调用这个方法进行更新的都是hibernate操作,二级cache应该被自动更新,不需要再手工处理
     */
    public static void setMap(List list, int operType, int operObj) throws BkmsException {
        if (list == null || list.size() == 0) return;
        int count = list.size();
        try {
            HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");


            for (int i = 0; i < count; i++) {
                switch (operObj) {
                    case CacheConstants.OBJ_ORG:
                        Org o = (Org) list.get(i);
                        ht.getSessionFactory().evict(Org.class, o.getOrgId());
                        break;
                    case CacheConstants.OBJ_PERSON:

                        Person p = (Person) list.get(i);
                        if (p != null) {
                            ht.getSessionFactory().evict(Person.class, p.getPersonId());
                            if (p.getPersonCode() != null)
                                ht.getSessionFactory().evict(PersonCode.class, p.getPersonCode());
                        }
                        break;
                }

            }
        } catch (Exception e) {
            throw new BkmsException("刷新缓存错误", e, SysCache.class);
        }

//        switch (operType) {
//            case CacheConstants.OPER_DELETE:
//                for (int i = 0; i < count; i++) {
        //若为机构、代码项、党务、发薪单位处理下级代码字符串
//                    if (operObj == CacheConstants.OBJ_CODEITEM) {
//                        CodeItemBO item = (CodeItemBO) list.get(i);
//                        delSubStr(operObj, codeItemSubStrMap, item.getItemId(), item.getItemSuper());
//                        HashMap map = (HashMap) codeItemMap.get(item.getSetId());
//                        if (map != null) {
//                            map.remove(item.getItemId());
//                        }
//                    } else if (operObj == CacheConstants.OBJ_CODESET) {
//                        CodeSetBO set = (CodeSetBO) list.get(i);
//                        //删除对应的代码对象
//                        HashMap itemMap = (HashMap) codeItemMap.get(set.getSetId());
//                        if (itemMap != null && itemMap.size() > 0) {
//                            Object[] key = itemMap.keySet().toArray();
//                            int size = key.length;
//                            for (int k = 0; k < size; k++) {
//                                codeItemSubStrMap.remove(key.toString());
//                            }
//                        }
//                        codeItemMap.remove(set.getSetId());
//                        codeSetMap.remove(set.getSetId());
//                    } else if (operObj == CacheConstants.OBJ_INFOITEM) { //删除对应的代码对象
//                        InfoItemBO item = (InfoItemBO) list.get(i);
//                        HashMap map = (HashMap) infoItemMap.get(item.getSetId());
//                        if (map != null) {
//                            map.remove(item.getItemId());
//                            String str = Tools.filterNull((String) SysCache.infoItemSubStrMap.get(item.getSetId()));
//                            str = str.replaceAll(item.getItemId() + ",", "");
//                            if (str.trim().length() == 0) {
//                                infoItemSubStrMap.remove(item.getSetId());
//                            } else {
//                                infoItemSubStrMap.put(item.getSetId(), str);
//                            }
//                        }
//                    } else if (operObj == CacheConstants.OBJ_INFOSET) { //删除对应的代码对象
//                        InfoSetBO set = (InfoSetBO) list.get(i);
//                        infoItemMap.remove(set.getSetId());
//                        infoItemSubStrMap.remove(set.getSetId());
//                        infoSetMap.remove(set.getSetId());
//                    } else if (operObj == CacheConstants.OBJ_ORG) {
//                        Org org = (Org) list.get(i);
//                        delSubStr(operObj, orgSubStrMap, org.getOrgId(), org.getSuperId());
//                        orgCodeMap.remove(org.getOrgId());
//                        orgMap.remove(org.getOrgId());
//                    } else if (operObj == CacheConstants.OBJ_PERSON) {
//                        Person person = (Person) list.get(i);
//                        personCodeMap.remove(person.getPersonId());
//                        personMap.remove(person.getPersonId());
//                    }
//                }
//                break;
//            case CacheConstants.OPER_ADD:
//            case CacheConstants.OPER_UPDATE:
//                try {
//                    for (int i = 0; i < count; i++)
//                        if (operObj == CacheConstants.OBJ_CODESET) {
//                            CodeSetBO set = (CodeSetBO) list.get(i);
//                            CodeSetBO newEle = new CodeSetBO();
//                            Tools.copyProperties(newEle, set);
//                            codeSetMap.put(newEle.getSetId(), newEle);
//                        } else if (operObj == CacheConstants.OBJ_CODEITEM) {
//                            CodeItemBO item = (CodeItemBO) list.get(i);
//                            CodeItemBO newEle = new CodeItemBO();
//                            Tools.copyProperties(newEle, item);
//                            HashMap hash = (HashMap) codeItemMap.get(newEle.getSetId());
//                            if (hash == null) hash = new HashMap();
//                            hash.put(newEle.getItemId(), newEle);
//                            codeItemMap.put(newEle.getSetId(), hash);
//
//                        } else if (operObj == CacheConstants.OBJ_INFOSET) {
//                            InfoSetBO set = (InfoSetBO) list.get(i);
//                            InfoSetBO newEle = new InfoSetBO();
//                            Tools.copyProperties(newEle, set);
//                            infoSetMap.put(newEle.getSetId(), newEle);
//                        } else if (operObj == CacheConstants.OBJ_INFOITEM) {
//                            InfoItemBO item = (InfoItemBO) list.get(i);
//                            InfoItemBO newEle = new InfoItemBO();
//                            Tools.copyProperties(newEle, item);
//                            HashMap hash = (HashMap) infoItemMap.get(newEle.getSetId());
//                            if (hash == null) hash = new HashMap();
//                            hash.put(newEle.getItemId(), newEle);
//                            infoItemMap.put(newEle.getSetId(), hash);
//
//                        } else if (operObj == CacheConstants.OBJ_ORG) {
//                            Org org = (Org) list.get(i);
//                            Org newEle = new Org();
//                            Tools.copyProperties(newEle, org);
//                            orgMap.put(newEle.getOrgId(), newEle);
//                            orgCodeMap.put(newEle.getOrgCode(), newEle.getOrgId());
//                        } else if (operObj == CacheConstants.OBJ_POST) {
//                            PostBO post = (PostBO) list.get(i);
//                            PostBO newEle = new PostBO();
//                            Tools.copyProperties(newEle, post);
//                            postMap.put(newEle.getPostId(), newEle);
//                        } else if (operObj == CacheConstants.OBJ_PERSON) {
//                            Person person = (Person) list.get(i);
//                            Person newEle = new Person();
//                            Tools.copyProperties(newEle, person);
//                            personMap.put(newEle.getPersonId(), newEle);
//                            personCodeMap.put(newEle.getPersonCode(), newEle.getPersonId());
//                        }
//                    break;
//                } catch (Exception e) {
//                    throw new BkmsException("缓存维护信息失败", e, SysCache.class);
//                }
//    }
    }

    /**
     * @param map
     * @param id
     * @param superId
     * @deprecated 方法未被调用
     */
    private static void delSubStr(int operObj, HashMap map, String id, String superId) {
        if (operObj == CacheConstants.OBJ_CODEITEM) {
            if ("-1".equals(superId)) superId = id.substring(0, 4);
        }
        String subStr = (String) map.get(superId);
        if (subStr == null || "".equals(subStr.trim())) return;
        subStr = subStr.replaceAll(id + ",", "");
        if (subStr.trim().length() == 0) {
            map.remove(superId);
        } else {
            map.put(superId, subStr);
        }
    }

    /**
     * @param operObj   需要操作的缓存对象类型。
     * @param codeSetId 如果数据对象是代码项，必须指定代码集ID，如果是指标集，必须制定指标集ID,否则 null
     * @param superId   上级代码ID
     * @param subObj    下级对象列表。
     * @deprecated 方法未被调用
     *             更新缓存中下级代码及其排列顺序。<br>
     *             各个模块按照自己的排序方式查询出下级对象，依次放入subObj对象中，按照subObj的排列顺序更新缓存中下级对象顺序
     *             需要处理下级对象的由：组织机构、代码项、党务、发薪单位
     */
    public static void updateSubStrMap(int operObj, String codeSetId, String superId, List subObj) {
//        if (subObj == null || subObj.size() == 0) return;
//        int count = subObj.size();
//        String substr = "";
//        switch (operObj) {
//            case CacheConstants.OBJ_CODEITEM:
//                for (int i = 0; i < count; i++)
//                    substr += ((CodeItemBO) subObj.get(i)).getItemId() + ",";
//                if ("-1".equals(superId)) superId = codeSetId;
//                codeItemSubStrMap.put(superId, substr);
//                break;
//            case CacheConstants.OBJ_ORG:
//                for (int i = 0; i < count; i++)
//                    substr += ((Org) subObj.get(i)).getOrgId() + ",";
//                orgSubStrMap.put(superId, substr);
//                break;
//            case CacheConstants.OBJ_INFOITEM:
//                for (int i = 0; i < count; i++)
//                    substr += ((InfoItemBO) subObj.get(i)).getItemId() + ",";
//                infoItemSubStrMap.put(superId, substr);
//                break;
//            default:
//                break;
//        }
    }

    /**
     * 更新下级节点信息
     *
     * @param operObj 数据对象
     * @param setId   如果数据对象是代码项，必须指定代码集ID，如果是指标集，必须制定指标集ID,否则 null
     * @param superId 上级ID
     */
    public static void updateSubStrMap(int operObj, String setId, String superId) throws BkmsException {

        //todo
        int count;
        List subObj;
        String substr = "";
        try {
            switch (operObj) {
                case CacheConstants.OBJ_CODEITEM:
                    //指标和代码都是用hibernate维护的不需要进行干预
//                    SysAPI sa = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_CODEITEM);
//                    subObj = sa.queryCodeItemsBySuperID(setId, superId);
//                    if (subObj == null) break;
//                    count = subObj.size();
//                    for (int i = 0; i < count; i++)
//                        substr += ((CodeItemBO) subObj.get(i)).getItemId() + ",";
//                    if ("-1".equals(superId))
//                        superId = setId;
//                    codeItemSubStrMap.put(superId, substr);
                    break;
                case CacheConstants.OBJ_INFOITEM:
                    //updateInfoItemBySetId(setId);
                    break;
                case CacheConstants.OBJ_ORG:
                    //放弃所有的org缓存
                    HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
                    ht.getSessionFactory().evict(Org.class);
//                    OrgService os = (OrgService) BkmsContext.getBean(CacheConstants.CFG_SERVICE_ORG);
//                    subObj = os.queryOrgBySuper(superId);
//                    if (subObj == null) break;
//                    count = subObj.size();
//                    for (int i = 0; i < count; i++)
//                        substr += ((Org) subObj.get(i)).getOrgId() + ",";
//                    orgSubStrMap.put(superId, substr);
                    break;
                default:
                    break;
            }
        } catch (BkmsException e) {
            throw new BkmsException("缓存维护信息失败", e, SysCache.class);
        }
    }

    /**
     * @param setId
     * @throws BkmsException
     * @deprecated
     */
    public static void updateInfoItemBySetId(String setId) throws BkmsException {
//        try {
//
//            SysAPI service = (SysAPI) BkmsContext.getBean(CacheConstants.CFG_SERVICE_INFOITEM);
//            InfoItemBO[] list = service.queryAllInfoItem(setId, "");
//            infoItemMap.remove(setId);
//            infoItemSubStrMap.remove(setId);
//            if (list != null && list.length > 0) {
//                int len = list.length;
//                HashMap map = new HashMap();
//                String str = "";
//                for (int i = 0; i < len; i++) {
//                    InfoItemBO newEle = new InfoItemBO();
//                    Tools.copyProperties(newEle, list[i]);
//                    map.put(newEle.getItemId(), newEle);
//                    str += newEle.getItemId() + ",";
//                }
//                infoItemMap.put(setId, map);
//                infoItemSubStrMap.put(setId, str);
//            }
//        } catch (BkmsException e) {
//            throw new BkmsException("缓存维护信息失败", e, SysCache.class);
//        }
    }

}
