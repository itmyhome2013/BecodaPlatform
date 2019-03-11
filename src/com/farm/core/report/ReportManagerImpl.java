package com.farm.core.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;

public class ReportManagerImpl implements ReportManagerInter {

	public String path;
	public String opath;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void generate(String fileName, Map<String, Object> parameter)
			throws ReportException {
		XLSTransformer transformer = new XLSTransformer();
		Workbook wb;
		try {
			//模板路径
			String classPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath(path + File.separator + fileName);
			//输出路径
			String classPath2 = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/") + opath + File.separator + fileName;

			wb = transformer.transformXLS(new FileInputStream(new File(
					classPath)), parameter);
			File file = new File(classPath2);
			file.createNewFile();
			wb.write(new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}

	public String getOpath() {
		return opath;
	}

	public void setOpath(String opath) {
		this.opath = opath;
	}

	@Override
	public String getReportPath(String fileName) {
		String classPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/") + opath + File.separator + fileName;
		return classPath;
	}

}
