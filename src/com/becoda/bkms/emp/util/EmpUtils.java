package com.becoda.bkms.emp.util;

import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

import com.becoda.bkms.cache.SysCacheTool;
import com.becoda.bkms.common.exception.BkmsException;
import com.becoda.bkms.common.exception.RollbackableException;
import com.becoda.bkms.common.pojo.vo.User;
import com.becoda.bkms.emp.EmpConstants;
import com.becoda.bkms.emp.pojo.bo.Person;
import com.becoda.bkms.emp.pojo.bo.PersonBO;
//import com.becoda.bkms.emp.pojo.bo.TurnPostBO;

import com.becoda.bkms.util.HrmsLog;
import com.becoda.bkms.util.Tools;

/**
 * iCITIC HR User: Jair.Shaw Date: 2015-4-24 Time: 13:29:35
 */
public class EmpUtils {
	public static void log(Object o, User user, String desc)
			throws RollbackableException {
		String userId = user == null ? "" : user.getUserId();
		HrmsLog
				.addOperLog(o.getClass(), userId, EmpConstants.MODULE_NAME,
						desc);
	}

	public static String toString(String[] ids) {
		if (ids == null || ids.length == 0) {
			return "";
		}
		StringBuffer idstr = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			String id = ids[i];
			idstr.append(",").append(id);
		}
		return idstr.substring(1);
	}

	public static String toString(PersonBO[] ids) {
		if (ids == null || ids.length == 0) {
			return "";
		}
		StringBuffer idstr = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			PersonBO id = ids[i];
			idstr.append(",").append(id.getPersonId());
		}
		return idstr.substring(1);
	}

	public static String approval(String code) {

		switch (Integer.parseInt(code)) {
		case 0:
			return "审批中";
		case 1:
			return "审批通过";
		case 2:
			return "审批未通过";

		}
		return null;
	}

	public static String reverApp(String text) {

		if (text != null && text.equals("6003600022"))
			return "0";
		else if (text != null && text.equals("6003600023"))
			return "1";
		else if (text != null && text.equals("6003600024"))
			return "2";
		else
			return null;

	}
//	keyRow: key所在行(从0开始), startRow:从那行开始读取数据,


}