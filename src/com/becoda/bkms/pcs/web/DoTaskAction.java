package com.becoda.bkms.pcs.web;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericPageAction;
import com.becoda.bkms.pcs.ucc.DoTaskUCC;
import com.becoda.bkms.pcs.util.UUIDGenerator;
import com.becoda.bkms.sys.platform.ucc.DataResult;
import com.becoda.bkms.sys.platform.ucc.PlatFormManager;
import com.becoda.bkms.util.Endecode;
import com.becoda.bkms.util.Tools;
/**
 * 
 * @项目名称：BecodaPlatForm
 * @名称：DoTaskAction
 * @描述：TODO(任务管理-待办任务ACTION)
 * @创建人： 张晓亮
 * @创建时间：2016年1月18日 下午2:16:49
 * @修改人：张晓亮
 * @修改时间：2016年1月18日 下午2:16:49
 * @修改备注：
 */
public class DoTaskAction extends GenericPageAction{
	
	
	
	/**
	 * 
	 * @方法名称: findDoTaskList
	 * @描述：TODO(代办理任务列表) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findDoTaskList(){
		try {
			String processName = Tools.filterNull(request.getParameter("processName"));
			DoTaskUCC doTaskUCC = (DoTaskUCC) getBean("pcs_doTaskUCC");
			List<Map<String, String>> doTaskPageList = new ArrayList<Map<String,String>>();
            doTaskPageList = doTaskUCC.queryDoTaskPageList(vo, user,processName);
            DataResult.runDictionaryList(doTaskPageList, PlatFormManager.getPlatFormBean().findCodeItemForIndex("urgency"), "URGENCY");
			request.setAttribute("doTaskPageList", doTaskPageList);
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "doTaskList";
	}
	
	/**
	 * 
	 * @方法名称: findviewCurrentImage
	 * @描述：TODO(查看当前流程图) 
	 * @返回值类型： String  
	 * @return
	 * @throws Exception
	 */
	public String findviewCurrentImage() throws Exception{
		 String taskId = Tools.filterNull(request.getParameter("taskId"));
		 taskId = Endecode.base64Decode(taskId);
		 DoTaskUCC doTaskUCC = (DoTaskUCC) getBean("pcs_doTaskUCC");
		 InputStream in= doTaskUCC.findviewCurrentImage(taskId);
		 OutputStream out = ServletActionContext.getResponse().getOutputStream();
			// 4：将输入流中的数据读取出来，写到输出流中
			for (int b = -1; (b = in.read()) != -1;) {
				out.write(b);
			}
			out.close();
			in.close();
			// 将图写到页面上，用输出流写
			return null;
	 }
	
	/**
	 * 
	 * @方法名称: doTaskfillFormList
	 * @描述：TODO(当前任务可填表单) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findDoTaskfillFormList() {
		List<Map<String,String>> doFillFromList = new  ArrayList<Map<String,String>>();
		String taskKey = Endecode.base64Decode(Tools.filterNull(request.getParameter("taskKey")));
		String processId =  Endecode.base64Decode(Tools.filterNull(request.getParameter("processId")));
		try {
			DoTaskUCC doTaskUCC = (DoTaskUCC) getBean("pcs_doTaskUCC");
			List<Map<String, String>> doTaskfillFormList = doTaskUCC.queryDoTaskfillFormList(taskKey, processId);
			for(int i=0;i<doTaskfillFormList.size();i++){
				Map<String, String> rowmap =doTaskfillFormList.get(i);
				String fromType =rowmap.get("FROMTYPE").toString();
				String url=rowmap.get("FORMURL").toString();
				//表单类型（0：url,1：实体）
				if(fromType.equals("0")){
					
				}else{
					if(null==rowmap.get("DATAID")){
						String dataid = UUIDGenerator.getUUID();
						url=url+"&pageset.pageType=1&dataid="+dataid+"&processid="+processId+"&pcsfromcfgid="+rowmap.get("PCSFROMCFGID")+"&informant="+user.getUserId()+"&fromtablename="+rowmap.get("FROMTABLENAME");
					}else{
						url=url+"&pageset.pageType=2&dataid="+rowmap.get("DATAID")+"&pcsfromcfgid="+rowmap.get("PCSFROMCFGID")+"&completedformid="+rowmap.get("COMPLETEDFORMID");
					}
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("pcsfromcfgid", rowmap.get("PCSFROMCFGID"));
				map.put("formname", rowmap.get("FORMNAME"));
				map.put("url", url);
				doFillFromList.add(map);
			}
			request.setAttribute("doFillFromList", doFillFromList);
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "doTaskfillFormList";
	}
	
	/**
	 * 
	 * @方法名称: findCompletedFormList
	 * @描述：TODO(已填写表单列表) 
	 * @返回值类型： String  
	 * @return
	 */
	public String findCompletedFormList(){
		List<Map<String,String>> fillFromList = new  ArrayList<Map<String,String>>();
		String processId =  Endecode.base64Decode(Tools.filterNull(request.getParameter("processId")));
		try {
			DoTaskUCC doTaskUCC = (DoTaskUCC) getBean("pcs_doTaskUCC");
			List<Map<String, String>> completedFormList = doTaskUCC.queryCompletedFormList(processId);
			for(int i=0;i<completedFormList.size();i++){
				Map<String, String> rowmap =completedFormList.get(i);
				String fromType =rowmap.get("FROMTYPE").toString();
				String url=rowmap.get("FORMURL").toString();
				//表单类型（0：url,1：实体）
				if(fromType.equals("0")){
					
				}else{
					url=url+"&pageset.pageType=0&dataid="+rowmap.get("DATAID")+"&processid="+processId+"&pcsfromcfgid="+rowmap.get("PCSFROMCFGID")+"&informant="+user.getUserId()+"&fromtablename="+rowmap.get("FROMTABLENAME");
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("formname", rowmap.get("FORMNAME")+"("+rowmap.get("TASKNAME")+")");
				map.put("url", url);
				fillFromList.add(map);
			}
			
			request.setAttribute("fillFromList", fillFromList);
		} catch (Exception e) {
			 BkmsException he = new BkmsException(new StringBuffer().append(getClass().getName()).append("错误:").toString(), e, this.getClass());
	         this.addActionError(he.getFlag()+he.getCause().getMessage());
		}
		return "completedFormList";
	}
	
}
