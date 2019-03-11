package com.becoda.bkms.east.daxx.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.becoda.bkms.common.Constants;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.east.daxx.pojo.DaxxBO;
import com.becoda.bkms.east.daxx.ucc.IDaxxUCC;
import com.becoda.bkms.pms.pojo.bo.RoleInfoBO;
import com.becoda.bkms.util.BkmsContext;
import com.becoda.bkms.util.Tools;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.EasyUiUtils;
import com.farm.core.sql.utils.JsonUtil;
import com.farm.core.util.FarmManager;

/**
 * <p>
 * Description: 档案信息action
 * </p> 
 * @author zhu_lw
 * @date 2018-04-10
 * 
 */
public class DaxxAction extends GenericPageAction {
	private Map<String, Object> jsonResult;// 结果集合
	private DataQuery query;// 条件查询
	private DaxxBO daBo;// 档案信息BO

	private File[] file; // 文件
	private String[] fileFileName; // 文件名
	private String[] filePath; // 文件路径
	private String downloadFilePath; // 文件下载路径
	private InputStream inputStream;
	private String fileFlag; // 判断是否上传成功
	private String isPdf; // 判断是否pdf
	private String treeId;
	
	public String forSend(){
		
		User user = (User) session.getAttribute(Constants.USER_INFO);
		List roleList = user.getUserRoleList();
		if(roleList.size()>0){
			RoleInfoBO roleInfoBO = (RoleInfoBO)roleList.get(0);
			treeId = roleInfoBO.getTreeId();
		}
		
		return "success";
	}
	
	/**
	 * 查询档案信息
	 * 
	 * @param query
	 * @return
	 */
	public String queryDaxxSon() {
		query = EasyUiUtils.formatGridQuery(request, query);
		try {
			IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
			DataResult search = ucc.queryList(query).search();
			search.runDictionary(
					FarmManager.instance().findDicTitleForIndex("BMJZ"),
					"DAXX_SSBM");
			search.runDictionary(
					FarmManager.instance().findDicTitleForIndex("BMJZ"),
					"DAXX_SSJZ");
			search.runDictionary(
					FarmManager.instance().findDicTitleForIndex("DALX"),
					"DAXX_DALX");
			jsonResult = EasyUiUtils.formatGridData(search);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 获取部门
	 * 
	 * @return
	 * @throws IOException
	 * @throws BkmsException
	 */
	public String initSsbm() throws IOException, BkmsException {
		IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		List<Map> list = null;
		list = ucc.findSsbm();
		if (list != null && list.size() > 0) {
			for (Map mapresult : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(mapresult.get("ID")));
				map.put("entityType",
						String.valueOf(mapresult.get("ENTITYTYPE")));
				map.put("text", String.valueOf(mapresult.get("NAME")));
				maps.add(map);
			}
		}
		String json = JsonUtil.toJson(maps);
		JsonUtil.printEasyuiJson(json, httpResponse);
		return null;
	}

	/**
	 * 获取所有机组
	 * 
	 * @return
	 * @throws IOException
	 * @throws BkmsException
	 */
	public String initTotalJz() throws IOException, BkmsException {
		IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		List<Map> list = null;
		list = ucc.findTotalJz();
		if (list != null && list.size() > 0) {
			for (Map mapresult : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(mapresult.get("ID")));
				map.put("entityType",
						String.valueOf(mapresult.get("ENTITYTYPE")));
				map.put("text", String.valueOf(mapresult.get("NAME")));
				maps.add(map);
			}
		}
		String json = JsonUtil.toJson(maps);
		JsonUtil.printEasyuiJson(json, httpResponse);
		return null;
	}

