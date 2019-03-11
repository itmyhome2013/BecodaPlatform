package com.becoda.bkms.util;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.emp.pojo.bo.Person;
//import com.becoda.bkms.emp.pojo.bo.TurnPostBO;
import com.becoda.bkms.org.pojo.bo.Org;
import com.becoda.bkms.pms.PmsConstants;
import com.becoda.bkms.sys.SysConstants;
import com.becoda.bkms.sys.pojo.bo.CodeItemBO;
import com.becoda.bkms.sys.pojo.bo.InfoItemBO;
import com.becoda.bkms.sys.pojo.vo.TableVO;
import jxl.Cell;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: kangdw
 * Date: 2015-6-26
 * Time: 11:40:28
 * To change this template use File | Settings | File Templates.
 */
public class FileUtil {
    public final static String ENCODE_UTF8 = "UTF-8";
    public final static String ENCODE_ANSI = "ANSI";
    public final static String ENCODE_UNICODE = "Unicode";

    /**
     * 创建文件
     *
     * @param fileContent  文件内容
     * @param absolutePath 文件存放绝对路径
     * @param fileType     文件类型，即文件后缀 如 xls 、 doc 等
     * @return 不含路径的文件名
     * @throws BkmsException
     */
    public static String createFile(byte[] fileContent, String absolutePath, String fileType) throws BkmsException {
        String fileName;
        try {
            File dir = new File(absolutePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            //delDiskFile(absolutePath);//删除文件
            fileName = Tools.getSysDate("yyyyMMdd") + "_" + SequenceGenerator.getUUID() + "." + fileType;
            String path = absolutePath + File.separator + fileName;
            FileOutputStream fos = new FileOutputStream(path);
            try {
                fos.write(fileContent);
            } finally {
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("指定位置创建文件失败", e, FileUtil.class);
        }
        return fileName;
    }

    /**
     * 创建文件
     *
     * @param fileContent  文件内容
     * @param absolutePath 文件存放绝对路径
     * @param fileType     文件类型，即文件后缀 如 xls 、 doc 等
     * @param encoding     编码格式 UTF-8 ,Unicode, ANSI
     * @return 不含路径的文件名
     * @throws BkmsException
     */
    public static String createFile(String fileContent, String absolutePath, String fileType, String encoding) throws BkmsException {
        String fileName;
        try {
            File dir = new File(absolutePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            fileName = Tools.getSysDate("yyyyMMdd") + "_" + SequenceGenerator.getUUID() + "." + fileType;
            String path = absolutePath + File.separator + fileName;
            File outFile = new File(path);

            //设定输出文件的编码
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), encoding));
            out.write(fileContent);
            out.flush();

            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("指定位置创建文件失败", e, FileUtil.class);
        }
        return fileName;
    }

