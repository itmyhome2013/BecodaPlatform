package com.becoda.bkms.csu.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.common.pojo.KpAttachement;
import com.becoda.bkms.csu.common.ucc.IKpAttachementUCC;
import com.becoda.bkms.csu.common.util.FileUploadUtil;

/**
 * @项目名称: plupload   
 * @类名称: FileUploaderAction   
 * @类描述: 上传后台处理 
 * @创建人: yinhui
 * @创建时间: 2016-9-29 下午4:21:14    
 * @version: 1.0
 */
public class FileUploaderAction extends GenericAction  {

	private static final long serialVersionUID = 1L;
	private HttpServletResponse response;
	/*
	 * 如果每次只上传一个文件 就可以使用上面介绍的代码
	 * 这里义的泛型集合，是为了上传多个文件，也可以用数组 如： 
	 * private File [] file; 
	 * private String [] fileFileName; 
	 * private String [] fileContentType;
	 */
	private List<File> file;//这里的"fileName"一定要与表单中的文件域名相同  
	private List<String> fileContentType;//格式同上"fileName"+ContentType  
	private List<String> fileFileName;//格式同上"fileName"+FileName  
	
	private String name; //文件名
	private List<String> names;
	//大文件上传 分块chul
	private Integer chunk = null; //分割块数
	private Integer chunks = null; //总分割数
	
	//文件关联外键
	private String foreignId;  //关联外键
	
	private String fileId;     //文件Id
	private String fileName;   //文件下载名称
	
	@SuppressWarnings("unused")
	private InputStream fileInputStream;  //文件读取流

	/**
	 * 保存附件到本地
	 * @return
	 * @throws BkmsException
	 */
	public void saveFile() throws BkmsException{
		response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		String fileName = "";  //文件名称
		String fileId = "";    //文件Id
		//指定上传的位置
		try {
			KpAttachement attachement = new KpAttachement();
			for (int i = 0; i < file.size(); i++) {
				//拼接上传文件的名称
				if(fileName == null || "".equals(fileName)) {
					fileName = this.getFileFileName().get(i);
				} else {
					fileName = fileName + "," + this.getFileFileName().get(i);
				}
				attachement = FileUploadUtil.upload(file.get(i), fileFileName.get(i));
				
				//拼接上传文件的名称
				if(fileId == null || "".equals(fileId)) {
					fileId = attachement.getFileId();
				} else {
					fileId = fileId + "," + attachement.getFileId();
				}
				
				//设置文件的关联
				attachement.setForeignId(foreignId);  
				
				//保存附件信息
				IKpAttachementUCC attachUCC = (IKpAttachementUCC) getBean("comm_kpAttachementUCC");
				attachUCC.addEntity(attachement, user);
			}
			response.getWriter().write("{\"status\":true,\"newName\":\""+fileName+"\",\"fileId\":\""+fileId+"\"}");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				response.getWriter().write("{\"status\":false}");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally{  
            
        }  
//		return null;
	}
	
	/**
	 * 下载附件
	 * @return
	 * @throws BkmsException
	 */
	public String downloadFile()throws BkmsException{
        return SUCCESS;
	}
	
	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public Integer getChunk() {
		return chunk;
	}

	public void setChunk(Integer chunk) {
		this.chunk = chunk;
	}

	public Integer getChunks() {
		return chunks;
	}

	public void setChunks(Integer chunks) {
		this.chunks = chunks;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}
	
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		IKpAttachementUCC attachUCC = null;
		KpAttachement attachement = null;
		try {
			attachUCC = (IKpAttachementUCC) getBean("comm_kpAttachementUCC");
			attachement = attachUCC.findEntity(fileId, user);
		} catch (BkmsException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 解决中文乱码
		try {
			fileName = java.net.URLEncoder.encode(attachement.getFileName(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
//			System.out.println(attachement.getFileUrl().replace("\\","\\\\"));
			return new FileInputStream(new File(attachement.getFileUrl()));
		}catch(Exception e){
			return null;
		}		
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	
}
