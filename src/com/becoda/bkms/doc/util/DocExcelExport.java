package com.becoda.bkms.doc.util;


import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.doc.pojo.vo.FamilyMemberVO;
import com.becoda.bkms.doc.pojo.vo.OrgBasicInfoVO;
import com.becoda.bkms.doc.pojo.vo.PersonBasicInfoVO;
import com.becoda.bkms.util.Tools;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class DocExcelExport {
    public static String getDateAll() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    /**
     * @param userID
     * @param ht
     * @param name
     * @param realPath
     */
    public static String genExport(String userID, Hashtable ht, String name, String realPath) throws BkmsException {
        try {
            String instanceId = userID + getDateAll();
            if (instanceId == null || instanceId.equals("")) {
                instanceId = "TempFile";
            }
            instanceId = instanceId + ".xls";
            //=ExcelExport.boToHashtable(bo);
            String old = realPath + "/file/doc/template/" + name;
            String newfile = realPath + "/file/doc/download/" + instanceId;
            //String old = realPath + "\\file\\chg\\" + name;
            //String newfile = realPath + "\\file\\chg\\temp\\" +instanceId;
            File ofile = new File(old);
            //File nfile= new File(newfile);
            FileInputStream fin = new FileInputStream(ofile);
            FileOutputStream fout = new FileOutputStream(newfile);

            Workbook template = Workbook.getWorkbook(fin);
            WritableWorkbook xls = Workbook.createWorkbook(fout, template);
            WritableSheet sheet = xls.getSheet(0);
            Enumeration et = ht.keys();

            while (et.hasMoreElements()) {
                String key = (String) et.nextElement();
                Label lb = (Label) sheet.findCell(key);
                if (lb != null) {
                    lb.setString((String) ht.get(key));
                }
            }
            xls.write();
            xls.close();
            template.close();
            fin.close();
            fout.close();
            return "/file/doc/download/" + instanceId;
        } catch (Exception ie) {
            throw new BkmsException("读取文件错误（doc）", ie, DocExcelExport.class);
        }
    }

    /**
     * @param fileName
     * @param ht
     * @param templateName
     * @param realPath 构导出数据到Excel文件程序
     */
    public static String orgExport(String fileName, Hashtable ht, String templateName, String realPath, byte[] photo) throws BkmsException {
        Workbook template = null;
        FileOutputStream fout = null;
        FileInputStream fin = null;
        WritableWorkbook xls = null;
        try {
            String old = realPath + "/file/doc/template/" + templateName;
            String newfile = realPath + "/file/doc/download/" + fileName;
            File ofile = new File(old);
            fin = new FileInputStream(ofile);
            fout = new FileOutputStream(newfile);

            template = Workbook.getWorkbook(fin);
            xls = Workbook.createWorkbook(fout, template);
            WritableSheet sheet = xls.getSheet(0);
            Enumeration et = ht.keys();

//          查找照片
            if (photo != null) {
                String strPhoto = getDateAll() + ".png";
                //转换成png格式
                try {
                    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(photo));
                    Graphics g = bi.createGraphics();
                    g.setColor(Color.black);
                    int x = bi.getWidth();
                    int y = bi.getHeight();
                    g.drawLine(0, 0, 0, y);
                    g.drawLine(0, 0, x, 0);
                    g.drawLine(x - 1, 0, x, y * 2);
                    g.drawLine(0, y - 1, x * 2, y);

                    File pic = new File(strPhoto);
                    ImageIO.write(bi, "PNG", pic);
                    /*开始图片导入*/
                    WritableImage image = new WritableImage(2, 16, 7, 2, pic);
                    sheet.addImage(image);
                } catch (Exception e2) {

                }
            }
            while (et.hasMoreElements()) {
                String key = (String) et.nextElement();
                Label lb = (Label) sheet.findCell(key);
                if (lb != null) {
                    lb.setString((String) ht.get(key));
                }
            }
            xls.write();
            return "/file/doc/download/" + fileName;
        } catch (Exception ie) {
            throw new BkmsException("读取文件错误（doc）", ie, DocExcelExport.class);
        } finally {
            try {
                if (xls != null) xls.close();
                if (template != null) template.close();
                if (fin != null) fin.close();
                if (fout != null) fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * //     * @param fileName
     * //     * @param ht
     * //     * @param name
     * //     * @param realPath
     * //     * @return
     * //     * @throws
     */
    public static String filefillCard(String fileName, Hashtable ht, String name, String realPath, byte[] photo) throws BkmsException {
        /************* add by yangxiuming 2015-1-16 ******************/
        Workbook template = null;
        FileOutputStream fout = null;
        FileInputStream fin = null;
        WritableWorkbook xls = null;
        /************* end add  ******************/
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date date = new Date();
            String dateStr = sdf.format(date);

            String old = realPath + "/file/doc/template/" + name;
            String newfile = realPath + "/file/doc/download/" + fileName;
            File ofile = new File(old);

            fin = new FileInputStream(ofile);
            fout = new FileOutputStream(newfile);

            template = Workbook.getWorkbook(fin);
            xls = Workbook.createWorkbook(fout, template);
            WritableSheet sheet = xls.getSheet(0);
            Enumeration et = ht.keys();

            //查找照片
            String strPhoto = getDateAll() + ".png";
            if (photo != null) {
                //转换成png格式
                try {
                    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(photo));
                    Graphics g = bi.createGraphics();
                    g.setColor(Color.black);
                    int x = bi.getWidth();
                    int y = bi.getHeight();
                    g.drawLine(0, 0, 0, y);
                    g.drawLine(0, 0, x, 0);
                    g.drawLine(x - 1, 0, x, y * 2);
                    g.drawLine(0, y - 1, x * 2, y);
                    String picpath = realPath + "/file/doc/download/";
                    File pic = new File(picpath, strPhoto);
                    ImageIO.write(bi, "PNG", pic);
                    /*开始图片导入*/

                    WritableImage image = new WritableImage(9, 2, 2, 4, pic);
                    sheet.addImage(image);
                } catch (Exception e2) {

                }
            }

            while (et.hasMoreElements()) {
                String key = (String) et.nextElement();
                Label lb = (Label) sheet.findCell(key);
                if (lb != null) {
                    lb.setString((String) ht.get(key));
                }
            }
            xls.write();
            return "/file/doc/download/" + fileName;
        } catch (Exception ie) {
            throw new BkmsException("读取文件错误", ie, DocExcelExport.class);
        } finally {
            /************* add by yangxiuming 2015-1-16 ******************/
            try {
                if (xls != null) xls.close();
                if (template != null) template.close();
                if (fin != null) fin.close();
                if (fout != null) fout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            /************ end add ***************************/
        }

    }

    private static String filterNull(String s) {
        if (s == null)
            return "";
        else
            return s;

    }

    public static Hashtable voToHashtable(PersonBasicInfoVO bo) {
        Hashtable ht = new Hashtable();
        ht.put("<制表日期>", Tools.getSysDate("yyyy.MM.dd"));
        ht.put("<姓名>", filterNull(bo.getName()));
        ht.put("<性别>", filterNull(bo.getSex()));
        ht.put("<出生日期>", filterNull(bo.getBirth()));
        ht.put("<民族>", filterNull(bo.getFolk()));

        ht.put("<籍贯>", filterNull(bo.getHomeTown()));
        ht.put("<政治面貌>", filterNull(bo.getPartyFigure()));
        ht.put("<入党（团）时间>", filterNull(bo.getPartyTime()));
        ht.put("<婚姻状况>", filterNull(bo.getMarry()));

        ht.put("<参加工作时间>", filterNull(bo.getWorkTime()));
        ht.put("<入本单位时间>", filterNull(bo.getUnitTime()));

        ht.put("<掌握何种外语>", filterNull(bo.getEnglish()));
        ht.put("<熟练程度>", filterNull(bo.getSecEnglish()));

        ht.put("<现任职部门及职务>", filterNull(bo.getPostAndDept()));
        ht.put("<身份证号>", filterNull(bo.getIdNum()));
        ht.put("<有何专长>", filterNull(bo.getStrongPoint()));

        ht.put("<全日制学历>", filterNull(bo.getStudyRecord()));
        ht.put("<全日制毕业院校及专业>", filterNull(bo.getStudySchoolAndMajor()));
        ht.put("<全日制毕业时间>", filterNull(bo.getStudyGraduateDate()));
        ht.put("<全日制学位>", filterNull(bo.getDegree()));

        ht.put("<在职学历>", filterNull(bo.getStudyRecordAW()));
        ht.put("<在职毕业院校及专业>", filterNull(bo.getStudySchoolAndMajorAW()));
        ht.put("<在职毕业时间>", filterNull(bo.getStudyGraduateDateAW()));
        ht.put("<在职学位>", filterNull(bo.getDegreeAW()));

        if (bo.getResumeListString() != null || "".equalsIgnoreCase(bo.getResumeListString())) {
            String t1 = filterNull(bo.getResumeListString().replaceAll("<br>", "\r\n"));
            ht.put("<工作经历>", t1.replaceAll("&nbsp;", " "));
        } else {
            ht.put("<工作经历>", "");
        }
        if (bo.getStudyListString() != null || "".equalsIgnoreCase(bo.getStudyListString())) {
            String t1 = filterNull(bo.getStudyListString().replaceAll("<br>", "\r\n"));
            ht.put("<教育经历>", t1.replaceAll("&nbsp;", " "));
        } else {
            ht.put("<教育经历>", "");
        }

        if (bo.getLetterListString() != null || "".equalsIgnoreCase(bo.getLetterListString())) {
            String t1 = filterNull(bo.getLetterListString().replaceAll("<br>", "\r\n"));
            ht.put("<证书信息>", t1.replaceAll("&nbsp;", " "));
        } else {
            ht.put("<证书信息>", "");
        }
        if (bo.getRwListString() != null || "".equalsIgnoreCase(bo.getRwListString())) {
            String t1 = filterNull(bo.getRwListString().replaceAll("<br>", "\r\n"));
            ht.put("<奖惩信息>", t1.replaceAll("&nbsp;", " "));
        } else {
            ht.put("<奖惩信息>", "");
        }
        

        if (bo.getFamilyList() != null && bo.getFamilyList().size() >= 0) {
            List flist = bo.getFamilyList();
            for (int i = 0; i < 8; i++) {
                if (i < flist.size()) {
                    FamilyMemberVO fvo = (FamilyMemberVO) flist.get(i);
                    ht.put("<姓名" + i + ">", filterNull(fvo.getFamilyName()));
                    ht.put("<称谓" + i + ">", filterNull(fvo.getFamilyRelation()));
                    ht.put("<出生日期" + i + ">", filterNull(fvo.getFamilyBirth()));
                    ht.put("<政治面貌" + i + ">", filterNull(fvo.getFamilyPartyFigure()));
                    ht.put("<学历" + i + ">", filterNull(fvo.getFamilyStudyRec()));
                    ht.put("<工作单位及职务" + i + ">", filterNull(fvo.getFamilyWorkInfo()));
                    ht.put("<联系电话" + i + ">", filterNull(fvo.getFamilyphone()));
                } else {
                    ht.put("<姓名" + i + ">", "");
                    ht.put("<称谓" + i + ">", "");
                    ht.put("<出生日期" + i + ">", "");
                    ht.put("<政治面貌" + i + ">", "");
                    ht.put("<学历" + i + ">", "");
                    ht.put("<工作单位及职务" + i + ">", "");
                    ht.put("<联系电话" + i + ">", "");
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                ht.put("<姓名" + i + ">", "");
                ht.put("<称谓" + i + ">", "");
                ht.put("<出生日期" + i + ">", "");
                ht.put("<政治面貌" + i + ">", "");
                ht.put("<学历" + i + ">", "");
                ht.put("<工作单位及职务" + i + ">", "");
                ht.put("<联系电话" + i + ">", "");
            }
        }
        return ht;
    }

    /**
     * @param bo //     * @return
     */
    public static Hashtable orgVoToHashtable(OrgBasicInfoVO bo) {
        Hashtable ht = new Hashtable();
        ht.put("<机构简称>", filterNull(bo.getName()));
        ht.put("<所在地（区、县、旗）>", SysCacheTool.interpretCodes(bo.getOrgArea()));
        ht.put("<机构负责人>", filterNull(bo.getFactPer()));
        ht.put("<机构类别>", filterNull(bo.getNature()));
        ht.put("<主管机构>", filterNull(bo.getSuperId()));
        ht.put("<机构地址>", filterNull(bo.getOrgAddress()));
        ht.put("<邮政编码>", filterNull(bo.getPostCode()));
        ht.put("<电话号码>", filterNull(bo.getPhone()));
        ht.put("<传真号码>", filterNull(bo.getFax()));
        
        String t1 = filterNull(bo.getChangeInfo()).replaceAll("<br>", "\n");
        ht.put("<机构变动情况>", t1.replaceAll("&nbsp;", " "));
        return ht;
    }

}

