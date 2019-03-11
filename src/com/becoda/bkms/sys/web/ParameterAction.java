package com.becoda.bkms.sys.web;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.variable.StaticVariable;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.sys.pojo.bo.ParameterBO;
import com.becoda.bkms.sys.pojo.vo.ParameterVO;
import com.becoda.bkms.sys.ucc.IParameterUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-9
 * Time: 11:22:51
 * To change this template use File | Settings | File Templates.
 */
public class ParameterAction extends GenericAction {
    private ParameterVO pform;

    public ParameterVO getPform() {
        return pform;
    }

    public void setPform(ParameterVO pform) {
        this.pform = pform;
    }//条件查询
    public String queryParamInfo() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        String key = Tools.filterNull(request.getParameter("key"));
        String moduleName = Tools.filterNull(request.getParameter("moduleName"));
        String keyValue = Tools.filterNull(request.getParameter("keyValue"));
        IParameterUCC ucc = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
        List list = ucc.queryParameter(key, keyValue, moduleName);
        request.setAttribute("paramList", list);
        return "success";
    }

    public String gotoParamInfoQuery() throws BkmsException {
        return "success";
    }

    //新建参数
    public String saveParam() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        try {
            ParameterBO bo = new ParameterBO();
//            ParameterVO pform = (ParameterVO) form;
            Tools.copyProperties(bo, pform);
            IParameterUCC ucc = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
            ucc.createParameter(bo);
            //同步缓存
            //SysCache.loadSysParameterMap();
            StaticVariable.saveWithoutSyncDatabase(bo.getKey(), bo.getValue());
            this.showMessage("保存成功！");
            getParamList();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            this.showMessage("错误:"+e.getMessage()+e.toString());
            return "padd";
        }
    }

    //跳转页面
    public String addParam() throws BkmsException {
        return "padd";
    }

    //参数修改
    public String editParam() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        ParameterBO bo = new ParameterBO();
        String key = request.getParameter("key");
        String[] value = request.getParameterValues(key);
        bo.setKey(key);
        bo.setModuleName(value[0]);
        bo.setValue(value[1]);
        IParameterUCC ucc = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
        ucc.updateParameter(bo);
        //同步缓存
        //SysCache.loadSysParameterMap();
        StaticVariable.saveWithoutSyncDatabase(bo.getKey(), bo.getValue());
        this.showMessage("保存成功！");
        getParamList();
        this.queryParamInfo();
        return "success";
    }

    //参数删除
    public String delParam() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        IParameterUCC ucc = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
        String[] keys = request.getParameterValues("chk");
        if (keys != null && !"".equals(keys)) {
            for (int i = 0; i < keys.length; i++) {
                ucc.deleteParameter(keys[i]);
            }
        }
        this.showMessage("删除成功！");
        getParamList();
        return "success";
    }

    public String list() throws BkmsException {
        BkmsHttpRequest request = this.getBrequest();
        IParameterUCC ucc = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
        getParamList();
        return "success";
    }

    public void getParamList() throws BkmsException {
        IParameterUCC ucc = (IParameterUCC) BkmsContext.getBean("sys_parameterUCC");
        List list = ucc.queryParameter();
        request.setAttribute("list", list);
    }
}
