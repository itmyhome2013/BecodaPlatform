package com.becoda.bkms.sys.service;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.web.PageVO;
import com.becoda.bkms.sys.dao.OperateLogDAO;
import com.becoda.bkms.sys.pojo.bo.OperLogBO;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;
import jxl.Workbook;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

/**
 * iCITIC HR
 * User: Jair.Shaw
 * Date: 2015-3-6
 * Time: 16:27:36
 */
public class OperateLogService {
    private OperateLogDAO operateLogDAO;

    public OperateLogDAO getOperateLogDAO() {
        return operateLogDAO;
    }

    public void setOperateLogDAO(OperateLogDAO operateLogDAO) {
        this.operateLogDAO = operateLogDAO;
    }

    private String path;

    private String setOperPath(String rootPath) {
        Constants.PATH_FILE_LOG_OPER = rootPath;
        this.path = rootPath + Constants.URL_FILE_LOG_OPER;
        return this.path;
    }


    /**
     * 批量添加操作日志
     *
     * @param logLog
     * @pdOid 617775ad-ae38-4a7f-bfa4-f1c41f2a33c7
     */

    public void addOperLog(OperLogBO[] logLog) throws RollbackableException {
        if (logLog == null || logLog.length == 0) return;
        int count = logLog.length;
        String[] index = SequenceGenerator.getUUID(count);
        for (int i = 0; i < count; i++) {
            logLog[i].setOperId(index[i]);
            operateLogDAO.createBo(logLog[i]);
        }
    }

    /**
     * 批量添加操作日志
     *
     * @param log
     * @throws BkmsException
     */
    public void addOperLog(Vector log) throws RollbackableException {
        if (log == null || log.size() == 0) return;
        int count = log.size();
        String[] index = SequenceGenerator.getUUID(count);
        for (int i = 0; i < count; i++) {
            OperLogBO tmp = (OperLogBO) log.elementAt(i);
            tmp.setOperId(index[i]);
            operateLogDAO.createBo(tmp);
        }
    }

    /**
     * 添加一条操作日志
     *
     * @param log
     * @throws BkmsException
     */
    public void addOperLog(OperLogBO log) throws RollbackableException {
        log.setOperId(SequenceGenerator.getUUID());
        operateLogDAO.createBo(log);
    }

    /**
     * 查询操作日志
     *
     * @param start    开始时间   timestamp in format yyyy-mm-dd hh:mm:ss.fffffffff
     * @param end      结束时间   timestamp in format yyyy-mm-dd hh:mm:ss.fffffffff
     * @param operName
     * @param userName
     * @pdOid f682813a-8121-49bb-bd42-e915e16a8f72
     */
    public List queryOperLog(String start, String end, String operName, String userName, String operType, String orgId, PageVO vo) throws RollbackableException {
        Timestamp st = null;
        Timestamp et = null;
        if (start != null && !"".equals(start)) {
            st = Timestamp.valueOf(Tools.getDateByFormat(start, "yyyy-MM-dd HH:mm:ss"));
        }
        if (end != null && !"".equals(end)) {
            et = Timestamp.valueOf(Tools.getDateByFormat(end, "yyyy-MM-dd HH:mm:ss"));
        }
        return operateLogDAO.queryOperLog(st, et, operName, userName, operType, orgId, vo);
    }

    /**
     * 查询，不分页
     *
     * @param start
     * @param end
     * @param operName
     * @param userName
     * @param operType
     * @param orgId
     * @return
     * @throws BkmsException
     */
    public List queryOperLog(String start, String end, String operName, String userName, String operType, String orgId, String[] setIds, String[] itemIds) throws RollbackableException {
        Timestamp st = null;
        Timestamp et = null;
        if (start != null && !"".equals(start)) {
            st = Timestamp.valueOf(Tools.getDateByFormat(start, "yyyy-MM-dd HH:mm:ss"));
        }
        if (end != null && !"".equals(end)) {
            et = Timestamp.valueOf(Tools.getDateByFormat(end, "yyyy-MM-dd HH:mm:ss"));
        }
        return operateLogDAO.queryOperLog(st, et, operName, userName, operType, orgId, setIds, itemIds);
    }

    /**
     * 导出操作日志到excel文件 ,删除系统记录
     *
     * @param operId   日志id
     * @param fileType 文件类型 1 xls,2 mdb,3 xml
     * @pdOid 8d9d1202-7e5a-413c-9c4b-d5f46ff8a811
     */
    public void expOperLog(String[] operId, int fileType, String rootPath) throws RollbackableException {
        OperLogBO[] logLog = operateLogDAO.queryOperLog(operId);
        if (logLog != null) {
            this.expOperLog(logLog, fileType, rootPath);
        }
    }

