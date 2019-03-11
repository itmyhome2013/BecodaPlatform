package com.becoda.bkms.csu.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class FileCodeUtil {
	
	public static String FILEPATH = SystemConfig.getString("configVideoPQPath");
	
	/**
	 * 将文件转成base64 字符串
	 * 
	 * @param path文件路径
	 * @return *
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
		return new BASE64Encoder().encode(buffer);

	}
	
	public static String getBASE64String(String filePath) {
		String readString = null;
		BufferedInputStream bis = null;
		HttpURLConnection urlconnection = null;
		URL url = null;
		try {
			url = new URL(filePath);
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.connect();
			bis = new BufferedInputStream(urlconnection.getInputStream());
			System.out.println("file type:"+ HttpURLConnection.guessContentTypeFromStream(bis));
			byte[] stream2Byte = Stream2Byte(bis);
			readString = new BASE64Encoder().encode(stream2Byte);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO异常：" + e);
		} finally {
			try {
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return readString;
	}

	/**
	 * 将base64字符解码
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static byte[] decoderBase64File(String base64Code)
			throws Exception {
		byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
		return buffer;

	}

	/**
	 * 将解码后的byte[] 转成file存到磁盘文件夹中
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */

	public static File toFile(byte[] buffer , String realPath)throws Exception {
		File destFile = new File(realPath);
		File destParent = destFile.getParentFile();
		if (!destParent.exists()) {
			destParent.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(destFile);
		out.write(buffer);
		out.close();
		return destFile;
	}

	
	 /** 
     * 解码 
     *  
     * @param str 
     * @return string 
     */  
     public static byte[] decode(String str) {  
	     byte[] bt = null;  
	     try {  
		     sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
		     bt = decoder.decodeBuffer(str);  
	     } catch (IOException e) {  
	    	 e.printStackTrace();  
	     }  
	     return bt;  
     }  
     
     public static byte[] Stream2Byte(BufferedInputStream in) {
 		ByteArrayOutputStream out = null;
 		try {
 			out = new ByteArrayOutputStream(1024);

 			System.out.println("Available bytes:" + in.available());

 			byte[] temp = new byte[1024];
 			int size = 0;
 			while ((size = in.read(temp)) != -1) {
 				out.write(temp, 0, size);
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			try {
 				in.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}
 		}

 		byte[] content = out.toByteArray();
 		System.out.println("Readed bytes count:" + content.length);
 		return content;
     }
     
     public static void main(String[] args) {
 		try {
 			/**-----------------------------**/
// 			String base64Code = encodeBase64File("C:/Users/HelloWorld/Desktop/知识导入模版.xls");
// 			System.out.println(base64Code);
// 			byte[] decoderBase64File = decoderBase64File(base64Code);
// 			toFile(decoderBase64File, "D:/attachment","知识导入模版.xls");
 			/**-----------------------------**/
// 			String fileData  = getBASE64String("http://10.212.202.199/20160615/60_155555.wav");
// 			byte[] decode = decode(fileData);
// 			String realPath = FileCodeUtil.FILEPATH+  DateFormatUtils.format(new Date(), "yyyyMMdd")+ File.separator+"60_1555555.wav";
// 			File attchFile = FileCodeUtil.toFile(decode, realPath);
// 			System.out.println(attchFile.getName());
 			/**-----------------------------**/
 			String fileUrl =  "http://10.212.202.199/20160615/60_155555.wav";
 			String str [] = fileUrl.split("/");
 			System.out.println(str[4]);
 			
  		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
     
}
