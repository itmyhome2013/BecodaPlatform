package com.becoda.bkms.run.web;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.run.pojo.bo.BulletinParamBO;
import com.becoda.bkms.run.pojo.vo.BulletinForm;
import com.becoda.bkms.run.ucc.IBulletionUCC;
import com.becoda.bkms.util.CodeUtil;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.common.web.BkmsHttpRequest;
import com.becoda.bkms.common.web.BkmsHttpResponse;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: huangh
 * Date: 2015-3-12
 * Time: 14:56:26
 * To change this template use File | Settings | File Templates.
 */
public class BulletinAction extends GenericPageAction {
     private BulletinForm bulletinform ;

    public BulletinForm getBulletinform() {
        return bulletinform;
    }

    public void setBulletinform(BulletinForm bulletinform) {
        this.bulletinform = bulletinform;
    }//查看公告
    public String view() throws BkmsException {
        String id = Tools.filterNull(request.getParameter("id"));
        IBulletionUCC ucc = (IBulletionUCC) BkmsContext.getBean("run_bulletinUCC");
//        BulletinForm bulletinform = (BulletinForm) form;
        HashMap hm = ucc.queryBulletinParamAndContent(id);
        bulletinform.setTopic((String) hm.get("topic"));
        bulletinform.setEndDate((String) hm.get("endDate"));
        bulletinform.setStartDate((String) hm.get("startDate"));
        bulletinform.setContent((String) hm.get("content"));
        return "view";
    }

    //更多
    public String more() throws BkmsException {
        IBulletionUCC ucc = (IBulletionUCC) BkmsContext.getBean("run_bulletinUCC");
        int iCount = -1;
        String count = Tools.filterNull(request.getParameter("count"));
        if (!"".equals(count)) {
            iCount = Integer.parseInt(count);
        }
        BulletinParamBO[] t = ucc.queryBulletinParam(user);
        List bList = new ArrayList();
        if (t != null && t.length > 0) {
            if (iCount == -1)
                iCount = t.length;

            for (int i = 0; i < t.length && i < iCount; i++) {
                BulletinParamBO bo = t[i];
                Person personbo = SysCacheTool.findPersonById(bo.getAuthorId());

                if (personbo != null) {
                    bo.setBlltnTopic("[" + CodeUtil.interpretCode("OU", personbo.getOrgId()) + "] " + bo.getBlltnTopic());
                }
                if (bo.getBlltnTopic().length() > 26) {
                    bo.setBlltnTopic(bo.getBlltnTopic().substring(0, 26) + "...");//保留26个字符长度
                }
                bo.setBlltnTopic(bo.getBlltnTopic() + " (" + bo.getStartDate().substring(bo.getStartDate().length() - 5, bo.getStartDate().length()) + ")");
                bList.add(bo);
            }

        } else {
            BulletinParamBO bo = new BulletinParamBO();
            bo.setBlltnId("");
            bo.setBlltnTopic("暂时没有公告！");
            bList.add(bo);
        }
        request.setAttribute("bList", bList);
        return "more";
    }

    //保存
    public String save() throws BkmsException {
//        BulletinForm bulletinform = (BulletinForm) form;
        String id = Tools.filterNull(bulletinform.getBulletinId());
        IBulletionUCC ucc = (IBulletionUCC) BkmsContext.getBean("run_bulletinUCC");
        if ("".equals(id)) { //添加
            Person person = SysCacheTool.findPersonById(user.getUserId());
            ucc.createBulletin(bulletinform, user.getUserId(), person.getOrgId(), user);
        } else {   //修改
            Person person = SysCacheTool.findPersonById(user.getUserId());
            ucc.updateBulletin(bulletinform, user.getUserId(), person.getOrgId(), user);
        }
//        this.actionErrors.add(new ActionMessage("msg", "保存成功！", "", ""));
        super.showMessage("发布成功!");
        return this.list();
    }
   //新增时初始化页面
    public String find() throws BkmsException {
        IBulletionUCC ucc = (IBulletionUCC) BkmsContext.getBean("run_bulletinUCC");
        String id = Tools.filterNull(request.getParameter("id"));
//        BulletinForm bulletinform = (BulletinForm) form;
        if (!"".equals(id)) {//编辑
            //PlanBO bo = manager.findById(id);
            //Tools.copyProperties(planform,bo);
            BulletinParamBO bulletinParamBO = ucc.findBulletinParamByBulletinId(id);
            if(bulletinform==null){
                bulletinform=new BulletinForm();
            }
            bulletinform.setTopic(bulletinParamBO.getBlltnTopic());
            bulletinform.setStartDate(bulletinParamBO.getStartDate());
            bulletinform.setEndDate(bulletinParamBO.getEndDate());
            bulletinform.setReaderType(bulletinParamBO.getReaderType());
            bulletinform.setBulletinId(id);
            bulletinform.setContent(ucc.findBulletinContent(id));

            String scope[] = ucc.findBulletinScopeByBulletinId(id);
            String scopeNames = "";
            String scopeIds = "";
            Org org = null;
            if (scope != null) {
                for (int i = 0; i < scope.length; i++) {
                    scopeIds = scopeIds + scope[i] + ",";
                    org = SysCacheTool.findOrgById(scope[i]);
                    if (org != null) {
                        scopeNames = scopeNames + org.getName() + ",";
                    }
                }
            }
            bulletinform.setScopeOrgNames(scopeNames);
            bulletinform.setScopeOrgIds(scopeIds);

        }
        return "find";
    }
    //删除
    public String del() throws BkmsException {
        IBulletionUCC ucc = (IBulletionUCC) BkmsContext.getBean("run_bulletinUCC");
        String ids[] = request.getParameterValues("chkblltnId");
        ucc.deleteBulletinByBulletinIdArray(ids, user);
//        this.actionErrors.add(new ActionMessage("msg", "删除成功！", "", ""));
        super.showMessage("删除成功!");
        this.list();
        return "list";
    }
    //查询
    public String list() throws BkmsException {
        IBulletionUCC ucc = (IBulletionUCC) BkmsContext.getBean("run_bulletinUCC");
        List bos = null;
        String topicQry = Tools.filterNull(request.getParameter("topicQry"));
        String dateQry = Tools.filterNull(request.getParameter("dateQry"));
        Person person = SysCacheTool.findPersonById(user.getUserId());
        bos = ucc.queryBulletinParamAndScopeByCreateOrgId(Tools.filterNull(topicQry), Tools.filterNull(dateQry), person.getOrgId(), vo);
        request.setAttribute("bos", bos);
        return "list";
    }
}
