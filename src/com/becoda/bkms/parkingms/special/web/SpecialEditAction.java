package com.becoda.bkms.parkingms.special.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import com.becoda.bkms.cache.CacheConstants;
import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.org.pojo.bo.OrgBO;
import com.becoda.bkms.org.ucc.IOrgUCC;
import com.becoda.bkms.parkingms.special.SpecialConstants;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialFile;
import com.becoda.bkms.parkingms.special.pojo.bo.ParmsSpecialInfo;
import com.becoda.bkms.parkingms.special.pojo.vo.ParmsSpecialInfoVo;
import com.becoda.bkms.parkingms.special.ucc.ISpecialSocreUCC;
import com.becoda.bkms.parkingms.special.ucc.ISpecialUCC;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.SequenceGenerator;
import com.becoda.bkms.util.Tools;


public class SpecialEditAction extends GenericAction {
	
    private  ParmsSpecialInfoVo special;

    private List<File> file;//这里的"fileName"一定要与表单中的文件域名相同  
    private List<String> fileContentType;//格式同上"fileName"+ContentType  
    private List<String> fileFileName;//格式同上"fileName"+FileName  
    //以下 action 与action 传参使用
    private String state;
    private String message;
    private String view;
    
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public ParmsSpecialInfoVo getSpecial() {
		return special;
	}

	public void setSpecial(ParmsSpecialInfoVo special) {
		this.special = special;
	}

