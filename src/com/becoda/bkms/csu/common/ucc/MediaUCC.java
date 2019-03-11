package com.becoda.bkms.csu.common.ucc;

/**
 * 
 * MediaDAO.java
 * @version ： 1.1
 * @author ：yinhui
 * @since ： 1.0 创建时间: 2016-9-30 上午10:19:54
 * TODO : interface MediaDao.java is used for ...
 */
public interface MediaUCC {

	/**
	 * 视频高清转码
	 * 
	 * @param ffmpegPath 转码工具的存放路径
	 * @param upFilePath 用于指定要转换格式的文件,要截图的视频源文件
	 * @param codcFilePath 格式转换后的的文件保存路径
	 * @param mediaPicPath 截图保存路径
	 * @return
	 * @throws Exception
	 */
	public boolean executeHDCodecs(String ffmpegPath, String upFilePath,
			String codcFilePath, String mediaPicPath) throws Exception;
	
	/**
	 * 视频普清转码
	 * 
	 * @param ffmpegPath 转码工具的存放路径
	 * @param upFilePath 用于指定要转换格式的文件,要截图的视频源文件
	 * @param codcFilePath 格式转换后的的文件保存路径
	 * @param mediaPicPath 截图保存路径
	 * @return
	 * @throws Exception
	 */
	public boolean executeSDCodecs(String ffmpegPath, String upFilePath,
			String codcFilePath, String mediaPicPath) throws Exception;
	
	
}
