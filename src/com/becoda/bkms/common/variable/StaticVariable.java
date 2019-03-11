package com.becoda.bkms.common.variable;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.sys.pojo.bo.ParameterBO;
import com.becoda.bkms.sys.ucc.IParameterUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kangsh
 * Date: 2015-2-24
 * Time: 10:35:52
 * 090826 康澍 将参数同步和读取从jndi 调整到 hibernate二级缓存中
 */
public class StaticVariable {
    /**
     * @deprecated
     */
    public static final String PARAMETER_JNDI_KEY = "HRMS_PARAMETER_323sds4";

    /**
     * @param variableName
     * @param value
     * @deprecated 废弃不用了吧.感觉运行时注册不太可能,直接在数据库里注册key和初始值算了
     */
    public static void registerVariable(String variableName, String value) {

    }

    /**
     * 从二级缓存或数据库中读取一个参数的值,
     *
     * @param variableName
     * @return
     * @throws BkmsException
     */
    public static String get(String variableName) throws BkmsException {
        //kangdw 修改,将参数查找从jndi放到hibernate中
        HibernateTemplate ht = (HibernateTemplate) BkmsContext.getBean("hibernateTemplate");
        try {
            ParameterBO bo = (ParameterBO) ht.load(ParameterBO.class, variableName);
            return bo.getValue();
        } catch (Exception e) {
            throw new BkmsException(e, StaticVariable.class);
        }
    }

    /**
     * @param variableName
     * @param newvalue
     * @throws BkmsException
     * @deprecated 方法未被调用,因为二级缓存也不需要在被调用了
     */
    public static void saveToDB(String variableName, String newvalue) throws BkmsException {
        JndiTemplate jndi = (JndiTemplate) BkmsContext.getBean("jndiTemplate");
        try {
            Hashtable ht = (Hashtable) jndi.lookup(PARAMETER_JNDI_KEY);
            ht.put(variableName, newvalue);
            IParameterUCC paramUCC = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
            ParameterBO param = new ParameterBO();
            param.setKey(variableName);
            param.setValue(newvalue);
            paramUCC.createParameter(param);

            jndi.rebind(PARAMETER_JNDI_KEY, ht);
        } catch (Exception e) {
            throw new BkmsException(e, StaticVariable.class);
        }
    }

    /**
     * @param variableName
     * @param newvalue
     * @throws BkmsException
     * @deprecated 参数缓存在二期缓存中,不需要放在jndi中,进行同步
     */
    public static void saveWithoutSyncDatabase(String variableName, String newvalue) throws BkmsException {
        //康澍修改 090826
//        JndiTemplate jndi = (JndiTemplate) BkmsContext.getBean("jndiTemplate");
//        try {
//            Hashtable ht = (Hashtable) jndi.lookup(PARAMETER_JNDI_KEY);
//            ht.put(variableName, newvalue);
//
//            jndi.rebind(PARAMETER_JNDI_KEY, ht);
//        } catch (Exception e) {
//            throw new BkmsException(e, StaticVariable.class);
//        }
    }

    /**
     * @deprecated 因为使用二级缓存 ，系统参数由二级缓存中读取，并进行同步，
     */
    public static void init() {
        Hashtable ht = new Hashtable();
        try {
            IParameterUCC paramUCC = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
            List list = paramUCC.queryParameter();
            if (list == null || list.size() == 0) return;
            int count = list.size();

            for (int i = 0; i < count; i++) {
                ParameterBO pbo = (ParameterBO) list.get(i);
                ht.put(pbo.getKey(), Tools.filterNull(pbo.getValue()));
            }
            //bind in jndi
            JndiTemplate jndi = (JndiTemplate) BkmsContext.getBean("jndiTemplate");
            try {
                Hashtable obj = (Hashtable) jndi.lookup(PARAMETER_JNDI_KEY);
            } catch (NamingException ne) {
                //没有找到jndi.需要绑定
                jndi.bind(PARAMETER_JNDI_KEY, ht);
            }

        } catch (Exception e) {
            new BkmsException(e, StaticVariable.class);
            System.out.println("读取参数时出错,系统直接退出!!!");
            //读取参数时出错,系统直接退出算了
            System.exit(0);
        }
    }
}
