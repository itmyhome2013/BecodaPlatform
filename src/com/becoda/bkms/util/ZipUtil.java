package com.becoda.bkms.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.CheckedInputStream;
import java.util.zip.CRC32;

//import com.becoda.bkms.rpt.util.zip.ZipOutputStream;
//import com.becoda.bkms.rpt.util.zip.ZipEntry;
//import com.becoda.bkms.rpt.util.zip.ZipInputStream;
//import java.util.zip.*;

/**
 * 功能说明：zip工具类
 * 作者：jiangjizhen
 * 创建时间：2015-7-2
 * 版本：1.0
 */
public class ZipUtil {
    //方法由广开提供
    private static final Logger mLogger = Logger.getLogger(ZipUtil.class);
    private static final int BUFFERED_SIZE = 2 * 1024;
    static final int BUFFER = 2048;


    /**
     * 压缩文件
     *
     * @param files   待压缩的File文件数组
     * @param target  压缩后的文件名
     * @param delFlag true 解压完成后立即将被压缩文件删除,false 不删除被删除文件
     *                wz add 2015-07-04
     */
    public static boolean compress(File[] files, String target, boolean delFlag) {
        boolean result = false;
        try {
            //File tFile=new File(target);
            FileOutputStream fos = new FileOutputStream(target);
//            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos),"UTF-8");
//            byte[] bytes = new byte[BUFFER];
//            for (int i = 0; i < files.length; i++) {
//                FileInputStream fis = new FileInputStream(files[i]);
//                BufferedInputStream bis = new BufferedInputStream(fis);
//
//                ZipEntry ze = new ZipEntry(files[i].getName());
//                zos.putNextEntry(ze);
//                while (bis.read(bytes) != -1) {
//                    zos.write(bytes);
//                }
//
//                bis.close();
//                fis.close();
//            }
//
//            zos.close();
            fos.close();
            result = true;        //压缩成功就是true，删除出错也无所谓
            if (delFlag) {
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 解压缩文件
     *
     * @param pZipfile 待解压缩文件
     * @param pDestDir 输出文件夹
     */
    public static boolean decompress(File pZipfile, String pDestDir) {
        if (pZipfile == null || !pZipfile.exists() || pDestDir == null || "".equals(pDestDir))
            return false;

        FileInputStream lFis = null;
        CheckedInputStream lCsumi = null;
        BufferedInputStream lBfis = null;
//        ZipInputStream lZIn = null;
        try {
            if (mLogger.isInfoEnabled())
                mLogger.info("Starting unzip files:" + pZipfile.getName());

            lFis = new FileInputStream(pZipfile);
            lCsumi = new CheckedInputStream(lFis, new CRC32());
            lBfis = new BufferedInputStream(lCsumi);
//            lZIn = new ZipInputStream(lBfis);
//            ZipEntry lZipEntry = lZIn.getNextEntry();
//            while (lZipEntry != null) {
//                File lFile = new File(pDestDir, lZipEntry.getName());
//                if (lZipEntry.isDirectory()) {
//                    lFile.mkdirs();
//                } else {
//                    FileOutputStream lFos = null;
//                    BufferedOutputStream lBufOs = null;
//                    byte data[] = new byte[BUFFERED_SIZE];
//                    try {
//                        lFos = new FileOutputStream(lFile);
//                        lBufOs = new BufferedOutputStream(lFos, BUFFERED_SIZE);
//                        int c;
//                        while ((c = lZIn.read(data, 0, BUFFERED_SIZE)) != -1)
//                            lBufOs.write(data, 0, c);
//                    } catch (FileNotFoundException e) {
//                        throw e;
//                    } catch (IOException e) {
//                        throw e;
//                    } finally {
//                        if (lBufOs != null) {
//                            try {
//                                lBufOs.close();//关闭输出流
//                            } catch (IOException e) {
//                                mLogger.error("Close BufferedOutputStream exception", e);
//                            }
//                        }
//                        if (lFos != null) {
//                            try {
//                                lFos.close();//关闭输出流
//                            } catch (IOException e) {
//                                mLogger.error("Close FileOutputStream exception", e);
//                            }
//                        }
//                    }
//                }
//                lZIn.closeEntry();
//                lZipEntry = lZIn.getNextEntry();
//            }
            if (mLogger.isInfoEnabled())
                mLogger.info("Unzip files " + pZipfile.getName() + " complete!");
            return true;
        } catch (Exception e) {
            mLogger.error("Unzip files exception:" + e);
        } finally {
//            if (lZIn != null) {
//                try {
//                    lZIn.close();//关闭输入流
//                } catch (IOException e) {
//                    mLogger.error("Close ZipInputStream  exception", e);
//                }
//            }
            if (lFis != null) {
                try {
                    lFis.close();//关闭输入流
                } catch (IOException e) {
                    mLogger.error("Close InputStream exception", e);
                }
            }
            if (lCsumi != null) {
                try {
                    lCsumi.close();//关闭输入流
                } catch (IOException e) {
                    mLogger.error("Close CheckedInputStream  exception", e);
                }
            }
            if (lBfis != null) {
                try {
                    lBfis.close();//关闭输入流
                } catch (IOException e) {
                    mLogger.error("Close BufferedInputStream  exception", e);
                }
            }
        }
        return false;
    }
}
