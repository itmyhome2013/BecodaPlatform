package com.becoda.bkms.csu.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileFormatUtil {
	
	 //文件类型 语音文件 1 ,普通文件 2 fileType;
	public static String FILEPATH = SystemConfig.getString("configVideoPQPath");
	public static List<String> voice = new ArrayList<String>();
	public static Map<String,String> VOICESUFFIX = new HashMap<String, String>();    // 视频后缀
	public static Map<String,String> VOICEMENSUFFIX = new HashMap<String, String>(); // 视频后缀
	public static Map<String,String> IMGSUFFIX = new HashMap<String, String>();      // 图片后缀
	public static Map<String,String> FILESUFFIX = new HashMap<String, String>();     // 文件后缀
	public static Map<String,Map<String,String>> SUFFIX = new HashMap<String, Map<String,String>>();
	static {
		VOICESUFFIX.put("4xm", "4xm");
		VOICESUFFIX.put("aac", "aac");
		VOICESUFFIX.put("ac3", "ac3");
		VOICESUFFIX.put("adts", "adts");
		VOICESUFFIX.put("aiff", "aiff");
		VOICESUFFIX.put("alaw", "alaw");
		VOICESUFFIX.put("amr", "amr");
		VOICESUFFIX.put("apc", "apc");
		VOICESUFFIX.put("ape", "ape");
		VOICESUFFIX.put("asf", "asf");
		VOICESUFFIX.put("asx", "asx");
		VOICESUFFIX.put("asf_stream", "asf_stream");
		VOICESUFFIX.put("au", "au");
		VOICESUFFIX.put("avi", "avi");
		VOICESUFFIX.put("avs", "avs");
		VOICESUFFIX.put("bethsoftvid", "bethsoftvid");
		VOICESUFFIX.put("c93", "c93");
		VOICESUFFIX.put("crc", "crc");
		VOICESUFFIX.put("daud", "daud");
		VOICESUFFIX.put("dsicin", "dsicin");
		VOICESUFFIX.put("dts", "dts");
		VOICESUFFIX.put("dv", "dv");
		VOICESUFFIX.put("dvd", "dvd");
		VOICESUFFIX.put("dxa", "dxa");
		VOICESUFFIX.put("ea", "ea");
		VOICESUFFIX.put("ea_cdata", "ea_cdata");
		VOICESUFFIX.put("Encoding", "Encoding");
		VOICESUFFIX.put("ffm", "ffm");
		VOICESUFFIX.put("film_cpk", "film_cpk");
		VOICESUFFIX.put("flac", "flac");
		VOICESUFFIX.put("flic", "flic");
		VOICESUFFIX.put("flv", "flv");
		VOICESUFFIX.put("framecrc", "framecrc");
		VOICESUFFIX.put("gxf", "gxf");
		VOICESUFFIX.put("h261", "h261");
		VOICESUFFIX.put("h263", "h263");
		VOICESUFFIX.put("h264", "h264");
		VOICESUFFIX.put("idcin", "idcin");
		VOICESUFFIX.put("image2", "image2");
		VOICESUFFIX.put("image2pipe", "image2pipe");
		VOICESUFFIX.put("ingenient", "ingenient");
		VOICESUFFIX.put("ipmovie", "ipmovie");
		VOICESUFFIX.put("libnut", "libnut");
		VOICESUFFIX.put("m4v", "m4v");
		VOICESUFFIX.put("matroska", "matroska");
		VOICESUFFIX.put("mjpeg", "mjpeg");
		VOICESUFFIX.put("mm", "mm");
		VOICESUFFIX.put("mmf", "mmf");
		VOICESUFFIX.put("mov", "mov");
		VOICESUFFIX.put("m4a", "m4a");
		VOICESUFFIX.put("3gp", "3gp");
		VOICESUFFIX.put("3g2", "3g2");
		VOICESUFFIX.put("mj2", "mj2");
		VOICESUFFIX.put("mp2", "mp2");
		VOICESUFFIX.put("mp3", "mp3");
		VOICESUFFIX.put("mp4", "mp4");
		VOICESUFFIX.put("mpc", "mpc");
		VOICESUFFIX.put("mpc8", "mpc8");
		VOICESUFFIX.put("mpeg", "mpeg");
		VOICESUFFIX.put("mpeg1video", "mpeg1video");
		VOICESUFFIX.put("mpeg2video", "mpeg2video");
		VOICESUFFIX.put("mpegts", "mpegts");
		VOICESUFFIX.put("mpegtsraw", "mpegtsraw");
		VOICESUFFIX.put("mpegvideo", "mpegvideo");
		VOICESUFFIX.put("mpg", "mpg");
		VOICESUFFIX.put("mpjpeg", "mpjpeg");
		VOICESUFFIX.put("MTV", "MTV");
		VOICESUFFIX.put("mulaw", "mulaw");
		VOICESUFFIX.put("mxf", "mxf");
		VOICESUFFIX.put("nsv", "nsv");
		VOICESUFFIX.put("null", "null");
		VOICESUFFIX.put("nut", "nut");
		VOICESUFFIX.put("nuv", "nuv");
		VOICESUFFIX.put("ogg", "ogg");
		VOICESUFFIX.put("psp", "psp");
		VOICESUFFIX.put("psxstr", "psxstr");
		VOICESUFFIX.put("rawvideo", "rawvideo");
		VOICESUFFIX.put("redir", "redir");
		VOICESUFFIX.put("roq", "roq");
		VOICESUFFIX.put("rtp", "rtp");
		VOICESUFFIX.put("rtsp", "rtsp");
		VOICESUFFIX.put("s16be", "s16be");
		VOICESUFFIX.put("s16le", "s16le");
		VOICESUFFIX.put("s8", "s8");
		VOICESUFFIX.put("shn", "shn");
		VOICESUFFIX.put("siff", "siff");
		VOICESUFFIX.put("smk", "smk");
		VOICESUFFIX.put("sol", "sol");
		VOICESUFFIX.put("svcd", "svcd");
		VOICESUFFIX.put("swf", "swf");
		VOICESUFFIX.put("thp", "thp");
		VOICESUFFIX.put("tiertexseq", "tiertexseq");
		VOICESUFFIX.put("tta", "tta");
		VOICESUFFIX.put("txd", "txd");
		VOICESUFFIX.put("u16be", "u16be");
		VOICESUFFIX.put("u16le", "u16le");
		VOICESUFFIX.put("u8", "u8");
		VOICESUFFIX.put("vc1", "vc1");
		VOICESUFFIX.put("vcd", "vcd");
		VOICESUFFIX.put("vmd", "vmd");
		VOICESUFFIX.put("vob", "vob");
		VOICESUFFIX.put("voc", "voc");
		VOICESUFFIX.put("wc3movie", "wc3movie");
		VOICESUFFIX.put("wsaud", "wsaud");
		VOICESUFFIX.put("wsvqa", "wsvqa");
		VOICESUFFIX.put("wv", "wv");
		VOICESUFFIX.put("wmv", "wmv");
		VOICESUFFIX.put("yuv4mpegpipe", "yuv4mpegpipe");
		VOICESUFFIX.put("vox", "vox");
		
		VOICESUFFIX.put("wmv9", "wmv9");
		VOICESUFFIX.put("rm", "rm");
		VOICESUFFIX.put("rmvb", "rmvb");
		
		VOICEMENSUFFIX.put("wmv9", "wmv9");
		VOICEMENSUFFIX.put("rm", "rm");
		VOICEMENSUFFIX.put("rmvb", "rmvb");
		
		IMGSUFFIX.put("bpm", "bpm");
		IMGSUFFIX.put("bmp", "bmp");
		IMGSUFFIX.put("dib", "dib");
		IMGSUFFIX.put("gif", "gif");
		IMGSUFFIX.put("gig", "gig");
		IMGSUFFIX.put("jpeg", "jpeg");
		IMGSUFFIX.put("jpg", "jpg");
		IMGSUFFIX.put("jpe", "jpe");
		IMGSUFFIX.put("psd", "psd");
		IMGSUFFIX.put("pcx", "pcx");
		IMGSUFFIX.put("png", "png");
		IMGSUFFIX.put("rgb", "rgb");
		IMGSUFFIX.put("tif", "tif");
		
		FILESUFFIX.put("doc", "doc");
		FILESUFFIX.put("docx", "docx");
		FILESUFFIX.put("xls", "xls");
		FILESUFFIX.put("xlsx", "xlsx");
		FILESUFFIX.put("ppt", "ppt");
		FILESUFFIX.put("pptx", "pptx");
		FILESUFFIX.put("pdf", "pdf");
		FILESUFFIX.put("txt", "txt");
		FILESUFFIX.put("zip", "zip");
		FILESUFFIX.put("rar", "rar");
		
		SUFFIX.put("0", VOICEMENSUFFIX);
		SUFFIX.put("1", VOICESUFFIX);
		SUFFIX.put("2", IMGSUFFIX);
		SUFFIX.put("3", FILESUFFIX);
	}
	
	/**
	 * 判断此文件的后缀名是否存在
	 * @param fileType
	 * @param suffix
	 * @return
	 */
	public static boolean suffixIsExist(String fileType,String suffix){
		Map<String, String> map = FileFormatUtil.SUFFIX.get(fileType);
		if(map!=null){
			if(map.get(suffix) != null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 *将byte[] 转换成文件  
	 * @param fileName
	 * @param by
	 * @return
	 */
	public static File operaFileData(String fileName, byte[] by) {
		{
			FileOutputStream fileout = null;
			String path = FILEPATH;
			fileName = "201605231443363.wav";
			String fileAllName = path +File.separator+fileName;
			File file = new File(fileAllName);
			if (file.exists()) {
				file.delete();
			}else {
				
			}
			try {
				fileout = new FileOutputStream(file);
				fileout.write(by, 0, by.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fileout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return file;
		}

	}
	
	/**
	* 将字节流转换成文件
	* @param filename
	* @param data
	* @throws Exception
	*/
	public static File saveFile(String filename,byte [] data)throws Exception{
		File file = null;
		filename = "201605231443363.wav";
		if (data != null) {
			String filepath = FILEPATH +File.separator+filename;
			file = new File(filepath);
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(data, 0, data.length);
			fos.flush();
			fos.close();
		}
		return file;
	}
	 
}