	/**
	 * 获取部门下的机组
	 * 
	 * @return
	 * @throws IOException
	 * @throws BkmsException
	 */
	public String initSsjz() throws IOException, BkmsException {
		IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
		String bmid = Tools.filterNull(request.getParameter("bmid"));
		List<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		List<Map> list = ucc.findSsjz(bmid);
		if (list != null && list.size() > 0) {
			for (Map mapresult : list) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", String.valueOf(mapresult.get("ID")));
				map.put("entityType",
						String.valueOf(mapresult.get("ENTITYTYPE")));
				map.put("text", String.valueOf(mapresult.get("NAME")));
				maps.add(map);
			}
		}
		String json = JsonUtil.toJson(maps);
		JsonUtil.printEasyuiJson(json, httpResponse);
		return null;
	}

	/**
	 * 新增档案信息初始化
	 * 
	 * @return
	 */
	public String addInit() {

		return "addInit";
	}

	/**
	 * 更新档案信息初始化
	 * 
	 * @return
	 */
	public String updateInit() {
		String daxxid = Tools.filterNull(request.getParameter("daxxid"));
		if (!daxxid.equals("")) {
			try {
				IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
				DaxxBO bo1 = new DaxxBO();
				bo1.setDaxx_id(daxxid);
				List<DaxxBO> queryByBO = ucc.queryByBO(bo1);
				if (queryByBO != null && queryByBO.size() > 0) {
					daBo = queryByBO.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "updateInit";
	}

	/**
	 * 更新档案信息
	 * 
	 * @throws IOException
	 */
	public void daxxEdit() throws IOException {
		boolean flag = false;
		try {
			IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
			daBo.setDaxx_person(user.getName());
			ucc.editDaxx(daBo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	/**
	 * 删除档案信息
	 * 
	 * @throws IOException
	 */
	public void deleteDaxx() throws IOException {
		boolean flag = false;
		try {
			IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
			if(daBo!=null){
				String path=daBo.getDaxx_filepath();
				if(!Tools.stringIsNull(path)){
					File deleteFile = new File(path);
					deleteFile.delete();//删除已上传的附件					
				}				
			}
			ucc.deleteDaxx(daBo,user);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		httpResponse.getWriter().print(flag);
	}

	/**
	 * 文件上传
	 * 
	 * @return
	 */
	public String daxxFileUpload() {
		String path = ServletActionContext.getServletContext().getRealPath(
				"/daxx/" + Tools.getSysDate("yyyy-MM"));
		File file = new File(path); // 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			if (this.file != null) {
				File f[] = this.getFile();
				filePath = new String[f.length];
				for (int i = 0; i < f.length; i++) {
					String fileName = java.util.UUID.randomUUID().toString(); // 采用时间+UUID的方式随即命名
					String name = fileName
							+ fileFileName[i].substring(fileFileName[i]
									.lastIndexOf(".")); // 保存在硬盘中的文件名

					String suffix = fileFileName[i].substring(fileFileName[i]
							.lastIndexOf(".") + 1);
					
					if(!"pdf".equals(suffix)){
						 fileFlag = "false"; 
						 isPdf="false";//throw new Exception("被请上传pdf格式文件");
					  }else{
					FileInputStream inputStream = new FileInputStream(f[i]);
					FileOutputStream outputStream = new FileOutputStream(path
							+ "\\" + name);
					byte[] buf = new byte[1024];
					int length = 0;
					while ((length = inputStream.read(buf)) != -1) {
						outputStream.write(buf, 0, length);
					}
					inputStream.close();
					outputStream.flush();
					// 文件保存的完整路径
					// 比如：D:\tomcat6\webapps\struts_ajaxfileupload\\upload\a0be14a1-f99e-4239-b54c-b37c3083134a.png
					filePath[i] = path + "\\" + name;
					// 保存
					IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
					if(daBo!=null){
						String daPath=daBo.getDaxx_filepath();
						if(!Tools.stringIsNull(daPath)){
							File deleteFile = new File(daPath);
							deleteFile.delete();//删除已上传的附件					
						}				
					}
					daBo.setDaxx_person(user.getName());
					daBo.setDaxx_filename(fileFileName[i]);
					daBo.setDaxx_filepath(filePath[i]);
					ucc.editDaxx(daBo,user);
					fileFlag = "true";
				 }
				}

			}else{
				//更新
				IDaxxUCC ucc = (IDaxxUCC) BkmsContext.getBean("daxxUCC");
				daBo.setDaxx_person(user.getName());
				ucc.editDaxx(daBo,user);
				fileFlag = "true";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 文件下载
	 * 
	 * @return
	 */
	public String downloadDaxxFile() {
		String path = downloadFilePath;
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = request.getParameter("downloadFileName");
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			String filenameString = new String(filename.getBytes("gbk"),
					"iso-8859-1");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ filenameString);
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			return "error";
		}
		return null;
	}

	public Map<String, Object> getJsonResult() {
		return jsonResult;
	}

	public void setJsonResult(Map<String, Object> jsonResult) {
		this.jsonResult = jsonResult;
	}

	public DataQuery getQuery() {
		return query;
	}

	public void setQuery(DataQuery query) {
		this.query = query;
	}

	public DaxxBO getDaBo() {
		return daBo;
	}

	public void setDaBo(DaxxBO daBo) {
		this.daBo = daBo;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileFlag() {
		return fileFlag;
	}

	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFilePath() {
		return filePath;
	}

	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}

	public String getIsPdf() {
		return isPdf;
	}

	public void setIsPdf(String isPdf) {
		this.isPdf = isPdf;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

}