    /**
     * @param path
     * @param table
     * @param isSort  导出时是否增加序号
     * @param delFile
     * @return
     * @throws BkmsException
     */
    public static String exportFile(String path, TableVO table, boolean isSort, boolean delFile) throws BkmsException {
        try {
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            String dir = path + File.separator;
            String filename;
            if (delFile) {
                delDiskFile(path);
            }
            filename = Tools.getSysDate("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".xls";
            File f = new File(dir + filename);

            WritableWorkbook book = Workbook.createWorkbook(f);
            WritableSheet sheet = book.createSheet("sheet1", 0);

            InfoItemBO[] header = table.getHeader();
            //画列头
            if (header != null && header.length > 0) {
                int i = 0;
                int count = header.length;
                if (isSort) {
                    sheet.addCell(new Label(0, 0, "序号"));
                    i = 1;
                }
                for (int n = 0; n < count; n++) {
                    if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[n].getItemDataType())
                            || (SysConstants.INFO_ITEM_EDIT_PROP_HIDE.equals(header[n].getEditProp()) && !"B001003".equals(header[n].getItemId()))
                            || (table.getColRight() != null && table.getColRight(n) == PmsConstants.PERMISSION_REFUSE)) {

                        continue;
                    }
//                    if (header[n].isShowId()) {
//                        sheet.addCell(new Label(i, 0, header[n].getItemName() + "代码"));
//                        i++;
//                    }
                    sheet.addCell(new Label(i, 0, header[n].getItemName()));
                    i++;
                }
            }
            //画表体
            if (table.getRows() != null && table.getRows().length > 0) {
                jxl.write.Label labelC;
                String[][] rows = table.getRows();
                int count = rows.length;
                HashMap methodHash = new HashMap();
                for (int i = 0; i < count; i++) {
                    String[] data = rows[i];
                    int col = 0;
                    if (isSort) {
                        col = 1;
                        sheet.addCell(new Label(0, i + 1, String.valueOf(i + 1)));
                    }
                    int size = data.length;
                    for (int n = 0; n < size; n++) {
                        if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[n].getItemDataType())
                                || (SysConstants.INFO_ITEM_EDIT_PROP_HIDE.equals(header[n].getEditProp()) && !"B001003".equals(header[n].getItemId()))
                                || (table.getColRight() != null && table.getColRight(n) == PmsConstants.PERMISSION_REFUSE)) {
                            continue;
                        }
                        if (table.getCellRight() != null && PmsConstants.PERMISSION_REFUSE == table.getCellRight(i, n)) {
                            continue;
                        }
                        String value2;
                        if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(header[n].getItemDataType()) || SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(header[n].getItemDataType())) {
                            Method method = (Method) methodHash.get(header[n].getInterpret());
                            Object obj = methodHash.get("class");
                            if (method == null) {
                                method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", header[n].getInterpret(), new Class[]{String.class});
                                methodHash.put(header[n].getInterpret(), method);
                            }
                            if (obj == null) {
                                obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                methodHash.put("class", obj);
                            }
                            value2 = method.invoke(obj, new String[]{data[n]}).toString();
                        } else {
                            value2 = Tools.filterNull(data[n]);
                        }
                        labelC = new jxl.write.Label(col, i + 1, value2);
                        sheet.addCell(labelC);
                        col++;
                    }
                }
            }
            book.write();
            book.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("导出excel错误", e, FileUtil.class);
        }
    }


    public static String exportFile(String path, ArrayList list) throws BkmsException {
        try {
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            String dir = path + File.separator;
            String filename;

            filename = Tools.getSysDate("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".xls";
            File f = new File(dir + filename);

            WritableWorkbook book = Workbook.createWorkbook(f);
            WritableSheet sheet = book.createSheet("sheet1", 0);

            //画列头
            String[] args = {"关键岗位名称", "工号", "姓名", "履职监督实施方式", "计划完成时间（月）", "履职监督组织实施责任人", "实际实施开始时间", "实际实施结束时间", "实施报告简要内容", "备注"};
            for (int i = 0; i < args.length; i++) {

                sheet.addCell(new Label(i, 0, args[i]));

            }
            String[] rows = new String[args.length];

            //画表体

            Iterator iterator = list.iterator();
//            for (int i = 1; iterator.hasNext(); i++) {
//                TurnPostBO bo = (TurnPostBO) iterator.next();
//                rows[0] = bo.getName();
//                rows[1] = (SysCacheTool.findPersonById(bo.getPerid())).getPersonCode();
//                rows[2] = (SysCacheTool.findPersonById(bo.getPerid())).getName();
//                rows[3] = SysCacheTool.interpretCode(bo.getStyle());
//                rows[4] = bo.getPlantime();
//                rows[5] = bo.getPrimarier();
//                rows[6] = bo.getRfpostdate();
//                rows[7] = bo.getRepostdate();
//                rows[8] = bo.getContents();
//                rows[9] = bo.getRemark();
//                for (int j = 0; j < args.length; j++) {
//                    sheet.addCell(new Label(j, i, rows[j]));
//                }
//            }

            book.write();
            book.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("导出excel错误", e, FileUtil.class);
        }
    }

    /**
     * 导出CSV文件
     *
     * @param path
     * @param table
     * @param delFile
     * @return
     * @throws BkmsException
     */
    public static String exportCSV(String path, TableVO table, boolean delFile) throws BkmsException {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            String dir = path + File.separator;
            String filename = "";
            if (delFile) {
                delDiskFile(path);
            }
            filename = Tools.getSysDate("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".csv";
            File f = new File(dir + filename);
            out = new FileOutputStream(f);
            osw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(osw);
            InfoItemBO[] header = table.getHeader();

            /********************start 生成头部显示项****************************/
            if (header != null && header.length > 0) {
                int count = header.length;
                for (int n = 0; n < count; n++) {
                    if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[n].getItemDataType())
                            || (SysConstants.INFO_ITEM_EDIT_PROP_HIDE.equals(header[n].getEditProp()) && !"B001003".equals(header[n].getItemId()))
                            || (table.getColRight() != null && table.getColRight(n) == PmsConstants.PERMISSION_REFUSE)) {
                        continue;
                    } else {
                        bw.write(header[n].getItemName() + "代码");
                        if (n != count - 1)
                            bw.write(",");

                    }
                }
            }
            bw.newLine();
            /********************end 生成头部显示项****************************/
            fillData(table, bw, header);
            return filename;
        } catch (Exception e) {
            throw new BkmsException("导出csv错误", e, FileUtil.class);
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            bw = null;
            if (osw != null) try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            osw = null;
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            out = null;
        }
    }

    private static void fillData(TableVO table, BufferedWriter bw, InfoItemBO[] header) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, IOException {
        if (table.getRows() != null && table.getRows().length > 0) {
            int count = table.getRows().length;
            HashMap methodHash = new HashMap();
            for (int i = 0; i < count; i++) {
                String[] data = table.getRows()[i];
                int size = data.length;
                for (int n = 0; n < size; n++) {
                    if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[n].getItemDataType())
                            || (SysConstants.INFO_ITEM_EDIT_PROP_HIDE.equals(header[n].getEditProp()) && !"B001003".equals(header[n].getItemId()) && !"A001728".equals(header[n].getItemId()) && !"A001738".equals(header[n].getItemId()))
                            || table.getColRight(n) == PmsConstants.PERMISSION_REFUSE) {
                        continue;
                    } else {
                        String value2 = "";
                        if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(header[n].getItemDataType()) || SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(header[n].getItemDataType())) {
                            Method method = (Method) methodHash.get(header[n].getInterpret());
                            Object obj = methodHash.get("class");
                            if (method == null) {
                                method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", header[n].getInterpret(), new Class[]{String.class});
                                methodHash.put(header[n].getInterpret(), method);
                            }
                            if (obj == null) {
                                obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                methodHash.put("class", obj);
                            }
                            value2 = method.invoke(obj, new String[]{data[n]}).toString();
                        } else {
                            value2 = Tools.filterNull(data[n]);
                        }
                        try {
                            if (SysConstants.INFO_ITEM_DATA_TYPE_INT.equals(header[n].getItemDataType())
                                    || SysConstants.INFO_ITEM_DATA_TYPE_FLOAT.equals(header[n].getItemDataType())
                                    || SysConstants.INFO_ITEM_DATA_TYPE_COMPUTE.equals(header[n].getItemDataType())) {
                                bw.write(value2);
                                if (n != size - 1)
                                    bw.write(",");
                            } else {
//                                    if (header[n].isShowId()) {
//                                        String id = data[n];
//                                        if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(header[n].getItemDataType()) && Tools.filterNull(id).length() > 4)
//                                            id = id.substring(4, id.length());
//                                        bw.write(id);
//                                        bw.write(",");
//                                    }
                                bw.write(value2);
                                if (n != size - 1)
                                    bw.write(",");
                            }
                        } catch (Exception e) {
                            bw.write(value2);
                            if (n != size - 1)
                                bw.write(",");
                        }
                    }
                }
                bw.newLine();
            }
        }
    }

    /**
     * 填充csv数据
     *
     * @param path
     * @param filename
     * @param table
     * @param delFile
     * @return
     * @throws BkmsException
     */
    public static String exportCSVBody(String path, String filename, TableVO table, boolean delFile) throws BkmsException {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            String dir = path + File.separator;
            if (delFile) {
                delDiskFile(path);
            }
            File f = new File(dir + filename);
            out = new FileOutputStream(f, true);
            osw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(osw);

            InfoItemBO[] header = table.getHeader();

            fillData(table, bw, header);
            return filename;
        } catch (Exception e) {
            throw new BkmsException("导出csv错误", e, FileUtil.class);
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            bw = null;
            if (osw != null) try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            osw = null;
            if (out != null) try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            out = null;
        }
    }

    /**
     * 导出CSV文件
     *
     * @param path        导出文件存放绝对路径
     * @param header      列头名称
     * @param colDataType 列的数据类型。 数组长度和列头数组一致，且一一对应，数据类型和指标项数据类型一致。如果导出时不需要对数据进行处理，则为null。
     * @param values      数据记录 。        每一条记录都是String[]，长度和列头数组一致，且一一对应。
     * @param delFile     是否路径下删除文件
     * @return excel      导出文件名称（不含路径）
     */
    public static String exportCSV(String path,
                                   String[] header,
                                   String[] colDataType,
                                   List values, boolean delFile) throws BkmsException {
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            if (header != null && colDataType != null && colDataType.length != colDataType.length) {
                throw new BkmsException("数据类型和显示列头长度不匹配");
            }
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }

            String dir = path + File.separator;
            String filename = "";
            if (delFile) {
                delDiskFile(path);
            }

            filename = Tools.getSysDate("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".csv";
            File f = new File(dir + filename);

            out = new FileOutputStream(f);
            osw = new OutputStreamWriter(out, "UTF-8");
            bw = new BufferedWriter(osw);

            int colNum = 0;
            int rowNum = 0;
            if (header != null && header.length > 0) {
                colNum = header.length;
                for (int n = 0; n < colNum; n++) {
                    bw.write(header[n]);
                    if (n != colNum - 1)
                        bw.write(",");
                }
            }
            bw.newLine();
            if (values != null && values.size() > 0) {
                rowNum = values.size();
                HashMap methodHash = new HashMap();
                for (int i = 0; i < rowNum; i++) {
                    String data[] = (String[]) values.get(i);
                    for (int n = 0; n < colNum; n++) {
                        String value2 = "";
                        if (colDataType != null) {
                            String[] tmp = colDataType[n].split("|");
                            if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(tmp[0])) {
                                value2 = SysCacheTool.interpretCode(data[n]);
                            } else if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(tmp[0])) {
                                Method method = (Method) methodHash.get(tmp[1]);
                                Object obj = methodHash.get("class");
                                if (method == null) {
                                    method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", tmp[1], new Class[]{String.class});
                                    methodHash.put(tmp[1], method);
                                }
                                if (obj == null) {
                                    obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                    methodHash.put("class", obj);
                                }
                                value2 = method.invoke(obj, new String[]{data[n]}).toString();
                            } else {
                                value2 = Tools.filterNull(data[n]);
                            }
                            try {
                                if (SysConstants.INFO_ITEM_DATA_TYPE_INT.equals(colDataType[n])
                                        || SysConstants.INFO_ITEM_DATA_TYPE_FLOAT.equals(colDataType[n])
                                        || SysConstants.INFO_ITEM_DATA_TYPE_COMPUTE.equals(colDataType[n])) {
                                    bw.write(value2);
                                    if (n != colNum - 1)
                                        bw.write(",");
                                } else {
                                    bw.write(value2);
                                    if (n != colNum - 1)
                                        bw.write(",");
                                }
                            } catch (Exception e) {
                                bw.write(value2);
                                if (n != colNum - 1)
                                    bw.write(",");
                            }
                        } else {
                            value2 = Tools.filterNull(data[n]);
                            bw.write(value2);
                            if (n != colNum - 1)
                                bw.write(",");
                        }
                    }
                    bw.newLine();
                }
            }
            return filename;
        } catch (Exception e) {
            throw new BkmsException("导出excel错误", e, FileUtil.class);
        } finally {
            if (bw != null) try {
                bw.close();
            } catch (IOException e) {

            }
            bw = null;
            if (osw != null) try {
                osw.close();
            } catch (IOException e) {

            }
            osw = null;
            if (out != null) try {
                out.close();
            } catch (IOException e) {

            }
            out = null;
        }
    }

    /**
     * 导出TXT文件 ,sunmh
     *
     * @param path      导出文件存放绝对路径
     * @param table     数据对象
     * @param delFile   是否目录下文件
     * @param separator 分割符
     * @return excel  导出文件名称（含绝对路径）
     */
    public static String exportTxtFile(String path, TableVO table, boolean delFile, String separator) throws BkmsException {
        try {
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }

            String dir = path + File.separator;
            String filename;
            if (delFile) {
                delDiskFile(path);
            }

            filename = Tools.getSysDate("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".txt";
            File f = new File(dir + filename);

            StringBuffer headerBuff = new StringBuffer();
            StringBuffer contentBuff = new StringBuffer();
            InfoItemBO[] header = table.getHeader();
            if (header != null && header.length > 0) {
                int count = header.length;
                for (int n = 0; n < count; n++) {
                    if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[n].getItemDataType())
                            || (SysConstants.INFO_ITEM_EDIT_PROP_HIDE.equals(header[n].getEditProp()) && !"B001003".equals(header[n].getItemId()))
                            || (table.getColRight() != null && table.getColRight(n) == PmsConstants.PERMISSION_REFUSE)) {
                        continue;
                    }
                    if (n != 0) {
                        headerBuff.append(separator);
                    }
//                    if (header[n].isShowId()) {
//                        headerBuff.append(header[n].getItemName() + "代码");
//                    }
                    headerBuff.append(header[n].getItemName());
                }
            }
            if (table.getRows() != null && table.getRows().length > 0) {
                int count = table.getRows().length;
                String[][] rows = table.getRows();
                HashMap methodHash = new HashMap();
                for (int i = 0; i < count; i++) {
                    String[] data = rows[i];
                    int size = data.length;
                    for (int n = 0; n < size; n++) {
                        if (SysConstants.INFO_ITEM_DATA_TYPE_CLOB.equals(header[n].getItemDataType())
                                || (SysConstants.INFO_ITEM_EDIT_PROP_HIDE.equals(header[n].getEditProp()) && !"B001003".equals(header[n].getItemId()))
                                || table.getColRight(n) == PmsConstants.PERMISSION_REFUSE) {
                            continue;
                        }
                        String value2;
                        if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(header[n].getItemDataType())) {
                            value2 = SysCacheTool.interpretCode(data[n]);
                        } else if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(header[n].getItemDataType())) {
                            Method method = (Method) methodHash.get(header[n].getInterpret());
                            Object obj = methodHash.get("class");
                            if (method == null) {
                                method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", header[n].getInterpret(), new Class[]{String.class});
                                methodHash.put(header[n].getInterpret(), method);
                            }
                            if (obj == null) {
                                obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                methodHash.put("class", obj);
                            }
                            value2 = method.invoke(obj, new String[]{data[n]}).toString();
                        } else {
                            value2 = Tools.filterNull(data[n]);
                        }
                        if (n == 0) {
                            contentBuff.append("\n");
                        } else {
                            contentBuff.append(separator);
                        }
                        try {
                            if (SysConstants.INFO_ITEM_DATA_TYPE_INT.equals(header[n].getItemDataType())
                                    || SysConstants.INFO_ITEM_DATA_TYPE_FLOAT.equals(header[n].getItemDataType())
                                    || SysConstants.INFO_ITEM_DATA_TYPE_COMPUTE.equals(header[n].getItemDataType())) {
                                contentBuff.append(value2);
                            } else {
//                                if (header[n].isShowId()) {
//                                    String id = data[n];
//                                    if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(header[n].getItemDataType()) && Tools.filterNull(id).length() > 4)
//                                        id = id.substring(4, id.length());
//                                    contentBuff.append(id).append(separator);
//                                }
                                contentBuff.append(value2);
                            }
                        } catch (Exception e) {
                            contentBuff.append(value2);
                        }
                    }
                }
                // contentBuff.append("\n");
            }
            String s = headerBuff.toString() + "\n" + contentBuff.toString();
            appendStringToFile(f, s);

            return filename;
        } catch (Exception e) {
            throw new BkmsException("导出excel错误", e, FileUtil.class);
        }
    }

    /**
     * 导出文件
     *
     * @param path        导出文件存放绝对路径
     * @param header      列头名称
     * @param colDataType 列的数据类型。 数组长度和列头数组一致，且一一对应，数据类型和指标项数据类型一致。
     *                    如果导出时不需要对数据进行处理， 则为null。如果数据类型是指标类型  则需要指明翻译方法。如   8|interpretOrg
     * @param values      数据记录 。        每一条记录都是String[]，长度和列头数组一致，且一一对应。
     * @param isSort      是否增加序号
     * @param delFile
     * @return excel      导出文件名称（不含路径）
     */
    public static String exportFile(String path,
                                    String[] header,
                                    String[] colDataType, List values,
                                    boolean isSort, boolean delFile) throws BkmsException {

        try {
            if (header != null && colDataType != null && colDataType.length != colDataType.length) {
                throw new BkmsException("数据类型和显示列头长度不匹配", FileUtil.class);
            }
            File dirPath = new File(path);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }

            String dir = path + File.separator;
            String filename;
            if (delFile) {
                delDiskFile(path);
            }

            filename = Tools.getSysDate("yyyyMMddHHmmss") + new Random().nextInt(10000) + ".xls";
            File f = new File(dir + filename);

            WritableWorkbook book = Workbook.createWorkbook(f);
            WritableSheet sheet = book.createSheet("sheet1", 0);
            int colNum = 0;
            int rowNum;
            if (header != null && header.length > 0) {
                colNum = header.length;
                int i = 0;
                if (isSort) {
                    sheet.addCell(new Label(0, 0, "序号"));
                    i = 1;
                }
                for (int n = 0; n < colNum; n++) {
                    sheet.addCell(new Label(i, 0, header[n]));
                    i++;
                }
            }
            if (values != null && values.size() > 0) {
                jxl.write.Label labelC;
                rowNum = values.size();
                HashMap methodHash = new HashMap();
                for (int i = 0; i < rowNum; i++) {
                    int col = 0;
                    if (isSort) {
                        col = 1;
                        sheet.addCell(new Label(0, i + 1, String.valueOf(i + 1)));
                    }
                    String data[] = (String[]) values.get(i);
                    for (int n = 0; n < colNum; n++) {
                        String value2 = "";
                        if (colDataType != null) {
                            String[] tmp = colDataType[n].split("|");
                            if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(tmp[0])) {
                                value2 = SysCacheTool.interpretCode(data[n]);
                            } else if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(tmp[0])) {
                                Method method = (Method) methodHash.get(tmp[1]);
                                Object obj = methodHash.get("class");
                                if (method == null) {
                                    method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", tmp[1], new Class[]{String.class});
                                    methodHash.put(tmp[1], method);
                                }
                                if (obj == null) {
                                    obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                    methodHash.put("class", obj);
                                }
                                value2 = method.invoke(obj, new String[]{data[n]}).toString();
                            } else {
                                value2 = Tools.filterNull(data[n]);
                            }
                        } else {
                            value2 = Tools.filterNull(data[n]);
                        }
                        labelC = new jxl.write.Label(col, i + 1, value2);
                        sheet.addCell(labelC);
                        col++;
                    }
                }
            }
            book.write();
            book.close();
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BkmsException("导出excel错误", e, FileUtil.class);
        }
    }

    /**
     * 删除指定路径下的文件 导出的excel文件
     *
     * @param path
     */
    public static void delDiskFile(String path) {
        File dir = new File(path);
        String[] filename = dir.list();
        if (filename == null) return;
        int count = filename.length;
        String date = Tools.getSysDate("yyyyMMdd");
        for (int i = 0; i < count; i++) {
            String str = filename[i];
            if (str.indexOf(date) == -1 && (str.indexOf("xls") > -1 || str.indexOf("txt") > -1 || str.indexOf("csv") > -1 || str.indexOf("jpg") > -1)) {
                File f = new File(path + File.separator + filename[i]);
                f.delete();
            }
        }
    }

    /**
     * 删除指定路径下的文件
     *
     * @param path
     */
    public static void delFile(String path) {
        File f = new File(path);
        if (f.exists())
            f.delete();
    }

    /**
     * 读取excle文件
     *
     * @param absolutePath 文件存放路径
     * @param fileName     文件名称(不含路径)
     * @param cols         数据读取列的数据类型
     * @param keyDataType  记录索引数据类型 沿用指标集分类（机构、人员、岗位等）
     * @param keyCol       索引数据所在列
     * @param impRs        读出数据存放 的hastable（如果为null不执行读数据操作）。 key:人员id、机构id等，value：String[] 数据列一致。
     * @param startCol     读取数据开始列(从0开始)
     * @param startRow     读取数据开始行(从0开始)
     * @param delFile      是否删除原文件 true 是， false 否
     * @return boolean 读文件数据是否有错误。数据无错误 true,数据有错误 false.  若返回false,原文件不删除，并在源文件的里生成新的sheet，记录错误信息
     */
    public static boolean readCSVFile(String absolutePath, String fileName,
                                      InfoItemBO[] cols,
                                      String keyDataType, int keyCol,
                                      Map impRs,
                                      int startCol, int startRow, boolean delFile) throws BkmsException {
        if (impRs == null) {
            throw new BkmsException("impRs参数为n瓦格ull", FileUtil.class);
        } else {
            impRs.clear();
        }
        if (keyDataType == null || "".equals(keyDataType)) {
            throw new BkmsException("没有指定文件索引类型", FileUtil.class);
        }
        File f = new File(absolutePath + File.separator + fileName);
        if (!f.exists() || !f.isFile()) {
            throw new BkmsException("指定文件不存在", FileUtil.class);
        }
        BufferedReader br = null;
        BufferedWriter bw = null;
        boolean isError = false;
        try {
            //读文件
            br = new BufferedReader(new FileReader(f));
            //创建错误日志文件
            bw = new BufferedWriter(new FileWriter(new File(absolutePath + File.separator + "ERROR_" + fileName)));
            //获取第一张Sheet表
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new BkmsException("导入的文件为空，请导入有内容的文件!", FileUtil.class);
            }
            int columns = headerLine.split(",").length;
            if ((cols.length + startCol) > columns) {
                throw new BkmsException("您所选项的数目超过Excel列的数目，请重新选择!", FileUtil.class);
            }
            if (startRow < 1) {
                startRow = 1;
            }
            for (int i = 1; i < startRow; i++) {
                br.readLine();
            }
            String lineData;
            int parsingLineNumber = startRow - 1;   //正在处理的行号,用于记录错误日志等.
            while ((lineData = br.readLine()) != null) {
                parsingLineNumber++;

                String[] data = lineData.split(",");
                if (data.length != (cols.length + startCol)) {
                    addErrorFormatLabel(bw, parsingLineNumber, keyCol, "数据错误，导入文件列数与所选择列数不一致或者数据中包含半角的\",\",不能导入");
                    isError = true;
                    continue;
                }
                String key = data[keyCol];
                if (key != null && !"".equals(key.trim())) {
                    key = key.trim();
                    if ("A".equals(keyDataType)) {
                        if (key.indexOf("-") >= 0 || key.indexOf("－") >= 0 || key.indexOf("_") >= 0) {
                            addErrorFormatLabel(bw, parsingLineNumber, keyCol, "记录错误，逻辑减员人员不能导入数据");
                            isError = true;
                            continue;
                        }
                        Person obj = SysCacheTool.findPersonByCode(key);
                        if (obj == null) {
                            addErrorFormatLabel(bw, parsingLineNumber, keyCol, "记录错误，人员不存在");
                            isError = true;
                            continue;
                        }
                        key = obj.getPersonId();
                    } else if ("B".equals(keyDataType)) {
                        Org obj = SysCacheTool.findOrgByCode(key);
                        if (obj == null) {
                            addErrorFormatLabel(bw, parsingLineNumber, keyCol, "记录错误，机构不存在");
                            isError = true;
                            continue;
                        }
                        key = obj.getOrgId();
//                    } else if ("C".equals(keyDataType)) {
//                        PostBO obj = SysCacheTool.findPost(key);
//                        if (obj == null) {
//                            addErrorFormatLabel(bw, parsingLineNumber, keyCol, "记录错误，岗位不存在");
//                            isError = true;
//                            continue;
//                        }
//                        key = obj.getPostId();
//                    } else if ("D".equals(keyDataType)) {
//                        PartyBO obj = SysCacheTool.findParty(key);
//                        if (obj == null) {
//                            addErrorFormatLabel(bw, parsingLineNumber, keyCol, "记录错误，党组织不存在");
//                            isError = true;
//                            continue;
//                        }
//                        key = obj.getPartyId();
                    }
                } else {
                    //出现空数据，停止读取
                    break;
                }
                int colNum = cols.length;
                String[] rs = new String[colNum];
                boolean colError = false;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                HashMap methodHash = new HashMap();
                for (int j = startCol; j < startCol + colNum; j++) { //start for
                    String value = data[j];
                    String colDataType = cols[j - startCol].getItemDataType();
                    if (value != null && !"".equals(value.trim())) {
                        if (SysConstants.INFO_ITEM_DATA_TYPE_CODE.equals(colDataType)) {
                            String code = cols[j - startCol].getItemCodeSet() + value.trim();
                            CodeItemBO tmp = SysCacheTool.findCodeItem(code);
                            if (tmp == null) {
                                addErrorFormatLabel(bw, parsingLineNumber, j, "代码不存在");
                                colError = true;
                            } else {
                                rs[j - startCol] = tmp.getItemId();
                            }
                        } else if (SysConstants.INFO_ITEM_DATA_TYPE_INFO.equals(colDataType)) {
                            Method method = (Method) methodHash.get(cols[j - startCol].getInterpretByCode());
                            Object obj = methodHash.get("class");
                            if (method == null) {
                                method = Tools.getInvokeMethod("com.becoda.bkms.cache.SysCacheTool", cols[j - startCol].getInterpretByCode(), new Class[]{String.class});
                                methodHash.put(cols[j - startCol].getInterpretByCode(), method);
                            }
                            if (obj == null) {
                                obj = Class.forName("com.becoda.bkms.cache.SysCacheTool").newInstance();
                                methodHash.put("class", obj);
                            }
                            String objId = method.invoke(obj, new String[]{value.trim()}).toString();

                            if (objId == null) {
                                addErrorFormatLabel(bw, parsingLineNumber, j, value + "不存在");
                                colError = true;
                            } else {
                                rs[j - startCol] = objId;
                            }
                        } else if (SysConstants.INFO_ITEM_DATA_TYPE_INT.equals(colDataType)) {
                            try {
                                String tempStr = value.trim();
                                if (tempStr.indexOf(".") > 0) {
                                    String str = tempStr.substring(0, tempStr.indexOf("."));
                                    Long.parseLong(str);
                                    rs[j - startCol] = str;
                                } else {
                                    Long.parseLong(tempStr);
                                    rs[j - startCol] = tempStr;
                                }
                            } catch (NumberFormatException e) {
                                colError = true;
                                addErrorFormatLabel(bw, parsingLineNumber, j, "数据格式错误，不是整型数据");
                            }
                        } else if (SysConstants.INFO_ITEM_DATA_TYPE_FLOAT.equals(colDataType)) {
                            try {
                                Double.parseDouble(value.trim());
                                rs[j - startCol] = value.trim();
                            } catch (NumberFormatException e) {
                                colError = true;
                                addErrorFormatLabel(bw, parsingLineNumber, j, "数据格式错误，不是小数数据");
                            }
                        } else if (SysConstants.INFO_ITEM_DATA_TYPE_DATE6.equals(colDataType)) {
                            try {
                                value = value.trim();
                                if (value.length() == 6) {
                                    if (Integer.parseInt(value.substring(0, 4)) > 1900
                                            && Integer.parseInt(value.substring(4, 6)) <= 12) {
                                        value = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + "01";
                                        java.sql.Date dt = java.sql.Date.valueOf(value);
                                        value = sdf.format(dt);
                                        rs[j - startCol] = value;
                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception e) {
                                colError = true;
                                addErrorFormatLabel(bw, parsingLineNumber, j, "数据格式错误，不是六位日期");
                            }
                        } else if (SysConstants.INFO_ITEM_DATA_TYPE_DATE8.equals(colDataType)) {
                            try {
                                value = value.trim();
                                if (value.length() == 8) {
                                    if (Integer.parseInt(value.substring(0, 4)) > 1900
                                            && Integer.parseInt(value.substring(4, 6)) <= 12
                                            && Integer.parseInt(value.substring(6, 8)) <= 31) {
                                        value = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
                                        java.sql.Date dt = java.sql.Date.valueOf(value);
                                        value = sdf1.format(dt);
                                        rs[j - startCol] = value;
                                    } else {
                                        throw new Exception();
                                    }
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception e) {
                                colError = true;
                                addErrorFormatLabel(bw, parsingLineNumber, j, "数据格式错误，不是八位日期");
                            }
                        } else {
                            rs[j - startCol] = value.trim();
                        }
                    } else {//end content not null
                        rs[j - startCol] = "";
                    }
                }//end for
                if (!colError) impRs.put(key, rs);
                if (!isError && colError) isError = colError;
            }
            return !isError;
        } catch (BkmsException be) {
            throw be;
        } catch (Exception e) {
            throw new BkmsException("读取文件失败", e, FileUtil.class);
        } finally {
            try {
                if (br != null)
                    br.close();
                if (bw != null)
                    bw.close();

                if (delFile && !isError) {
                    f.delete();
                }
            } catch (Exception ea) {
                ea.printStackTrace();
            }
        }
    }

    /**
     * 读取csv文件
     * 简化版,不记录错误,不判断数据类型,不判断每行的列数,出现错误直接写为空字符
     *
     * @param absolutePath 文件存放路径
     * @param fileName     文件名称(不含路径)
     * @param cols         数据读取列的数据类型
     * @param keyCol       索引数据所在列
     * @param impRs        读出数据存放 的hastable（如果为null不执行读数据操作）。 key:人员id、机构id等，value：String[] 数据列一致。
     * @param startCol     读取数据开始列(从0开始)
     * @param startRow     读取数据开始行(从0开始)
     * @param delFile      是否删除原文件 true 是， false 否
     * @return boolean 读文件数据是否有错误。数据无错误 true,数据有错误 false.  若返回false,原文件不删除，并在源文件的里生成新的sheet，记录错误信息
     */
    public static boolean readCSVFile(String absolutePath, String fileName,
                                      InfoItemBO[] cols,
                                      int keyCol,
                                      Map impRs,
                                      int startCol, int startRow, boolean delFile, int rowSize) throws BkmsException {
        if (impRs == null) {
            throw new BkmsException("impRs参数为null");
        } else {
            impRs.clear();
        }

        File f = new File(absolutePath + File.separator + fileName);
        if (!f.exists() || !f.isFile()) {
            throw new BkmsException("指定文件不存在");
        }
        BufferedReader br = null;
//        BufferedWriter bw = null;
        boolean isError = false;
        try {
            //读文件
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            //创建错误日志文件
//            bw = new BufferedWriter(new FileWriter(new File(absolutePath + File.separator + "ERROR_" + fileName)));
            //获取第一张Sheet表

            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new BkmsException("导入的文件为空，请导入有内容的文件!");
            }

            //end add by sunmh 2015-01-10
            int columns = headerLine.split(",").length;
            if ((cols.length + startCol) > columns) {
                throw new BkmsException("您所选项的数目超过Excel列的数目，请重新选择!");
            }
            if (startRow < 1) {
                startRow = 1;
            }
            for (int i = 1; i < startRow; i++) {
                br.readLine();
            }
            String lineData, temp, temp1;
//            int parsingLineNumber = startRow - 1;   //正在处理的行号,用于记录错误日志等.
            int tempSize = 0;    //用于控制最大导入条数
            while ((lineData = br.readLine()) != null && !isError) {
                tempSize++;
//                parsingLineNumber ++;
                temp = lineData.replaceAll(",", " ,");
                temp1 = temp.replaceAll(",", " ,");
                lineData = temp1 + " ";
                String[] data = lineData.split(",");
                String key = data[keyCol];
                if (data == null) break;
//                if (data.length != (cols.length + startCol)) {
//                    addErrorFormatLabel(bw, parsingLineNumber, keyCol, "数据导入的列数和实际的列数不相等或者有空数据，不能导入");
//                    isError = true;
//                    continue;
//                }

                int colNum = cols.length;
                String[] rs = new String[colNum];
                boolean colError = false;
                for (int j = startCol; j < startCol + colNum; j++) { //start for
                    try {
                        String value = data[j].trim();
                        if (value != null && !"".equals(value.trim())) {
                            rs[j - startCol] = value.trim();
                        } else {
                            rs[j - startCol] = "";
                        }
                    } catch (Exception e) {
                        rs[j - startCol] = "";
                    }
                }//end for

                if (!colError)
                    impRs.put(key, rs);

                if (!isError && colError)
                    isError = colError;

                if (tempSize >= rowSize)  //如果超出最大条数
                    isError = true;
            }
            return !isError;
        } catch (BkmsException be) {
            throw be;
        } catch (Exception e) {
            throw new BkmsException("读取文件失败", e, FileUtil.class);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            if (delFile && !isError) {
                f.delete();
            }
        }
    }


    /**
     * 将错误信息记录到错误日志中
     *
     * @param bw
     * @param row
     * @param col
     * @param error
     */
    private static void addErrorFormatLabel(BufferedWriter bw, int row, int col, String error) {
        try {
            bw.write(addCsvField("第 " + row + " 行,第 " + (col + 1) + " 列 " + error));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String addCsvField(String str) {
        if (str == null)
            return ",";
        StringBuffer sb;

        if (str.indexOf("\"") != -1)
            sb = new StringBuffer(str.replaceAll("\"", "\"\""));
        else
            sb = new StringBuffer(str);
        if (str.indexOf(",") != -1) {
            sb.insert(0, "\"");
            sb.append("\"");
        }
        return sb.append(",").toString();
    }

    /**
     * 将str追加到文件
     *
     * @param file
     * @param str
     * @throws IOException
     */
    private static void appendStringToFile(File file, String str) throws IOException {
        if (!file.exists())
            file.createNewFile();
        if (str != null) {
            FileOutputStream out = new FileOutputStream(file, true);
            out.write(str.getBytes("GBK"));
            out.close();
        }
    }


    public static boolean readXLSField(String absolutePath, String fileName, ArrayList arraylist) throws Exception {
        File f = new File(absolutePath + File.separator + fileName);
        try {
            InputStream is = new FileInputStream(f);
            jxl.Workbook wb = Workbook.getWorkbook(is);
            jxl.Sheet st = wb.getSheet(0);
            Cell cell = null;
            String content = null;
            int rows = st.getRows();
            int cols = st.getColumns();
            for (int i = 1; i < rows; i++) {
                String[] cells = new String[cols];
                for (int j = 0; j < cols; j++) {

                    cell = st.getCell(j, i);
                    content = cell.getContents();
                    cells[j] = content;

                }
                if (!cells[1].equals("") && cells[1] != null)
                    arraylist.add(cells);
            }
            return true;
        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * 读取csv文件
     * 实施关键岗位轮岗专用
     *
     * @param absolutePath 文件存放路径
     * @param fileName     文件名称(不含路径)
     * @param cols         读取的数据列数
     * @param keyCol       索引数据所在列
     * @param impRs        读出数据存放 的hastable（如果为null不执行读数据操作）。 key:人员id、机构id等，value：String[] 数据列一致。
     * @param startCol     读取数据开始列(从0开始)
     * @param startRow     读取数据开始行(从0开始)
     * @param delFile      是否删除原文件 true 是， false 否
     * @return boolean 读文件数据是否有错误。数据无错误 true,数据有错误 false.  若返回false,原文件不删除，并在源文件的里生成新的sheet，记录错误信息
     */
    public static boolean readCSVFile(String absolutePath, String fileName,
                                      int cols,
                                      int keyCol,
                                      Map impRs,
                                      int startCol, int startRow, boolean delFile, int rowSize) throws BkmsException {
        if (impRs == null) {
            throw new BkmsException("impRs参数为null");
        } else {
            impRs.clear();
        }

        File f = new File(absolutePath + File.separator + fileName);
        if (!f.exists() || !f.isFile()) {
            throw new BkmsException("指定文件不存在");
        }
        BufferedReader br = null;
//        BufferedWriter bw = null;
        boolean isError = false;
        try {
            //读文件
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            //创建错误日志文件
//            bw = new BufferedWriter(new FileWriter(new File(absolutePath + File.separator + "ERROR_" + fileName)));
            //获取第一张Sheet表

            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new BkmsException("导入的文件为空，请导入有内容的文件!");
            }

            //end add by sunmh 2015-01-10
            // int columns = headerLine.split(",").length;
            /* if ((cols.length + startCol) > columns) {
                throw new BkmsException("您所选项的数目超过Excel列的数目，请重新选择!");
            }*/
            if (startRow < 1) {
                startRow = 1;
            }
            for (int i = 1; i < startRow; i++) {
                br.readLine();
            }
            String lineData, temp, temp1;
//            int parsingLineNumber = startRow - 1;   //正在处理的行号,用于记录错误日志等.
            int tempSize = 0;    //用于控制最大导入条数
            while ((lineData = br.readLine()) != null && !isError) {
                tempSize++;
//                parsingLineNumber ++;
                temp = lineData.replaceAll(",", " ,");
                temp1 = temp.replaceAll(",", " ,");
                lineData = temp1 + " ";
                String[] data = lineData.split(",");
                String key = data[keyCol];
                if (data == null) break;
//                if (data.length != (cols.length + startCol)) {
//                    addErrorFormatLabel(bw, parsingLineNumber, keyCol, "数据导入的列数和实际的列数不相等或者有空数据，不能导入");
//                    isError = true;
//                    continue;
//                }

                // int colNum = cols.length;
                // int colNum = 0;
                String[] rs = new String[cols];
                boolean colError = false;
                for (int j = startCol; j < startCol + cols; j++) { //start for
                    try {
                        String value = data[j].trim();
                        if (value != null && !"".equals(value.trim())) {
                            rs[j - startCol] = value.trim();
                        } else {
                            rs[j - startCol] = "";
                        }
                    } catch (Exception e) {
                        rs[j - startCol] = "";
                    }
                }//end for

                if (!colError)
                    impRs.put(key, rs);

                if (!isError && colError)
                    isError = colError;

                if (tempSize >= rowSize)  //如果超出最大条数
                    isError = true;
            }
            return !isError;
        } catch (BkmsException be) {
            throw be;
        } catch (Exception e) {
            throw new BkmsException("读取文件失败", e, FileUtil.class);
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            if (delFile && !isError) {
                f.delete();
            }
        }
    }
    
    //实现文件的简单处理,复制和移动文件、目录等
	public static void move(String from, String to) throws Exception {// 移动指定文件夹内的全部文件
		try {
			File dir = new File(from);
			File[] files = dir.listFiles();// 将文件或文件夹放入文件集
			if (files == null)// 判断文件集是否为空
				return;
			File moveDir = new File(to);// 创建目标目录
			if (!moveDir.exists()) {// 判断目标目录是否存在
				moveDir.mkdirs();// 不存在则创建
			}
			for (int i = 0; i < files.length; i++) {// 遍历文件集
				if (files[i].isDirectory()) {// 如果是文件夹或目录,则递归调用fileMove方法，直到获得目录下的文件
					move(files[i].getPath(), to + "\\" + files[i].getName());// 递归移动文件
					files[i].delete();// 删除文件所在原目录
				}
				File moveFile = new File(moveDir.getPath() + "\\"// 将文件目录放入移动后的目录
						+ files[i].getName());
				if (moveFile.exists()) {// 目标文件夹下存在的话，删除
					moveFile.delete();
				}
				files[i].renameTo(moveFile);// 移动文件
				System.out.println(files[i] + " 移动成功");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	// 复制目录下的文件（不包括该目录）到指定目录，会连同子目录一起复制过去。
	public static void copyFileFromDir(String toPath, String fromPath) {
		File file = new File(fromPath);
		createFile(toPath, false);// true:创建文件 false创建目录
		if (file.isDirectory()) {// 如果是目录
			copyFileToDir(toPath, listFile(file));
		}
	}

	// 复制目录到指定目录,将目录以及目录下的文件和子目录全部复制到目标目录
	public static void copyDir(String toPath, String fromPath) {
		File targetFile = new File(toPath);// 创建文件
		createFile(targetFile, false);// 创建目录
		File file = new File(fromPath);// 创建文件
		if (targetFile.isDirectory() && file.isDirectory()) {// 如果传入是目录
			copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
					listFile(file));// 复制文件到指定目录
		}
	}

	// 复制一组文件到指定目录。targetDir是目标目录，filePath是需要复制的文件路径
	public static void copyFileToDir(String toDir, String[] filePath) {
		if (toDir == null || "".equals(toDir)) {// 目录路径为空
			System.out.println("参数错误，目标路径不能为空");
			return;
		}
		File targetFile = new File(toDir);
		if (!targetFile.exists()) {// 如果指定目录不存在
			targetFile.mkdir();// 新建目录
		} else {
			if (!targetFile.isDirectory()) {// 如果不是目录
				System.out.println("参数错误，目标路径指向的不是一个目录！");
				return;
			}
		}
		for (int i = 0; i < filePath.length; i++) {// 遍历需要复制的文件路径
			File file = new File(filePath[i]);// 创建文件
			if (file.isDirectory()) {// 判断是否是目录
				copyFileToDir(toDir + "/" + file.getName(), listFile(file));// 递归调用方法获得目录下的文件
				System.out.println("复制文件 " + file);
			} else {
				copyFileToDir(toDir, file, "");// 复制文件到指定目录
			}
		}
	}

	public static void copyFileToDir(String toDir, File file, String newName) {// 复制文件到指定目录
		String newFile = "";
		if (newName != null && !"".equals(newName)) {
			newFile = toDir + "/" + newName;
		} else {
			newFile = toDir + "/" + file.getName();
		}
		File tFile = new File(newFile);
		copyFile(tFile, file);// 调用方法复制文件
	}

	public static void copyFile(File toFile, File fromFile) {// 复制文件
		if (toFile.exists()) {// 判断目标目录中文件是否存在
			System.out.println("文件" + toFile.getAbsolutePath() + "已经存在，跳过该文件！");
			return;
		} else {
			createFile(toFile, true);// 创建文件
		}
		System.out.println("复制文件" + fromFile.getAbsolutePath() + "到"
				+ toFile.getAbsolutePath());
		try {
			InputStream is = new FileInputStream(fromFile);// 创建文件输入流
			FileOutputStream fos = new FileOutputStream(toFile);// 文件输出流
			byte[] buffer = new byte[1024];// 字节数组
			while (is.read(buffer) != -1) {// 将文件内容写到文件中
				fos.write(buffer);
			}
			is.close();// 输入流关闭
			fos.close();// 输出流关闭
		} catch (FileNotFoundException e) {// 捕获文件不存在异常
			e.printStackTrace();
		} catch (IOException e) {// 捕获异常
			e.printStackTrace();
		}
	}

	public static String[] listFile(File dir) {// 获取文件绝对路径
		String absolutPath = dir.getAbsolutePath();// 声获字符串赋值为路传入文件的路径
		String[] paths = dir.list();// 文件名数组
		String[] files = new String[paths.length];// 声明字符串数组，长度为传入文件的个数
		for (int i = 0; i < paths.length; i++) {// 遍历显示文件绝对路径
			files[i] = absolutPath + "/" + paths[i];
		}
		return files;
	}

	public static void createFile(String path, boolean isFile) {// 创建文件或目录
		createFile(new File(path), isFile);// 调用方法创建新文件或目录
	}

	public static void createFile(File file, boolean isFile) {// 创建文件
		if (!file.exists()) {// 如果文件不存在
			if (!file.getParentFile().exists()) {// 如果文件父目录不存在
				createFile(file.getParentFile(), false);
			} else {// 存在文件父目录
				if (isFile) {// 创建文件
					try {
						file.createNewFile();// 创建新文件
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					file.mkdir();// 创建目录
				}
			}
		}
	}

	/**
	 * 创建父文件路径
	 * @param realPath
	 */
	public static void createParentFilePath(String realPath) {
		File destFile = new File(realPath);
		File destParent = destFile.getParentFile();
		if (!destParent.exists()) {
			destParent.mkdirs();
		}
	}
	
	/**
	 * 获取父路径
	 * @param realPath
	 * @return
	 */
	public static File getParentFile(String realPath) {
		File destFile = new File(realPath);
		File destParent = destFile.getParentFile();
		return destParent;
	}
}
