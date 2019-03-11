package com.becoda.bkms.tls.webmgr.ucc.impl;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.tls.TlsConstants;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;
import com.becoda.bkms.tls.webmgr.service.InformationService;
import com.becoda.bkms.tls.webmgr.ucc.IInformationUCC;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2015-7-28
 * Time: 16:44:57
 */
public class InformationUCCImpl implements IInformationUCC {
    private InformationService service;

    public InformationService getService() {
        return service;
    }

    public void setService(InformationService service) {
        this.service = service;
    }

    public String saveInfomation(InformationBO bo) throws RollbackableException {
        return service.saveInfomation(bo);
    }

    public void updateInfomation(InformationBO bo) throws RollbackableException {
        service.updateInfomation(bo);
    }

    public void deleteInfomation(String id) throws RollbackableException {
        service.deleteInformation(id);
    }

    public void deleteInfomation(String[] id) throws RollbackableException {
        service.deleteInformation(id);
    }

    public InformationBO findInfoById(String id) throws RollbackableException {
        return service.getInfoById(id);
    }

    public List queryInfoByType(PageVO page, String orgId, String type) throws RollbackableException {
        StringBuffer hql = new StringBuffer("from InformationBO i where 1=1 ");
        if (orgId != null && orgId .trim().length() > 0) {
            hql.append(" and i.createOrgId='").append(orgId).append("'");
        }
        if (type != null&&!type.equals("null") && type .trim().length() > 0) {
            hql.append(" and i.type='").append(type).append("'");
        }
        String countHql = "select count(i.id) " + hql.toString();
        hql.append(" order by i.isTop desc,i.infoIsBanner desc ,i.createTime desc");
        return service.getInformationDAO().pageQuery(page, countHql, hql.toString());
    }
    
    public List queryInfo() throws RollbackableException {
        return service.queryInfo();
    }
    
    public List queryInfoByOrg(PageVO page, String orgId, String type) throws RollbackableException {
        return service.queryInfoByOrg(page, orgId, type);
    }
    
    public boolean checkSameName(String name, String type, String id) throws RollbackableException {
        return service.checkSameName(name, type, id);
    }

    public void updateAllStatus(String[] ids, String col, String value) throws RollbackableException {
        service.updateAllStatus(ids, col, value);
    }

    //根据主键获得信息集合
    public List queryInfo(String[] ids) throws RollbackableException {
        return service.queryInfo(ids);
    }

    /**
     * 批量生成html，并更新informationBO的html路径
     *
     * @param templateName html的模板名称
     * @param path         绝对路径
     * @param list         InformationBO集合
     * @throws RollbackableException
     */
    public void saveBuildHtml(List list, String path, String templateName, String baseHttpPath) throws RollbackableException {
        try {
            if (!list.isEmpty() && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    InformationBO bo = (InformationBO) list.get(i);
                    String staticURL = this.buildHtml(bo, path, templateName, baseHttpPath);
                    bo.setStaticFileURL(staticURL);
                    bo.setStatus(TlsConstants.INFO_STATUS_YES);
                    bo.setUpdateTime(Tools.getSysDate(null));
                    service.updateInfomation(bo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成html
     *
     * @param templateName html的模板名称
     * @param path         html模板的绝对路径
     * @throws RollbackableException
     */
    public String buildHtml(InformationBO vo, String path, String templateName, String baseHttpPath) throws RollbackableException {
        String htmlURL;
        String baseDir = path + File.separator + "file" + File.separator + "tls" + File.separator + "htmlfile" + File.separator;
        String fileName = SequenceGenerator.getUUID();//生成html页面名称
        try {
            byte bytes[] = generateTemplate(path + File.separator + "file" + File.separator, templateName);
            File file = new File(baseDir);
            if (!file.exists())
                file.mkdir();
            String strHtml = new String(bytes, "UTF-8");
            strHtml = strHtml.replaceAll("\\$position", SysCacheTool.interpretCode(vo.getType()));
            strHtml = strHtml.replaceAll("\\$infoContent", vo.getContent());
            strHtml = strHtml.replaceAll("\\$infoTitle", vo.getTitle());
            strHtml = strHtml.replaceAll("\\$createTime", vo.getCreateTime());
            strHtml = strHtml.replaceAll("\\＜", "\\<");
            strHtml = strHtml.replaceAll("\\＞", "\\>");
            strHtml = strHtml.replaceAll("\\＂", "\"");
            htmlURL = baseDir + fileName + ".html";//创建文件绝对路径
            File htmlFile = new File(htmlURL);
            htmlFile.createNewFile();
            FileOutputStream os = new FileOutputStream(htmlFile);
            os.write(strHtml.getBytes());
            os.flush();
            os.close();
            htmlURL = "/file/tls/htmlfile/" + fileName + ".html";//静态调用文件地址
        } catch (Exception e) {
            e.printStackTrace();
            throw new RollbackableException(e, this.getClass());
        }
        return htmlURL;
    }

    /**
     * 获取html模板文件的流
     *
     * @param templateName html的模板名称
     * @param tempPath     html模板文件路径
     * @throws RollbackableException
     */
    public static byte[] generateTemplate(String tempPath, String templateName) throws Exception {
        try {
            FileInputStream fis = new FileInputStream(tempPath + templateName);
            int lenght = fis.available();
            byte[] bytes = new byte[lenght + 1];
            fis.read(bytes);
            fis.close();
            return bytes;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