    /**
     * 导出操作日志文件。查询出所有操作日志，导出到文件，后删除系统记录
     *
     * @pdOid 0962f3c4-7765-4083-aa2d-31ba44320deb
     */
    public void expOperLog(OperLogBO[] logLog, int fileType, String rootPath) throws RollbackableException {
        if (logLog != null) {
            this.setOperPath(rootPath);
            switch (fileType) {
                case 1:
                    this.expLogFile2Excel(logLog);
                    break;

                case 3:
                    this.expLogFile2Xml(logLog);
                    break;
            }
            operateLogDAO.removeLog(logLog);
        }
    }

    public void expLogFile2Excel(OperLogBO[] logLog) throws RollbackableException {
        if (logLog == null) {
            return;
        }
        try {
            String dt = Tools.getSysDate("yyyyMMddHHmmss");
            File fDir = new File(this.path);
            if (fDir.exists() && fDir.isDirectory()) {
            } else {
                fDir.mkdirs();
            }
            File f = new File(path + System.getProperty("file.separator") + dt + ".xls");

            OperLogBO tmp = new OperLogBO();
            int count = logLog.length;
            jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(f);
            jxl.write.WritableSheet ws = wwb.createSheet("Sheet 1", 0);
            jxl.write.Label labelC = null;
            for (int i = 0; i < count + 1; i++) {
                if (i != 0) {
                    tmp = logLog[i - 1];
                }
                labelC = new jxl.write.Label(0, i, i == 0 ? "操作id" : tmp.getOperId());
                ws.addCell(labelC);
                String dataTime = "";
                if (tmp.getOperDatetime() != null) {
                    dataTime = tmp.getOperDatetime().toString();
                }
                labelC = new jxl.write.Label(1, i, i == 0 ? "用户操作时间" : Tools.filterNull(dataTime));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(2, i, i == 0 ? "用户id" : Tools.filterNull(tmp.getOperatorId()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(3, i, i == 0 ? "用户名" : Tools.filterNull(tmp.getOperatorName()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(4, i, i == 0 ? "操作类型" : Tools.filterNull(tmp.getOperType()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(5, i, i == 0 ? "被操作人id" : Tools.filterNull(tmp.getOperPersonId()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(6, i, i == 0 ? "被操作人姓名" : Tools.filterNull(tmp.getOperUsername()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(7, i, i == 0 ? "被操作人部门" : Tools.filterNull(tmp.getOperUserDept()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(8, i, i == 0 ? "操作数据id" : Tools.filterNull(tmp.getOperRecordId()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(9, i, i == 0 ? "指标集id" : Tools.filterNull(tmp.getOperInfosetId()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(10, i, i == 0 ? "指标集名称" : Tools.filterNull(tmp.getOperInfosetName()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(11, i, i == 0 ? "指标项id" : Tools.filterNull(tmp.getOperInfoitemId()));
                ws.addCell(labelC);
                labelC = new jxl.write.Label(12, i, i == 0 ? "指标项名称" : Tools.filterNull(tmp.getOperInfoitemName()));
                ws.addCell(labelC);
                int flag = Tools.filterNull(tmp.getOperInfoitemId()).lastIndexOf("|7");
                int flag1 = Tools.filterNull(tmp.getOperInfoitemId()).lastIndexOf("|6");
                if (flag > 0) {
                    labelC = new jxl.write.Label(13, i, i == 0 ? "操作前数值" : Tools.filterNull(SysCacheTool.interpretCode("OU", tmp.getOperValuePre())));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(15, i, i == 0 ? "操作后数值" : Tools.filterNull(SysCacheTool.interpretCode("OU", tmp.getOperValueSuf())));
                    ws.addCell(labelC);
                } else if (flag1 > 0) {
                    labelC = new jxl.write.Label(13, i, i == 0 ? "操作前数值" : Tools.filterNull(SysCacheTool.interpretCode("INFOITEM", tmp.getOperValuePre())));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(15, i, i == 0 ? "操作后数值" : Tools.filterNull(SysCacheTool.interpretCode("INFOITEM", tmp.getOperValueSuf())));
                    ws.addCell(labelC);
                } else {
                    labelC = new jxl.write.Label(13, i, i == 0 ? "操作前数值" : Tools.filterNull(tmp.getOperValuePre()));
                    ws.addCell(labelC);
                    labelC = new jxl.write.Label(15, i, i == 0 ? "操作后数值" : Tools.filterNull(tmp.getOperValueSuf()));
                    ws.addCell(labelC);
                }
            }
            wwb.write();
            wwb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 日志导出到xml文件
     *
     * @param logLog
     * @throws BkmsException
     */
    public void expLogFile2Xml(OperLogBO[] logLog) throws RollbackableException {
        try {
            File fDir = new File(path);
            if (fDir.exists() && fDir.isDirectory()) {
            } else {
                fDir.mkdirs();
            }
            String dt = Tools.getSysDate("yyyyMMddHHmmss");
            File file = new File(path + System.getProperty("file.separator") + dt + ".xml");
            if (logLog != null) {
                this.buildXMLDoc(logLog, file);
            }
        } catch (Exception e) {
            throw new RollbackableException("导出日志失败", e, this.getClass());
        }
    }

    /**
     * 删除磁盘日志文件
     *
     * @param fileName
     * @pdOid c2fda429-56db-40f0-a592-31eeb9fa6d1a
     */
    public void delDiskLogFile(String[] fileName, String rootPath) {
        this.setOperPath(rootPath);
        int count = fileName.length;
        for (int i = 0; i < count; i++) {
            File f = new File(path + System.getProperty("file.separator") + fileName[i]);
            f.delete();
        }
    }

    /**
     * 得到磁盘的日志文件列表
     *
     * @return
     */

    public String[] getDiskLogFile(String rootPath) {
        this.setOperPath(rootPath);
        File f = new File(path);
        return f.list();
    }

    /**
     * 导出xml的方法
     *
     * @param logLog
     * @param f
     * @throws java.io.IOException
     * @throws org.jdom.JDOMException
     */
    public void buildXMLDoc(OperLogBO[] logLog, File f) throws IOException, RollbackableException {
        if (logLog != null) {
            int count = logLog.length;
            Element root, childerAttribute, id;
            Document Doc;
            root = new Element("operation_information");
            Doc = new Document(root);
            root = Doc.getRootElement();
            List operation;
            operation = root.getChildren();
            for (int i = 0; i < count; i++) {
                id = new Element("operation_log");
                id.addContent(new Element("operId").setText(logLog[i].getOperId()));
                String dateTime = "";
                if (logLog[i].getOperDatetime() != null) {
                    dateTime = logLog[i].getOperDatetime().toString();
                }
                id.addContent(new Element("operDatetime").setText(dateTime));
                id.addContent(new Element("operatorId").setText(logLog[i].getOperatorId()));
                id.addContent(new Element("operatorName").setText(logLog[i].getOperatorName()));
                id.addContent(new Element("operType").setText(logLog[i].getOperType()));
                id.addContent(new Element("operPersonId").setText(logLog[i].getOperPersonId()));
                id.addContent(new Element("operUsername").setText(logLog[i].getOperUsername()));
                id.addContent(new Element("operUserDept").setText(logLog[i].getOperUserDept()));
                id.addContent(new Element("operRecordId").setText(logLog[i].getOperRecordId()));
                id.addContent(new Element("operInfosetId").setText(logLog[i].getOperInfosetId()));
                id.addContent(new Element("operInfosetName").setText(logLog[i].getOperInfosetName()));
                id.addContent(new Element("operInfoitemId").setText(logLog[i].getOperInfoitemId()));
                id.addContent(new Element("operInfoitemName").setText(logLog[i].getOperInfoitemName()));
                int flag = Tools.filterNull(logLog[i].getOperInfoitemId()).lastIndexOf("|7");
                int flag1 = Tools.filterNull(logLog[i].getOperInfoitemId()).lastIndexOf("|6");
                if (flag > 0) {
                    id.addContent(new Element("operValuePre").setText(SysCacheTool.interpretCode("OU", logLog[i].getOperValuePre())));
                    id.addContent(new Element("operValueSuf").setText(SysCacheTool.interpretCode("OU", logLog[i].getOperValueSuf())));
                } else if (flag1 > 0) {
                    id.addContent(new Element("operValuePre").setText(SysCacheTool.interpretCode("INFOITEM", logLog[i].getOperValuePre())));
                    id.addContent(new Element("operValueSuf").setText(SysCacheTool.interpretCode("INFOITEM", logLog[i].getOperValueSuf())));
                } else {
                    id.addContent(new Element("operValuePre").setText(logLog[i].getOperValuePre()));
                    id.addContent(new Element("operValueSuf").setText(logLog[i].getOperValueSuf()));
                }
//                childerAttribute = id.addContent((Content) new Element("operValuePre").setText(Tools.codeInterpert(logLog[i].getOperValuePre())));
//                childerAttribute = id.addContent((Content) new Element("operValueSuf").setText(Tools.codeInterpert(logLog[i].getOperValueSuf())));
                operation.add(id);
            }
            XMLOutputter XMLOut = new XMLOutputter();
            XMLOut.output(Doc, new FileOutputStream(f));
        }
    }


    public String getSql(OperLogBO[] logLog, int j, String dt) throws RollbackableException {
        if (logLog != null) {
            String sqlStr = "";
            StringBuffer sql = new StringBuffer();
            sql.append("insert into ").append(dt).append(" values('").append(logLog[j].getOperId()).append("','");
            sql.append(logLog[j].getOperDatetime().toString()).append("','");
            if (logLog[j].getOperatorId() != null) {
                sql.append(logLog[j].getOperatorId()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperType() != null) {
                sql.append(logLog[j].getOperType()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperatorName() != null) {
                sql.append(logLog[j].getOperatorName()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperPersonId() != null) {
                sql.append(logLog[j].getOperPersonId()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperUsername() != null) {
                sql.append(logLog[j].getOperUsername()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperUserDept() != null) {
                sql.append(logLog[j].getOperUserDept()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperRecordId() != null) {
                sql.append(logLog[j].getOperRecordId()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperInfosetId() != null) {
                sql.append(logLog[j].getOperInfosetId()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperInfosetName() != null) {
                sql.append(logLog[j].getOperInfosetName()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperInfoitemId() != null) {
                sql.append(logLog[j].getOperInfoitemId()).append("','");
            } else {
                sql.append("null").append("','");
            }
            if (logLog[j].getOperInfoitemName() != null) {
                sql.append(logLog[j].getOperInfoitemName()).append("','");
            } else {
                sql.append("null").append("','");
            }
            int flag = Tools.filterNull(logLog[j].getOperInfoitemId()).lastIndexOf("|7");
            int flag1 = Tools.filterNull(logLog[j].getOperInfoitemId()).lastIndexOf("|6");
            if (flag > 0) {
                if (logLog[j].getOperValuePre() != null) {
                    sql.append(SysCacheTool.interpretCode("OU", logLog[j].getOperValuePre())).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperDescPre() != null) {
                    sql.append(logLog[j].getOperDescPre()).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperValueSuf() != null) {
                    sql.append(SysCacheTool.interpretCode("OU", logLog[j].getOperValueSuf())).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperDescSuf() != null) {
                    sql.append(logLog[j].getOperDescSuf()).append("')");
                } else {
                    sql.append("null ").append("')");
                }
            } else if (flag1 > 0) {
                if (logLog[j].getOperValuePre() != null) {
                    sql.append(SysCacheTool.interpretInfoItem(null, logLog[j].getOperValuePre())).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperDescPre() != null) {
                    sql.append(logLog[j].getOperDescPre()).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperValueSuf() != null) {
                    sql.append(SysCacheTool.interpretInfoItem("", logLog[j].getOperValueSuf())).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperDescSuf() != null) {
                    sql.append(logLog[j].getOperDescSuf()).append("')");
                } else {
                    sql.append("null ").append("')");
                }
            } else {
                if (logLog[j].getOperValuePre() != null) {
                    sql.append(logLog[j].getOperValuePre()).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperDescPre() != null) {
                    sql.append(logLog[j].getOperDescPre()).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperValueSuf() != null) {
                    sql.append(logLog[j].getOperValueSuf()).append("','");
                } else {
                    sql.append("null").append("','");
                }
                if (logLog[j].getOperDescSuf() != null) {
                    sql.append(logLog[j].getOperDescSuf()).append("')");
                } else {
                    sql.append("null ").append("')");
                }
            }

            sqlStr = sql.toString();
            return sqlStr;
        }
        return null;
    }

    public OperLogBO[] queryAllOperLog() throws BkmsException {
        return operateLogDAO.queryOperLog(null);
    }

    /**
     * 根据被操作人编号条件查询日志信息
     * added by qinyan
     *
     * @return OperationLog[]
     * @throws BkmsException
     */
    public OperLogBO[] query(OperLogBO logLog) throws BkmsException {
//        OperLogBO logConLog = new OperLogBO();
//        Tools.copyProperties(logConLog, logLog);
        return operateLogDAO.query(logLog);
    }

    public void removeOperLog(OperLogBO[] logLog) throws BkmsException {
        if (logLog == null) return;
        operateLogDAO.removeLog(logLog);
    }
}