	/**
	 * 新增初始化
	 * @return
	 * @throws BkmsException
	 */
	public String init() throws BkmsException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Constants.USER_INFO);
        if(user==null){
        	return "usernull";
        }
        special = new ParmsSpecialInfoVo();
        //初始化工单编号
        special.setCode(Tools.getSysDate("yyyyMMddHHmmss"));
        //初始化用户id
        special.setCheckUserId(user.getUserId());
        //用户名
        special.setCheckUserName(user.getName());
        //机构id
        special.setCheckOrgId(user.getOrgId());
        //机构名称
        special.setCheckOrgName(user.getOrgName()+"管理处");
        //附件 文件夹标识
        special.setFilePathUUID(SequenceGenerator.getUUID());
        //示范街/小区名称
        ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
        Map map = specUCC.queryReceiveOrg(user.getOrgId());
        request.setAttribute("orgMap", map);
        
        //存在问题   默认查询 街道
        List isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, SpecialConstants.CHECK_STANDARD_STREET, "-1");
        special.setIsProblemList(isProblemList);
        special.setCheckdate(Tools.getSysDate("yyyy-MM-dd"));
        return "toEditPage";
    }
   
	/**
	 * 根据  街道 /小区 不同 查询 不同的问题
	 */
	public String queryProblem() throws BkmsException{
		
		String orgId = request.getParameter("orgId");
		
		IOrgUCC  orgUcc = (IOrgUCC) BkmsContext.getBean("org_orgUCC");
		OrgBO org = orgUcc.findOrgByDept(orgId);
		List isProblemList = null;
		
		if(org.getOrgCode()!=null&&org.getOrgCode().equals("XZ")){
			isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, SpecialConstants.CHECK_STANDARD_COMMUNITY, "-1");
		}else{
			isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, SpecialConstants.CHECK_STANDARD_STREET, "-1");
		}
		JSONArray jsonArray = JSONArray.fromObject(isProblemList);
		
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jsonArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 新增数据
	 * @return
	 * @throws BkmsException
	 */
	public String save()throws BkmsException{
		ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
		String time = Tools.getSysDate("yyyyMMddHHmmss");
		
		special.setCreateTime(time);
        special.setLastStateTime(time);
        ParmsSpecialInfo po = new ParmsSpecialInfo();
        Tools.copyProperties(po, special);
		specUCC.editSpecial(po);
		List recList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3002", "-1");
        List isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3003", "-1");
        special.setRecList(recList);
        special.setIsProblemList(isProblemList);
        //判断跳转
        if(special.getState().equals("1")){
        	setState("99");
        	setMessage("comity");
        }else{
        	setState(special.getState());
        	setMessage("savey");
        }
		return "toQuery";
	}
	
	/**
	 * 修改初始化
	 * @return
	 * @throws BkmsException
	 */
	public String updateInit()throws BkmsException{
		User user = (User)session.getAttribute(Constants.USER_INFO);
		String specialId = request.getParameter("specialId");
		ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
		//通过id获取数据
		ParmsSpecialInfo po = specUCC.getSpecialInfo(specialId);
		special = new ParmsSpecialInfoVo();
		Tools.copyProperties(special, po);
		List recList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3002", "-1");
        List isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3003", "-1");
        special.setRecList(recList);
        special.setIsProblemList(isProblemList);
        //指派机构
        Map map = specUCC.queryReceiveOrg(user.getOrgId());
        request.setAttribute("orgMap", map);
        //获取附件
        List fileList = specUCC.querySpecailFileList(po.getSpecialId());
        request.setAttribute("fileList", fileList);
		return "toEditPage";
	}
	
	/**
	 * 修改
	 * @return
	 * @throws BkmsException
	 */
	public String update()throws BkmsException{
		ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
		String time = Tools.getSysDate("yyyyMMddHHmmss");
        special.setLastStateTime(time);
        ParmsSpecialInfo po = new ParmsSpecialInfo();
        Tools.copyProperties(po, special);
		specUCC.editSpecial(po);
		//判断是否为提交
		if(po.getState().equals("1")){
			//如果为1提交  调用扣分方法 进行扣分
			ISpecialSocreUCC socreUCC;
			try {
				socreUCC = (ISpecialSocreUCC)BkmsContext.getBean("specialsorceUCC");
				socreUCC.editSorce(po);
			} catch (BkmsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		List recList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3002", "-1");
        List isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3003", "-1");
        special.setRecList(recList);
        special.setIsProblemList(isProblemList);
        //判断跳转
        if(special.getState().equals("1")){
        	setState("99");
        	setMessage("comity");
        }else{
        	setState(special.getState());
        	setMessage("savey");
        }
		return "toQuery";
	}
	
	/**
	 * 修改状态以及改变状态时提交的相关信息
	 * @return
	 * @throws BkmsException
	 */
	public String updateState()throws BkmsException{
		User user = (User)session.getAttribute(Constants.USER_INFO);
        if(user==null){
        	return "usernull";
        }
		ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
		ParmsSpecialInfo po = specUCC.getSpecialInfo(special.getSpecialId());
		po.setState(special.getState());
		String time = Tools.getSysDate("yyyyMMddHHmmss");
		po.setLastStateTime(time);
		
		if(special.getState().equals("2")||special.getState().equals("9")){
			po.setTrialContent(special.getTrialContent());
			po.setTrialTime(time);
			po.setTrialUserId(user.getUserId());
			po.setTrialUserName(user.getName());
		}
		if(special.getState().equals("3")||special.getState().equals("10")){
			po.setAuditOpinion(special.getAuditOpinion());
			po.setAuditTime(time);
			po.setAuditUserId(user.getUserId());
			po.setAuditUserName(user.getName());
		}
		if(special.getState().equals("4")||special.getState().equals("11")){
			po.setGuidance(special.getGuidance());
			po.setGuideTime(time);
			po.setGuideUserId(user.getUserId());
			po.setGuideUserName(user.getName());
			//审定通过自动指派
			if(special.getState().equals("4")){
				po.setReceiveTime(time);
				po.setReceiveOrgId(po.getCheckOrgId());
				po.setReceiveOrgName(po.getCheckOrgName());
			}
		}
		if(special.getState().equals("5")){
			po.setRecTification(special.getRecTification());
			po.setAgainCheckContent(null);
			po.setRecUserId(user.getUserId());
			po.setRecuserName(user.getName());
			po.setRecTime(time);
			po.setFilePars(special.getFilePars());
		}
		if(special.getState().equals("6")){
			po.setConfirmContent(special.getConfirmContent());
			po.setConfirmTime(time);
			po.setConfirmUserId(user.getUserId());
			po.setConfirmUserName(user.getName());
		}
		if(special.getState().equals("7")){
			po.setAgainCheckContent(special.getAgainCheckContent());
			po.setAgainTime(time);
		}
		//复查未通过
		if(special.getState().equals("12")){
			po.setState("4");
			po.setAuditOpinion(null);
			po.setGuidance(null);
			po.setReceiveOrgId(null);
			po.setRecTification(null);
			po.setAgainCheckContent(special.getAgainCheckContent());
			po.setAgainTime(time);
		}
		specUCC.editSpecial(po);
		Tools.copyProperties(special,po);
		List recList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3002", "-1");
        List isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3003", "-1");
        Map map = specUCC.queryReceiveOrg(user.getOrgId());
        request.setAttribute("orgMap", map);
        special.setRecList(recList);
        special.setIsProblemList(isProblemList);
        if(special.getState().equals("1")){
        	setState("99");
        	setMessage("comity");
        }else{
        	if(special.getState().equals("11")||special.getState().equals("9")||special.getState().equals("2")){
        		setState("1");
        		if(special.getState().equals("9")||special.getState().equals("11")){
        			setMessage("tuihuiy");
        		}else{
        			setMessage("tongguoy");
        		}
        	}
        	if(special.getState().equals("10")||special.getState().equals("3")){
        		setState("2");
        		if(special.getState().equals("10")){
        			setMessage("bohuiy");
        		}else{
        			setMessage("stongguoy");
        		}
        	}
        	if(special.getState().equals("4")){
        		setState("3");
        		setMessage("fenpaiy");
        	}
        	if(special.getState().equals("5")){
        		setState("4");
        		setMessage("huizhiy");
        	}
        	if(special.getState().equals("6")){
        		setState("5");
        		setMessage("tongzhiy");
        	}
        	if(special.getState().equals("7")){
        		setState("6");
        		setMessage("fuyany");
        	}
        	if(special.getState().equals("8")){
        		setState("7");
        		setMessage("banjiey");
        	}
        	if(special.getState().equals("12")){
        		setState("1");
        		setMessage("fuyanwy");
        	}
        	//setState(special.getState());
        }
		return "toQuery";
	}
	
	/**
	 * 删除
	 * @return
	 * @throws BkmsException
	 */
	public String deleteSpecial()throws BkmsException{
		String specialId = request.getParameter("specialId");
		ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
		ParmsSpecialInfo po = specUCC.getSpecialInfo(specialId);
		specUCC.deleteSpecial(po);
		setMessage("deletey");
		return "toQuery";
	}
	
	/**
	 * 查询
	 * @return
	 * @throws BkmsException
	 */
	public String queryView()throws BkmsException{
		String specialId = request.getParameter("specialId");
		ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
		//通过id获取数据
		ParmsSpecialInfo po = specUCC.getSpecialInfo(specialId);
		special = new ParmsSpecialInfoVo();
		Tools.copyProperties(special, po);
		List recList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3002", "-1");
        List isProblemList = SysCacheTool.querySubObject(CacheConstants.OBJ_CODEITEM, "3003", "-1");
        special.setRecList(recList);
        special.setIsProblemList(isProblemList);
        //指派机构
        Map map = specUCC.queryReceiveOrg(user.getOrgId());
        request.setAttribute("orgMap", map);
        //获取附件
        List fileList = specUCC.querySpecailFileList(po.getSpecialId());
        request.setAttribute("fileList", fileList);
        setView("view");
		return "toEditPage";
	}
	
	/**
	 * 保存附件到本地
	 * @return
	 * @throws BkmsException
	 */
	public String saveFile()throws BkmsException{
		List<File> files=getFile(); 
		String path = request.getSession().getServletContext().getRealPath("");
		String webPath = "\\special\\file\\";
		String uuid = request.getParameter("filePathUUID");
		String time = Tools.getSysDate("yyyyMMddHHmmss");
		File destFile =  new File(path+webPath+uuid+"\\"+time);
		if(!destFile.exists()){
			destFile.mkdirs();
		}
		String filePath = "";
		String fileNa = "";
		try{
			for(int i=0;i<files.size();i++){
				String fileName = getFileFileName().get(i);
				FileOutputStream fos=new FileOutputStream(path+webPath+uuid+"\\"+time+"\\"+fileName);
	            FileInputStream fis=new FileInputStream(getFile().get(i));
	            byte []buffers=new byte[1024];
	            int len=0;
	            while((len=fis.read(buffers))!=-1){
	                fos.write(buffers,0,len);
	            }
	            fis.close();
	            fos.close();
	            filePath += webPath+uuid+"\\"+time+"\\"+getFileFileName().get(i);
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
			ISpecialUCC specUCC = (ISpecialUCC) BkmsContext.getBean("specialUCC");
			String fileId = request.getParameter("fileId");
			ParmsSpecialFile filepo = specUCC.getSpecialFile(fileId);
			File file = new File(request.getSession().getServletContext().getRealPath("")+filepo.getFilePath());  
		    // 路径为文件且不为空则进行删除  
		    if (file.isFile() && file.exists()) {  
		        file.delete();  
		    }  
		    specUCC.deleteSpecialFile(filepo);
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
