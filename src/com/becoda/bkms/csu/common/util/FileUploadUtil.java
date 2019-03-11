package com.becoda.bkms.csu.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.csu.common.pojo.KpAttachement;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.util.FileUtil;

/**
 * 文件上传工具类
 * @author yinhui
 *
 */
public class FileUploadUtil {

	public static String VD_PATH = SystemConfig.getString("configVideoPQPath");    //文件上传路径
	public static String HD_PATH= SystemConfig.getString("configVideoHDPath");     //视频文件高清上传路径
	public static String SD_PATH = SystemConfig.getString("configVideoSDPath");    //视频文件标清上传路径
	public static String IMG_PATH = SystemConfig.getString("configImgPath");       //图片路径
	public static String FILE_PATH = SystemConfig.getString("configFilePath");     //文件路径
	public static String OTHER_PATH = SystemConfig.getString("configOtherPath");   //其他文件的上传路径 
	
	/**
	 * 根据文件类型获取文件上传路径
	 * @param fileType
	 * @return
	 */
	public static String buildPath(int fileType, String serialName) {
		String path = "";
		switch (fileType) {
			case 1:
				path = VD_PATH;    //视频路径
				break;
			case 2:
				path = IMG_PATH;  //图片路径
				break;
			case 3:
				path = FILE_PATH;  //文件路径
				break;
			case 4:
				path = OTHER_PATH;  //文件路径
				break;
			default:
				path = OTHER_PATH;  //文件路径
				break;
		}
		path = path + DateFormatUtils.format(new Date(), "yyyyMMdd") + File.separator + serialName;    //视频路径，含文件名，但不包括文件扩展名
		return path;
	}
	
	/**
	 * 跟文件的后缀判断文件的类型
	 * @param fileName
	 * @return int 1：视频   2：图片 3：文件 4：其他
	 */
	public static int judgeFileType(String fileName) {
		int fileType = 4;
		if (fileName != null && !"".equals(fileName)) {
			String suffix = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
			
			if (FileFormatUtil.suffixIsExist("1", suffix)) {  //视频判断
				fileType = 1;
			} else if (FileFormatUtil.suffixIsExist("2", suffix)) {  //图片判断
				fileType = 2;
			} else if (FileFormatUtil.suffixIsExist("3", suffix)) {  //文件判断
				fileType = 3;
			} else {   //其他
				fileType = 4;
			}
		}
		return fileType;
	}
	
