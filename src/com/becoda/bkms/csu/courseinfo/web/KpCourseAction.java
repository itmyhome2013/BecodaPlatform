package com.becoda.bkms.csu.courseinfo.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.web.GenericAction;
import com.becoda.bkms.csu.courseinfo.pojo.KpLessonManageInfo;
import com.becoda.bkms.csu.courseinfo.ucc.IKpCourseUCC;
import com.becoda.bkms.pcs.util.DateUtil;
import com.becoda.bkms.util.BkmsContext;

public class KpCourseAction extends GenericAction {
	/**
	 * 首次进入页面
	 * @return
	 * @throws BkmsException
	 */
	public String toList()throws BkmsException{
		return "toList";
	}
	/**
	 * 分页查询
	 * @return
	 * @throws BkmsException
	 */
	public String queryCourseInfoList() throws BkmsException{
		IKpCourseUCC ucc = (IKpCourseUCC) BkmsContext.getBean("csu_KpCourseUCC");

		String lessonId = request.getParameter("lessonId");
		KpLessonManageInfo info = new KpLessonManageInfo();
		info.setLessonId(lessonId);
		Map map = ucc.queryCourseInfoList(page, rows, info);
		for (KpLessonManageInfo po : (List<KpLessonManageInfo>)map.get("rows")) {
			if(StringUtils.isNotBlank(po.getLessonClassIficationmethod())){//课程分类方式处理
				String[] methods = StringUtils.split(po.getLessonClassIficationmethod(),",");
				String method = "";
				for (int i = 0; i < methods.length; i++) {
					int metho = Integer.parseInt(methods[i]);
					if(i == methods.length-1){
						switch (metho) {
						case 1:
							method += "学科专业";
							break;
						case 2:
							method += "科普类型";
							break;
						case 3:
							method += "适合人群";
							break;
						default:
							method += "学科专业";
							break;
						}
					}else{
						switch (metho) {
						case 1:
							method += "学科专业,";
							break;
						case 2:
							method += "科普类型,";
							break;
						case 3:
							method += "适合人群,";
							break;
						default:
							method += "学科专业,";
							break;
						}
					}
				}
				po.setLessonClassIficationmethod(method);
			}
			if(StringUtils.isNotBlank(po.getLessonShape())){//课程形式处理
				int shape = Integer.parseInt(po.getLessonShape());
				switch (shape) {
				case 1:
					po.setLessonShape("形式一");
					break;
				case 2:
					po.setLessonShape("形式二");
					break;
				case 3:
					po.setLessonShape("形式三");
					break;
				default:
					po.setLessonShape("形式一");
					break;
				}
			}
			if(StringUtils.isNotBlank(po.getLessonSource())){//课程来源处理
				int type = Integer.parseInt(po.getLessonSource());
				switch (type) {
				case 1:
					po.setLessonSource("自行录制");
					break;
				case 2:
					po.setLessonSource("购买");
					break;
				case 3:
					po.setLessonSource("其他");
					break;
				default:
					po.setLessonSource("自行录制");
					break;
				}
			}
			if(StringUtils.isNotBlank(po.getLessonType())){//课程类型处理
				int type = Integer.parseInt(po.getLessonType());
				switch (type) {
				case 1:
					po.setLessonType("线上");
					break;
				case 2:
					po.setLessonType("线下");
					break;
				default:
					po.setLessonType("线上");
					break;
				}
			}
		}

		//JSONArray jsonArray = JSONArray.fromObject(isProblemList);
		JSONObject jo = JSONObject.fromObject(map);

		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存
	 * @return
	 * @throws BkmsException
	 */
	public String saveCourseInfo() throws BkmsException{
		IKpCourseUCC ucc = (IKpCourseUCC) BkmsContext.getBean("csu_KpCourseUCC");
		/*
		 								1. 输入
			lessonName					1). 课程名称     必填  <=40 （备注：所有数字表示的是汉字个数）
			lessonNum					2). 课程编号     必填  编码规则  自动生成
			lessonClassIficationmethod	3). 课程分类方式  1.学科专业；2.科普类型；3.适合人群   甲方提供  必填   多选
			lessonShape					4). 课程形式       甲方提供  必填  单选 
			lessonDescription			5). 课程简介       必填   300以内
			isOpen						6). 是否公开       必填   单选  默认是  （备注：如选择不公开，只能本站点或分校可以学习此课程）
			lessonDuration				7). 课程时长       必填  分钟  限制数字  200以内
			lessonHours					8). 课程学时       选填  1位数字   45分钟1学分 超过20分钟+1分
			lessonSource				9). 课件来源     自行录制   购买   其他   甲方提供  必填 
			lessonFormSource			9a). 转载来源    选择 转载时 必填  <=100
			lectureSpecialist			10) . 讲座专家     必填，如没有需填“无”   <=40   输入专家名字后，可联想匹配师资库，进行选择
			lessonType					11). 课程类型   线上  线下   必填 单选
										12). 课件附件单独上传 多条 (上述基本信息保存后添加，课程类型为线下则无课件上传，ppt、word、pdf 等学习材料上传到班级空间里)
			uploadTime					13）。上传时间，系统默认
			coursewareDevelopTime		14）。 课件开发时间：加日历选择，____年____月。（默认为上传时间）
										页面上除了要有“提交”等按钮，还要有“保存草稿”按钮，或系统定时自动缓存
										固定字段要留出扩展项
										选择填入的要留出扩展项
										
										2. 输出
										1). 除6）是否公开、13）。上传时间、14）。 课件开发时间不显示，其他显示
										2). 选课人数  （学习结束人数）
										3). 点击人数

		*/
		String lessonName = request.getParameter("lessonName");
		String lessonNum = request.getParameter("lessonNum");
		String lessonClassIficationmethod = request.getParameter("lessonClassIficationmethod");
		String lessonShape = request.getParameter("lessonShape");
		String lessonDescription = request.getParameter("lessonDescription");
		String isOpen = request.getParameter("isOpen");
		String lessonDuration = request.getParameter("lessonDuration");
		String lessonHours = request.getParameter("lessonHours");
		String lessonSource = request.getParameter("lessonSource");
		String lessonFormSource = request.getParameter("lessonFormSource");
		String lectureSpecialist = request.getParameter("lectureSpecialist");
		String lessonType = request.getParameter("lessonType");
		String uploadTime = request.getParameter("uploadTime");
		String coursewareDevelopTime = request.getParameter("coursewareDevelopTime");
		
		KpLessonManageInfo po = new KpLessonManageInfo();
		po.setLessonName(lessonName);
		po.setLessonNum(lessonNum);
		po.setLessonClassIficationmethod(lessonClassIficationmethod);
		po.setLessonShape(lessonShape);
		po.setLessonDescription(lessonDescription);
		po.setIsOpen(isOpen);
		po.setLessonDuration(Long.parseLong(lessonDuration));
		po.setLessonHours(Byte.parseByte(lessonHours));
		po.setLessonSource(lessonSource);
		po.setLessonFormSource(lessonFormSource);
		po.setLectureSpecialist(lectureSpecialist);
		po.setLessonType(lessonType);
		po.setCoursewareDevelopTime(coursewareDevelopTime);
		po.setUploadTime(uploadTime);
		
		Date currentDate = new Date();
		po.setCreateTime(DateUtil.date2Str(currentDate, DateUtil.datetimeFormat));
		po.setIsdel("0");
		ucc.saveCourseInfo(po);
		
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 真实删除
	 * @return
	 * @throws BkmsException
	 */
	public String deleteTrueCourseInfo() throws BkmsException{
		IKpCourseUCC ucc = (IKpCourseUCC) BkmsContext.getBean("csu_KpCourseUCC");
		String genericIds = request.getParameter("genericIds");
		String[] lessonIds = StringUtils.split(genericIds, ",");
		for (int i = 0; i < lessonIds.length; i++) {
			KpLessonManageInfo po = ucc.getCourseInfoById(lessonIds[i]);
			ucc.deleteCourseInfo(po);
		}
		
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 逻辑删除(更新删除状态位)
	 * @return
	 * @throws BkmsException
	 */
	public String deleteCourseInfo() throws BkmsException{
		IKpCourseUCC ucc = (IKpCourseUCC) BkmsContext.getBean("csu_KpCourseUCC");
		
		String genericIds = request.getParameter("genericIds");
		String[] lessonIds = StringUtils.split(genericIds, ",");
		for (int i = 0; i < lessonIds.length; i++) {
			KpLessonManageInfo po = ucc.getCourseInfoById(lessonIds[i]);
			//Date currentDate = new Date();
			//po.setUpdateTime(currentDate);
			po.setIsdel("1");
			ucc.updateCourseInfo(po);
		}
		
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 更新
	 * @return
	 * @throws BkmsException
	 */
	public String updateCourseInfo() throws BkmsException{
		IKpCourseUCC ucc = (IKpCourseUCC) BkmsContext.getBean("csu_KpCourseUCC");
		
		String lessonId = request.getParameter("lessonId");
		String lessonName = request.getParameter("lessonName");
		String lessonNum = request.getParameter("lessonNum");
		String lessonClassIficationmethod = request.getParameter("lessonClassIficationmethod");
		String lessonShape = request.getParameter("lessonShape");
		String lessonDescription = request.getParameter("lessonDescription");
		String isOpen = request.getParameter("isOpen");
		String lessonDuration = request.getParameter("lessonDuration");
		String lessonHours = request.getParameter("lessonHours");
		String lessonSource = request.getParameter("lessonSource");
		String lessonFormSource = request.getParameter("lessonFormSource");
		String lectureSpecialist = request.getParameter("lectureSpecialist");
		String lessonType = request.getParameter("lessonType");
		String uploadTime = request.getParameter("uploadTime");
		String coursewareDevelopTime = request.getParameter("coursewareDevelopTime");
		
		KpLessonManageInfo po = ucc.getCourseInfoById(lessonId);
		po.setLessonName(lessonName);
		po.setLessonNum(lessonNum);
		po.setLessonClassIficationmethod(lessonClassIficationmethod);
		po.setLessonShape(lessonShape);
		po.setLessonDescription(lessonDescription);
		po.setIsOpen(isOpen);
		po.setLessonDuration(Long.parseLong(lessonDuration));
		po.setLessonHours(Byte.parseByte(lessonHours));
		po.setLessonSource(lessonSource);
		po.setLessonFormSource(lessonFormSource);
		po.setLectureSpecialist(lectureSpecialist);
		po.setLessonType(lessonType);
		po.setCoursewareDevelopTime(coursewareDevelopTime);
		po.setUploadTime(uploadTime);
		
		Date currentDate = new Date();
		po.setUpdateTime(DateUtil.date2Str(currentDate, DateUtil.datetimeFormat));
		ucc.updateCourseInfo(po);
		
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通过主键获得对象
	 * @return
	 * @throws Exception
	 */
	public String getCourseInfoById() throws Exception{
		IKpCourseUCC ucc = (IKpCourseUCC) BkmsContext.getBean("csu_KpCourseUCC");
		String lessonId = request.getParameter("lessonId");
		KpLessonManageInfo po = ucc.getCourseInfoById(lessonId);
		
		JSONObject jo = JSONObject.fromObject(po);
		httpResponse.setCharacterEncoding("UTF-8");
		httpResponse.setContentType("text/html;charset=UTF-8");
		try {
			httpResponse.getWriter().print(jo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
