package com.becoda.bkms.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialFile;
import com.becoda.bkms.parkingms.special.ucc.ISpecialUCC;
import com.becoda.bkms.tls.webmgr.pojo.bo.InformationBO;
import com.becoda.bkms.tls.webmgr.ucc.IInformationUCC;

/**
 * @author hudl
 * 2015-11-04 20:45:17
 */
public class UploadFileAction extends GenericAction {
	
    private List<File> file;//这里的"fileName"一定要与表单中的文件域名相同  
    
	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	private List<String> fileContentType;//格式同上"fileName"+ContentType  
    private List<String> fileFileName;//格式同上"fileName"+FileName  
	
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

	/**
	 * 保存附件到本地
	 * @return
	 * @throws BkmsException
	 */
	public String saveFile()throws BkmsException{
		List<File> files=getFile(); 
		String path = request.getSession().getServletContext().getRealPath("");
		String webPath = "/file/tls/htmlfile/images";
		String time = Tools.getSysDate("yyyyMMddHHmmss");
		File destFile =  new File(path+webPath+"/"+time);
		if(!destFile.exists()){
			destFile.mkdirs();
		}
		String filePath = "";
		String fileNa = "";
		try{
			for(int i=0;i<files.size();i++){
				String fileName = getFileFileName().get(i);
				FileOutputStream fos=new FileOutputStream(path+webPath+"/"+time+"/"+fileName);
	            FileInputStream fis=new FileInputStream(getFile().get(i));
	            byte []buffers=new byte[1024];
	            int len=0;
	            while((len=fis.read(buffers))!=-1){
	                fos.write(buffers,0,len);
	            }
	            fis.close();
	            fos.close();
	            filePath += webPath+"/"+time+"/"+getFileFileName().get(i);
	            fileNa += fileName;
	            if(i+1<files.size()){
	            	filePath +=",";
	            	fileNa +=",";
	            }
	        }
			httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("text/html;charset=UTF-8");
			httpResponse.getWriter().print(filePath+";"+fileNa);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 下载附件
	 * @return
	 * @throws BkmsException
	 */
	public String downloadFile()throws BkmsException{
		 try {
			ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
			String fileId = request.getParameter("fileId");
			ParmsSpecialFile filepo = specUCC.getSpecialFile(fileId);
			String path = request.getSession().getServletContext().getRealPath("")+filepo.getFilePath();
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            httpResponse.reset();
            httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("text/html;charset=UTF-8");
            // 设置response的Header
            httpResponse.addHeader("Content-Disposition", "attachment;filename=" +  java.net.URLEncoder.encode(filename, "UTF-8"));
            httpResponse.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(httpResponse.getOutputStream());
            httpResponse.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
	}
	
	/**
	 * 删除附件
	 * @return
	 */
	public String deleteFile(){
		String re = "sucesse";
		try {
			IInformationUCC ucc = (IInformationUCC) getBean("tls_informationUCC");
			String fileId = request.getParameter("fileId");
			String filePath = request.getSession().getServletContext().getRealPath("");
			if(!fileId.equals("noFile")){
				InformationBO bo = ucc.findInfoById(fileId);
				filePath += bo.getAttachFileURL();
				bo.setAttachFileURL("");
				ucc.updateInfomation(bo);
			}else{
				String filDel = request.getParameter("filDel");
				String [] filDels = filDel.split(",");
				filePath += filDels[1];
			}
			
			File file = new File(filePath);  
			 // 路径为文件且不为空则进行删除  
		    if (file.isFile() && file.exists()) {  
		        file.delete();  
		    }
		    //获取文件上级文件夹目录
		    String fileorPath = filePath.substring(0, filePath.lastIndexOf("/"));
		    File fileor = new File(fileorPath); 
		    //获取文件夹下文件或目录
		    String fi [] = fileor.list();
		    //如果文件夹下没有文件  删除文件夹
		    if(fi==null||fi.length==0){
		    	fileor.delete();
		    }
		    httpResponse.setCharacterEncoding("UTF-8");
			httpResponse.setContentType("text/html;charset=UTF-8");
		} catch (Exception e) {
			re = "err";
			e.printStackTrace();
		}
		try {
			httpResponse.getWriter().print(re);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