	/**
	 * 截取文件的扩展名
	 * @param fileName 文件名.XXX
	 * @return
	 */
	public static String suffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
	}
	
	/**
	 * 文件上传
	 * @throws BkmsException
	 */
	public static KpAttachement upload(File file, String fileName) throws BkmsException{
		int fileType = 0;  //1:视频  2:图片  3:文件
		String suffix = "";  //后缀 
		BufferedOutputStream outputStream=null;  //输出流
		BufferedInputStream inputStream=null;   //输入流
		String path = "";     //文件上传路径
		boolean flag = false;
		//指定上传的位置
		KpAttachement attachement = new KpAttachement();
		try {
			//自定义方式产生文件名
            String serialName = String.valueOf(System.currentTimeMillis());
            
            //获取文件的扩展名
            suffix = suffix(fileName);
            
			//跟文件的后缀判断文件的类型
            fileType = judgeFileType(fileName);
            path = buildPath(fileType, serialName);

			//生成文件上传路径
			String realPath = path+"."+suffix;
			FileUtil.createParentFilePath(realPath);

			// 获取输入流
			inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[1024];
			int length = 0;
			
			// 创建输出流对象
			File destParent = FileUtil.getParentFile(realPath);
			File destFile = new File(destParent, serialName+"."+suffix);
			outputStream = new BufferedOutputStream(new FileOutputStream(destFile));
			// 开始上传
			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
				outputStream.flush();
			}
			outputStream.flush();
			
			try {  
            	if(outputStream!=null)
            		outputStream.close(); 
            	if(inputStream!=null)
            		inputStream.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } 
			
			attachement.setFileId(UUID.randomUUID().toString());
			attachement.setFileUrl(realPath);
            attachement.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            attachement.setFileName(fileName);
            attachement.setSerialName(serialName);
            attachement.setFileType(fileType+"");
			
            //待转码的文件
			if(1==fileType) {
				attachement = transcoding(attachement, serialName, suffix);
			}
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally{  
            
        }  
		return attachement;
	}
	
	/**
	 * 
	 * @param attachement
	 * @param serialName
	 * @param suffix
	 * @return
	 */
	public static KpAttachement transcoding(KpAttachement attachement, String serialName, String suffix) {
		boolean flag = false;
		//判断是否是ffmpeg能解析的格式
		if(FileFormatUtil.suffixIsExist("0", suffix)) {   //不能
			
		} else {  //能
			//设置转换为flv格式后文件的保存路径
			String codeHDFilePath = new File(HD_PATH 
					+ DateFormatUtils.format(new Date(), "yyyyMMdd") 
					+ File.separator, serialName + ".flv").toString();   
			FileUtil.createParentFilePath(codeHDFilePath);
			
			String codeSDFilePath = new File(SD_PATH 
					+ DateFormatUtils.format(new Date(), "yyyyMMdd") 
					+ File.separator, serialName + ".flv").toString();   
			FileUtil.createParentFilePath(codeSDFilePath);
			
			//设置上传视频截图的保存路径
			String mediaPicPath = new File(IMG_PATH 
					+ DateFormatUtils.format(new Date(), "yyyyMMdd") 
					+ File.separator, serialName + ".jpg").toString();    
			FileUtil.createParentFilePath(mediaPicPath);
			// 获取配置的转换工具（ffmpeg.exe）的存放路径
			String ffmpegPath = ServletActionContext.getServletContext().getRealPath("/tools/ffmpeg.exe");
			
			attachement.setFileHdUrl(codeHDFilePath);
			attachement.setFileSdUrl(codeSDFilePath);
			attachement.setFileImgUrl(mediaPicPath);
			
			//转码
			try {
				flag = executeHDCodecs(ffmpegPath, attachement.getFileUrl(), codeHDFilePath, mediaPicPath);
				
				flag = executeSDCodecs(ffmpegPath, attachement.getFileUrl(), codeSDFilePath, mediaPicPath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return attachement;
	}
	
	
	/**
     * 视频高清转码
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @param mediaPicPath    截图保存路径
     * @return
     * @throws Exception
     */
    public static boolean executeHDCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
            String mediaPicPath) throws Exception {
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath);   // 添加转换工具路径
        convert.add("-i");         // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath);   // 添加要转换格式的视频文件的路径
        convert.add("-qscale");    //指定转换的质量
        convert.add("6");
        convert.add("-ab");        //设置音频码率
        convert.add("64");
        convert.add("-ac");        //设置声道数
        convert.add("2");
        convert.add("-ar");        //设置声音的采样频率
        convert.add("22050");
        convert.add("-r");         //设置帧频
        convert.add("24");
        convert.add("-y");         //添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss");     // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("17");      // 添加起始时间为第17秒
        cutpic.add("-t");      // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001");   // 添加持续时间为1毫秒
        cutpic.add("-s");      // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*280"); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();
            
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }
    
    /**
     * 视频普清转码
     * @param ffmpegPath    转码工具的存放路径
     * @param upFilePath    用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath    格式转换后的的文件保存路径
     * @param mediaPicPath    截图保存路径
     * @return 
     * @throws Exception
     */
    public static boolean executeSDCodecs(String ffmpegPath, String upFilePath, String codcFilePath,
            String mediaPicPath) throws Exception {
        // 创建一个List集合来保存转换视频文件为flv格式的命令
        List<String> convert = new ArrayList<String>();
        convert.add(ffmpegPath);   // 添加转换工具路径
        convert.add("-i");         // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(upFilePath);   // 添加要转换格式的视频文件的路径
        convert.add("-qscale");    //指定转换的质量
        convert.add("6");
        convert.add("-ab");        //设置音频码率
        convert.add("64");
        convert.add("-ac");        //设置声道数
        convert.add("2");
        convert.add("-ar");        //设置声音的采样频率
        convert.add("22050");
        convert.add("-r");         //设置帧频
        convert.add("24");
        convert.add("-y");         //添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(codcFilePath);

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss");     // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("17");      // 添加起始时间为第17秒
        cutpic.add("-t");      // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001");   // 添加持续时间为1毫秒
        cutpic.add("-s");      // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add("800*280"); // 添加截取的图片大小为350*240
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(convert);
            builder.redirectErrorStream(true);
            builder.start();
            
            //只获取一次截图,此次先注掉
//            builder.command(cutpic);
//            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
//            builder.start();
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
        return mark;
    }
}
